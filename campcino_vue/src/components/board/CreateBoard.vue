<template>
  <div class="create-post-container">
    <h2 class="text-2xl font-bold mb-4">새 게시글 작성</h2>
    <form @submit.prevent="submitPost">
      <div class="mb-4">
        <label class="block text-gray-700">제목</label>
        <input
          v-model="post.title"
          type="text"
          class="w-full border rounded p-2"
          required
        />
      </div>
      <div class="mb-4">
        <label class="block text-gray-700">내용</label>
        <textarea
          v-model="post.content"
          class="w-full border rounded p-2"
          rows="5"
          required
        ></textarea>
      </div>
      <div class="mb-4">
        <label class="block text-gray-700">카테고리</label>
        <select
          v-model="post.category"
          class="w-full border rounded p-2"
          required
        >
          <option value="" disabled>카테고리를 선택하세요</option>
          <option
            v-for="category in categories"
            :key="category"
            :value="category"
          >
            {{ category }}
          </option>
        </select>
      </div>
      <div class="mb-4">
        <label class="block text-gray-700">이미지 URL (선택 사항)</label>
        <input
          v-model="post.imgUrl"
          type="text"
          class="w-full border rounded p-2"
        />
      </div>
      <div class="flex space-x-4">
        <button
          type="button"
          @click="cancelPost"
          class="flex-1 px-4 py-2 bg-gray-300 text-black rounded hover:bg-gray-400"
        >
          취소
        </button>
        <button
          type="submit"
          class="flex-1 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
        >
          작성
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/auth"; // 사용자 인증 정보 가져오기
import { createBoard } from "@/api";

export default {
  name: "CreateBoard",
  setup() {
    const router = useRouter();
    const authStore = useAuthStore(); // 사용자 인증 정보
    const post = ref({
      title: "",
      content: "",
      category: "",
      imgUrl: "",
    });

    const categories = ["질문", "추천", "수다", "장비", "기타"];

    const submitPost = async () => {
      try {
        const userId = authStore.user.userId; // 로그인한 사용자 ID 가져오기
        if (!userId) {
          alert("로그인이 필요합니다!");
          return;
        }

        // 게시글 생성 API 요청
        await createBoard({
          boardTitle: post.value.title,
          boardContent: post.value.content,
          category: post.value.category,
          imgUrl: post.value.imgUrl,
          userId, // 사용자 ID 포함
        });
        alert("게시글이 성공적으로 작성되었습니다!");
        router.push("/boards");
      } catch (error) {
        console.error("게시글 작성 중 오류가 발생했습니다:", error);
        alert("게시글 작성에 실패했습니다.");
      }
    };

    const cancelPost = () => {
      router.back(); // 이전 페이지로 이동
    };

    return {
      post,
      categories,
      submitPost,
      cancelPost,
    };
  },
};
</script>

<style scoped>
.create-post-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 2rem;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .create-post-container {
    padding: 1rem;
  }

  .flex > * {
    margin-bottom: 0.5rem;
  }
}
</style>
