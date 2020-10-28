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
      path: 'todayAppointment',
      name: 'todayAppointment',
      meta: {
        title: 'Today Appointment'
      },
      component: () => import('@/views/project/appointment/todayAppointment')
    },
    {
      path: 'allAppointment',
      name: 'allAppointment',
      meta: {
        title: 'All Appointment'
      },
      component: () => import('@/views/project/appointment/allAppointment')
    }
  ]
}

export default appointmentRouter
