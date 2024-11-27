<!-- src/components/camp/MapSection.vue -->
<template>
  <div ref="mapContainer" class="w-full h-full"></div>
</template>

<script>
import { onMounted, ref, watch, onBeforeUnmount } from "vue";

export default {
  name: "MapSection",
  props: {
    markers: {
      type: Array,
      default: () => [],
    },
  },
  setup(props, { expose }) {
    const mapContainer = ref(null);
    let map = null;
    let kakaoMarkers = [];
    let infowindows = [];
    let resizeObserver = null;

    onMounted(() => {
      if (window.kakao && window.kakao.maps) {
        initMap();
      } else {
        const script = document.createElement("script");
        script.src =
          "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=850d7b0fe59c47b55e0c0511520c3335&libraries=services,clusterer,drawing";
        script.onload = () => {
          window.kakao.maps.load(() => {
            initMap();
          });
        };
        document.head.appendChild(script);
      }
    });

    onBeforeUnmount(() => {
      if (resizeObserver) {
        resizeObserver.disconnect();
      }
    });

    function initMap() {
      const mapOption = {
        center: new kakao.maps.LatLng(35.159545, 126.852601),
        level: 12,
      };
      map = new kakao.maps.Map(mapContainer.value, mapOption);
      updateMarkers();
      observeResize(); // 지도 컨테이너 크기 변화 감지 시작

      // 지도 레벨 변경 이벤트 리스너 추가
      kakao.maps.event.addListener(map, "zoom_changed", function () {
        const currentLevel = map.getLevel();
        if (currentLevel >= 8) {
          closeAllInfowindows();
        }
      });
    }

    watch(
      () => props.markers,
      () => {
        updateMarkers();
        updateMapCenter();
      },
      { deep: true }
    );

    function updateMarkers() {
      if (!map) return;

      // 기존 마커 및 인포윈도우 제거
      kakaoMarkers.forEach((marker) => marker.setMap(null));
      kakaoMarkers = [];
      infowindows = [];

      // 새로운 마커 추가
      props.markers.forEach((markerData, index) => {
        const marker = new kakao.maps.Marker({
          map,
          position: markerData.latlng,
          title: markerData.title,
        });

        const infowindow = new kakao.maps.InfoWindow({
          content: `<div style="padding:5px;">${markerData.title}</div>`,
        });

        // 마커 클릭 이벤트
        kakao.maps.event.addListener(marker, "click", function () {
          map.setLevel(3);
          map.setCenter(marker.getPosition());
          infowindow.open(map, marker);
        });

        kakaoMarkers.push(marker);
        infowindows.push(infowindow);
      });
    }

    function updateMapCenter() {
      if (!map || kakaoMarkers.length === 0) return;

      const bounds = new kakao.maps.LatLngBounds();
      kakaoMarkers.forEach((marker) => {
        bounds.extend(marker.getPosition());
      });

      map.setBounds(bounds);
    }

    function focusMarker(markerIndex) {
      if (kakaoMarkers[markerIndex]) {
        const marker = kakaoMarkers[markerIndex];
        const infowindow = infowindows[markerIndex];
        map.setLevel(3);
        map.setCenter(marker.getPosition());
        infowindow.open(map, marker);
      }
    }

    // 지도 컨테이너의 크기 변화를 감지하여 지도 업데이트
    function observeResize() {
      resizeObserver = new ResizeObserver(() => {
        if (map) {
          map.relayout(); // 지도 크기 변경 알림
          updateMapCenter(); // 지도 중심 및 영역 업데이트
        }
      });
      resizeObserver.observe(mapContainer.value);
    }

    // 모든 인포윈도우 닫기 함수
    function closeAllInfowindows() {
      infowindows.forEach((infowindow) => {
        infowindow.close();
      });
    }

    // 지도 중심 업데이트 함수를 노출하여 부모 컴포넌트에서 호출 가능하도록 설정
    expose({ focusMarker, updateMapCenter });

    return {
      mapContainer,
    };
  },
};
</script>

<style scoped>
div {
  width: 100%;
  height: 100%;
}
</style>
