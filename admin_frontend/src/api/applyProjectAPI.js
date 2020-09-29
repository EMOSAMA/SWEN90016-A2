import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// 查询教师
export function queryApplyProject ({ studentId, studentName, status, projectType, pageIndex }) {
  return baseInstance({
    url: '/admin/apply/applyProject/query',
    data: {
      studentId,
      studentName,
      projectType,
      status,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// 创建教师
export function addApplyProject ({ studentId, projectType, applyReason }) {
  return baseInstance({
    url: '/admin/apply/applyProject',
    data: {
      studentId,
      projectType,
      applyReason
    }
  })
}

// 更新申请项目
export function updateApplyProject ({ id, studentId, projectType, applyReason, status, studentName }) {
  return baseInstance({
    url: '/admin/apply/applyProject',
    method: 'put',
    data: {
      id,
      studentId,
      projectType,
      applyReason,
      status,
      studentName
    }
  })
}

// 删除教师
export function deleteApplyProject (ids) {
  return baseInstance({
    url: '/admin/apply/applyProject',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取教师
export function getApplyProject (id) {
  return baseInstance({
    url: '/admin/apply/applyProject',
    method: 'get',
    params: {
      id
    }
  })
}
