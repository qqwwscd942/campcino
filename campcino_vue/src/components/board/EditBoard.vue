<template>
  <div class="edit-post-container">
    <h2 class="text-2xl font-bold mb-4">게시글 수정</h2>
    <form @submit.prevent="submitEdit">
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
          @click="cancelEdit"
          class="flex-1 px-4 py-2 bg-gray-300 text-black rounded hover:bg-gray-400"
        >
          취소
        </button>
        <button
          type="submit"
          class="flex-1 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
        >
          저장
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getBoardById, updateBoard } from "@/api";

export default {
  name: "EditBoard",
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  setup(props) {
    const router = useRouter();
    const post = ref({
      title: "",
      content: "",
      category: "",
      imgUrl: "",
    });

    const categories = ["질문", "추천", "수다", "장비", "기타"];

    // 게시글 데이터 로드
    const fetchPost = async () => {
      try {
        const response = await getBoardById(props.id);
        post.value = {
          title: response.boardTitle,
          content: response.boardContent,
          category: response.category,
          imgUrl: response.imgUrl,
        };
      } catch (error) {
        console.error("게시글을 불러오는 중 오류가 발생했습니다:", error);
        alert("게시글을 불러올 수 없습니다.");
        router.push("/boards"); // 오류 발생 시 목록으로 이동
      }
    };

    // 게시글 수정 제출
    const submitEdit = async () => {
      try {
        await updateBoard(props.id, {
          boardTitle: post.value.title,
          boardContent: post.value.content,
          category: post.value.category,
          imgUrl: post.value.imgUrl,
        });
        alert("게시글이 성공적으로 수정되었습니다!");
        router.push(`/boards/${props.id}`); // 상세 페이지로 이동
      } catch (error) {
        console.error("게시글 수정 중 오류가 발생했습니다:", error);
        alert("게시글 수정에 실패했습니다.");
      }
    };

    // 수정 취소
    const cancelEdit = () => {
      router.push(`/boards/${props.id}`); // 상세 페이지로 돌아가기
    };

    onMounted(() => {
      fetchPost(); // 컴포넌트 마운트 시 게시글 로드
    });

    return {
      post,
      categories,
      submitEdit,
      cancelEdit,
    };
  },
};
</script>

<style scoped>
.edit-post-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 2rem;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .edit-post-container {
    padding: 1rem;
  }

  .flex > * {
    margin-bottom: 0.5rem;
  }
}
</style>
