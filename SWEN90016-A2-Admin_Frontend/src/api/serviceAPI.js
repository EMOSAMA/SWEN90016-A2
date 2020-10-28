import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// query service
export function queryService ({ serviceName, pageIndex }) {
  return baseInstance({
    url: '/service/query',
    data: {
      serviceName,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// query all service
export function queryAllServices () {
  return baseInstance({
    url: '/service/queryAll',
    method: 'get',
    params: {
    }
  })
}
// create service
export function addService ({ serviceName, cost, duration }) {
  return baseInstance({
    url: '/service',
    data: {
      serviceName,
      cost,
      duration
    }
  })
}

// 更新学生
export function updateService ({ id, serviceName, cost, duration }) {
  return baseInstance({
    url: '/service',
    method: 'put',
    data: {
      id,
      serviceName,
      cost,
      duration
    }
  })
}

// 删除学生
export function deleteService (ids) {
  return baseInstance({
    url: '/service',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取学生
export function getService (id) {
  return baseInstance({
    url: '/service',
    method: 'get',
    params: {
      id
    }
  })
}
