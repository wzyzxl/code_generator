<template>
  <div class="common-layout">
    <div class="header">
      <CodeGeneratorHeader></CodeGeneratorHeader>
    </div>
    <div class="main">
      <CodeGeneratorMain></CodeGeneratorMain>
    </div>
  </div>
</template>


<script setup lang="ts" name="CodeGeneratorView">
import CodeGeneratorHeader from "@/components/code-generator/layout/CodeGeneratorHeader.vue";
import CodeGeneratorMain from "@/components/code-generator/layout/CodeGeneratorMain.vue";

// 表单数据
</script>

<style scoped>
.common-layout {
  height: 100%;
}
.header {
  height: 100px;
}
.main {
  height: calc(100% - 100px);
}
</style>
