import Vue from 'vue'
import ElementUI from 'element-ui'
import locale from 'element-ui/lib/locale/lang/en'
Vue.prototype.$message = Element.Message
Vue.prototype.$messageBox = Element.MessageBox
Vue.use(ElementUI, { locale })
