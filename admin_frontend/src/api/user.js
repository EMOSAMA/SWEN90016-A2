import { baseInstance } from './request'
import store from '@/store'

export function login ({ userName, password }) {
  return baseInstance({
    url: '/admin/login',
    data: {
      userName,
      password
    }
  })
}

export function changePassword ({ oldPassword, password }) {
  return baseInstance({
    url: '/admin/admin-user/password',
    method: 'put',
    data: {
      userId: store.getters.userId,
      oldPassword,
      password
    }
  })
}
