import { baseInstance } from './request'
import store from '@/store'
import md5 from 'js-md5'

export function login ({ userName, password }) {
  password = md5(password)
  return baseInstance({
    url: '/admin/login',
    method: 'post',
    data: {
      userName,
      password
    }
  })
}

export function changePassword ({ oldPassword, password }) {
  oldPassword = md5(oldPassword)
  password = md5(password)
  return baseInstance({
    url: '/admin/password',
    method: 'put',
    data: {
      userId: store.getters.userId,
      oldPassword,
      password
    }
  })
}
