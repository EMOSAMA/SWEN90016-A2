import Layout from '@/layout/index.vue'

const structureRouter = {
  path: '/project/structure',
  name: 'structure',
  meta: {
    title: '学校架构'
  },
  component: Layout,
  children: [
    {
      path: 'department',
      name: 'department',
      meta: {
        title: '学院管理'
      },
      component: () => import('@/views/project/structure/department/department')
    },
    {
      path: 'major',
      name: 'major',
      meta: {
        title: '专业管理'
      },
      component: () => import('@/views/project/structure/major/major')
    },
    {
      path: 'class',
      name: 'class',
      meta: {
        title: '班级管理'
      },
      component: () => import('@/views/project/structure/class/class')
    },
    {
      path: 'studentList/:classId',
      name: 'studentlist',
      meta: {
        title: '班级管理 / 学生列表'
      },
      component: () => import('@/views/project/structure/class/studentList')
    },
    {
      path: 'allocateNewStudents/:classId',
      name: 'allocateNewStudents',
      meta: {
        title: '班级管理 / 分配新学生'
      },
      component: () => import('@/views/project/structure/class/allocateNewStudents')
    },
    {
      path: 'teacherList/:allocateUnitId',
      name: 'teacherList',
      meta: {
        title: '班级管理 / 教师列表'
      },
      component: () => import('@/views/project/structure/class/teacherList')
    },
    {
      path: 'allocateNewTeachers/:classId',
      name: 'allocateNewTeachers',
      meta: {
        title: '班级管理 / 分配新教师'
      },
      component: () => import('@/views/project/structure/class/allocateNewTeachers')
    },
    {
      path: 'unitList/:classId',
      name: 'unitlist',
      meta: {
        title: '班级管理 / 課程列表'
      },
      component: () => import('@/views/project/structure/class/unitList')
    },
    {
      path: 'allocateUnits/:classId',
      name: 'allocateUnits',
      meta: {
        title: '班级管理 / 分配課程'
      },
      component: () => import('@/views/project/structure/class/allocateUnits')
    }
  ]
}

export default structureRouter
