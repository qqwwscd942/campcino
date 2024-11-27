<!-- src/App.vue -->
<template>
  <div id="app">
    <!-- 기존 router-view -->
    <router-view v-slot="{ Component, route }">
      <keep-alive include="CampSearchView">
        <component :is="Component" :key="route.fullPath" />
      </keep-alive>
    </router-view>

    <!-- 모달 컴포넌트 -->
    <Modal :isOpen="isModalOpen" @close="closeModal">
      <CampDetailView v-if="isModalOpen" :campId="selectedCampId" />
    </Modal>
  </div>
  <bot/>
</template>

<script>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useAuthStore } from "@/stores/auth";
import Modal from "@/components/common/Modal.vue";
import CampDetailView from "@/views/camp/CampDetailView.vue";
import bot from "@/views/chattbot/ChatbotPopup.vue";


export default {
  name: "App",
  components: {
    Modal,
    CampDetailView,
    bot
  },
  setup() {
    const authStore = useAuthStore();
    console.log("Calling initializeAuth..."); // 디버깅 메시지
    authStore.initializeAuth(); // 앱 로드 시 인증 초기화
    const route = useRoute();
    const router = useRouter();
    const isModalOpen = ref(false);
    const selectedCampId = ref(null);


    const openModal = (campId) => {
      selectedCampId.value = campId;
      isModalOpen.value = true;
    };

    const closeModal = () => {
      isModalOpen.value = false;
      selectedCampId.value = null;
      router.back(); // 뒤로 가기
    };

    // 라우트 감시
    watch(
      () => route.name,
      (newName, oldName) => {
        if (newName === "CampDetail") {
          openModal(Number(route.params.id));
        } else if (oldName === "CampDetail") {
          isModalOpen.value = false;
          selectedCampId.value = null;
        }
      },
      { immediate: true }
    );

    return {
      isModalOpen,
      selectedCampId,
      closeModal,
    };
  },
};
</script>
