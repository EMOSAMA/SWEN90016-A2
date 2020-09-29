import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// 查询教师
export function queryTeacher ({ name, departmentId, pageIndex }) {
  return baseInstance({
    url: '/admin/user/teacher/query',
    data: {
      name,
      departmentId,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}

// 查询教师
export function queryTeacherByAllocatedUnit ({ name, departmentId, allocateUnitId, pageIndex }) {
  return baseInstance({
    url: '/admin/user/teacher/queryTeacherByAllocatedUnit',
    data: {
      name,
      departmentId,
      allocateUnitId,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}

// 创建教师
export function addTeacher ({ username, password, name, birth, departmentId, pageIndex }) {
  return baseInstance({
    url: '/admin/user/teacher',
    data: {
      username,
      password,
      name,
      birth,
      departmentId,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}

// 更新教师
export function updateTeacher ({ id, username, password, name, birth, departmentId, age, nationality, politicalStatus, identityNumber }) {
  return baseInstance({
    url: '/admin/user/teacher',
    method: 'put',
    data: {
      id,
      username,
      password,
      name,
      birth,
      departmentId,
      age,
      nationality,
      politicalStatus,
      identityNumber
    }
  })
}

// 删除教师
export function deleteTeacher(ids) {
  return baseInstance({
    url: '/admin/user/teacher',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取教师
export function getTeacher(id) {
  return baseInstance({
    url: '/admin/user/teacher',
    method: 'get',
    params: {
      id
    }
  })
}
