import CodeGeneratorView from '@/components/code-generator/CodeGeneratorView.vue'
import FileManagement from '@/views/FileManagement.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      redirect: '/code_generator'
    },
    {
      path: '/code_generator',
      component: CodeGeneratorView
    },
    {
      path: '/file_management',
      component: FileManagement
    }
  ],
})

export default router
