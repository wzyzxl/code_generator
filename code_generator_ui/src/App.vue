<template>
  <div class="app">
    <RouterView></RouterView>
  </div>
</template>

<script setup lang="ts" name="App">
import { RouterView } from "vue-router";

</script>

<style scoped>
.app {
  width: 100%;
  height: 100vh;
  padding: 0;
  margin: 0;
}
</style>
