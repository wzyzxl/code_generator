<template>
  <div class="file-box">
    <div class="last" @click="queryLastPath">
      <el-icon>
        <Back />
      </el-icon>
    </div>
    <span class="current-path"> {{ currentPath }} </span>
    <hr>
    <div class="files">
      <div class="file" v-for="file in fileLists">
        <!-- 文件夹 -->
        <el-row class="folder" v-if="file.type == 'folder'" @dblclick="showNextFolder(file)">
          <el-col :span="22">
            <el-icon>
              <Folder />
            </el-icon>
            <span>{{ file.name }}</span>
          </el-col>
          <el-col :span="2" class="delete-icon">
            <el-icon @click="deleteFile(file.type, file.path)" title="删除文件夹">
              <DeleteFilled />
            </el-icon>
          </el-col>
        </el-row>

        <!-- 文件 -->
        <el-row class="file" v-else @dblclick="showFille(file)">
          <el-col :span="22">
            <el-icon>
              <Document />
            </el-icon>
            <span class="filename">{{ file.name }}</span>
          </el-col>
          <el-col :span="2" class="delete-icon">
            <el-icon @click="deleteFile(file.type, file.path)" title="删除文件">
              <DeleteFilled />
            </el-icon>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script lang="ts" name="FileManagement" setup>
import { deleteFiles, queryFiles } from '@/apis/files';
import { Stack } from '@/types/Stack';
import { downloadFile } from '@/utils/utils';
import { ElMessage, ElMessageBox } from 'element-plus';
import { onMounted, ref, type Ref } from 'vue';

// 文件列表
const fileLists: Ref<Array<{ name: string, path: string, type: string, url: string }>> = ref([]);

// 当前路径
const currentPath: Ref<string> = ref('/');

// 上一路径
const lastPath: Stack<string> = new Stack();

// 挂载时请求文件列表
onMounted(() => {
  queryFilesByPath(currentPath.value);
})

/**
 * 显示下一个文件夹
 * @param file 当前文件目录
 */
function showNextFolder(file: { name: string, path: string, type: string, url: string }) {
  lastPath.push(currentPath.value);
  currentPath.value = file.path;
  queryFilesByPath(file.path);
}

// 下载文件
function showFille(file: { name: string, path: string, type: string, url: string }) {
  downloadFile(file.url, file.name);
}

/**
 * 显示上一级目录
 */
function queryLastPath() {
  if (lastPath.isEmpty()) {
    return;
  }

  // 获取上一级目录
  const path: string | null = lastPath.pop();
  currentPath.value = path == null ? '/' : path;
  queryFilesByPath(path == null ? '/' : path);
}

/**
 * 根据路径请求文件
 * @param path 路径
 */
function queryFilesByPath(path: string) {
  // 请求文件列表
  queryFiles(path).then((res) => {
    fileLists.value = res.data.data;
  })
}

/**
 * 删除文件及文件夹
 * @param type 文件类型
 * @param path 路径
 */
function deleteFile(type: string, path: string) {
  ElMessageBox.confirm('确定要删除吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 删除文件
    deleteFiles(type, path).then((res) => {
      if (res.data.code == 200) {
        ElMessage.success('删除成功');
        // 刷新文件列表
        queryFilesByPath(currentPath.value);
      } else {
        ElMessage.error(res.data.message);
      }
    }).catch((e) => {
      ElMessage.error('出错了:' + e.message);
    });
  }).catch(() => {
    // 处理取消操作，空白
  });
}
</script>

<style scoped>
.file-box {
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
}

.last {
  width: 30px;
  display: inline-block;
  cursor: pointer;
  margin-right: 20px;
  padding-bottom: 10px;
}

.current-path {
  font-size: 18px;
}

.file,
.folder {
  height: 30px;
  font-size: 18px;
  line-height: 30px;
  cursor: pointer;
  padding: 0px 10px;
}

.file:hover,
.folder:hover {
  background-color: #d9eeff;
}

.delete-icon {
  text-align: right;
}
</style>
