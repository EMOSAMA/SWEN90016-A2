import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// 查询学生
export function queryCustomer ({ name, pageIndex }) {
  return baseInstance({
    url: '/customer/query',
    data: {
      name,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// 创建学生
export function addCustomer ({ name, address, phone, email, password, comment }) {
  return baseInstance({
    url: '/customer',
    data: {
      name,
      address,
      phone,
      email,
      password,
      comment
    }
  })
}

// 更新学生
export function updateCustomer ({ id, name, address, phone, email, password, comment}) {
  return baseInstance({
    url: '/customer',
    method: 'put',
    data: {
      id,
      name,
      address,
      phone,
      email,
      password,
      comment
    }
  })
}

// 删除学生
export function deleteCustomer (ids) {
  return baseInstance({
    url: '/customer',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取学生
export function getCustomer (id) {
  return baseInstance({
    url: '/customer',
    method: 'get',
    params: {
      id
    }
  })
}
