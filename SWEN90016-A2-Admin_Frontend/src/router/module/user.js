import Layout from '@/layout/index.vue'

const userRouter = {
  path: '/project/user',
  name: 'user',
  meta: {
    title: 'User Management'
  },
  component: Layout,
  children: [
    {
      path: 'customer',
      name: 'customer',
      meta: {
        title: 'Customer'
      },
      component: () => import('@/views/project/user/customerUser')
    }
  ]
}

export default userRouter
