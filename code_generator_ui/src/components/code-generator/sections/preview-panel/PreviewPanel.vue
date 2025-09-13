<template>
  <div class="view-code-box">
    <!-- 添加复制按钮 -->
    <el-icon style="position: absolute;right: 20px;top: 20px;" @click="copyTextToClipboard(code)" title="复制代码">
      <CopyDocument />
    </el-icon>
    <!-- 添加v-if确保元素存在时才渲染 -->
    <pre v-if="code"><code ref="codeElement" class="language-java" v-text="code"></code></pre>
    <!-- 加载状态提示 -->
    <div v-else class="loading">加载中...</div>
  </div>
</template>

<script setup lang="ts" name="PreviewPanel">
import { getCodeByFileName } from '@/apis/code';
import useDatabase from '@/hooks/useDatabase';
import type RequestType from '@/types/request_type';
import { ElMessage } from 'element-plus';
import { onMounted, ref, watch, nextTick, type Ref } from 'vue';
import { copyTextToClipboard } from '@/utils/utils';

const props = defineProps<{
  form: RequestType
}>();

const code: Ref<string> = ref('');
const codeElement: Ref<HTMLElement | null> = ref(null);
const isLoading = ref(false); // 添加加载状态

// 默认获取代码
onMounted(() => {
  generatorCode(props.form);
});

watch(() => props.form.fileName, () => {
  generatorCode(props.form);
}, { deep: true })

// 展示code
async function generatorCode(form: RequestType) {
  isLoading.value = true; // 开始加载

  try {
    const connectionCode = await useDatabase().connections(form);
    if (!connectionCode) return;

    const res = await getCodeByFileName(form);
    if (res?.data?.code === 200) {
      code.value = res.data.data.code || '';
    } else {
      ElMessage.error(res?.data?.message || '获取代码失败');
    }
  } catch (error) {
    ElMessage.error('请求异常');
  } finally {
    isLoading.value = false; // 结束加载
  }
}
</script>

<style scoped>
.view-code-box {
  position: relative;
  width: 100%;
  min-height: 400px;
  background-color: #2d2d2d;
  color: #FFF;
  padding: 10px;
  box-sizing: border-box;
  border-radius: 4px;
  font-size: 1.1rem;
}

pre {
  margin: 0;
  overflow: auto;
}

.loading {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #888;
}
</style>
