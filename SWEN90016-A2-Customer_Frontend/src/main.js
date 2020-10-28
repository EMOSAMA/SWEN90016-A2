import Vue from 'vue'

import App from './App.vue'
import router from './router'
import store from './store'
import Blob from './excel/Blob.js'
import Export2Excel from './excel/Export2Excel.js'

import './plugins/element.js'
import './common/scss/index.scss'
import './permission'

Vue.config.productionTip = false
new Vue({
  router,
  store,
  Blob,
  Export2Excel,
  render: h => h(App)
}).$mount('#app')
