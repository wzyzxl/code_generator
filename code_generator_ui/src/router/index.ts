import CodeGeneratorView from '@/components/code-generator/CodeGeneratorView.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: CodeGeneratorView
    }
  ],
})

export default router
