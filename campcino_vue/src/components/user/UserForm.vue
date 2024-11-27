<!-- src/components/user/UserForm.vue -->

<template>
  <form @submit.prevent>
    <div class="form-group">
      <label for="email">이메일</label>
      <input
        v-model="localUserData.email"
        type="email"
        id="email"
        class="form-control"
        placeholder="이메일 입력"
      />
      <span v-if="errors.email" class="error-text">{{ errors.email }}</span>
    </div>

    <div class="form-group">
      <label for="phone">전화번호</label>
      <input
        v-model="localUserData.phone"
        type="tel"
        id="phone"
        class="form-control"
        placeholder="전화번호 입력 (예: 010-1234-5678)"
      />
      <span v-if="errors.phone" class="error-text">{{ errors.phone }}</span>
    </div>

    <div class="form-group">
      <label for="gender">성별</label>
      <select v-model="localUserData.gender" id="gender" class="form-control">
        <option value="male">남성</option>
        <option value="female">여성</option>
        <option value="other">기타</option>
      </select>
      <span v-if="errors.gender" class="error-text">{{ errors.gender }}</span>
    </div>
  </form>
</template>

<script>
export default {
  name: "UserForm",
  props: {
    modelValue: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      localUserData: { ...this.modelValue },
      errors: {},
    };
  },
  watch: {
    localUserData: {
      handler(newData) {
        this.$emit("update:modelValue", newData);
        this.validate();
      },
      deep: true,
    },
    modelValue: {
      handler(newVal) {
        this.localUserData = { ...newVal };
      },
      deep: true,
    },
  },
  methods: {
    validate() {
      this.errors = {};

      // 이메일 검증
      if (!this.localUserData.email) {
        this.errors.email = "이메일을 입력해주세요.";
      } else if (!/\S+@\S+\.\S+/.test(this.localUserData.email)) {
        this.errors.email = "유효한 이메일 형식이 아닙니다.";
      }

      // 전화번호 검증
      if (!this.localUserData.phone) {
        this.errors.phone = "전화번호를 입력해주세요.";
      } else if (!/^\d{3}-\d{3,4}-\d{4}$/.test(this.localUserData.phone)) {
        this.errors.phone = "유효한 전화번호 형식이 아닙니다. 예: 010-1234-5678";
      }

      // 성별 검증
      if (!this.localUserData.gender) {
        this.errors.gender = "성별을 선택해주세요.";
      }
    },
  },
  mounted() {
    this.validate();
  },
};
</script>

<style scoped>
.form-group {
  margin-bottom: 1rem;
}

.error-text {
  color: red;
  font-size: 0.875rem;
}
</style>
