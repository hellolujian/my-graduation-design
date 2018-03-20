// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css';    // 默认主题
//import 'element-ui/lib/theme-chalk/index.css';    // 默认主题

//import '../static/css/theme-green/index.css';       // 浅绿色主题

import iView from 'iview';
import 'iview/dist/styles/iview.css';

import axios from 'axios'

Vue.prototype.$axios = axios;
//axios.defaults.baseURL = 'http://127.0.0.1:8082/';

Vue.use(ElementUI);
Vue.use(iView);

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
