import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Test from '@/components/Test'

Vue.use(Router)

const login = r => require.ensure([], () => r(require('@/components/page/Login')), 'login');
const readme = r => require.ensure([], () => r(require('@/components/page/Readme')), 'readme');
const home = r => require.ensure([], () => r(require('@/components/common/Home')), 'home');
const resourceType = r => require.ensure([], () => r(require('@/components/page/ResourceType')), 'resourceType');
const tag = r => require.ensure([], () => r(require('@/components/page/Tag')), 'tag');
const parentCategory = r => require.ensure([], () => r(require('@/components/page/ParentCategory')), 'parentCategory');
const childCategory = r => require.ensure([], () => r(require('@/components/page/ChildCategory')), 'childCategory');
const suffix = r => require.ensure([], () => r(require('@/components/page/ResourceSuffix')), 'suffix');
const upload = r => require.ensure([], () => r(require('@/components/common/upload')), 'upload');
const resources = r => require.ensure([], () => r(require('@/components/page/Resources')), 'resources');
const uploadResource = r => require.ensure([], () => r(require('@/components/page/UploadResource')), 'uploadResource');
const user = r => require.ensure([], () => r(require('@/components/page/User')), 'user');

export default new Router({
    //mode: 'history',
    routes: [
        {
            path: '/',
            redirect: '/login'
            //component: Test
        },
        {
            path: '/login',
            component: login
        },
        {
            path: '/readme',
            name: 'readme',
            component: home,
            children:[
                {
                    path: '/',
                    component: readme,
                    meta: []
                },
                {
                    path: '/resources',
                    component: resources,
                    name: 'resources',
                    meta: ['文档管理','文档类别']
                },
                {
                    path: '/uploadResource',
                    component: uploadResource,
                    name: 'uploadResource',
                    meta: ['文档管理','上传文档']
                },
                {
                    path: '/user',
                    component: user,
                    name: 'user',
                    meta: ['用户管理']
                },
                {
                    path: '/resource_type',
                    component: resourceType,
                    name: 'resourceType',
                    meta: ['类型管理']
                },
                {
                    path: '/tag',
                    component: tag,
                    name: 'tag',
                    meta: ['标签管理']
                },
                {
                    path: '/parent_category',
                    component: parentCategory,
                    name: 'parentCategory',
                    meta: ['分类管理']
                },
                {
                    path: '/parent_category/:id/child_category',
                    component: childCategory,
                    name: 'childCategory',
                    meta: ['分类管理','查看子分类']
                },
                {
                    path: '/suffix',
                    component: suffix,
                    name: 'suffix',
                    meta: ['后缀名管理']
                },
                {
                    path: '/upload',
                    component: upload,
                    name: 'upload'
                },
                {
                    path: '/hello',
                    component: HelloWorld,
                    name: 'hello'
                }
            ]
        },
    ]
})

