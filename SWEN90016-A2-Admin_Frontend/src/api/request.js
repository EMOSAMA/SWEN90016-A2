import axios from 'axios'
import { Message } from 'element-ui'
import { API_HOST } from '@/common/js/config/host'

axios.defaults.retry = 4
axios.defaults.retryDelay = 1000

const baseInstance = axios.create({
  baseURL: API_HOST,
  timeout: 5000,
  method: 'post'
})

baseInstance.interceptors.request.use(config => {
  return config
}, error => {
  console.log(error)
  Message.info('Please waiting...') // 服务器异常
  return Promise.reject(error)
})

baseInstance.interceptors.response.use(response => {
  const res = response.data
  if (res.success) {
    return res
  } else {
    // Message.info('Please waiting...')
    Message.error(res.message || 'Please waiting...') // 服务器异常
    return false
  }
}, error => {
  console.log(error)
  Message.info('Please waiting...') // 服务器异常

  var config = error.config
  var backoff = new Promise(function (resolve) {
    setTimeout(function () {
      resolve()
    }, config.retryDelay || 1)
  })
  return backoff.then(function () {
    return axios(config)
  })
  // return Promise.reject(error)
})

export { baseInstance }
