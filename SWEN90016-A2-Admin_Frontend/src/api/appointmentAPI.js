import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// 查询学生
export function queryAppointment ({ serviceId, customerName, date, status, pageIndex }) {
  return baseInstance({
    url: '/appointment/query',
    data: {
      serviceId,
      customerName,
      date,
      status,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// 创建学生
export function addAppointment ({ serviceId, customerId, date, time, location, message, status }) {
  return baseInstance({
    url: '/appointment',
    data: {
      serviceId,
      customerId,
      date,
      time,
      location,
      message,
      status
    }
  })
}

// 更新学生
export function updateAppointment ({ id, serviceId, customerId, date, time, location, message, status }) {
  return baseInstance({
    url: '/appointment',
    method: 'put',
    data: {
      id,
      serviceId,
      customerId,
      date,
      time,
      location,
      message,
      status
    }
  })
}

// 删除学生
export function deleteAppointment (ids) {
  return baseInstance({
    url: '/appointment',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取学生
export function getAppointment (id) {
  return baseInstance({
    url: '/appointment',
    method: 'get',
    params: {
      id
    }
  })
}
