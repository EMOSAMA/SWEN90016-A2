import Layout from '@/layout/index.vue'

const serviceRouter = {
  path: '/project/service',
  name: 'service',
  meta: {
    title: 'Service Management'
  },
  component: Layout,
  children: [
    {
      path: 'service',
      name: 'service',
      meta: {
        title: 'Service'
      },
      component: () => import('@/views/project/service/service')
    }
  ]
}

export default serviceRouter
