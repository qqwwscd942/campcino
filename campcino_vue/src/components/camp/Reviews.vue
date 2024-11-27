<!-- src/components/camp/Reviews.vue -->
<template>
  <div class="mt-4">
    <div v-if="reviews.length === 0" class="text-sm text-secondary">
      아직 작성된 리뷰가 없습니다.
    </div>
    <div v-else>
      <div v-for="review in reviews" :key="review.review_id" class="mb-6">
        <!-- 리뷰어 정보 -->
        <div class="flex items-center">
          <!-- 기본 아바타 이미지 사용 -->
          <img
            src="https://via.placeholder.com/48"
            alt="리뷰어 아바타"
            class="w-12 h-12 rounded-full mr-4"
          />
          <div>
            <h3 class="text-lg font-semibold text-primary">
              {{ review.userId }}
            </h3>
            <!-- 생성일자를 보기 좋게 포맷 (옵션) -->
            <p v-if="review.createdAt" class="text-sm text-secondary">
              {{ formatDate(review.createdAt) }}
            </p>
          </div>
        </div>

        <!-- 평점 표시 -->
        <div class="flex items-center mt-2">
          <StarRating :rating="review.campRate" size="16px" />
          <!-- 'campRate'로 변경 -->
          <span class="ml-2 text-sm text-primary">{{ review.campRate }}</span>
          <!-- 'campRate'로 변경 -->
        </div>

        <!-- 리뷰 내용 -->
        <p class="text-base text-primary mt-2">{{ review.comment }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import StarRating from "./StarRating.vue";

export default {
  name: "Reviews",
  components: {
    StarRating,
  },
  props: {
    reviews: {
      type: Array,
      required: true,
    },
  },
  methods: {
    /**
     * 날짜를 보기 좋은 형식으로 포맷하는 메서드
     * @param {String} dateStr - 원본 날짜 문자열
     * @returns {String} - 포맷된 날짜 문자열
     */
    formatDate(dateStr) {
      if (!dateStr) return "";
      const options = { year: "numeric", month: "short", day: "numeric" };
      return new Date(dateStr).toLocaleDateString("ko-KR", options);
    },
  },
};
</script>

<style scoped>
.mt-4 {
  margin-top: 1rem;
}

.mb-6 {
  margin-bottom: 1.5rem;
}

.text-lg {
  font-size: 1.125rem;
}

.text-sm {
  font-size: 0.875rem;
}

.text-base {
  font-size: 1rem;
}

.text-primary {
  color: #1c160c;
}

.text-secondary {
  color: #a18249;
}

.flex {
  display: flex;
}

.items-center {
  align-items: center;
}

.rounded-full {
  border-radius: 9999px;
}

.w-12 {
  width: 3rem;
}

.h-12 {
  height: 3rem;
}

.mr-4 {
  margin-right: 1rem;
}

.ml-2 {
  margin-left: 0.5rem;
}

.mt-2 {
  margin-top: 0.5rem;
}
</style>
