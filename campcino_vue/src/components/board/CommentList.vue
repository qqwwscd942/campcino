<template>
  <div class="comment-list-container">
    <h3 class="text-lg font-bold mb-4">댓글</h3>

    <div v-if="comments.items.length === 0">
      <p>작성된 댓글이 없습니다.</p>
    </div>
    <div v-else>
      <div
        v-for="comment in comments.items"
        :key="comment.commentId"
        class="comment-item mb-4 p-4 border rounded"
      >
        <div class="flex justify-between mb-2">
          <span class="font-semibold">
            {{ comment.userId }}
            <!-- "수정됨" 표시 -->
            <span v-if="isEdited(comment.commentId)" class="text-muted">(수정됨)</span>
          </span>
          <span class="text-sm text-gray-500">{{ formatDate(comment.commentCreatedAt) }}</span>
        </div>
        <p v-if="!isEditing[comment.commentId]">{{ comment.commentContent }}</p>
        <textarea
          v-else
          v-model="editContent[comment.commentId]"
          class="w-full border rounded p-2 mb-2"
          rows="3"
        ></textarea>
        <div v-if="isOwner(comment.userId)">
          <button
            v-if="!isEditing[comment.commentId]"
            @click="startEditing(comment)"
            class="px-3 py-1 bg-yellow-500 text-white rounded mr-2"
          >
            수정
          </button>
          <button
            v-if="!isEditing[comment.commentId]"
            @click="confirmDelete(comment)"
            class="px-3 py-1 bg-red-500 text-white rounded"
          >
            삭제
          </button>
          <button
            v-if="isEditing[comment.commentId]"
            @click="submitEdit(comment)"
            class="px-3 py-1 bg-green-500 text-white rounded mr-2"
          >
            저장
          </button>
          <button
            v-if="isEditing[comment.commentId]"
            @click="cancelEditing(comment)"
            class="px-3 py-1 bg-gray-500 text-white rounded"
          >
            취소
          </button>
        </div>
      </div>

      <!-- 페이징 처리 -->
      <div class="pagination flex justify-center mt-4">
        <button
          @click="fetchComments(currentPage - 1)"
          :disabled="currentPage === 1"
          class="px-3 py-1 mr-2 bg-gray-200 rounded disabled:opacity-50"
        >
          이전
        </button>
        <span>페이지 {{ currentPage }} / {{ totalPages }}</span>
        <button
          @click="fetchComments(currentPage + 1)"
          :disabled="currentPage === totalPages"
          class="px-3 py-1 ml-2 bg-gray-200 rounded disabled:opacity-50"
        >
          다음
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, watch } from "vue";
import { getCommentsByBoardId, updateComment, deleteComment } from "@/api"; // API 함수 임포트
import { useAuthStore } from "@/stores/auth";

export default {
  name: "CommentList",
  props: {
    boardId: {
      type: Number,
      required: true,
    },
  },
  setup(props) {
    const authStore = useAuthStore();
    const comments = ref({
      items: [],
      currentPage: 1,
      totalPages: 1,
      totalItems: 0,
    });
    const currentPage = ref(1);
    const totalPages = ref(1);

    const isEditing = ref({}); // { commentId: true/false }
    const editContent = ref({}); // { commentId: "edited content" }

    // 수정된 댓글이 "수정됨" 상태인지 확인
    const isEdited = (commentId) => {
      const editedComments = JSON.parse(localStorage.getItem('editedComments') || '[]');
      return editedComments.includes(commentId);
    };

    // 댓글 목록을 가져오는 함수
    const fetchComments = async (page = 1) => {
      if (page < 1 || page > totalPages.value) return;
      try {
        const response = await getCommentsByBoardId(props.boardId, page, 10);
        comments.value = response;
        currentPage.value = response.currentPage;
        totalPages.value = response.totalPages;
      } catch (error) {
        console.error("댓글을 가져오는 중 오류가 발생했습니다:", error);
      }
    };

    const formatDate = (dateString) => {
      const options = { year: "numeric", month: "short", day: "numeric", hour: "2-digit", minute: "2-digit" };
      return new Date(dateString).toLocaleDateString(undefined, options);
    };

    const startEditing = (comment) => {
      isEditing.value[comment.commentId] = true;
      editContent.value[comment.commentId] = comment.commentContent;
    };

    const cancelEditing = (comment) => {
      isEditing.value[comment.commentId] = false;
      delete editContent.value[comment.commentId];
    };

    const submitEdit = async (comment) => {
      const updatedContent = editContent.value[comment.commentId];
      if (!updatedContent.trim()) {
        alert("댓글 내용을 입력해주세요!");
        return;
      }

      try {
        const updatedCommentData = {
          commentId: comment.commentId,
          commentContent: updatedContent,
          userId: authStore.user.userId,
        };

        await updateComment(props.boardId, comment.commentId, updatedCommentData);
        alert("댓글이 성공적으로 수정되었습니다!");
        isEditing.value[comment.commentId] = false;
        delete editContent.value[comment.commentId];

        // 수정된 댓글을 localStorage에 저장
        const editedComments = JSON.parse(localStorage.getItem('editedComments') || '[]');
        if (!editedComments.includes(comment.commentId)) {
          editedComments.push(comment.commentId);
          localStorage.setItem('editedComments', JSON.stringify(editedComments));
        }

        fetchComments(currentPage.value);
      } catch (error) {
        console.error("댓글 수정 중 오류가 발생했습니다:", error);
        alert("댓글 수정에 실패했습니다. 다시 시도해주세요.");
      }
    };

    const confirmDelete = (comment) => {
      if (confirm("정말로 이 댓글을 삭제하시겠습니까?")) {
        deleteCommentById(comment);
      }
    };

    const deleteCommentById = async (comment) => {
      try {
        await deleteComment(props.boardId, comment.commentId);
        alert("댓글이 성공적으로 삭제되었습니다!");
        fetchComments(currentPage.value);
      } catch (error) {
        console.error("댓글 삭제 중 오류가 발생했습니다:", error);
        alert("댓글 삭제에 실패했습니다. 다시 시도해주세요.");
      }
    };

    const isOwner = (commentUserId) => {
      return authStore.user.userId === commentUserId;
    };

    onMounted(() => {
      fetchComments();
    });

    watch(() => props.boardId, () => {
      fetchComments(1);
    });

    return {
      comments,
      currentPage,
      totalPages,
      fetchComments,
      formatDate,
      isEditing,
      editContent,
      startEditing,
      cancelEditing,
      submitEdit,
      confirmDelete,
      isOwner,
      isEdited,
    };
  },
};
</script>

<style scoped>
.comment-list-container {
  margin-top: 2rem;
}

.comment-item {
  background-color: #f9f9f9;
}
</style>
