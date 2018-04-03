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
//const searchResource = r => require.ensure([], () => r(require('@/components/chonggou/searchResource')), 'searchResource');

const condition = r => require.ensure([], () => r(require('@/components/common/Condition')), 'condition');
const resource2 = r => require.ensure([], () => r(require('@/components/common/Resource2')), 'resource2');
//const resource2 = r => require.ensure([], () => r(require('@/components/chonggou/Resource2')), 'resource2');

const resourceDetail = r => require.ensure([], () => r(require('@/components/common/ResourceDetail')), 'resourceDetail');
const remark = r => require.ensure([], () => r(require('@/components/common/Remark')), 'remark');
const index = r => require.ensure([], () => r(require('@/components/page/Index')), 'index');
const download = r => require.ensure([], () => r(require('@/components/page/Download')), 'download');
const userCenter = r => require.ensure([], () => r(require('@/components/page/UserCenter')), 'userCenter');


//const header = r => require.ensure([], () => r(require('@/components/chonggou/Header')), 'header');
//const tags = r => require.ensure([], () => r(require('@/components/chonggou/Tags')), 'tags');
//const sideBar = r => require.ensure([], () => r(require('@/components/chonggou/SideBar')), 'sideBar');
//const banner = r => require.ensure([], () => r(require('@/components/chonggou/Banner')), 'banner');
const header = r => require.ensure([], () => r(require('@/components/common/Header')), 'header');
const tags = r => require.ensure([], () => r(require('@/components/common/Tags')), 'tags');
const sideBar = r => require.ensure([], () => r(require('@/components/common/SideBar')), 'sideBar');
const banner = r => require.ensure([], () => r(require('@/components/common/Banner')), 'banner');


export default new Router({
  //linkActiveClass: 'active-class',
  routes: [
    /*
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
    */
    {
      path: '/',
      component: index,
      children: [{
        path: '',
        name: 'index',
        components: {
          sideBar: sideBar,
          resource: resource,
          tags: tags,
          sideUserInfo: sideUserInfo,
        }
      }, ]
    },
    {
      path: '/resources/:parentCategoryId/:childCategoryId/:resourceTypeId',
      component: searchResource,
      children: [{
        path: '',
        name: 'searchResource',
        components: {
          condition: condition,
          resource2: resource2,
          sideUserInfo: sideUserInfo
        }

      }]
    },
    {
      path: '/download/resource/:resourceId',
      component: download,
      children: [
        {
          path: '',
          name: 'download',
          components: {
            resourceDetail: resourceDetail,
            remark: remark,
            sideUserInfo: sideUserInfo
          }
        }
      ]
    },
    {
      path: '/userCenter',
      component: userCenter,
      name: 'userCenter'
    },
    {
      path: '/login',
      name: 'login',
      component: login
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
      path: '/resourceDetail/:resourceId',
      name: 'resourceDetail',
      component: resourceDetail
    },
    {
      path: '/remark/resource/:resourceId',
      name: 'remark',
      component: remark
    },
    {
      path: '/test',
      name: 'name',
      component: test
    },
    {
      path: '/header',
      name: 'header',
      component: header
    },
    {
      path: '/tags',
      name: 'tags',
      component: tags
    },
    {
      path: '/sideBar',
      name: 'sideBar',
      component: sideBar
    },
    {
      path: '/banner',
      name: 'banner',
      component: banner
    }
  ]
})
