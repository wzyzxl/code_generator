<template>
  <div class="download-box">
    <el-button :icon="Download" @click="download">下载ZIP</el-button>
  </div>

</template>

<script setup lang="ts" name="FileTabs">
import { downloadZipFile } from '@/apis/code';
import useDatabase from '@/hooks/useDatabase';
import type RequestType from '@/types/request_type';
import { downloadFile } from '@/utils/utils';
import { Download } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';

// 传递的数据
const props = defineProps<{
  form: RequestType
}>();

function download() {
  // 验证连接
  useDatabase().connections(props.form).then((connectionState: boolean) => {
    if (connectionState) {
      // 进行下载
      downloadZipFile(props.form).then((res) => {
        if (res.data.code == 200 && res.data.data && res.data.data.url) {
          // 下载
          ElMessage.success(res.data.data.message);
          downloadFile(res.data.data.url, props.form.table + '.zip');
        } else {
          ElMessage.error(res.data.message);
        }
      });
    }
  })
}
</script>
<style scoped>
.download-box {
  margin-top: 10px;
}
</style>
