<template>
  <div class="code-box">
    <!-- 导航栏 -->
    <el-menu default-active="0" mode="horizontal" @select="handleSelect">
      <el-menu-item v-for="(fileName, index) in props.fileNames" :index="index + ''" :key="index">{{ fileName
        }}</el-menu-item>
    </el-menu>

    <!-- 代码预览 -->
    <PreviewPanel :form="form"></PreviewPanel>

    <!-- 下载tab -->
    <FileTabs :form="props.form"></FileTabs>
  </div>
</template>

<script setup lang="ts" name="CodePreview">
import type RequestType from '@/types/request_type';
import PreviewPanel from './PreviewPanel.vue';
import FileTabs from './FileTabs.vue';

// 传递的数据
const props = defineProps<{
  form: RequestType,
  fileNames: Array<string>
}>();

// 选择后触发
const handleSelect = (index: number) => {
  // 显示代码
  props.form.fileName = props.fileNames[index];
}
</script>

<style scoped>
.code-box {
  width: 98%;
  padding: 10px 0;
  margin: 0 auto;
  box-sizing: border-box;
}

.tabs>.el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.tabs .custom-tabs-label .el-icon {
  vertical-align: middle;
}

.tabs .custom-tabs-label span {
  vertical-align: middle;
  margin-left: 4px;
}
</style>
