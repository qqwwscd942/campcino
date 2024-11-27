<!-- src/components/camp/ReviewForm.vue -->
<template>
  <div class="review-form-container">
    <h2 class="text-2xl font-semibold text-primary mb-4">리뷰 작성</h2>

    <!-- 별점 시스템 -->
    <div class="flex items-center mb-4">
      <span class="mr-2 text-lg font-medium text-primary">별점:</span>
      <div class="flex">
        <span
          v-for="star in 5"
          :key="star"
          @click="setRating(star)"
          class="cursor-pointer"
        >
          <svg
            v-if="star <= currentRating"
            xmlns="http://www.w3.org/2000/svg"
            class="h-8 w-8 text-yellow-500"
            viewBox="0 0 20 20"
            fill="currentColor"
          >
            <path
              d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.976a1 1 0 00.95.69h4.179c.969 0 1.371 1.24.588 1.81l-3.376 2.455a1 1 0 00-.364 1.118l1.287 3.976c.3.921-.755 1.688-1.54 1.118L10 13.347l-3.376 2.455c-.785.57-1.838-.197-1.54-1.118l1.287-3.976a1 1 0 00-.364-1.118L2.098 9.403c-.783-.57-.38-1.81.588-1.81h4.179a1 1 0 00.95-.69l1.286-3.976z"
            />
          </svg>
          <svg
            v-else
            xmlns="http://www.w3.org/2000/svg"
            class="h-8 w-8 text-gray-300"
            viewBox="0 0 20 20"
            fill="currentColor"
          >
            <path
              d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.976a1 1 0 00.95.69h4.179c.969 0 1.371 1.24.588 1.81l-3.376 2.455a1 1 0 00-.364 1.118l1.287 3.976c.3.921-.755 1.688-1.54 1.118L10 13.347l-3.376 2.455c-.785.57-1.838-.197-1.54-1.118l1.287-3.976a1 1 0 00-.364-1.118L2.098 9.403c-.783-.57-.38-1.81.588-1.81h4.179a1 1 0 00.95-.69l1.286-3.976z"
            />
          </svg>
        </span>
      </div>
    </div>

    <!-- 리뷰 작성 텍스트 영역 -->
    <div class="mb-4">
      <label for="review" class="block text-sm font-medium text-primary mb-2">
        리뷰 내용:
      </label>
      <textarea
        id="review"
        v-model="comment"
        rows="5"
        class="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
        placeholder="리뷰를 작성해주세요."
      ></textarea>
    </div>

    <!-- 제출 버튼 -->
    <div class="flex justify-end">
      <button
        @click="submitReview"
        class="px-4 py-2 bg-white text-black border border-gray-300 rounded-lg hover:bg-gray-100"
      >
        리뷰 제출
      </button>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import axios from "axios";
import { useAuthStore } from "@/stores/auth";

export default {
  name: "ReviewForm",
  props: {
    campId: {
      type: Number,
      required: true,
    },
  },
  setup(props) {
    const authStore = useAuthStore();
    const currentRating = ref(0);
    const comment = ref("");

    const setRating = (rating) => {
      currentRating.value = rating;
    };

    const submitReview = async () => {
      if (currentRating.value === 0) {
        alert("별점을 입력해주세요!");
        return;
      }

      if (comment.value.trim() === "") {
        alert("리뷰 내용을 입력해주세요!");
        return;
      }

      try {
        const userId = authStore.user.userId; // user에서 ID 가져오기
        if (!userId) {
          alert("로그인이 필요합니다.");
          return;
        }
        const response = await axios.post(
          "/reviews",
          {
            campId: props.campId,
            campRate: currentRating.value,
            comment: comment.value,
            userId: userId,
          },
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
              "Content-Type": "application/json", // JSON 형식으로 전달
            },
          }
        );

        if (response.status === 201) {
          alert("리뷰가 성공적으로 작성되었습니다!");
          // 리뷰 작성 후 페이지 이동 또는 상태 업데이트
          window.location.reload(); // 예시로 페이지를 새로고침
        }
      } catch (error) {
        console.error("리뷰 작성 중 오류 발생:", error);
        alert("리뷰 작성에 실패했습니다. 다시 시도해주세요.");
      }
    };

    return {
      user: authStore.user,
      currentRating,
      comment,
      setRating,
      submitReview,
    };
  },
};
</script>

<style scoped>
.review-form-container {
  background-color: #ffffff;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-height: 80vh; /* 최대 높이 설정 */
  overflow-y: auto; /* 내용이 넘칠 경우 스크롤 */
}
</style>
