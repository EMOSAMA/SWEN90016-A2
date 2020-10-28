import Layout from '@/layout/index.vue'

const serviceRouter = {
  path: '/project/service',
  name: 'service',
  meta: {
    title: 'Service'
  },
  component: Layout,
  children: [
    {
      path: 'bookService',
      name: 'bookService',
      meta: {
        title: 'Book Service'
      },
      component: () => import('@/views/project/service/bookService')
    },
    {
      path: 'introduceService',
      name: 'introduceService',
      meta: {
        title: 'Introduce Service'
      },
      component: () => import('@/views/project/service/introduceService')
    }
  ]
}

export default serviceRouter
