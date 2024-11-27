<!-- src/components/camp/FavoriteList.vue -->
<template>
  <div class="favorite-list" :class="{ minimized: isMinimized }">
    <div class="header">
      <h2 class="title">찜 목록</h2>
      <button
        @click="toggleMinimize"
        class="minimize-button"
        :aria-label="isMinimized ? '찜 목록 열기' : '찜 목록 최소화'"
      >
        {{ isMinimized ? "▶" : "◀" }}
      </button>
    </div>
    <div v-if="!isMinimized" class="content">
      <ul>
        <li v-for="camp in favorites" :key="camp.id" class="mb-2">
          <div class="flex justify-between items-center">
            <span
              @click="viewDetails(camp.id)"
              class="cursor-pointer text-blue-500 hover:underline"
            >
              {{ camp.name }}
            </span>
            <button
              @click="$emit('remove-favorite', camp.id)"
              class="text-red-500 hover:text-red-700"
            >
              삭제
            </button>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "FavoriteList",
  props: {
    favorites: {
      type: Array,
      required: true,
    },
  },
  emits: ["remove-favorite", "toggle-minimize", "view-details"],
  data() {
    return {
      isMinimized: false,
    };
  },
  methods: {
    toggleMinimize() {
      this.isMinimized = !this.isMinimized;
      this.$emit("toggle-minimize", this.isMinimized);
    },
    viewDetails(campId) {
      this.$emit("view-details", campId);
    },
  },
};
</script>

<style scoped>
/* 기존 스타일 유지 */
</style>
