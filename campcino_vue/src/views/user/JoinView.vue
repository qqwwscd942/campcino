<template>
  <div
    class="relative flex w-full min-h-screen flex-col bg-white overflow-x-hidden"
    style="font-family: 'Noto Serif', 'Noto Sans', sans-serif"
  >
    <div class="layout-container flex h-full grow flex-col">
      <div class="px-40 flex flex-1 justify-center py-5">
        <div
          class="layout-content-container flex flex-col w-full max-w-[512px] py-5 flex-1"
        >
          <h1
            class="text-[#1C160C] text-2xl font-bold leading-tight tracking-[-0.015em] px-4 text-center pb-3 pt-5"
          >
            회원 가입
          </h1>
          <form @submit.prevent="handleSubmit" class="mt-6">
            <!-- 아이디 입력 -->
            <div class="mb-4">
              <label for="userId" class="block mb-2 font-semibold">아이디 입력</label>
              <input
                type="text"
                id="userId"
                v-model="form.userId"
                placeholder="아이디를 입력하세요"
                class="w-full p-2 border border-gray-300 rounded"
              />
            </div>
            <!-- 비밀번호 -->
            <div class="mb-4">
              <label for="userPassword" class="block mb-2 font-semibold">비밀번호</label>
              <input
                type="password"
                id="userPassword"
                v-model="form.userPassword"
                placeholder="비밀번호"
                class="w-full p-2 border border-gray-300 rounded"
              />
            </div>
            <!-- 비밀번호 확인 -->
            <div class="mb-4">
              <label for="confirmPassword" class="block mb-2 font-semibold">비밀번호 확인</label>
              <input
                type="password"
                id="confirmPassword"
                v-model="confirmPassword"
                placeholder="비밀번호 확인"
                class="w-full p-2 border border-gray-300 rounded"
              />
            </div>
            <!-- 휴대전화 -->
            <div class="mb-4">
              <label for="userPhone" class="block mb-2 font-semibold">휴대전화</label>
              <input
                type="text"
                id="userPhone"
                v-model="form.userPhone"
                placeholder="010-0000-0000"
                class="w-full p-2 border border-gray-300 rounded"
              />
            </div>
            <!-- 나이 -->
            <div class="mb-4">
              <label for="userAge" class="block mb-2 font-semibold">나이</label>
              <input
                type="number"
                id="userAge"
                v-model="form.userAge"
                placeholder="나이"
                class="w-full p-2 border border-gray-300 rounded"
              />
            </div>
            <!-- 이메일 -->
            <div class="mb-4">
              <label for="userEmail" class="block mb-2 font-semibold">이메일</label>
              <input
                type="email"
                id="userEmail"
                v-model="form.userEmail"
                placeholder="example@domain.com"
                class="w-full p-2 border border-gray-300 rounded"
              />
            </div>
            <!-- 성별 -->
            <div class="mb-4">
              <label for="userGender" class="block mb-2 font-semibold">성별</label>
              <select
                id="userGender"
                v-model="form.userGender"
                class="w-full p-2 border border-gray-300 rounded"
              >
                <option value="M">남성</option>
                <option value="F">여성</option>
              </select>
            </div>
            <!-- 회원가입 버튼 -->
            <button
              type="submit"
              class="w-full py-2 mt-4 bg-green-600 text-white font-bold rounded"
            >
              회원 가입
            </button>
            <!-- 로그인 페이지로 이동 -->
            <p
              class="text-blue-500 text-sm mt-4 cursor-pointer text-center"
              @click="goToLogin"
            >
              이미 계정이 있으신가요? 로그인하기
            </p>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

export default {
  name: "UserJoin",
  setup() {
    const router = useRouter();
    const form = ref({
      userId: "",
      userPassword: "",
      userPhone: "",
      userAge: null,
      userEmail: "",
      userGender: "M",
    });
    const confirmPassword = ref("");

    const handleSubmit = async () => {
  if (form.value.userPassword !== confirmPassword.value) {
    alert("비밀번호가 일치하지 않습니다.");
    return;
  }
  try {
    const response = await axios.post("/user/join", form.value, {
      headers: {
        "Content-Type": "application/json", // JSON 형식으로 요청
      },
    });
    alert(response.data); // 서버 응답 메시지
    router.push("/login");
  } catch (error) {
    console.error("회원가입 실패:", error.response || error.message);
    alert("회원가입에 실패했습니다. 정보를 확인해주세요.");
  }
};

    const goToLogin = () => {
      router.push("/login");
    };

    return {
      form,
      confirmPassword,
      handleSubmit,
      goToLogin,
    };
  },
};
</script>

<style scoped>
/* 스타일 정의 필요 시 추가 */
</style>
