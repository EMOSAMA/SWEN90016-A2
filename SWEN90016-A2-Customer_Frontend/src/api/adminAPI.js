import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// 查询学生
export function queryAdmin ({ name, pageIndex }) {
  return baseInstance({
    url: '/admin/query',
    data: {
      name,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// 创建学生
export function addAdmin ({password, name, email }) {
  return baseInstance({
    url: '/admin',
    data: {
      password,
      name,
      email
    }
  })
}

// 更新学生
export function updateAdmin ({ id, password, name, email }) {
  return baseInstance({
    url: '/admin',
    method: 'put',
    data: {
      id,
      password,
      name,
      email
    }
  })
}

// 删除学生
export function deleteAdmin (ids) {
  return baseInstance({
    url: '/admin',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取学生
export function getAdmin (id) {
  return baseInstance({
    url: '/admin',
    method: 'get',
    params: {
      id
    }
  })
}
