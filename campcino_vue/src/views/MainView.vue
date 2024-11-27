
<template>
  <div class="main-container">
    <img
      src="@/assets/logo4_no.png"
      alt="Campcino"
      class="logo"
      @click="goToLogin"
    />
  </div>
</template>

<script>
export default {
  name: "MainView",
  methods: {
    goToLogin() {
      this.$router.push({ name: "Login" });
    },
  },
};
</script>

<style scoped>
.main-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f8f9fa; /* Optional: Light background */
}

.logo {
  width: 500px; /* Adjust the size as needed */
  cursor: pointer;
}
</style>
