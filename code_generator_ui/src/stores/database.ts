import { ref, type Ref } from 'vue'
import { defineStore } from 'pinia'

/**
 * 存储数据库基本信息
 */
export const useDatabaseStore = defineStore('database', () => {
  // 无需显式标注 Ref 类型，TS 会自动推断
  const databaseId: Ref<string | null> = ref(null)
  const databaseType = ref('')
  const host = ref('')
  const port = ref(-1)
  const databaseName = ref('')
  const username = ref('');
  const password = ref('');

  return { databaseId, databaseType, host, port, databaseName, username, password }
})
