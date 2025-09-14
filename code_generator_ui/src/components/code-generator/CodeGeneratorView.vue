<template>
  <div class="common-layout">
    <div class="header">
      <CodeGeneratorHeader :form="form"></CodeGeneratorHeader>
    </div>
    <div class="main">
      <CodeGeneratorMain :form="form"></CodeGeneratorMain>
    </div>
  </div>
</template>


<script setup lang="ts" name="CodeGeneratorView">
import CodeGeneratorHeader from "@/components/code-generator/layout/CodeGeneratorHeader.vue";
import CodeGeneratorMain from "@/components/code-generator/layout/CodeGeneratorMain.vue";
import type RequestType from "@/types/request_type";
import { ref, type Ref } from "vue";

// 定义向后端请求的form
const form: Ref<RequestType> = ref({
  databaseId: null,
  databaseType: '',
  host: '',
  port: 3306,
  databaseName: '',
  username: '',
  password: '',
  changeFlag: true,
  pattern: '',
  table: '',
  configName: '',
  createEntity: true,
  createRepository: true,
  createController: false,
  createService: false,
  createSimpleSql: false,
  prePackageName: '',
  fileName: ''
});
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
