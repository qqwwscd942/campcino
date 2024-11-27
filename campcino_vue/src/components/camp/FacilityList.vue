<!-- src/components/camp/FacilityList.vue -->
<template>
  <ul class="list-disc pl-5">
    <li
      v-for="amenity in amenities"
      :key="amenity"
      class="flex items-center text-gray-700"
    >
      <font-awesome-icon :icon="getIcon(amenity)" class="mr-2" />
      {{ amenity }}
    </li>
  </ul>
</template>

<script>
import { amenityIconMap } from "@/assets/amenityIcons";
import { faQuestionCircle } from "@fortawesome/free-solid-svg-icons";

export default {
  name: "FacilityList",
  props: {
    amenities: {
      type: Array,
      required: true,
    },
  },
  methods: {
    getIcon(amenity) {
      return amenityIconMap[amenity] || faQuestionCircle; // 기본 아이콘 설정
    },
  },
};
</script>

<style scoped>
ul {
  margin-top: 8px;
}
</style>
