<template>
  <header class="flex items-center justify-between px-10 py-3 border-b border-[#F4EFE6]">
    <div class="flex items-center gap-8">
      <div class="w-15 h-16">
        <router-link to="/">
          <img
            src="@/assets/logo4_no.png"
            alt="logo"
            class="w-full h-full object-contain"
          />
        </router-link>
      </div>
    </div>

    <nav class="flex items-center gap-4">
      <router-link
        v-for="(link, index) in links"
        :key="index"
        :to="link.path"
        class="text-[#1C160C] hover:text-[#A18249] font-medium"
      >
        {{ link.name }}
      </router-link>
    </nav>

    <div class="flex items-center gap-8">
      <!-- 로그인 상태에 따라 버튼 변경 -->
      <template v-if="isAuthenticated">
        <span class="font-bold text-[#1C160C]">
          {{ user.userId }}님 환영합니다!
        </span>
        <router-link to="/profile">
          <button class="px-4 py-2 bg-[#F4EFE6] text-[#1C160C] font-bold">
            회원정보
          </button>
        </router-link>
        <button
          @click="handleLogout"
          class="px-4 py-2 bg-[#F4EFE6] text-[#1C160C] font-bold"
        >
          로그아웃
        </button>
      </template>
      <template v-else>
        <router-link to="/login">
          <button class="px-4 py-2 bg-blue-500 text-white font-bold">
            로그인
          </button>
        </router-link>
      </template>
    </div>
  </header>
</template>

<script>
import { useAuthStore } from "@/stores/auth";
import { computed } from "vue";

export default {
  name: "Header",
  setup() {
    const authStore = useAuthStore();
    const isAuthenticated = computed(() => authStore.isAuthenticated);
    const user = computed(() => authStore.user);

    const handleLogout = () => {
      authStore.logoutUser();
      location.reload(); // 로그아웃 후 새로고침
    };

    const links = [
      { name: "캠핑장 조회", path: "/search" },
      { name: "게시판", path: "/boards" },
      { name: "스토어", path: "/stores" },
      { name: "리뷰", path: "/reviews" },
    ];

    return { isAuthenticated, user, handleLogout, links };
  },
};
</script>

<style scoped>
/* 필요에 따라 스타일 추가 */
</style>
