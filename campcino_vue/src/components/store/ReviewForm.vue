<!-- src/components/store/ReviewForm.vue -->
<template>
  <div class="mt-6">
    <h3 class="text-xl font-semibold mb-2">리뷰 작성</h3>
    <form @submit.prevent="handleSubmit" class="space-y-4">
      <div>
        <label for="rating" class="block text-sm font-medium text-gray-700">평점</label>
        <select v-model.number="review.shopRate" id="rating" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm">
          <option disabled value="">평점을 선택하세요</option>
          <option v-for="n in 5" :key="n" :value="n">{{ n }} 점</option>
        </select>
      </div>
      <div>
        <label for="comment" class="block text-sm font-medium text-gray-700">리뷰</label>
        <textarea
          v-model="review.comment"
          id="comment"
          rows="4"
          class="mt-1 block w-full border-gray-300 rounded-md shadow-sm"
          placeholder="리뷰를 작성하세요"
          required
        ></textarea>
      </div>
      <button type="submit" class="px-4 py-2 bg-blue-500 text-white rounded">리뷰 제출</button>
    </form>
  </div>
</template>

<script>
import { reactive } from 'vue';
import { useAuthStore } from "@/stores/auth"; // auth 스토어 임포트

export default {
  name: "ReviewForm",
  props: {
    storeId: {
      type: Number,
      required: true
    }
  },
  emits: ['submit-review'],
  setup(props, { emit }) {
    const authStore = useAuthStore(); // auth 스토어 사용
    const review = reactive({
      shopRate: null,
      comment: "",
    });

    const handleSubmit = () => {
      if (review.shopRate && review.comment) {
        emit('submit-review', { 
          userId: authStore.user.userId, // 실제 사용자 ID 사용
          shopRate: review.shopRate, 
          comment: review.comment 
        });
        // 폼 초기화
        review.shopRate = null;
        review.comment = "";
      } else {
        alert("평점과 리뷰 내용을 모두 입력해주세요.");
      }
    };

    return {
      review,
      handleSubmit,
    };
  },
};
</script>

<style scoped>
/* 스타일 추가 가능 */
</style>
