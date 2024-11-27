// src/stores/campStore.js
import { defineStore } from "pinia";
import { ref } from "vue";
import { getCamps, getRegions, getCategories } from "@/api";

export const useCampStore = defineStore("campStore", () => {
  // 상태
  const regions = ref([]);
  const categories = ref([]);
  const camps = ref([]);
  const markers = ref([]);
  const loading = ref(false);
  const error = ref(null);
  const selectedRegionId = ref(0);
  const selectedCategory = ref("0");
  const query = ref("");

  // 카테고리 매핑
  const categoryMappings = {
    글램핑: [1, 2, 3, 7, 8, 10, 14, 15, 18],
    일반야영장: [2, 3, 4, 5, 8, 10, 11, 12, 16, 17],
    자동차야영장: [2, 5, 7, 9, 10, 12, 15, 16],
    카라반: [6, 11, 12, 13, 14, 15, 17, 18],
  };

  // 필터 데이터 가져오기
  const fetchFilters = async () => {
    try {
      const [regionsData, categoriesData] = await Promise.all([
        getRegions(),
        getCategories(),
      ]);
      regions.value = regionsData;
      categories.value = categoriesData;
    } catch (err) {
      error.value = "필터 데이터를 가져오는 중 오류가 발생했습니다.";
      console.error(err);
    }
  };

  // 캠핑장 데이터 가져오기
  const fetchCamps = async () => {
    loading.value = true;
    error.value = null;
    try {
      const categoryIdsToSearch =
        selectedCategory.value === "0"
          ? [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]
          : categoryMappings[selectedCategory.value] || [];

      const campData = await getCamps(
        selectedRegionId.value,
        categoryIdsToSearch,
        query.value
      );
      camps.value = campData.map((item) => ({
        id: item.campId,
        name: item.campName,
        address: item.roadAddress,
        latitude: item.latitude,
        longitude: item.longitude,
      }));
      updateMarkers();
    } catch (err) {
      error.value = "캠핑장 데이터를 가져오는 중 오류가 발생했습니다.";
      console.error(err);
    } finally {
      loading.value = false;
    }
  };

  // 마커 업데이트
  const updateMarkers = () => {
    markers.value = camps.value.map((camp) => ({
      id: camp.id,
      title: camp.name,
      latlng: new kakao.maps.LatLng(camp.latitude, camp.longitude),
    }));
  };

  // 필터 설정
  const setFilters = (regionId, category, searchQuery) => {
    selectedRegionId.value = regionId;
    selectedCategory.value = category;
    query.value = searchQuery;
  };

  // 스크롤 위치 저장 및 복원 (선택 사항)
  const scrollPosition = ref({});

  const setScrollPosition = (routeName, position) => {
    scrollPosition.value[routeName] = position;
  };

  const getScrollPosition = (routeName) => {
    return scrollPosition.value[routeName] || { top: 0 };
  };

  return {
    regions,
    categories,
    camps,
    markers,
    loading,
    error,
    selectedRegionId,
    selectedCategory,
    query,
    fetchFilters,
    fetchCamps,
    updateMarkers,
    setFilters,
    scrollPosition,
    setScrollPosition,
    getScrollPosition,
  };
});
