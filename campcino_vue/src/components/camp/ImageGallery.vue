<!-- src/components/camp/ImageGallery.vue -->
<template>
  <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
    <img
      v-for="(image, index) in images"
      :key="index"
      :src="image"
      alt="캠핑장 이미지"
      class="w-full h-64 object-cover rounded-lg"
    />
  </div>
</template>

<script>
export default {
  name: "ImageGallery",
  props: {
    images: {
      type: Array,
      required: true,
    },
  },
};
</script>

<style scoped>
.grid-cols-1 {
  grid-template-columns: repeat(1, minmax(0, 1fr));
}
.md\:grid-cols-3 {
  grid-template-columns: repeat(3, minmax(0, 1fr));
}
</style>
