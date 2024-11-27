<!-- src/components/camp/ReserveButton.vue -->
<template>
  <button
    @click="reserve"
    class="w-full bg-[#019863] text-white py-3 rounded-lg font-semibold hover:bg-[#017a57]"
  >
    예약하기
  </button>
</template>

<script>
export default {
  name: "ReserveButton",
  emits: ["reserve"],
  methods: {
    reserve() {
      this.$emit("reserve");
    },
  },
};
</script>

<style scoped></style>
