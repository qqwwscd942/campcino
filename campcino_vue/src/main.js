// src/main.js
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import { createPinia } from "pinia"; // Pinia 임포트 추가
import "./assets/index.css"; // Tailwind CSS
import axios from "axios";
import "@fortawesome/fontawesome-free/css/all.css";

// Font Awesome 설정
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
  faBolt,
  faHotTub,
  faWifi,
  faFire,
  faWalking,
  faSwimmer,
  faPlay,
  faShoppingCart,
  faRestroom,
  faShower,
  faSink,
  faFish,
  faHiking,
  faUmbrellaBeach,
  faWater,
  faMountain,
  faSwimmingPool,
  faUsers,
  faSeedling,
  faChild,
  faBed,
  faTv,
  faUtensils,
  faSnowflake,
  faTools,
  faQuestionCircle,
} from "@fortawesome/free-solid-svg-icons";

// 사용할 아이콘을 라이브러리에 추가
library.add(
  faBolt,
  faHotTub,
  faWifi,
  faFire,
  faWalking,
  faSwimmer,
  faPlay,
  faShoppingCart,
  faRestroom,
  faShower,
  faSink,
  faFish,
  faHiking,
  faUmbrellaBeach,
  faWater,
  faMountain,
  faSwimmingPool,
  faUsers,
  faSeedling,
  faChild,
  faBed,
  faTv,
  faUtensils,
  faSnowflake,
  faTools,
  faQuestionCircle
);

// Axios 기본 설정
axios.defaults.baseURL = "http://localhost:8080"; // 백엔드 서버 URL

// Pinia 및 Vue 앱 설정
import { useAuthStore } from "@/stores/auth"; // Auth 스토어 가져오기

const app = createApp(App);
const pinia = createPinia(); // Pinia 인스턴스 생성 추가
app.component("font-awesome-icon", FontAwesomeIcon);
app.use(pinia); // Pinia 사용 추가
app.use(router);

// 로그인 상태 초기화
const authStore = useAuthStore(pinia); // Pinia 인스턴스를 전달하여 스토어 초기화
authStore.initializeAuth(); // 로그인 상태 확인 및 초기화

app.mount("#app");
