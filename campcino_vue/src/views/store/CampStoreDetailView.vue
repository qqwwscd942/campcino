<!-- src/views/StoreDetailView.vue -->
<template>
  
  <div>
    <Header />  
    <div v-if="storeDetail" class="p-6">
      <h1 class="text-2xl font-bold">{{ storeDetail.shopTitle }}</h1>
      <img
        :src="storeDetail.image"
        :alt="storeDetail.shopTitle"
        @error="handleImageError"
        class="w-50 h-64 object-cover rounded mt-4"
      />
      <p class="text-lg mt-4">
        Price: ₩{{ storeDetail.shopPrice.toLocaleString() }}
      </p>
      <p class="text-yellow-500">
        ⭐ {{ formattedRating }}
      </p>
      <p class="mt-2">{{ storeDetail.shopComment }}</p>

      <!-- 리뷰 섹션 -->
      <div class="mt-6">
        <h2 class="text-xl font-semibold mb-4">Reviews</h2>
        <ReviewList :reviews="storeReviews" @edit-review="handleEditReview" @delete-review="handleDeleteReview" />
        <ReviewForm :storeId="storeId" @submit-review="submitReview" />
      </div>
    </div>
    <div v-else class="p-6">
      <p>스토어 상세 정보를 로드 중입니다...</p>
    </div>

    <!-- 리뷰 수정 모달 -->
    <div v-if="editingReview" class="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-50">
      <div class="bg-white p-6 rounded shadow-lg w-1/2">
        <h3 class="text-xl font-semibold mb-4">리뷰 수정</h3>
        <form @submit.prevent="updateReview" class="space-y-4">
          <div>
            <label for="edit-rating" class="block text-sm font-medium text-gray-700">평점</label>
            <select v-model.number="editingReview.shopRate" id="edit-rating" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm">
              <option disabled value="">평점을 선택하세요</option>
              <option v-for="n in 5" :key="n" :value="n">{{ n }} 점</option>
            </select>
          </div>
          <div>
            <label for="edit-comment" class="block text-sm font-medium text-gray-700">리뷰</label>
            <textarea
              v-model="editingReview.comment"
              id="edit-comment"
              rows="4"
              class="mt-1 block w-full border-gray-300 rounded-md shadow-sm"
              placeholder="리뷰를 작성하세요"
              required
            ></textarea>
          </div>
          <div class="flex justify-end space-x-2">
            <button type="button" @click="cancelEdit" class="px-4 py-2 bg-gray-300 text-gray-700 rounded">취소</button>
            <button type="submit" class="px-4 py-2 bg-blue-500 text-white rounded">수정 완료</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { useStoreStore } from "@/stores/storeStore";
import { useAuthStore } from "@/stores/auth"; // auth 스토어 임포트
import { onMounted, computed, ref } from "vue";
import Header from "@/components/common/Header.vue";
import ReviewList from "@/components/store/ReviewList.vue";
import ReviewForm from "@/components/store/ReviewForm.vue"; // 리뷰 폼 컴포넌트 임포트
import { useRoute } from "vue-router";

export default {
  name: "StoreDetailView",
  components: { Header, ReviewList, ReviewForm },
  setup() {
    const storeStore = useStoreStore();
    const authStore = useAuthStore(); // auth 스토어 사용
    const route = useRoute();
    const storeId = parseInt(route.params.id, 10);
    const editingReview = ref(null); // 리뷰 수정 상태

    onMounted(async () => {
      try {
        await storeStore.fetchStoreDetail(storeId);
        console.log('스토어 상세 정보:', storeStore.getStoreDetail(storeId)); // 디버깅용
        await storeStore.fetchStoreReviews(storeId);
      } catch (error) {
        console.error("스토어 상세 정보 또는 리뷰를 가져오는 중 오류 발생:", error);
        alert("스토어 상세 정보를 로드하지 못했습니다. 나중에 다시 시도해주세요.");
      }
    });

    const submitReview = async (review) => {
  try {
    // 리뷰 작성
    const newReview = await storeStore.submitShopReview(storeId, review);

    // 서버로부터 받은 새로운 리뷰 데이터를 상태에 추가
    storeStore.storeReviews[storeId].push(newReview);

    alert("리뷰가 성공적으로 제출되었습니다.");
  } catch (error) {
    console.error("리뷰 제출 실패:", error);
    alert("리뷰 제출에 실패했습니다. 나중에 다시 시도해주세요.");
  }
};

    const handleEditReview = (review) => {
      editingReview.value = { ...review };
    };

    const handleDeleteReview = async (reviewId) => {
      try {
        const userId = authStore.user.userId;
        await storeStore.deleteShopReview(storeId, reviewId, userId);
        alert("리뷰가 성공적으로 삭제되었습니다.");
      } catch (error) {
        console.error("리뷰 삭제 실패:", error);
        alert("리뷰 삭제에 실패했습니다. 나중에 다시 시도해주세요.");
      }
    };

    const updateReview = async () => {
      try {
        const userId = authStore.user.userId;
        await storeStore.updateShopReview(storeId, editingReview.value);
        alert("리뷰가 성공적으로 수정되었습니다.");
        editingReview.value = null; // 수정 상태 해제
      } catch (error) {
        console.error("리뷰 수정 실패:", error);
        alert("리뷰 수정에 실패했습니다. 나중에 다시 시도해주세요.");
      }
    };

    const cancelEdit = () => {
      editingReview.value = null;
    };

    const handleImageError = (event) => {
      event.target.src = "https://via.placeholder.com/400x300?text=Image+Not+Available"; // 대체 이미지 URL
    };

    // 안전한 접근을 위한 계산된 속성
    const formattedRating = computed(() => {
      const rating = storeStore.getStoreDetail(storeId)?.rating;
      return rating !== undefined ? rating.toFixed(2) : 'No rating';
    });

    const storeDetail = computed(() => storeStore.getStoreDetail(storeId));
    const storeReviews = computed(() => storeStore.getStoreReviews(storeId));

    return {
      storeDetail,
      storeReviews,
      submitReview,
      handleEditReview,
      handleDeleteReview,
      updateReview,
      cancelEdit,
      handleImageError,
      formattedRating,
      storeId,
      editingReview,
    };
  },
};
</script>

<style scoped>
/* 필요한 경우 추가 스타일링 */
</style>
