import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Home from '@/components/common/Home'

Vue.use(Router)

export default new Router({
  linkActiveClass: 'active-class',
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    }
  ]
})
