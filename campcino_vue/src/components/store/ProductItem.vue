<template>
  <div
    class="product-item border rounded-lg p-4 shadow hover:shadow-lg transition-shadow duration-300 cursor-pointer"
    @click="$emit('click')"
  >
    <img
      :src="product.image"
      :alt="product.shopTitle"
      @error="handleImageError"
      class="w-full h-48 object-cover rounded"
    />
    <h3 class="mt-2 text-lg font-semibold">{{ product.shopTitle }}</h3>
    <p class="text-gray-700">Price: ₩{{ product.shopPrice.toLocaleString() }}</p>
    <p class="text-yellow-500">⭐ {{ displayRating }}</p>
    <p class="text-gray-600">{{ product.shopComment }}</p>
  </div>
</template>

<script>
export default {
  name: "ProductItem",
  props: {
    product: {
      type: Object,
      required: true,
    },
  },
  computed: {
    displayRating() {
      return this.product.rating ? this.product.rating.toFixed(1) : "평점 없음";
    },
  },
  methods: {
    handleImageError(event) {
      event.target.src = "https://via.placeholder.com/400x300?text=Image+Not+Available"; // 대체 이미지 URL
    },
  },
  mounted() {
    console.log('ProductItem received product:', this.product);
  },
};
</script>

<style scoped>
.product-item {
  /* 추가적인 스타일링이 필요하면 여기에 작성 */
}
</style>