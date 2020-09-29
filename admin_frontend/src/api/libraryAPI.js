import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// 查询书本
export function queryBook ({ bookName, author, pageIndex }) {
  return baseInstance({
    url: '/admin/library/book/query',
    data: {
      bookName,
      author,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// 创建书本
export function addBook ({ bookName, bookDescription, author, totalAmount, rentAmount }) {
  return baseInstance({
    url: '/admin/library/book',
    data: {
      bookName,
      bookDescription,
      author,
      totalAmount,
      rentAmount
    }
  })
}

// 更新书本
export function updateBook ({ id, bookName, bookDescription, author, totalAmount, rentAmount }) {
  return baseInstance({
    url: '/admin/library/book',
    method: 'put',
    data: {
      id,
      bookName,
      bookDescription,
      author,
      totalAmount,
      rentAmount
    }
  })
}

// 删除书本
export function deleteBook (ids) {
  return baseInstance({
    url: '/admin/library/book',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取书本
export function getBook (id) {
  return baseInstance({
    url: '/admin/library/book',
    method: 'get',
    params: {
      id
    }
  })
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////

// 查询借书
export function queryRentBook ({ studentId, studentName, bookName, isReturn, isTake, className, majorId, departmentId, pageIndex }) {
  return baseInstance({
    url: '/admin/library/rentBook/query',
    data: {
      studentId,
      studentName,
      bookName,
      isReturn,
      isTake,
      className,
      majorId,
      departmentId,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// 创建借书
export function addRentBook ({ studentId, bookId }) {
  return baseInstance({
    url: '/admin/library/rentBook',
    data: {
      studentId,
      bookId
    }
  })
}

// 更新借书
export function updateRentBook ({ id, studentId, bookId, bookName, isReturn, isTake }) {
  return baseInstance({
    url: '/admin/library/rentBook',
    method: 'put',
    data: {
      id,
      studentId,
      bookId,
      bookName,
      isReturn,
      isTake
    }
  })
}

// 删除借书
export function deleteRentBook (ids) {
  return baseInstance({
    url: '/admin/library/rentBook',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取借书
export function getRentBook (id) {
  return baseInstance({
    url: '/admin/library/rentBook',
    method: 'get',
    params: {
      id
    }
  })
}
