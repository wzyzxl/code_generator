<template>
  <el-row>
    <el-col :span="20">
      <div class="logo-box">
        <img :src="logo"></img>
      </div>
    </el-col>
    <el-col :span="4">
      <div class="save-config-box">
        <el-link href="/file_management">文件管理</el-link>
        &nbsp;&nbsp;
        <el-link href="http://localhost:8080/swagger-ui/index.html" target="_blank">查看API</el-link>
        &nbsp;&nbsp;
        <el-button type="primary" @click="dialogVisible = true">保存配置</el-button>
      </div>
    </el-col>
  </el-row>

  <!-- 输入配置名称 -->
  <el-dialog v-model="dialogVisible" title="配置名" width="30%" draggable>
    <el-form-item label="配置名">
      <el-input v-model="form.configName" max="16" />
    </el-form-item>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="success" @click="save">
          保存
        </el-button>
        <el-button type="danger" @click="dialogVisible = false">
          取消
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts" name="CodeGeneratorHeader">
import { databaseSave } from '@/apis/database';
import logo from '@/assets/logo/logo.png';
import useDatabase from '@/hooks/useDatabase';
import { loadingComponent } from '@/utils/utils';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';

const props = defineProps(["form"]);

// 显示弹窗
let dialogVisible = ref(false);

// 保存配置
async function save() {
  // 判断配置名是不是为空
  if (!props.form.configName) {
    ElMessage.error("配置名称不能为空");
    return;
  }
  // 测试连接
  const connectionState = await useDatabase().connections(props.form);

  // 连接成功，则保存数据库配置信息
  if (connectionState) {
    // 开始加载
    loadingComponent.fullLoading(true);

    // 保存配置
    databaseSave(props.form).then((res) => {
      // 关闭加载
      loadingComponent.fullLoading(false);

      if (res.data.code == 200 && res.data.data.state) {
        dialogVisible.value = false;
        ElMessage.success(res.data.message);
      } else {
        ElMessage.error(res.data.message);
      }
    })
  }
}
</script>

<style scoped>
.logo-box,
.logo-box img,
.save-config-box {
  height: 100px;
}

.save-config-box {
  display: flex;
  align-items: center;
  justify-content: end;
  margin-right: 30px;
}
</style>
