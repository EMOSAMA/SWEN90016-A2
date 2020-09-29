import Layout from '@/layout/index.vue'

const userRouter = {
  path: '/project/user',
  name: 'user',
  meta: {
    title: '账号管理'
  },
  component: Layout,
  children: [
    {
      path: 'student',
      name: 'student',
      meta: {
        title: '学生账号管理'
      },
      component: () => import('@/views/project/user/student/studentUser')
    },
    {
      path: 'teacher',
      name: 'teacher',
      meta: {
        title: '教师账号管理'
      },
      component: () => import('@/views/project/user/teacher/teacherUser')
    },
    {
      path: 'admin',
      name: 'admin',
      meta: {
        title: '管理员账号管理'
      },
      component: () => import('@/views/project/user/admin/adminUser')
    }
  ]
}

export default userRouter
