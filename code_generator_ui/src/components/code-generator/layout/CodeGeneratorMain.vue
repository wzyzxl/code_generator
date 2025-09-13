<template>
  <el-row>
    <el-col :span="8">
      <ConnectionPanel :form="form"></ConnectionPanel>
    </el-col>
    <el-col :span="8">
      <StructurePanel :form="form"></StructurePanel>
    </el-col>
    <el-col :span="8">
      <ConfigPanel :form="form" @changeFileNames="changeFileNames" @showCodeBox="showCodeBox"
        @hideCodeBox="hideCodeBox"></ConfigPanel>
    </el-col>
  </el-row>
  <hr>
  <!-- 代码区域 -->
  <el-row>
    <CodePreview :form="form" :fileNames="fileNames" v-if="showCode"></CodePreview>
  </el-row>
</template>

<script setup lang="ts" name="CodeGeneratorMain">
import ConnectionPanel
  from "@/components/code-generator/sections/connection-panel/ConnectionPanel.vue";
import StructurePanel
  from "@/components/code-generator/sections/structure-panel/StructurePanel.vue";
import ConfigPanel from "@/components/code-generator/sections/config-panel/ConfigPanel.vue";
import CodePreview from "@/components/code-generator/sections/preview-panel/CodePreview.vue";
import type RequestType from '@/types/request_type';
import { ref, type Ref } from "vue";

// 声明接收 form prop
const props = defineProps<{
  form: RequestType
}>();

// 文件名
const fileNames: Ref<Array<string>> = ref([]);

// 显示代码
let showCode: Ref<boolean> = ref(false);

// 更改文件名称
function changeFileNames(res: Array<string>) {
  fileNames.value = res;
}

// 显示预览框
function showCodeBox() {
  showCode.value = true;
}

// 隐藏预览框
function hideCodeBox() {
  showCode.value = false;
}
</script>

<style scoped></style>
