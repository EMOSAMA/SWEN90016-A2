import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// 查询学生
export function queryAdmin ({ name, pageIndex }) {
  return baseInstance({
    url: '/admin/user/admin/query',
    data: {
      name,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// 创建学生
export function addAdmin ({ username, password, name, email }) {
  return baseInstance({
    url: '/admin/user/admin',
    data: {
      username,
      password,
      name,
      email
    }
  })
}

// 更新学生
export function updateAdmin ({ id, username, password, name, email }) {
  return baseInstance({
    url: '/admin/user/admin',
    method: 'put',
    data: {
      id,
      username,
      password,
      name,
      email
    }
  })
}

// 删除学生
export function deleteAdmin (ids) {
  return baseInstance({
    url: '/admin/user/admin',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取学生
export function getAdmin (id) {
  return baseInstance({
    url: '/admin/user/admin',
    method: 'get',
    params: {
      id
    }
  })
}
