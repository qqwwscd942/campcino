// src/stores/auth.js
import { defineStore } from "pinia";
import { loginUser, logoutUser, refreshToken, getUserInfo } from "@/api"; // API 파일에서 가져오기

export const useAuthStore = defineStore("auth", {
  state: () => ({
    isAuthenticated: false, // 로그인 상태
    user: null, // 사용자 정보 (이름, 이메일 등)
  }),
  actions: {
    // 초기화: 리프레시 토큰으로 인증 상태 확인
    // async initializeAuth() {
    //   console.log("Initializing Auth..."); // 디버깅 메시지
    //   const token = localStorage.getItem("accessToken");
    //   if (!token) {
    //     console.log("No token found in localStorage");
    //     this.isAuthenticated = false;
    //     this.user = null;
    //     return;
    //   }
    //   try {
    //     console.log("Token found, refreshing access token...");
    //     await this.refreshAccessToken(); // 토큰 갱신
    //     console.log("Access token refreshed, fetching user info...");
    //     await this.fetchUserInfo(); // 사용자 정보 가져오기
    //     this.isAuthenticated = true;
    //     console.log("User authenticated:", this.user);
    //   } catch (error) {
    //     console.error("Authentication initialization failed:", error);
    //     this.isAuthenticated = false;
    //     this.user = null;
    //     localStorage.removeItem("accessToken"); // 실패 시 토큰 제거
    //   }
    // },
    async initializeAuth() {
      const token = localStorage.getItem("accessToken");
      if (!token) {
        this.isAuthenticated = false;
        this.user = null;
        return;
      }

      try {
        console.log("Restoring session...");
        await this.fetchUserInfo(); // 사용자 정보 가져오기
        this.isAuthenticated = true;
      } catch (error) {
        console.error("Session restoration failed:", error);
        this.logoutUser();
      }
    },


    // 로그인 처리
    async loginUser(credentials) {
      try {
        const accessToken = await loginUser(credentials);
        localStorage.setItem("accessToken", accessToken); // 액세스 토큰 로컬 스토리지에 저장
        await this.fetchUserInfo(); // 사용자 정보 업데이트
        this.isAuthenticated = true;
      } catch (error) {
        console.error("로그인 실패:", error.response?.data?.message || "로그인에 실패했습니다.");
        throw new Error(error);
      }
    },

    // 로그아웃 처리
    async logoutUser() {
      try {
        localStorage.removeItem("accessToken"); // 로컬 스토리지에서 액세스 토큰 제거
        this.isAuthenticated = false;
        this.user = null; // 사용자 정보 초기화
      } catch (error) {
        console.error("로그아웃 실패:", error);
        throw error;
      }
    },

    // 액세스 토큰 갱신
    async refreshAccessToken() {
      try {
        const newAccessToken = await refreshToken();
        localStorage.setItem("accessToken", newAccessToken); // 새 액세스 토큰 저장
        this.isAuthenticated = true;
      } catch (error) {
        console.error("토큰 갱신 실패:", error);
        this.isAuthenticated = false;
        throw error;
      }
    },

    // 사용자 정보 가져오기
    async fetchUserInfo() {
      try {
        const userInfo = await getUserInfo(); // API를 통해 사용자 정보 가져오기
        this.user = userInfo; // 상태에 사용자 정보 저장
      } catch (error) {
        console.error("사용자 정보 가져오기 실패:", error);
        this.user = null; // 가져오기 실패 시 초기화
        throw error;
      }
    },

    // 로컬 스토리지에서 액세스 토큰 가져오기
    async getAccessToken() {
      return localStorage.getItem("accessToken");
    },
  },
});
