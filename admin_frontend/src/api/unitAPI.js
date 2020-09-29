import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// 获取所有课程的名字
export function listUnitNames () {
  return baseInstance({
    url: '/admin/unit/listUnitNames',
    method: 'get',
    params: {
    }
  })
}
// 查询课程
export function queryUnit ({ unitName,unitType, pageIndex }) {
  return baseInstance({
    url: '/admin/unit/query',
    data: {
      unitName,
      unitType,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// 创建课程
export function addUnit ({ unitName, unitDescription, unitType }) {
  return baseInstance({
    url: '/admin/unit',
    data: {
      unitName,
      unitDescription,
      unitType
    }
  })
}

// 更新课程
export function updateUnit ({ id, unitName, unitDescription, unitType }) {
  return baseInstance({
    url: '/admin/unit',
    method: 'put',
    data: {
      id,
      unitName,
      unitDescription,
      unitType
    }
  })
}

// 删除课程
export function deleteUnit (ids) {
  return baseInstance({
    url: '/admin/unit',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取课程
export function getUnit (id) {
  return baseInstance({
    url: '/admin/unit',
    method: 'get',
    params: {
      id
    }
  })
}
