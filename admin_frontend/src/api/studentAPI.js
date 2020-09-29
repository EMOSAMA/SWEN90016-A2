import { baseInstance } from './request'
import { PAGE_SIZE } from '../common/js/config/query'

// 查询学生
export function queryStudent ({ name, majorId, classId, exceptClass, pageIndex }) {
  return baseInstance({
    url: '/admin/user/student/query',
    data: {
      name,
      majorId,
      classId,
      exceptClass,
      pageIndex,
      pageSize: PAGE_SIZE
    }
  })
}
// 创建学生
export function addStudent ({ username, password, name, birth, majorId, classId, highSchool, gender, nationality, identityNumber, homeAddress, contactNumber, politicalStatus }) {
  return baseInstance({
    url: '/admin/user/student',
    data: {
      username,
      password,
      name,
      birth,
      majorId,
      classId,
      highSchool,
      gender,
      nationality,
      identityNumber,
      homeAddress,
      contactNumber,
      politicalStatus
    }
  })
}

// 创建学生
export function addStudents (formStudents) {
  return baseInstance({
    url: '/admin/user/students',
    data: formStudents
  })
}

// 更新学生
export function updateStudent ({ id, username, password, name, birth, majorId, classId, highSchool, gender, nationality, identityNumber, homeAddress, contactNumber, politicalStatus }) {
  return baseInstance({
    url: '/admin/user/student',
    method: 'put',
    data: {
      id,
      username,
      password,
      name,
      birth,
      majorId,
      classId,
      highSchool,
      gender,
      nationality,
      identityNumber,
      homeAddress,
      contactNumber,
      politicalStatus
    }
  })
}

// 删除学生
export function deleteStudent (ids) {
  return baseInstance({
    url: '/admin/user/student',
    method: 'delete',
    data: {
      ids
    }
  })
}

// 获取学生
export function getStudent (id) {
  return baseInstance({
    url: '/admin/user/student',
    method: 'get',
    params: {
      id
    }
  })
}
