import Layout from '@/layout/index.vue'

const unitRouter = {
  path: '/project/unit',
  name: 'unit',
  meta: {
    title: '课程管理'
  },
  component: Layout,
  children: [
    {
      path: 'unit',
      name: 'unit',
      meta: {
        title: '课程管理'
      },
      component: () => import('@/views/project/unit/unit')
    }
  ]
}

export default unitRouter
