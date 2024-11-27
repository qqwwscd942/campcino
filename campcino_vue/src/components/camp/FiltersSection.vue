<!-- src/components/camp/FiltersSection.vue -->
<template>
  <div class="flex flex-col md:flex-row items-center gap-4 mb-6">
    <!-- 지역 선택 -->
    <select
      v-model="selectedRegionId"
      class="p-2 border rounded w-full md:w-auto"
    >
      <option :value="0">지역</option>
      <option
        v-for="region in regions"
        :key="region.sidoId"
        :value="region.sidoId"
      >
        {{ region.sidoName }}
      </option>
    </select>

    <!-- 카테고리 선택 -->
    <div class="flex gap-2">
      <select
        v-model="selectedCategory"
        class="p-2 border rounded w-full md:w-auto"
      >
        <option value="0">카테고리</option>
        <option value="일반야영장">일반야영장</option>
        <option value="글램핑">글램핑</option>
        <option value="자동차야영장">자동차야영장</option>
        <option value="카라반">카라반</option>
      </select>
    </div>

    <!-- 검색어 입력 -->
    <input
      type="text"
      v-model="query"
      placeholder="검색어 입력"
      class="p-2 border rounded flex-1 w-full md:w-auto"
      @keyup="handleKeyUp"
    />

    <!-- 검색 버튼 -->
    <button
      @click="onSearch"
      class="bg-green-600 text-white px-4 py-2 rounded w-full md:w-auto"
    >
      검색
    </button>
  </div>
</template>

<script>
import { ref } from "vue";

export default {
  name: "FiltersSection",
  props: {
    regions: {
      type: Array,
      default: () => [],
    },
    categories: {
      type: Array,
      default: () => [],
    },
    selectedRegionId: {
      type: Number,
      default: 0,
    },
    selectedCategory: {
      type: String,
      default: "0",
    },
  },
  emits: ["search"],
  setup(props, { emit }) {
    const selectedRegionId = ref(props.selectedRegionId);
    const selectedCategory = ref(props.selectedCategory);
    const query = ref("");

    const onSearch = () => {
      emit(
        "search",
        selectedRegionId.value,
        selectedCategory.value,
        query.value
      );
    };

    // 검색어 입력 후 Enter 키 누르면 검색 실행
    const handleKeyUp = (event) => {
      if (event.key === "Enter") {
        onSearch();
      }
    };

    return {
      selectedRegionId,
      selectedCategory,
      query,
      onSearch,
      handleKeyUp,
    };
  },
};
</script>

<style scoped>
/* 필요한 스타일을 추가하세요 */
</style>
