import { baseInstance } from './request'

// 将课程从班级中删除
export function deleteManageUnits (ids) {
  return baseInstance({
    url: '/admin/structure/manageUnits',
    method: 'delete',
    data: {
      ids
    }
  })
}
