<template>
  <el-row>
    <el-col :span="24">
      <div class="databse-box">
        <img :src="database" width="48" />
        <span>数据库连接配置</span>
      </div>
    </el-col>
  </el-row>
  <hr>
  <div class="form-box">
    <el-form :model="form">
      <!-- 数据库类型 -->
      <el-row>
        <el-col :span="24">
          数据库类型
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item>
            <el-select v-model="form.databaseType" placeholder="请选择数据库类型">
              <el-option v-for="(databaseTypeItem, index) in databaseType" :key="index" :value="databaseTypeItem.value"
                :label="databaseTypeItem.name" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 主机地址 -->
      <el-row>
        <el-col :span="24">
          主机地址
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item>
            <el-input v-model="form.host" />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 端口 -->
      <el-row>
        <el-col :span="24">
          端口
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item>
            <el-input v-model="form.port" type="number" />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 数据库名称 -->
      <el-row>
        <el-col :span="24">
          数据库名称
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item>
            <el-input v-model="form.databaseName" />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 用户名 -->
      <el-row>
        <el-col :span="24">
          用户名
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item>
            <el-input v-model="form.username" />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 密码 -->
      <el-row>
        <el-col :span="24">
          密码
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item>
            <el-input v-model="form.password" type="password" />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 测试连接 -->
      <el-row>
        <el-col :span="24">
          <el-form-item>
            <el-button type="info" @click="useDatabase().connections(form, true)" style="width: 100%;">测试连接</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup lang="ts" name="ConnectionPanel">
import database from '@/assets/logo/database.svg';
import type RequestType from '@/types/request_type';
import { ref, type Ref } from 'vue';
import { getDatabaseType } from '@/apis/database';
import useDatabase from '@/hooks/useDatabase';

// form
const props = defineProps<{
  form: RequestType
}>();

// 获取数据库类型
const databaseType: Ref<Array<{ value: string, name: string }>> = ref([]);
getDatabaseType().then((res) => {
  databaseType.value = res.data.data || [];
});
</script>

<style scoped>
.databse-box {
  display: flex;
  align-items: center;
  font-weight: bold;
}

.form-box {
  padding: 5px 20px 0 10px;
}
</style>
