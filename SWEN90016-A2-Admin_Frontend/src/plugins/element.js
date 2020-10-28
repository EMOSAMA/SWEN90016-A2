import Vue from 'vue'
import ElementUI from 'element-ui'
import locale from 'element-ui/lib/locale/lang/en'

Vue.use(ElementUI, { locale })

Vue.prototype.$message = ElementUI.Message
Vue.prototype.$messageBox = ElementUI.MessageBox
