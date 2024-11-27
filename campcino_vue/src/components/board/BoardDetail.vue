<template>
  <Header />
  <div class="board-detail-container" v-if="board">
    <h2 class="text-2xl font-bold mb-4">{{ board.boardTitle }}</h2>
    <div class="flex items-center justify-between mb-2">
      <span class="text-gray-600">작성자: {{ board.userId }}</span>
      <span class="text-gray-600">조회수: {{ board.boardView }}</span>
    </div>
    <p class="text-gray-700 mb-4">{{ board.boardContent }}</p>
    <div v-if="board.imgUrl" class="mb-4">
      <img
        :src="board.imgUrl"
        alt="게시글 이미지"
        class="w-auto h-auto rounded"
      />
    </div>
    <p class="text-gray-500">작성일: {{ formattedDate }}</p>

    <div class="mt-4 flex space-x-4">
      <button
        @click="goBackToList"
        class="px-4 py-2 bg-gray-500 text-white rounded"
      >
        목록
      </button>
      <button
        v-if="isOwner"
        @click="editPost"
        class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
      >
        수정
      </button>
      <button
        v-if="isOwner"
        @click="deletePost"
        class="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
      >
        삭제
      </button>
    </div>

    <!-- 댓글 목록 -->
    <CommentList ref="commentList" :boardId="board.boardId" />

    <!-- 댓글 작성 폼 -->
    <CommentForm :boardId="board.boardId" @commentSubmitted="refreshComments" />
  </div>
  <div v-else>
    <p>게시글을 로드 중입니다...</p>
  </div>
</template>

<script>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getBoardById, deleteBoardById } from "@/api";
import { useAuthStore } from "@/stores/auth";
import CommentList from "./CommentList.vue";
import CommentForm from "./CommentForm.vue";
import Header from "@/components/common/Header.vue";

export default {
  name: "BoardDetail",
  components: {
    Header,
    CommentList,
    CommentForm,
  },
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  setup(props) {
    const router = useRouter();
    const board = ref(null);
    const formattedDate = ref("");
    const commentList = ref(null);
    const authStore = useAuthStore();

    const fetchBoard = async () => {
      try {
        const response = await getBoardById(props.id);
        board.value = response;
        formattedDate.value = new Date(
          response.boardCreatedAt
        ).toLocaleString();
      } catch (error) {
        console.error("게시글을 가져오는 중 오류가 발생했습니다:", error);
      }
    };

    // 댓글 목록 새로고침
    const refreshComments = () => {
      if (commentList.value) {
        commentList.value.fetchComments(1); // 댓글 목록을 즉시 새로고침
      }
    };

    const editPost = () => {
      router.push(`/boards/${props.id}/edit`);
    };

    const deletePost = async () => {
      if (!confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
        return;
      }

      try {
        await deleteBoardById(props.id);
        alert("게시글이 성공적으로 삭제되었습니다!");
        router.push("/boards");
      } catch (error) {
        console.error("게시글 삭제 중 오류가 발생했습니다:", error);
        alert("게시글 삭제에 실패했습니다.");
      }
    };

    const goBackToList = () => {
      router.push("/boards");
    };

    const isOwner = computed(
      () => board.value?.userId === authStore.user.userId
    );

    onMounted(() => {
      fetchBoard();
    });

    return {
      board,
      formattedDate,
      commentList,
      refreshComments,
      goBackToList,
      isOwner,
      editPost,
      deletePost,
    };
  },
};
</script>

<style scoped>
.board-detail-container {
  padding: 1rem;
}
.board-detail-container img {
  max-height: 400px;
  object-fit: cover;
}
</style>
