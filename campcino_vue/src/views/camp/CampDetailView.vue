<!-- src/views/camp/CampDetailView.vue -->
<template>
  <div class="camp-detail-container">
    <main class="container mx-auto p-6">
      <!-- 로딩 상태 -->
      <div v-if="loading" class="text-center">캠핑장 정보를 로딩 중...</div>

      <!-- 오류 상태 -->
      <div v-else-if="error" class="text-center text-red-500">{{ error }}</div>

      <!-- 캠핑장 상세 정보 -->
      <div v-else class="camp-info bg-white p-6 rounded-lg shadow-lg">
        <!-- 캠핑장 이미지 갤러리 -->
        <ImageGallery :images="camp.images" />

        <!-- 캠핑장 기본 정보 -->
        <div
          class="mt-6 flex flex-col md:flex-row md:justify-between md:items-center"
        >
          <div>
            <h1
              class="text-4xl font-bold text-primary md:mb-6 flex items-center"
            >
              {{ camp.campName }}
              <!-- 운영 상태 아이콘 추가 -->
              <span class="ml-2">
                <CheckCircleIcon
                  v-if="isOperatingNow"
                  class="h-6 w-6 text-green-500"
                />
                <XCircleIcon v-else class="h-6 w-6 text-red-500" />
              </span>
            </h1>
            <p class="text-2xl text-secondary">{{ camp.roadAddress }}</p>
          </div>
        </div>

        <!-- 캠핑장 설명 -->
        <p class="text-base text-primary mt-4">{{ camp.campExplanation }}</p>

        <!-- 섹션들을 Grid로 수평 정렬 -->
        <div class="mt-6 grid grid-cols-1 md:grid-cols-4 gap-6">
          <!-- 편의 시설 -->
          <div>
            <h2 class="text-2xl font-semibold text-primary">편의 시설</h2>
            <AmenitiesList :amenities="camp.amenities" />
          </div>

          <!-- 근처 시설 -->
          <div>
            <h2 class="text-2xl font-semibold text-primary">근처 시설</h2>
            <NearAmenitiesList
              v-if="camp.nearAmenities && camp.nearAmenities.length > 0"
              :amenities="camp.nearAmenities"
            />
            <div v-else class="text-sm text-secondary">없음</div>
          </div>

          <!-- 운영 시간 -->
          <div>
            <h2 class="text-2xl font-semibold text-primary">운영 시간</h2>
            <OperatingHours :operatingHours="camp.operatingHours" />
          </div>

          <!-- 글램핑 시설 (옵션) -->
          <div v-if="camp.glampingInfo">
            <h2 class="text-2xl font-semibold text-primary">글램핑 시설</h2>
            <GlampingInfo :glampingInfo="camp.glampingInfo" />
          </div>
        </div>

        <!-- 캠핑장 홈페이지 링크 -->
        <div class="mt-6">
          <a
            :href="camp.campHomepage"
            target="_blank"
            class="text-blue-500 hover:underline text-lg font-medium"
          >
            캠핑장 홈페이지 방문
          </a>
        </div>

        <!-- 리뷰 섹션 -->
        <div class="mt-8">
          <!-- 리뷰 헤더와 평점 및 리뷰 수를 수평으로 정렬 -->
          <div
            class="flex flex-col md:flex-row md:justify-between md:items-center"
          >
            <h2 class="text-2xl font-semibold text-primary">리뷰</h2>
            <div class="flex items-center mt-2 md:mt-0">
              <StarRating :rating="camp.rating" size="25px" />
              <span class="ml-2 text-lg font-semibold text-primary">
                {{ camp.rating }}
              </span>
              <span class="ml-4 text-sm text-secondary">
                {{ totalReviewCount }} 리뷰
              </span>
            </div>
          </div>

          <!-- 리뷰 리스트 -->
          <Reviews :reviews="reviews" />

          <!-- 리뷰 작성 버튼 추가 -->
          <div class="mt-4 md:mt-0">
            <button
              @click="openReviewForm"
              class="px-4 py-2 bg-white text-black border border-gray-300 rounded-lg hover:bg-gray-100"
            >
              리뷰 작성
            </button>
          </div>

          <!-- 리뷰 페이지네이션 -->
          <div class="flex justify-center mt-4">
            <button
              @click="goToReviewPage(currentReviewPage - 1)"
              :disabled="currentReviewPage === 1"
              class="px-3 py-1 mx-1 bg-gray-200 rounded disabled:opacity-50"
            >
              이전
            </button>
            <button
              v-for="page in visibleReviewPages"
              :key="page"
              @click="goToReviewPage(page)"
              :class="[
                'px-3 py-1 mx-1 rounded',
                {
                  'bg-blue-500 text-white': page === currentReviewPage,
                  'bg-gray-200': page !== currentReviewPage,
                },
              ]"
            >
              {{ page }}
            </button>
            <button
              @click="goToReviewPage(currentReviewPage + 1)"
              :disabled="currentReviewPage === totalReviewPages"
              class="px-3 py-1 mx-1 bg-gray-200 rounded disabled:opacity-50"
            >
              다음
            </button>
          </div>
        </div>

        <!-- 예약 섹션 -->
        <div class="mt-8 bg-[#F4EFE6] p-6 rounded-lg">
          <h2 class="text-2xl font-semibold text-primary">예약하기</h2>
          <div class="mt-4 flex flex-col md:flex-row md:items-center md:gap-6">
            <!-- 날짜 선택 -->
            <div class="flex-1">
              <label class="block text-sm font-medium text-primary">
                예약 날짜
              </label>
              <Calendar @date-selected="handleDateSelected" />
            </div>
            <!-- 게스트 선택 -->
            <div class="flex-1 mt-4 md:mt-0">
              <label class="block text-sm font-medium text-primary">
                게스트
              </label>
              <Guests :guests="camp.guests" />
            </div>
          </div>
          <!-- 예약 버튼 -->
          <div class="mt-6">
            <ReserveButton @reserve="handleReserve" />
          </div>
        </div>
      </div>
    </main>

    <!-- 리뷰 작성 모달 -->
    <div
      v-if="isReviewFormVisible"
      class="fixed inset-0 flex justify-center items-end z-50 mt-40 translate-y-60"
    >
      <div
        class="bg-white p-6 rounded-lg w-11/12 md:w-1/2 lg:w-1/3 relative shadow-lg transform translate-y-60"
      >
        <button
          ref="closeButton"
          @click="closeReviewForm"
          class="absolute top-2 right-2 text-gray-500 hover:text-gray-700 text-2xl focus:outline-none"
          aria-label="리뷰 작성 모달 닫기"
        >
          &times;
        </button>
        <ReviewForm :campId="campId" />
      </div>
    </div>
  </div>
