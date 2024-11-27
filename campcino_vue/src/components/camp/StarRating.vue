<!-- src/components/camp/StarRating.vue -->
<template>
  <div class="flex" :aria-label="`평점 ${rating}점`" role="img">
    <svg
      v-for="star in 5"
      :key="star"
      xmlns="http://www.w3.org/2000/svg"
      :width="size"
      :height="size"
      fill="currentColor"
      viewBox="0 0 256 256"
      :class="star <= rating ? 'text-filled' : 'text-empty'"
      aria-hidden="true"
    >
      <path
        d="M234.5,114.38l-45.1,39.36,13.51,58.6a16,16,0,0,1-23.84,17.34l-51.11-31-51,31a16,16,0,0,1-23.84-17.34L66.61,153.8,21.5,114.38a16,16,0,0,1,9.11-28.06l59.46-5.15,23.21-55.36a15.95,15.95,0,0,1,29.44,0h0L166,81.17l59.44,5.15a16,16,0,0,1,9.11,28.06Z"
      ></path>
    </svg>
  </div>
</template>

<script>
export default {
  name: "StarRating",
  props: {
    rating: {
      type: Number,
      required: true,
      validator: (value) => value >= 0 && value <= 5,
    },
    size: {
      type: String,
      default: "20px",
    },
  },
};
</script>

<style scoped>
.flex {
  display: flex;
}

.text-filled {
  color: #019863;
}

.text-empty {
  color: #e9dfce;
}
</style>
