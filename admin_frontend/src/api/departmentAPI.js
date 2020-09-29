import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// 获取所有学院的名字
export function listDepartmentNames () {
  return baseInstance({
    url: '/admin/structure/department/listDepartmentNames',
    method: 'get',
    params: {
    }
  })
}
// 查询学院
export function queryDepartment ({ departmentName, pageIndex }) {
  return baseInstance({
    url: '/admin/structure/department/query',
    data: {
      departmentName,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// 创建学院
export function addDepartment ({ departmentName, departmentDescription }) {
  return baseInstance({
    url: '/admin/structure/department',
    data: {
      departmentName,
      departmentDescription
    }
  })
}

// 更新学院
export function updateDepartment ({ id, departmentName, departmentDescription }) {
  return baseInstance({
    url: '/admin/structure/department',
    method: 'put',
    data: {
      id,
      departmentName,
      departmentDescription
    }
  })
}

// 删除学院
export function deleteDepartment (ids) {
  return baseInstance({
    url: '/admin/structure/department',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取学院
export function getDepartment (id) {
  return baseInstance({
    url: '/admin/structure/department',
    method: 'get',
    params: {
      id
    }
  })
}
