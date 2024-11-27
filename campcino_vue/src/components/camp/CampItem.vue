<template>
    <div class="flex flex-col gap-3">
      <div class="aspect-square bg-cover rounded" :style="{ backgroundImage: `url(${camp.image})` }"></div>
      <div>
        <p class="text-base font-medium">{{ camp.name }}</p>
        <p class="text-sm text-[#A18249]">{{ camp.price }}</p>
        <p class="text-sm text-[#A18249]">{{ camp.availability }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "CampItem",
    props: {
      camp: {
        type: Object,
        required: true,
      },
    },
  };
  </script>
  