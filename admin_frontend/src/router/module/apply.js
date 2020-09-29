import Layout from '@/layout/index.vue'

const applyRouter = {
  path: '/project/apply',
  name: 'apply',
  meta: {
    title: '项目申请'
  },
  component: Layout,
  children: [
    {
      path: 'apply',
      name: 'apply',
      meta: {
        title: '项目申请'
      },
      component: () => import('@/views/project/apply/apply')
    }
  ]
}

export default applyRouter
