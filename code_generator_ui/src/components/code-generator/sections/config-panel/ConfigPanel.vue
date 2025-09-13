<template>
  <el-row>
    <el-col :span="24">
      <div class="databse-box">
        <img :src="dataConfig" width="40" />
        <span>生成参数配置</span>
      </div>
    </el-col>
  </el-row>
  <hr>
  <!-- 生成选项 -->
  <div class="create-option-box">
    <el-row>
      <el-col :span="24">
        <h4>生成选项</h4>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-checkbox v-model="form.createEntity" label="生成实体类" size="large" disabled />
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-checkbox v-model="form.createRepository" label="生成Mapper" size="large" disabled />
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-checkbox v-model="form.createService" label="生成Service" size="large" />
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-checkbox v-model="form.createController" label="生成Controller" size="large" />
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-checkbox v-model="form.createSimpleSql" label="生成简单增删改查" size="large" />
      </el-col>
    </el-row>
  </div>
  <hr>
  <!-- 前置包名 -->
  <div class="package-box">
    <el-row>
      <el-col :span="24">
        <h4>前置包名</h4>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-input placeholder="请输入" v-model="form.prePackageName" />
      </el-col>
    </el-row>
  </div>

  <!-- 生成按钮 -->
  <div class="button-box">
    <el-row>
      <el-col :span="24">
        <el-button type="success" style="width: 100%;" @click="codeGenerator">生成代码</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts" name="ConfigPanel">
import type RequestType from '@/types/request_type';
import dataConfig from '@/assets/logo/data_config.svg'
import useDatabase from '@/hooks/useDatabase';
import { getFileNames } from '@/apis/code';
import { ElMessage } from 'element-plus';

// form
const props = defineProps<{
  form: RequestType
}>();

// emit
const emits = defineEmits(['changeFileNames', 'showCodeBox', 'hideCodeBox']);

// 生成代码
function codeGenerator() {
  // 隐藏预览框
  emits('hideCodeBox');
  // 连接
  useDatabase().connections(props.form).then((connectionState) => {
    // 连接成功再执行
    if (connectionState) {
      // 获取对应文件
      getFileNames(props.form).then((res) => {
        try {
          if (res.data.data.length > 0) {
            props.form.fileName = res.data.data[0];
            emits('changeFileNames', res.data.data);
            // 显示预览框
            emits('showCodeBox');
          }
        } catch (e) {
          ElMessage.error('出错了:' + e);
        }

      })
    }
  })
}
</script>

<style scoped>
.databse-box {
  display: flex;
  align-items: center;
  font-weight: bold;
  padding-bottom: 9px;
}

.form-box {
  padding: 16px 0;
}

.el-form-item {
  margin-bottom: 16px;
}

.create-option-box,
.package-box,
.button-box {
  padding-left: 20px;
  padding-bottom: 10px;
  padding-right: 20px;
}
</style>
