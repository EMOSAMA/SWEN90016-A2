import { login, register } from 'api/user'

const actions = {
  // 登录
  login ({ commit }, userInfo) {
    const { userName, password } = userInfo
    return new Promise((resolve, reject) => {
      login({
        userName: userName.trim(),
        password: password.trim()
      }).then(response => {
        if (response.success) {
          const { userId, isSuper } = response.data
          commit('SET_LOGIN', true)
          commit('SET_USER_NAME', userName)
          commit('SET_IS_SUPER', isSuper)
          commit('SET_USER_ID', userId)
          const userInfo = {
            isLogin: true,
            userName,
            userId,
            isSuper
          }
          sessionStorage.setItem('User_Info', JSON.stringify(userInfo))
          resolve()
        } else {
          reject(Error('登录失败'))
        }
      }).catch(error => {
        reject(error)
      })
    })
  },
  // 注册
  register ({ commit }, userInfo) {
    const { name, email, password, confirmPassword, phone, address, comment, billerName, billerEmail } = userInfo
    return new Promise((resolve, reject) => {
      register({
        name: name.trim(),
        email: email.trim(),
        password: password.trim(),
        confirmPassword: confirmPassword.trim(),
        phone: phone,
        address: address.trim(),
        comment: comment.trim(),
        billerName: billerName.trim(),
        billerEmail: billerEmail.trim()
      }).then(response => {
        if (response.success) {
          const { userId, isSuper } = response.data
          commit('SET_LOGIN', true)
          commit('SET_NAME', name)
          commit('SET_USER_NAME', email)
          commit('SET_IS_SUPER', isSuper)
          commit('SET_USER_ID', userId)
          const userInfo = {
            isLogin: true,
            email,
            userId,
            isSuper
          }
          sessionStorage.setItem('User_Info', JSON.stringify(userInfo))
          resolve()
        } else {
          reject(Error('登录失败'))
        }
      }).catch(error => {
        reject(error)
      })
    })
  },
  // 退出登录
  logout ({ commit }) {
    return new Promise(resolve => {
      commit('SET_LOGIN', false)
      sessionStorage.removeItem('User_Info')
      resolve()
    })
  }
}

export default actions
