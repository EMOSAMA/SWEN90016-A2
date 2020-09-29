import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// 查询专业
export function queryClass ({ className, majorId, pageIndex }) {
  return baseInstance({
    url: '/admin/structure/class/query',
    method: 'post',
    data: {
      className,
      majorId,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// 创建专业
export function addClass ({ className, classDescription, majorId, enrollmentDate, fee, dormitoryFee, bookFee, totalFee }) {
  return baseInstance({
    url: '/admin/structure/class',
    data: {
      className,
      classDescription,
      majorId,
      enrollmentDate,
      fee,
      dormitoryFee,
      bookFee,
      totalFee
    }
  })
}

// 更新专业
export function updateClass ({ id, className, classDescription, majorId, enrollmentDate, fee, dormitoryFee, bookFee, totalFee }) {
  return baseInstance({
    url: '/admin/structure/class',
    method: 'put',
    data: {
      id,
      className,
      classDescription,
      majorId,
      enrollmentDate,
      fee,
      dormitoryFee,
      bookFee,
      totalFee
    }
  })
}

// 删除专业
export function deleteClass (ids) {
  return baseInstance({
    url: '/admin/structure/class',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取专业
export function getClass (id) {
  return baseInstance({
    url: '/admin/structure/class',
    method: 'get',
    params: {
      id
    }
  })
}

// 将学生分配到班级
export function allocateStudentsToClass (studentIds, classId) {
  return baseInstance({
    url: '/admin/structure/class/allocateStudentsToClass',
    data: {
      studentIds,
      classId
    }
  })
}

// 将学生移出班级
export function removeStudentsFromClass (studentIds, classId) {
  return baseInstance({
    url: '/admin/structure/class/removeStudentsFromClass',
    method: 'post',
    data: {
      studentIds,
      classId
    }
  })
}

export function allocateTeachersToClass (teacherIds, allocateUnitId) {
  return baseInstance({
    url: '/admin/structure/class/allocateTeachersToClass',
    method: 'post',
    data: {
      teacherIds,
      allocateUnitId
    }
  })
}
