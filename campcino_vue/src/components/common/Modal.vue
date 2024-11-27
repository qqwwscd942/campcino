<!-- src/components/common/Modal.vue -->
<template>
  <transition name="modal">
    <div
      v-if="isOpen"
      class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50"
      @click.self="close"
      aria-modal="true"
      role="dialog"
    >
      <div
        ref="modal"
        class="bg-white rounded-lg shadow-lg w-11/12 md:w-3/4 lg:w-3/4 xl:w-2/3 max-h-[90vh] overflow-y-auto transform transition-all duration-300 p-6 relative"
        @keydown.esc="close"
        tabindex="0"
      >
        <!-- 닫기 버튼 -->
        <button
          @click="close"
          class="absolute top-4 right-4 text-gray-500 hover:text-gray-700"
          aria-label="Close modal"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="h-6 w-6"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            aria-hidden="true"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M6 18L18 6M6 6l12 12"
            />
          </svg>
        </button>

        <!-- 모달 내용 -->
        <div class="modal-content">
          <slot></slot>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: "Modal",
  props: {
    isOpen: {
      type: Boolean,
      required: true,
    },
  },
  emits: ["close"],
  methods: {
    close() {
      this.$emit("close");
    },
    focusModal() {
      this.$nextTick(() => {
        if (this.$refs.modal) {
          this.$refs.modal.focus();
        }
      });
    },
  },
  watch: {
    isOpen(newVal) {
      if (newVal) {
        this.focusModal();
        document.body.style.overflow = "hidden"; // 스크롤 방지
      } else {
        document.body.style.overflow = ""; // 스크롤 복원
      }
    },
  },
  beforeUnmount() {
    document.body.style.overflow = ""; // 클린업
  },
};
</script>

<style scoped>
.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.3s ease;
}
.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}

.modal-content {
  /* 위아래 여백 추가 */
  margin-top: 2rem;
  margin-bottom: 2rem;
}
</style>
