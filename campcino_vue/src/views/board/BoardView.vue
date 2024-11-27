<template>
  <div
    class="relative flex w-full min-h-screen flex-col bg-white overflow-x-hidden"
    style="font-family: 'Be Vietnam Pro', 'Noto Sans', sans-serif"
  >
    <Header />

    <!-- Main Content -->
    <main class="flex flex-1 justify-center py-5 px-6">
      <div class="max-w-[920px] w-full flex flex-col">
        <!-- Bulletin Board Title -->
        <div class="flex justify-between gap-3 p-4">
          <p class="text-[#1C160C] text-2xl font-bold">캠핑 게시판</p>
        </div>

        <!-- Search for a Topic -->
        <SearchBar v-model="topicSearch" placeholder="검색어를 입력하세요." />

        <!-- Category Tags -->
        <CategoryTags
          :categories="categories"
          :selectedCategory="selectedCategory"
          @update:selectedCategory="updateSelectedCategory"
        />

        <!-- Popular Topics -->
        <BoardList
          :posts="posts"
          :currentPage="currentPage"
          :totalPages="totalPages"
          @changePage="changePage"
        />

        <!-- Start New Post Button -->
        <StartNewPostButton />
      </div>
    </main>
  </div>
</template>

<script>
import Header from "@/components/common/Header.vue";
import SearchBar from "@/components/board/SearchBar.vue";
import CategoryTags from "@/components/board/CategoryTags.vue";
import BoardList from "@/components/board/BoardList.vue";
import StartNewPostButton from "@/components/board/StartNewPostButton.vue";
import { getBoards } from "@/api";

export default {
  name: "Home",
  components: {
    Header,
    SearchBar,
    CategoryTags,
    BoardList,
    StartNewPostButton,
  },
  data() {
    return {
      topicSearch: "",
      selectedCategory: "전체",
      categories: ["전체", "질문", "추천", "수다", "장비", "기타"],
      posts: [],
      currentPage: 1,
      totalPages: 1,
      totalItems: 0,
    };
  },
  created() {
    this.fetchPosts();
  },
  watch: {
    topicSearch(newSearch) {
      this.fetchPosts(1); // 검색어가 변경될 때마다 첫 페이지부터 재조회
    },
  },
  methods: {
    async fetchPosts(page = 1) {
      try {
        const response = await getBoards(
          this.selectedCategory !== "전체"
            ? [this.selectedCategory]
            : ["질문", "추천", "수다", "장비", "기타"],
          this.topicSearch,
          page,
          10, // size
          "board_created_at",
          "DESC"
        );
        this.posts = response.items;
        this.currentPage = response.currentPage;
        this.totalPages = response.totalPages;
        this.totalItems = response.totalItems;
      } catch (error) {
        console.error("게시글을 가져오는 중 오류가 발생했습니다:", error);
      }
    },
    updateSelectedCategory(newCategory) {
      this.selectedCategory = newCategory;
      this.currentPage = 1;
      this.fetchPosts(1);
    },
    changePage(newPage) {
      if (newPage >= 1 && newPage <= this.totalPages) {
        this.currentPage = newPage;
        this.fetchPosts(newPage);
      }
    },
  },
};
</script>

<style scoped>
/* 필요에 따라 스타일을 추가하세요 */
</style>
