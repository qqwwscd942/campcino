<template>
  <div class="comment-form">
    <h3 class="text-lg font-bold mb-2">댓글 작성</h3>
    <textarea
      v-model="commentContent"
      class="w-full border rounded p-2 mb-2"
      placeholder="댓글을 작성해주세요"
      rows="3"
    ></textarea>
    <button
      @click="submitComment"
      class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
    >
      작성
    </button>
  </div>
</template>

<script>
import { ref } from "vue";
import { useAuthStore } from "@/stores/auth"; // 사용자 인증 정보
import { createComment } from "@/api"; // createComment 함수 임포트

export default {
  name: "CommentForm",
  props: {
    boardId: {
      type: Number,
      required: true,
    },
  },
  emits: ["commentSubmitted"], // 이벤트 선언
  setup(props, { emit }) {
    const commentContent = ref("");
    const authStore = useAuthStore(); // 로그인한 사용자 정보 가져오기

    const submitComment = async () => {
      if (!commentContent.value.trim()) {
        alert("댓글 내용을 입력해주세요!");
        return;
      }

      try {
        const userId = authStore.user.userId; // 로그인한 사용자의 ID
        if (!userId) {
          alert("로그인이 필요합니다!");
          return;
        }

        // 댓글 API 요청
        await createComment(props.boardId, {
          userId,
          boardId: props.boardId,
          commentContent: commentContent.value,
        });

        alert("댓글이 성공적으로 작성되었습니다!");
        commentContent.value = ""; // 입력 필드 초기화

        // 댓글 작성 후 부모 컴포넌트에 이벤트 전달하여 댓글 목록을 새로고침
        emit("commentSubmitted"); // 부모 컴포넌트에서 댓글 목록 갱신하도록 처리
      } catch (error) {
        console.error("댓글 작성 중 오류가 발생했습니다:", error);
        alert("댓글 작성에 실패했습니다. 다시 시도해주세요.");
      }
    };

    return {
      commentContent,
      submitComment,
      authStore,
    };
  },
};
</script>

<style scoped>
.comment-form {
  margin-top: 2rem;
}
</style>
