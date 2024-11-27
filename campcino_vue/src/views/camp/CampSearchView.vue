<!-- src/components/camp/CampSearchView.vue -->
<template>

  <div
  class="relative flex w-full min-h-screen flex-col bg-white overflow-x-hidden"
    style="font-family: 'Be Vietnam Pro', 'Noto Sans', sans-serif"
  >
  <Header />

    <!-- 찜 목록 컴포넌트 -->
    <FavoriteList
      v-if="favorites.length"
      :favorites="favorites"
      @remove-favorite="handleRemoveFavorite"
      @toggle-minimize="handleToggleMinimize"
      @view-details="viewDetails"
      class="favorite-list"
    />
    <div class="flex flex-col items-center px-10 py-6">
      <div class="w-full max-w-5xl">
        <FiltersSection
          :regions="regions"
          :categories="categories"
          @search="handleSearch"
        />
        <!-- 지도 섹션 -->
        <div class="w-full h-96 mb-6">
          <MapSection
            :markers="markers"
            ref="mapSection"
            class="w-full h-full"
          />
        </div>
        <div v-if="loading" class="text-center mt-4">캠핑장 로딩 중...</div>
        <div v-else-if="error" class="text-center text-red-500 mt-4">
          {{ error }}
        </div>
        <div v-else class="mt-6">
          <CampList
            :camps="camps"
            :favorites="favorites"
            @focus-marker="focusMarker"
            @toggle-favorite="handleToggleFavorite"
            @view-details="viewDetails"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { getCamps, getRegions, getCategories } from "@/api";
import Header from "@/components/common/Header.vue";
import FiltersSection from "@/components/camp/FiltersSection.vue";
import MapSection from "@/components/camp/MapSection.vue";
import CampList from "@/components/camp/CampList.vue";
import FavoriteList from "@/components/camp/FavoriteList.vue";

const router = useRouter();

const regions = ref([]);
const categories = ref([]);
const camps = ref([]);
const markers = ref([]);
const mapSection = ref(null);

const loading = ref(false);
const error = ref(null);

// 찜 목록 상태 관리
const favorites = ref([]);

// 카카오맵 API 로드 상태 확인
const kakaoLoaded = ref(false);

// 세션 스토리지에서 찜 목록 불러오기 및 카카오맵 API 로드
onMounted(() => {
  fetchData();
  loadKakaoMaps();

  const storedFavorites = sessionStorage.getItem("favorites");
  if (storedFavorites) {
    favorites.value = JSON.parse(storedFavorites);
  }
});

// 찜 목록의 길이를 감시하여 지도 업데이트
watch(
  () => favorites.value.length,
  () => {
    if (mapSection.value && mapSection.value.updateMapCenter) {
      // 약간의 지연 시간을 두어 레이아웃이 완료된 후에 지도 업데이트
      setTimeout(() => {
        mapSection.value.updateMapCenter();
      }, 300);
    }
  }
);

// 카카오맵 API 로드 함수
const loadKakaoMaps = () => {
  if (window.kakao && window.kakao.maps) {
    kakaoLoaded.value = true;
    updateMarkers(); // 카카오맵 API가 로드되었으므로 마커 업데이트
  } else {
    const script = document.createElement("script");
    script.src =
      "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=850d7b0fe59c47b55e0c0511520c3335&libraries=services,clusterer,drawing";
    script.onload = () => {
      window.kakao.maps.load(() => {
        kakaoLoaded.value = true;
        updateMarkers(); // 카카오맵 API가 로드되었으므로 마커 업데이트
      });
    };
    document.head.appendChild(script);
  }
};

// 캠프장 카테고리와 그에 해당하는 ID 매핑
const categoryMappings = {
  글램핑: [1, 2, 3, 7, 8, 10, 14, 15, 18],
  일반야영장: [2, 3, 4, 5, 8, 10, 11, 12, 16, 17],
  자동차야영장: [2, 5, 7, 9, 10, 12, 15, 16],
  카라반: [6, 11, 12, 13, 14, 15, 17, 18],
};

// 캠프장 데이터를 가져오는 함수
const fetchCamps = async (
  regionId = 0,
  selectedCategories = [],
  query = ""
) => {
  loading.value = true;
  error.value = null;
  try {
    const campData = await getCamps(regionId, selectedCategories, query);
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

// 필터 데이터를 가져오는 함수
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

// 초기 데이터를 가져오는 함수
const fetchData = async () => {
  await fetchFilters();
};

// 검색 버튼 클릭 시 필터링된 캠프장 데이터 가져오기
const handleSearch = (regionId, selectedCategory, query) => {
  const categoryIdsToSearch =
    selectedCategory === "0"
      ? [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]
      : categoryMappings[selectedCategory] || [];

  fetchCamps(regionId, categoryIdsToSearch, query);
};

// 마커 업데이트 함수
const updateMarkers = () => {
  if (!kakaoLoaded.value) return;

  markers.value = camps.value.map((camp) => ({
    id: camp.id,
    title: camp.name,
    latlng: new kakao.maps.LatLng(camp.latitude, camp.longitude),
  }));
};

// 캠프장 마커에 포커스하는 함수
const focusMarker = (campId) => {
  const campIndex = camps.value.findIndex((camp) => camp.id === campId);
  if (campIndex !== -1 && mapSection.value) {
    mapSection.value.focusMarker(campIndex);
  }
};

// 찜하기 이벤트 처리
const handleToggleFavorite = (camp) => {
  const index = favorites.value.findIndex((fav) => fav.id === camp.id);
  if (index !== -1) {
    // 이미 찜한 캠핑장이라면 제거
    favorites.value.splice(index, 1);
  } else {
    // 찜 목록에 추가
    favorites.value.push(camp);
  }
  // 세션 스토리지에 저장
  sessionStorage.setItem("favorites", JSON.stringify(favorites.value));
};

// 찜 목록에서 제거
const handleRemoveFavorite = (campId) => {
  favorites.value = favorites.value.filter((camp) => camp.id !== campId);
  // 세션 스토리지에 저장
  sessionStorage.setItem("favorites", JSON.stringify(favorites.value));
};

// "상세 보기" 버튼을 통해 CampDetailView 페이지로 이동
const viewDetails = (campId) => {
  router.push({ name: "CampDetail", params: { id: campId } });
};
</script>

<style scoped>
/* 찜 목록을 화면 오른쪽에 고정시키는 스타일 */
.favorite-list {
  position: fixed;
  top: 100px; /* 원하는 위치로 조정 */
  right: 20px; /* 화면 오른쪽에서의 간격 */
  width: 300px; /* 찜 목록의 너비 */
  max-height: calc(100vh - 150px); /* 화면 높이에 따른 최대 높이 설정 */
  overflow-y: auto;
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  z-index: 1000; /* 다른 요소보다 위에 표시되도록 */
}

/* 작은 화면에서는 찜 목록이 고정되지 않도록 */
@media (max-width: 1500px) {
  .favorite-list {
    position: static;
    width: 100%;
    max-height: none;
    margin-top: 16px;
    right: 0;
    top: 0;
  }
}
</style>
