import { baseInstance } from './request'
import store from '@/store'
import md5 from 'js-md5';

export function login ({ userName, password }) {
  password = md5(password)
  return baseInstance({
    url: '/customer/login',
    method: 'post',
    data: {
      userName,
      password
    }
  })
}

export function register ({ name, email, password, confirmPassword, phone, address, comment, billerName, billerEmail }) {
  password = md5(password)
  return baseInstance({
    url: '/customer/register',
    method: 'post',
    data: {
      name,
      email,
      password,
      confirmPassword,
      phone,
      address,
      comment,
      billerName,
      billerEmail
    }
  })
}

export function changePassword ({ oldPassword, password }) {
  password = md5(password)
  oldPassword = md5(oldPassword)
  return baseInstance({
    url: '/customer/password',
    method: 'put',
    data: {
      userId: store.getters.userId,
      oldPassword,
      password
    }
  })
}