</template>

<script>
// Heroicons v2에서 Solid 아이콘 임포트
import { CheckCircleIcon, XCircleIcon } from "@heroicons/vue/24/solid";
import { ref, onMounted, computed, onBeforeUnmount, nextTick } from "vue";
import Header from "@/components/common/Header.vue";
import ImageGallery from "@/components/camp/ImageGallery.vue";
import AmenitiesList from "@/components/camp/AmenitiesList.vue";
import NearAmenitiesList from "@/components/camp/NearAmenitiesList.vue";
import OperatingHours from "@/components/camp/OperatingHours.vue";
import GlampingInfo from "@/components/camp/GlampingInfo.vue";
import Reviews from "@/components/camp/Reviews.vue";
import Calendar from "@/components/camp/Calendar.vue";
import Guests from "@/components/camp/Guests.vue";
import ReserveButton from "@/components/camp/ReserveButton.vue";
import StarRating from "@/components/camp/StarRating.vue";
import ReviewForm from "@/components/camp/ReviewForm.vue"; // 추가
import { getCampById, getPaginatedReviewsByCampId } from "@/api";

export default {
  name: "CampDetailView",
  components: {
    Header,
    ImageGallery,
    AmenitiesList,
    NearAmenitiesList,
    OperatingHours,
    GlampingInfo,
    Reviews,
    Calendar,
    Guests,
    ReserveButton,
    StarRating,
    ReviewForm, // 추가
    CheckCircleIcon,
    XCircleIcon,
  },
  props: {
    campId: {
      type: Number,
      required: true,
    },
  },
  setup(props) {
    const camp = ref(null);
    const loading = ref(true);
    const error = ref(null);

    // 리뷰 페이징 상태
    const reviews = ref([]);
    const currentReviewPage = ref(1);
    const totalReviewPages = ref(1);
    const totalReviewCount = ref(0);
    const reviewPageSize = 5;

    // 사용자 프로필 이미지 (예시)
    const profileImage = ref("https://example.com/default-image.jpg");

    // 리뷰 작성 모달 상태
    const isReviewFormVisible = ref(false);

    const closeButton = ref(null); // 닫기 버튼 ref

    const fetchCampDetail = async () => {
      try {
        const data = await getCampById(props.campId);
        console.log("Raw API Data:", data); // 원본 데이터 확인

        camp.value = {
          ...data,
          amenities: getAmenities(data),
          nearAmenities: getNearAmenities(data),
          operatingHours: getOperatingHours(data),
          glampingInfo: data.glampingInfo
            ? getGlampingInfo(data.glampingInfo)
            : null,
          rating: calculateAverageRating(data.reviews),
          images: getImages(data),
          // 리뷰는 별도로 페이징 처리할 것이므로 제외
        };

        // 총 리뷰 수 설정
        totalReviewCount.value = data.reviewCount || data.reviews.length;

        console.log("Processed Camp Data:", camp.value); // 처리된 데이터 확인
        console.log("Calculated Rating:", camp.value.rating); // 계산된 평점 확인
      } catch (err) {
        error.value = "캠핑장 정보를 가져오는 중 오류가 발생했습니다.";
        console.error(err);
      } finally {
        loading.value = false;
      }
    };

    const fetchReviews = async () => {
      try {
        const data = await getPaginatedReviewsByCampId(
          props.campId,
          currentReviewPage.value,
          reviewPageSize
        );
        reviews.value = data.items;
        currentReviewPage.value = data.currentPage;
        totalReviewPages.value = data.totalPages;
        totalReviewCount.value = data.totalItems;
      } catch (error) {
        console.error("리뷰를 가져오는 중 오류 발생:", error);
      }
    };

    const getAmenities = (data) => {
      const amenities = [];
      if (data.amenities.f_elec) amenities.push("전기");
      if (data.amenities.f_hotWater) amenities.push("온수");
      if (data.amenities.f_wifi) amenities.push("와이파이");
      if (data.amenities.f_firewood) amenities.push("장작");
      if (data.amenities.f_trails) amenities.push("산책로");
      if (data.amenities.f_swimming) amenities.push("수영장");
      if (data.amenities.f_playground) amenities.push("놀이터");
      if (data.amenities.f_market) amenities.push("시장");
      if (data.amenities.f_toilets)
        amenities.push(`화장실: ${data.amenities.f_toilets}개`);
      if (data.amenities.f_showers)
        amenities.push(`샤워장: ${data.amenities.f_showers}개`);
      if (data.amenities.f_sinks)
        amenities.push(`싱크대: ${data.amenities.f_sinks}개`);
      if (data.amenities.f_fireExtinguishers)
        amenities.push(`소화기: ${data.amenities.f_fireExtinguishers}개`);
      return amenities;
    };

    const getNearAmenities = (data) => {
      const amenities = [];
      if (data.nearAmenities.n_fishing) amenities.push("낚시터");
      if (data.nearAmenities.n_hiking) amenities.push("등산로");
      if (data.nearAmenities.n_beach) amenities.push("해변");
      if (data.nearAmenities.n_waterSports) amenities.push("수상 스포츠");
      if (data.nearAmenities.n_valley) amenities.push("계곡");
      if (data.nearAmenities.n_river) amenities.push("강");
      if (data.nearAmenities.n_pool) amenities.push("수영장");
      if (data.nearAmenities.n_youth) amenities.push("청소년 시설");
      if (data.nearAmenities.n_farm) amenities.push("농장");
      if (data.nearAmenities.n_kids) amenities.push("어린이 시설");
      return amenities;
    };

    const getOperatingHours = (data) => {
      return {
        spring: data.operatingHours.o_spring,
        summer: data.operatingHours.o_summer,
        fall: data.operatingHours.o_fall,
        winter: data.operatingHours.o_winter,
        weekday: data.operatingHours.o_weekday,
        weekend: data.operatingHours.o_weekend,
      };
    };

    const getGlampingInfo = (glampingInfoData) => {
      return {
        bed: glampingInfoData.g_bed,
        tv: glampingInfoData.g_tv,
        fridge: glampingInfoData.g_fridge,
        internet: glampingInfoData.g_internet,
        toilet: glampingInfoData.g_toilet,
        aircon: glampingInfoData.g_aircon,
        heater: glampingInfoData.g_heater,
        cookTool: glampingInfoData.g_cook_tool,
      };
    };

    const getImages = (data) => {
      // 실제 이미지 URL을 가져오는 로직을 구현하세요.
      // 여기서는 예시로 더미 이미지를 반환합니다.
      return [
        data.image1 ||
          "data:image/jpeg;base64,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",
        data.image2 ||
          "data:image/jpeg;base64,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",
        data.image3 ||
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8KyNQJhX3um9rsxOe9X3fbabPLkWhrnQ6mA&s",
        data.image4 ||
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHPWPPyRsQzkTqbcrAcUY1P_v1VmcRCQ_EHQ&s",
        data.image5 ||
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT0fXfsfvDt6TGHGHa2jM6XFgWuidZJanB6gA&s",
      ];
    };

    /**
     * 평균 평점을 계산하는 함수
     * @param {Array} reviews - 리뷰 배열
     * @returns {Number} - 평균 평점 (소수점 첫째 자리까지)
     */
    const calculateAverageRating = (reviews) => {
      if (!reviews || reviews.length === 0) return 0;
      let total = 0;
      let validCount = 0;

      reviews.forEach((review) => {
        // 'campRate' 필드 이름을 확인하세요 (백엔드 응답에 따라 변경)
        const rate = Number(review.campRate); // 'camp_rate'에서 'campRate'로 변경
        console.log(
          `Review ID: ${review.reviewId}, campRate: ${review.campRate}, Converted Rate: ${rate}`
        );
        if (!isNaN(rate) && rate >= 0 && rate <= 5) {
          total += rate;
          validCount += 1;
        } else {
          console.warn(
            `Invalid camp_rate found: ${review.campRate} in review ID ${review.reviewId}`
          );
        }
      });

      if (validCount === 0) return 0;

      const avg = total / validCount;
      console.log(
        `Total valid ratings: ${total}, Count: ${validCount}, Average: ${avg}`
      );
      return parseFloat(avg.toFixed(1));
    };

    /**
     * 현재 계절을 반환하는 함수
     * @returns {String} - 'spring', 'summer', 'fall', 'winter'
     */
    const getCurrentSeason = () => {
      const month = new Date().getMonth() + 1; // 0-based index
      if (month >= 3 && month <= 5) return "spring";
      if (month >= 6 && month <= 8) return "summer";
      if (month >= 9 && month <= 11) return "fall";
      return "winter";
    };

    /**
     * 현재 계절에 따른 운영 상태를 계산하는 함수
     * @returns {Boolean} - 운영 중이면 true, 아니면 false
     */
    const isOperatingNow = computed(() => {
      if (!camp.value || !camp.value.operatingHours) return false;
      const currentSeason = getCurrentSeason();
      const seasonHours = camp.value.operatingHours[currentSeason];
      // seasonHours이 true/false 또는 운영 시간을 나타내는 값에 따라 조건을 조정하세요.
      // 예시: 'o_spring': true 또는 false
      return seasonHours === true;
    });

    /**
     * 키보드 이벤트 핸들링: Esc 키로 모달 닫기
     */
    const handleKeyDown = (event) => {
      if (event.key === "Escape" && isReviewFormVisible.value) {
        closeReviewForm();
      }
    };

    onMounted(() => {
      fetchCampDetail();
      fetchReviews();
      window.addEventListener("keydown", handleKeyDown);
    });

    onBeforeUnmount(() => {
      window.removeEventListener("keydown", handleKeyDown);
    });

    const handleDateSelected = (date) => {
      console.log("선택된 날짜:", date);
      // 선택된 날짜를 처리하는 로직을 추가하세요.
    };

    const handleReserve = () => {
      console.log("예약 버튼 클릭됨");
      // 예약 로직을 추가하세요.
    };

    // 페이지네이션 계산
    const visibleReviewPages = computed(() => {
      const pages = [];
      const maxVisible = 5;
      let start = Math.max(
        currentReviewPage.value - Math.floor(maxVisible / 2),
        1
      );
      let end = start + maxVisible - 1;

      if (end > totalReviewPages.value) {
        end = totalReviewPages.value;
        start = Math.max(end - maxVisible + 1, 1);
      }

      for (let i = start; i <= end; i++) {
        pages.push(i);
      }
      return pages;
    });

    const goToReviewPage = (page) => {
      if (page < 1 || page > totalReviewPages.value) return;
      currentReviewPage.value = page;
      fetchReviews();
    };

    // 리뷰 작성 모달 열기
    const openReviewForm = () => {
      isReviewFormVisible.value = true;
      // 스크롤 방지 (선택 사항)
      document.body.style.overflow = "hidden";
      // 모달이 열린 후 닫기 버튼에 포커스 이동
      nextTick(() => {
        closeButton.value.focus();
      });
    };

    // 리뷰 작성 모달 닫기
    const closeReviewForm = () => {
      isReviewFormVisible.value = false;
      // 스크롤 방지 해제 (선택 사항)
      document.body.style.overflow = "";
    };

    return {
      camp,
      loading,
      error,
      reviews,
      currentReviewPage,
      totalReviewPages,
      totalReviewCount,
      visibleReviewPages,
      goToReviewPage,
      handleDateSelected,
      handleReserve,
      profileImage, // 프로필 이미지 전달
      isOperatingNow, // 운영 상태
      CheckCircleIcon,
      XCircleIcon,
      isReviewFormVisible, // 리뷰 작성 모달 상태
      openReviewForm, // 리뷰 작성 모달 열기
      closeReviewForm, // 리뷰 작성 모달 닫기
      closeButton, // 닫기 버튼 ref
    };
  },
};
</script>

<style scoped>
.camp-detail-container {
  background-color: #f9f9f9;
  min-height: 100vh;
}

.camp-info h1 {
  color: #1c160c;
}

.camp-info p {
  color: #a18249;
}

.mt-6 {
  margin-top: 1.5rem;
}

.mt-8 {
  margin-top: 2rem;
}

.review-form-container {
  background-color: #ffffff;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-height: 80vh; /* 최대 높이 설정 */
  overflow-y: auto; /* 내용이 넘칠 경우 스크롤 */
}

/* 포커스 스타일 */
button:focus {
  outline: none;
  box-shadow: 0 0 0 2px #3182ce; /* Focus 스타일 */
}
</style>
