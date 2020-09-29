import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// 获取所有专业的名字
export function listMajorNames () {
  return baseInstance({
    url: '/admin/structure/department/listMajorNames',
    method: 'get',
    params: {
    }
  })
}
// 查询专业
export function queryMajor ({ majorName, departmentId, pageIndex }) {
  return baseInstance({
    url: '/admin/structure/major/query',
    data: {
      majorName,
      departmentId,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// 创建专业
export function addMajor ({ majorName, majorDescription, departmentName }) {
  return baseInstance({
    url: '/admin/structure/major',
    data: {
      majorName,
      majorDescription,
      departmentName
    }
  })
}

// 更新专业
export function updateMajor ({ id, majorName, majorDescription, departmentName }) {
  return baseInstance({
    url: '/admin/structure/major',
    method: 'put',
    data: {
      id,
      majorName,
      majorDescription,
      departmentName
    }
  })
}

// 删除专业
export function deleteMajor (ids) {
  return baseInstance({
    url: '/admin/structure/major',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取专业
export function getMajor (id) {
  return baseInstance({
    url: '/admin/structure/major',
    method: 'get',
    params: {
      id
    }
  })
}
