import { baseInstance } from './request'
import {PAGE_SIZE} from "../common/js/config/query";

export function queryAllocateUnitsByClass (classId) {
  return baseInstance({
    url: '/admin/structure/class/queryAllocateUnitsByClass',
    method: 'get',
    params: {
      classId
    }
  })
}

export function queryAllocateUnits ({ classId, unitName, year, semester, pageIndex }) {
  return baseInstance({
    url: '/admin/structure/class/queryAllocateUnits',
    data: {
      classId,
      unitName,
      year,
      semester,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}

// 将课程从班级中删除
export function deleteAllocateUnits (ids) {
  return baseInstance({
    url: '/admin/structure/allocateUnits',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 将课程分配到班级
export function createAllocateUnits ({ classId, unitId, year, semester }) {
  return baseInstance({
    url: '/admin/structure/allocateUnits',
    data: {
      classId,
      unitId,
      year,
      semester
    }
  })
}
