<!-- src/components/camp/NearAmenitiesList.vue -->
<template>
  <FacilityList :amenities="amenities" />
</template>

<script>
import FacilityList from "./FacilityList.vue";

export default {
  name: "NearAmenitiesList",
  components: {
    FacilityList,
  },
  props: {
    amenities: {
      type: Array,
      required: true,
    },
  },
};
</script>

<style scoped>
/* 필요한 스타일 추가 */
</style>
