<!-- src/components/store/ReviewList.vue -->
<template>
  <div>
    <div v-if="reviews && reviews.length > 0">
      <div v-for="review in reviews" :key="review.reviewId" class="border p-4 rounded mb-4">
        <div class="flex items-center justify-between">
          <!-- 평점 및 날짜 -->
          <div class="flex items-center">
            <i class="fas fa-star text-yellow-500 mr-2"></i>
            <span>{{ review.shopRate }}</span>
            <span class="ml-2 text-sm text-gray-600">{{ review.createdAt }}</span>
          </div>
          <!-- 수정 및 삭제 버튼 (작성자만 표시) -->
          <div v-if="isAuthor(review)" class="flex space-x-2">
            <button @click="editReview(review)" class="text-blue-500">수정</button>
            <button @click="deleteReview(review.reviewId)" class="text-red-500">삭제</button>
          </div>
        </div>
        <!-- 리뷰 내용 -->
        <p class="mt-2">{{ review.comment || "리뷰 내용 없음" }}</p>
      </div>
    </div>
    <div v-else>
      <p>리뷰가 없습니다.</p>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from "@/stores/auth"; // auth 스토어 임포트
import { ref } from "vue";

export default {
  name: "ReviewList",
  props: {
    reviews: {
      type: Array,
      required: true,
    },
  },
  emits: ["edit-review", "delete-review"],
  setup(props, { emit }) {
    const authStore = useAuthStore();

    // 작성자인지 확인
    const isAuthor = (review) => {
      return authStore.user && authStore.user.userId === review.userId;
    };

    // 리뷰 수정 핸들러
    const editReview = (review) => {
      emit("edit-review", review);
    };

    // 리뷰 삭제 핸들러
    const deleteReview = (reviewId) => {
      if (confirm("정말로 이 리뷰를 삭제하시겠습니까?")) {
        emit("delete-review", reviewId);
      }
    };

    return {
      isAuthor,
      editReview,
      deleteReview,
    };
  },
};
</script>

<style scoped>
/* 스타일 추가 가능 */
</style>
