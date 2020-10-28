import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// query biller
export function queryBiller ({ customerId, pageIndex }) {
  return baseInstance({
    url: '/biller/query',
    data: {
      customerId,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// create biller
export function addBiller ({ customerId, name, email }) {
  return baseInstance({
    url: '/biller',
    data: {
      customerId,
      name,
      email
    }
  })
}

// update biller
export function updateBiller ({ id, customerId, name, email }) {
  return baseInstance({
    url: '/biller',
    method: 'put',
    data: {
      id,
      customerId,
      name,
      email
    }
  })
}

// delete biller
export function deleteBiller (ids) {
  return baseInstance({
    url: '/biller',
    method: 'delete',
    data: {
      ids
    }
  })
}

// get biller
export function getBiller (id) {
  return baseInstance({
    url: '/biller',
    method: 'get',
    params: {
      id
    }
  })
}
