<template>
  <div class="flex flex-wrap gap-3 p-3">
    <div
      v-for="category in categories"
      :key="category"
      @click="selectCategory(category)"
      :class="[
        'flex h-8 items-center justify-center gap-x-2 rounded px-4 bg-[#F4EFE6] cursor-pointer',
        selectedCategory === category
          ? 'bg-[#D1BFA3] border-2 border-[#1C160C]'
          : '',
      ]"
    >
      <p class="text-[#1C160C] text-sm font-medium">{{ category }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "CategoryTags",
  props: {
    categories: {
      type: Array,
      default: () => ["전체", "질문", "추천", "수다", "장비", "기타"],
    },
    selectedCategory: {
      type: String,
      default: "전체",
    },
  },
  emits: ["update:selectedCategory"],
  methods: {
    selectCategory(category) {
      this.$emit("update:selectedCategory", category);
    },
  },
};
</script>

<style scoped>
/* 필요에 따라 스타일을 추가하세요 */
</style>
