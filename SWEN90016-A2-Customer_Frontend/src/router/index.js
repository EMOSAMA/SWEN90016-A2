/* eslint-disable */
import Vue from 'vue'
import Router from 'vue-router'
import userRouter from './module/user'
import serviceRouter from './module/service'
import appointmentRouter from './module/appointment'
import store from '@/store'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/register')
    },
    userRouter,
    serviceRouter,
    appointmentRouter
  ]
})

router.beforeEach((to, from, next) => {
  // 添加百度统计
  if (to.path) {
    _hmt.push(['_trackPageview', to.fullPath])
  }

  const isLogin = store.getters.isLogin
  /*
  if (isLogin) {
    if (to.path === '/login') {
      next('/project/user/data')
    } else {
      next()
    }
  } else {
    if (to.path === '/login') {
      next()
    }
    else if(to.path === '/register') {
      next()
    }
    else {
      next('/login')
    }
  }
  */
  if (isLogin) {
    if (to.path === '/login') {
      next('/project/user/data')
    } else {
      next()
    }
  } else {
    if (to.path === '/login') {
      next()
    }
    else if(to.path === '/register') {
      next()
    }
    else if(to.path === '/project/service/introduceService') {
      next()
    }
    else {
      next('/project/service/introduceService')
    }
  }
})

export default router
