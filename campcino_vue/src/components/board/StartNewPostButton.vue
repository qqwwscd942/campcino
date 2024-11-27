<!-- src/components/StartNewPostButton.vue -->
<template>
  <div class="flex px-4 py-3">
    <router-link to="/boards/create" class="flex-1">
      <button
        class="flex-1 min-w-[84px] max-w-[480px] flex items-center justify-center h-12 px-5 bg-[#F4EFE6] text-[#1C160C] text-base font-bold rounded"
      >
        글쓰기
      </button>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "StartNewPostButton",
};
</script>

<style scoped>
/* 필요에 따라 스타일을 추가하세요 */
</style>
