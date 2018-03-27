import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const test = r => require.ensure([], () => r(require('@/components/page/Test')), 'test');
const home = r => require.ensure([], () => r(require('@/components/common/Home')), 'home');
const login = r => require.ensure([], () => r(require('@/components/page/Login')), 'login');
const middle = r => require.ensure([], () => r(require('@/components/common/Middle')), 'middle');
const resource = r => require.ensure([], () => r(require('@/components/common/Resource')), 'resource');
const sideUserInfo = r => require.ensure([], () => r(require('@/components/common/SideUserInfo')), 'sideUserInfo');
const searchResource = r => require.ensure([], () => r(require('@/components/page/searchResource')), 'searchResource');
const condition = r => require.ensure([], () => r(require('@/components/common/Condition')), 'condition');
const resource2 = r => require.ensure([], () => r(require('@/components/common/Resource2')), 'resource2');

export default new Router({
  //linkActiveClass: 'active-class',
  routes: [
    {
      path: '/',
      component: home,
      children: [
        {
          path: '',
          name: 'home',
          components: {
              middle: middle,
              resource: resource,
              sideUserInfo: sideUserInfo,
          }
        }
      ]
    },
    {
      path: '/resources/:parentCategoryId/:childCategoryId/:typeId',
      component: searchResource,
      children: [
        {
          path: '',
          name: 'searchResource',
          components: {

          }
        }
        
      ]
      
    },
    {
      path: '/resource',
      name: 'resource',
      component: resource
    },
    {
      path: '/resource2',
      name: 'resource2',
      component: resource2
    },
    {
      path: '/sideUserInfo',
      name: 'sideUserInfo',
      component: sideUserInfo
    },
    {
      path: '/condition',
      name: 'condition',
      component: condition
    },
    {
      path: '/test',
      name: 'name',
      component: test
    }
  ]
})
