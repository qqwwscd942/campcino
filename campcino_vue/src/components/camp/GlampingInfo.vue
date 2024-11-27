<!-- src/components/camp/GlampingInfo.vue -->
<template>
  <ul class="list-disc pl-5">
    <li v-if="glampingInfo.bed" class="flex items-center text-gray-700">
      <font-awesome-icon :icon="bedIcon" class="mr-2" />
      침대 제공
    </li>
    <li v-if="glampingInfo.tv" class="flex items-center text-gray-700">
      <font-awesome-icon :icon="tvIcon" class="mr-2" />
      TV 제공
    </li>
    <li v-if="glampingInfo.fridge" class="flex items-center text-gray-700">
      <font-awesome-icon :icon="fridgeIcon" class="mr-2" />
      냉장고 제공
    </li>
    <li v-if="glampingInfo.internet" class="flex items-center text-gray-700">
      <font-awesome-icon :icon="internetIcon" class="mr-2" />
      인터넷 제공
    </li>
    <li v-if="glampingInfo.toilet" class="flex items-center text-gray-700">
      <font-awesome-icon :icon="toiletIcon" class="mr-2" />
      화장실 제공
    </li>
    <li v-if="glampingInfo.aircon" class="flex items-center text-gray-700">
      <font-awesome-icon :icon="airconIcon" class="mr-2" />
      에어컨 제공
    </li>
    <li v-if="glampingInfo.heater" class="flex items-center text-gray-700">
      <font-awesome-icon :icon="heaterIcon" class="mr-2" />
      히터 제공
    </li>
    <li v-if="glampingInfo.cookTool" class="flex items-center text-gray-700">
      <font-awesome-icon :icon="cookToolIcon" class="mr-2" />
      조리 도구 제공
    </li>
  </ul>
</template>

<script>
import {
  faBed,
  faTv,
  faUtensils,
  faWifi,
  faRestroom,
  faSnowflake,
  faFire,
  faTools,
  faQuestionCircle,
} from "@fortawesome/free-solid-svg-icons";

export default {
  name: "GlampingInfo",
  props: {
    glampingInfo: {
      type: Object,
      required: true,
    },
  },
  computed: {
    bedIcon() {
      return faBed;
    },
    tvIcon() {
      return faTv;
    },
    fridgeIcon() {
      return faUtensils; // 냉장고 대신 조리 도구 아이콘 사용
    },
    internetIcon() {
      return faWifi;
    },
    toiletIcon() {
      return faRestroom;
    },
    airconIcon() {
      return faSnowflake; // 에어컨을 나타내는 아이콘
    },
    heaterIcon() {
      return faFire;
    },
    cookToolIcon() {
      return faTools;
    },
  },
};
</script>

<style scoped>
ul {
  margin-top: 8px;
}
</style>
