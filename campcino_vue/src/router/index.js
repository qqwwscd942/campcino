import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/stores/auth"; // Pinia 스토어 가져오기
import BoardView from "@/views/board/BoardView.vue";
import MainView from "@/views/MainView.vue";
import CampDetailView from "@/views/camp/CampDetailView.vue";
import CampStoreView from "@/views/store/CampStoreView.vue";
import CampStoreDetailView from "@/views/store/CampStoreDetailView.vue";
import LoginView from "@/views/user/LoginView.vue";
import JoinView from "@/views/user/JoinView.vue";
import CampSearchView from "@/views/camp/CampSearchView.vue";
import ReviewListView from "@/views/review/ReviewListView.vue";
import ReviewForm from "@/components/camp/ReviewForm.vue";
import BoardDetail from "@/components/board/BoardDetail.vue";
import CreateBoard from "@/components/board/CreateBoard.vue";
import EditBoard from "@/components/board/EditBoard.vue"; // 게시글 수정 컴포넌트 추가
import ProfileView from "@/views/user/ProfileView.vue";

const routes = [
  { path: "/", name: "MainView", component: MainView },
  { path: "/boards", name: "BoardView", component: BoardView },
  {
    path: "/boards/:id",
    name: "BoardDetail",
    component: BoardDetail,
    props: true,
  },
  { path: "/boards/create", name: "CreateBoard", component: CreateBoard },
  {
    path: "/boards/:id/edit",
    name: "EditBoard",
    component: EditBoard,
    props: true,
    meta: { requiresAuth: true }, // 인증 필요
  },
  {
    path: "/camps/:id",
    name: "CampDetail",
    component: CampDetailView,
    props: true,
  },
  {
    path: "/camps/:id/review",
    name: "ReviewForm",
    component: ReviewForm,
    props: true,
  },
  { path: "/main", name: "Main", component: MainView },
  { path: "/stores", name: "CampStore", component: CampStoreView },
  { path: "/search", name: "CampSearchView", component: CampSearchView },
  {
    path: "/stores/:id",
    name: "CampStoreDetail",
    component: CampStoreDetailView,
    props: true,
  },
  {
    path: "/login",
    name: "Login",
    component: LoginView,
    beforeEnter: (to, from, next) => {
      const authStore = useAuthStore();
      if (authStore.isAuthenticated) {
        next("/search");
      } else {
        next();
      }
    },
  },
  { path: "/join", name: "Join", component: JoinView },
  { path: "/reviews", name: "ReviewList", component: ReviewListView },
  {
    path: "/profile",
    name: "Profile",
    component: ProfileView,
    meta: { requiresAuth: true }, // 인증 필요
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
  },
});

// 인증 확인을 위한 글로벌 네비게이션 가드
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next("/login");
  } else {
    next();
  }
});

export default router;
