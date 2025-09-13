<template>
  <el-row>
    <el-col :span="24">
      <div class="databse-box">
        <img :src="databseTable" width="40" />
        <span>数据表结构选择</span>
      </div>
    </el-col>
  </el-row>
  <hr>
  <div class="form-box">
    <el-form :model="form">
      <!-- 数据库模式 -->
      <el-row v-if="form.databaseType && form.databaseType != 'MYSQL'">
        <el-col :span="24">
          数据库模式
        </el-col>
      </el-row>
      <el-row v-if="form.databaseType && form.databaseType != 'MYSQL'">
        <el-col :span="24">
          <el-form-item>
            <el-autocomplete v-model="form.pattern" :fetch-suggestions="callbackPattern" clearable />
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 数据表 -->
      <el-row>
        <el-col :span="24">
          数据表名称
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item>
            <el-autocomplete v-model="form.table" :fetch-suggestions="callbackTable" clearable />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup lang="ts" name="StructurePanel">
import { queryDatabasePattern, queryDatabaseTable } from '@/apis/database';
import databseTable from '@/assets/logo/data_table.svg';
import useDatabase from '@/hooks/useDatabase';
import type RequestType from '@/types/request_type';

// form
const props = defineProps<{
  form: RequestType
}>();

// 获取模式
let patternTimeout: ReturnType<typeof setTimeout>;
const callbackPattern = (queryString: string, cb: (arg: any) => void) => {
  clearTimeout(patternTimeout)
  patternTimeout = setTimeout(() => {
    // 先连接
    useDatabase().connections(props.form).then((state) => {
      if (state) {
        // 从后台获取数据
        queryDatabasePattern(props.form).then((res) => {
          const result: Array<{ value: any }> = [];
          res.data.data.forEach((item: any) => {
            result.push({
              value: item
            })
          });
          cb(result);
        })
      }
    });
  }, 1000);
};

// 获取数据表
let tableTimeout: ReturnType<typeof setTimeout>;
const callbackTable = (queryString: string, cb: (arg: any) => void) => {
  clearTimeout(tableTimeout)
  tableTimeout = setTimeout(() => {
    // 先连接
    useDatabase().connections(props.form).then((state) => {
      if (state) {
        // 从后台获取数据
        queryDatabaseTable(props.form).then((res) => {
          const result: Array<{ value: any }> = [];
          res.data.data.forEach((item: any) => {
            result.push({
              value: item
            })
          });
          cb(result);
        })
      }
    });
  }, 1000);
};
</script>

<style scoped>
.databse-box {
  display: flex;
  align-items: center;
  font-weight: bold;
  padding-bottom: 8px;
}

.form-box {
  padding: 16px 0;
}

.el-form-item {
  margin-bottom: 16px;
}
</style>
