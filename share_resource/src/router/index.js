import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const home = r => require.ensure([], () => r(require('@/components/common/Home')), 'home');
const login = r => require.ensure([], () => r(require('@/components/page/Login')), 'login');
const middle = r => require.ensure([], () => r(require('@/components/common/Middle')), 'middle');
const resource = r => require.ensure([], () => r(require('@/components/common/Resource')), 'resource');

export default new Router({
  //linkActiveClass: 'active-class',
  routes: [
    {
      path: '/',
      name: 'home',
      component: home,
      children: [
        {
          path: '',
          components: {
              middle: middle,
              resource: resource
          }
        }
      ]
    },
    {
      path: '/resource',
      name: 'resource',
      component: resource
    }
  ]
})
