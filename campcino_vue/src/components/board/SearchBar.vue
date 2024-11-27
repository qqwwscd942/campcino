<template>
  <label class="flex flex-col min-w-40 h-10 max-w-64">
    <div class="flex w-full flex-1 items-stretch rounded h-full">
      <div
        class="text-[#A18249] flex bg-[#F4EFE6] items-center justify-center pl-4 rounded-l"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          fill="currentColor"
          viewBox="0 0 256 256"
          class="w-6 h-6"
        >
          <path
            d="M229.66,218.34l-50.07-50.06a88.11,88.11,0,1,0-11.31,11.31l50.06,50.07a8,8,0,0,0,11.32-11.32ZM40,112a72,72,0,1,1,72,72A72.08,72.08,0,0,1,40,112Z"
          ></path>
        </svg>
      </div>
      <input
        type="text"
        :placeholder="placeholder"
        class="flex w-full flex-1 resize-none overflow-hidden rounded-r text-[#1C160C] bg-[#F4EFE6] px-4 placeholder:text-[#A18249] text-base font-normal focus:outline-none"
        :value="modelValue"
        @input="$emit('update:modelValue', $event.target.value)"
      />
    </div>
  </label>
</template>

<script>
export default {
  name: "SearchBar",
  props: {
    modelValue: {
      type: String,
      default: "",
    },
    placeholder: {
      type: String,
      default: "Search",
    },
  },
};
</script>

<style scoped>
/* 필요에 따라 스타일을 추가하세요 */
</style>
