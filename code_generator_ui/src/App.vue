<template>
  <div class="app">
    <CodeGeneratorView></CodeGeneratorView>
  </div>
</template>

<script setup lang="ts" name="App">
import CodeGeneratorView from "@/components/code-generator/CodeGeneratorView.vue";
</script>

<style scoped>
.app {
  width: 100%;
  height: 100vh;
  padding: 0;
  margin: 0;
}
</style>
