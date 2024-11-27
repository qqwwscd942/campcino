<!-- src/views/StoreListView.vue -->
<template>
  <div>
    <Header />
    <h1 class="text-2xl font-bold mb-4">상품 리스트</h1>
    <div v-if="stores.length > 0" class="grid grid-cols-2 sm:grid-cols-5 lg:grid-cols-4 gap-4">
      <ProductItem
        v-for="store in stores"
        :key="store.shopId"
        :product="store"
        @click="viewStore(store.shopId)"
      />
    </div>
    <div v-else class="text-center mt-8">
      <p class="text-gray-500">No stores available.</p>
    </div>
  </div>
</template>

<script>
import { useStoreStore } from "@/stores/storeStore";
import { computed, onMounted } from "vue";
import Header from "@/components/common/Header.vue";
import ProductItem from "@/components/store/ProductItem.vue"; // ProductItem 컴포넌트 임포트
import { useRouter } from "vue-router";

export default {
  name: "StoreListView",
  components: { Header, ProductItem },
  setup() {
    const storeStore = useStoreStore();
    const router = useRouter();

    onMounted(async () => {
      try {
        await storeStore.fetchStores();
      } catch (error) {
        console.error("Failed to fetch stores:", error);
        alert("Failed to load store data. Please try again later.");
      }
    });

    const viewStore = (id) => {
      router.push(`/stores/${id}`);
    };

    return {
      stores: computed(() => storeStore.stores),
      viewStore,
    };
  },
};
</script>
