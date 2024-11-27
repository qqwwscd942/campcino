<template>
  <div class="operating-hours">
    <p><strong>평일:</strong> {{ operatingHours.weekday ? "운영" : "휴무" }}</p>
    <p><strong>주말:</strong> {{ operatingHours.weekend ? "운영" : "휴무" }}</p>
    <p><strong>봄:</strong> {{ operatingHours.spring ? "운영" : "휴무" }}</p>
    <p><strong>여름:</strong> {{ operatingHours.summer ? "운영" : "휴무" }}</p>
    <p><strong>가을:</strong> {{ operatingHours.fall ? "운영" : "휴무" }}</p>
    <p><strong>겨울:</strong> {{ operatingHours.winter ? "운영" : "휴무" }}</p>
  </div>
</template>

<script>
export default {
  name: "OperatingHours",
  props: {
    operatingHours: {
      type: Object,
      required: true,
    },
  },
};
</script>

<style scoped>
.operating-hours p {
  margin: 4px 0;
}
</style>
