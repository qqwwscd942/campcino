<!-- src/components/board/BoardList.vue -->
<template>
  <div class="board-list-container">
    <h2 class="text-2xl font-bold mb-4">커뮤니티 게시판</h2>

    <!-- 게시글 목록 -->
    <div v-if="posts.length > 0">
      <div v-for="board in posts" :key="board.boardId" class="border-b py-4">
        <router-link :to="`/boards/${board.boardId}`" class="text-xl font-semibold text-blue-600 hover:underline">
          {{ board.boardTitle }}
        </router-link>
        <p class="text-gray-600">{{ board.boardContent.substring(0, 100) }}...</p>
        <div class="flex justify-between text-sm text-gray-500 mt-2">
          <span>작성자: {{ board.userId }}</span>
          <span>조회수: {{ board.boardView }}</span>
        </div>
      </div>

      <!-- 페이지네이션 -->
      <div class="flex justify-center mt-4">
        <button
          @click="$emit('changePage', currentPage - 1)"
          :disabled="currentPage === 1"
          class="px-3 py-1 mr-2 bg-gray-200 rounded disabled:opacity-50"
        >
          이전
        </button>
        <span>페이지 {{ currentPage }} / {{ totalPages }}</span>
        <button
          @click="$emit('changePage', currentPage + 1)"
          :disabled="currentPage === totalPages"
          class="px-3 py-1 ml-2 bg-gray-200 rounded disabled:opacity-50"
        >
          다음
        </button>
      </div>
    </div>
    <div v-else>
      <p>게시글이 없습니다.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BoardList',
  props: {
    posts: {
      type: Array,
      required: true,
    },
    currentPage: {
      type: Number,
      required: true,
    },
    totalPages: {
      type: Number,
      required: true,
    },
  },
};
</script>

<style scoped>
.board-list-container {
  padding: 1rem;
}
</style>
