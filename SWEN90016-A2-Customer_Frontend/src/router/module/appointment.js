import Layout from '@/layout/index.vue'

const appointmentRouter = {
  path: '/project/appointment',
  name: 'appointment',
  meta: {
    title: 'Appointment Management'
  },
  component: Layout,
  children: [
    {
      path: 'appointment',
      name: 'appointment',
      meta: {
        title: 'My Appointment'
      },
      component: () => import('@/views/project/appointment/appointment')
    },
    {
      path: 'pendingApplication',
      name: 'pendingApplication',
      meta: {
        title: 'Pending Appointment'
      },
      component: () => import('@/views/project/appointment/pendingApplication')
    }
  ]
}

export default appointmentRouter
