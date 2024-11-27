<!-- src/components/camp/Guests.vue -->
<template>
  <div class="flex items-center bg-[#F4EFE6] p-2 rounded-lg">
    <span class="text-[#1C160C] mr-2">👤</span>
    <input
      type="number"
      v-model.number="guestCount"
      min="1"
      class="w-16 text-center bg-transparent border-none text-[#1C160C] focus:outline-none"
    />
    <span class="text-[#A18249]">명</span>
  </div>
</template>

<script>
import { ref, watch } from "vue";

export default {
  name: "Guests",
  props: {
    guests: {
      type: Number,
      required: true,
    },
  },
  emits: ["update:guests"],
  setup(props, { emit }) {
    const guestCount = ref(props.guests);

    watch(guestCount, (newCount) => {
      emit("update:guests", newCount);
    });

    return {
      guestCount,
    };
  },
};
</script>

<style scoped></style>
