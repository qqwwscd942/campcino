<!-- src/components/camp/CampList.vue -->
<template>
  <div class="overflow-x-auto">
    <table class="min-w-full bg-white">
      <thead>
        <tr>
          <th class="p-4 text-left text-sm font-medium text-gray-500">
            캠핑장 이름
          </th>
          <th class="p-4 text-left text-sm font-medium text-gray-500">주소</th>
          <th class="p-4 text-left text-sm font-medium text-gray-500">
            찜하기
          </th>
          <th class="p-4 text-left text-sm font-medium text-gray-500">
            상세 보기
          </th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="camp in camps"
          :key="camp.id"
          class="border-b hover:bg-gray-50"
        >
          <td
            @click="$emit('focus-marker', camp.id)"
            class="p-4 text-sm text-[#1C160C] cursor-pointer hover:underline"
          >
            {{ camp.name }}
          </td>
          <td class="p-4 text-sm text-[#1C160C]">{{ camp.address }}</td>
          <td class="p-4">
            <button
              @click="$emit('toggle-favorite', camp)"
              class="flex items-center gap-2 px-4 py-2 border rounded"
            >
              <span class="text-gray-600">
                {{ isFavorited(camp.id) ? "♥ 찜 취소" : "♡ 찜하기" }}
              </span>
            </button>
          </td>
          <td class="p-4">
            <button
              @click="$emit('view-details', camp.id)"
              class="text-blue-500 hover:underline"
            >
              자세히 보기
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "CampList",
  props: {
    camps: {
      type: Array,
      required: true,
    },
    favorites: {
      type: Array,
      required: true,
    },
  },
  emits: ["focus-marker", "toggle-favorite", "view-details"],
  methods: {
    isFavorited(campId) {
      return this.favorites.some((fav) => fav.id === campId);
    },
  },
};
</script>

<style scoped>
/* 필요한 스타일 추가 */
</style>
