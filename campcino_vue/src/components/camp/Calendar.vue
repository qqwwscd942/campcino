<!-- src/components/camp/Calendar.vue -->
<template>
  <div>
    <!-- 간단한 달력 UI 예시 -->
    <div class="grid grid-cols-7 gap-2">
      <div
        v-for="day in weekDays"
        :key="day"
        class="text-center text-sm font-bold text-[#1C160C]"
      >
        {{ day }}
      </div>
      <button
        v-for="date in dates"
        :key="date"
        @click="selectDate(date)"
        :class="[
          'w-full h-10 rounded-full flex items-center justify-center',
          selectedDate === date
            ? 'bg-[#019863] text-white'
            : 'bg-[#F4EFE6] text-[#1C160C]',
        ]"
      >
        {{ date }}
      </button>
    </div>
  </div>
</template>

<script>
import { ref, computed } from "vue";

export default {
  name: "Calendar",
  emits: ["date-selected"],
  setup(props, { emit }) {
    const selectedDate = ref(null);
    const weekDays = ["S", "M", "T", "W", "T", "F", "S"];

    const dates = computed(() => {
      // 현재 월의 날짜를 생성하는 로직을 추가하세요.
      // 여기서는 1일부터 30일까지의 날짜를 예시로 사용합니다.
      return Array.from({ length: 30 }, (_, i) => i + 1);
    });

    const selectDate = (date) => {
      selectedDate.value = date;
      emit("date-selected", date);
    };

    return {
      weekDays,
      dates,
      selectedDate,
      selectDate,
    };
  },
};
</script>

<style scoped></style>
