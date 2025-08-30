<template>
  <el-row>
    <el-col :span="8">
      <ConnectionPanel></ConnectionPanel>
    </el-col>
    <el-col :span="8">
      <StructurePanel></StructurePanel>
    </el-col>
    <el-col :span="8">
      <ConfigPanel></ConfigPanel>
    </el-col>
  </el-row>
  <el-row>
    <CodePreview></CodePreview>
  </el-row>
</template>

<script setup lang="ts" name="CodeGeneratorMain">
import ConnectionPanel
  from "@/components/code-generator/sections/connection-panel/ConnectionPanel.vue";
import StructurePanel
  from "@/components/code-generator/sections/structure-panel/StructurePanel.vue";
import ConfigPanel from "@/components/code-generator/sections/config-panel/ConfigPanel.vue";
import CodePreview from "@/components/code-generator/sections/preview-panel/CodePreview.vue";
import type RequestType from "@/types/requestType.ts";
import {type Ref, ref} from "vue";
import {useDatabaseStore} from "@/stores/database.ts";

// 调用数据库配置仓库
const databaseStore = useDatabaseStore();

// 定义向后端请求的form
const form: Ref<RequestType> = ref({
  databaseId: databaseStore.databaseId,
  databaseType: databaseStore.databaseType,
  host: databaseStore.host,
  port: databaseStore.port,
  databaseName: databaseStore.databaseName,
  changeFlag: true,
  pattern: '',
  table: '',
  configName: '',
  createEntity: true,
  createRepository: true,
  createController: false,
  createService: false,
  createSimpleSql: false,
  prePackageName: ''
});

console.log(form.value);
</script>

<style scoped></style>
