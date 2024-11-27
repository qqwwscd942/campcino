<!-- src/views/review/ReviewListView.vue -->
<template>
  <div class="review-list-container">
    <Header />
    <main class="container mx-auto p-6">
      <h1 class="text-3xl font-bold mb-4">캠핑장 리뷰 목록</h1>

      <!-- Sorting Controls -->
      <div class="flex justify-end mb-4">
        <label class="mr-2">정렬 기준:</label>
        <select
          v-model="sortBy"
          @change="fetchReviews"
          class="border rounded px-2 py-1 mr-4"
        >
          <option value="avgRating">평점</option>
          <option value="reviewCount">리뷰 수</option>
          <option value="campName">캠핑장 이름</option>
        </select>

        <label class="mr-2">정렬 순서:</label>
        <select
          v-model="sortOrder"
          @change="fetchReviews"
          class="border rounded px-2 py-1"
        >
          <option value="asc">오름차순</option>
          <option value="desc">내림차순</option>
        </select>
      </div>

      <!-- Review List -->
      <table class="min-w-full bg-white border">
        <thead>
          <tr>
            <th class="py-2 px-4 border-b">캠핑장 이름</th>
            <th class="py-2 px-4 border-b">별점</th>
            <th class="py-2 px-4 border-b">평점</th>
            <th class="py-2 px-4 border-b">리뷰 수</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="camp in reviews"
            :key="camp.campId"
            class="hover:bg-gray-100"
          >
            <td class="py-2 px-4 border-b">
              <router-link
                :to="{ name: 'CampDetail', params: { id: camp.campId } }"
                class="text-blue-500 hover:underline"
              >
                {{ camp.campName }}
              </router-link>
            </td>
            <td class="py-2 px-4 border-b">
              <StarRating :rating="camp.avgRating" size="16px" />
            </td>
            <td class="py-2 px-4 border-b">{{ camp.avgRating.toFixed(1) }}</td>
            <td class="py-2 px-4 border-b">{{ camp.reviewCount }}</td>
          </tr>
        </tbody>
      </table>

      <!-- Pagination -->
      <div class="flex justify-center mt-4">
        <button
          @click="goToPage(currentPage - 1)"
          :disabled="currentPage === 1"
          class="px-3 py-1 mx-1 bg-gray-200 rounded disabled:opacity-50"
        >
          이전
        </button>
        <button
          v-for="page in visiblePages"
          :key="page"
          @click="goToPage(page)"
          :class="[
            'px-3 py-1 mx-1 rounded',
            {
              'bg-blue-500 text-white': page === currentPage,
              'bg-gray-200': page !== currentPage,
            },
          ]"
        >
          {{ page }}
        </button>
        <button
          @click="goToPage(currentPage + 1)"
          :disabled="currentPage === totalPages"
          class="px-3 py-1 mx-1 bg-gray-200 rounded disabled:opacity-50"
        >
          다음
        </button>
      </div>
    </main>
  </div>
</template>

<script>
import Header from "@/components/common/Header.vue";
import StarRating from "@/components/camp/StarRating.vue";
import { getCampReviewSummaries } from "@/api";

export default {
  name: "ReviewListView",
  components: {
    Header,
    StarRating,
  },
  data() {
    return {
      reviews: [],
      sortBy: "avgRating",
      sortOrder: "desc",
      currentPage: 1,
      pageSize: 15,
      totalPages: 1,
      totalItems: 0,
    };
  },
  computed: {
    visiblePages() {
      const pages = [];
      const maxVisible = 5;
      let start = Math.max(this.currentPage - Math.floor(maxVisible / 2), 1);
      let end = start + maxVisible - 1;

      if (end > this.totalPages) {
        end = this.totalPages;
        start = Math.max(end - maxVisible + 1, 1);
      }

      for (let i = start; i <= end; i++) {
        pages.push(i);
      }
      return pages;
    },
  },
  methods: {
    async fetchReviews() {
      try {
        const data = await getCampReviewSummaries(
          this.sortBy,
          this.sortOrder,
          this.currentPage,
          this.pageSize
        );
        this.reviews = data.items;
        this.currentPage = data.currentPage;
        this.pageSize = data.pageSize;
        this.totalItems = data.totalItems;
        this.totalPages = data.totalPages;
      } catch (error) {
        console.error("리뷰 목록을 가져오는 중 오류 발생:", error);
      }
    },
    goToPage(page) {
      if (page < 1 || page > this.totalPages) return;
      this.currentPage = page;
      this.fetchReviews();
    },
  },
  mounted() {
    this.fetchReviews();
  },
};
</script>

<style scoped>
.review-list-container {
  background-color: #f9f9f9;
  min-height: 100vh;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  text-align: left;
}

th {
  background-color: #f2f2f2;
}

.hover\:bg-gray-100:hover {
  background-color: #f2f2f2;
}
</style>
