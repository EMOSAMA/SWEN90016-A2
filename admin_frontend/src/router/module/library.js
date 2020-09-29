import Layout from '@/layout/index.vue'

const libraryRouter = {
  path: '/project/library',
  name: 'library',
  meta: {
    title: '图书中心'
  },
  component: Layout,
  children: [
    {
      path: 'books',
      name: 'books',
      meta: {
        title: '书籍管理'
      },
      component: () => import('@/views/project/library/books/books')
    },
    {
      path: 'rentBooks',
      name: 'rentBooks',
      meta: {
        title: '出借书籍管理'
      },
      component: () => import('@/views/project/library/rentBooks/rentBooks')
    }
  ]
}

export default libraryRouter
