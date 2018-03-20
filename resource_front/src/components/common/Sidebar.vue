<template>

    <div class="sidebar">
        <el-menu 
            :default-active="onRoutes" 
            class="el-menu-vertical-demo" 
            theme="dark"
            unique-opened>
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>{{ item.title }}
                        </template>
                        <template v-for="(subItem,i) in item.subs">
                            <router-link :to="{ name: subItem.name}" :key="i" >
                                <el-menu-item :index="subItem.index">
                                    {{ subItem.title }}         
                                </el-menu-item>
                            </router-link> 
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <router-link :to="{ name: item.name}" :key="item.index">
                        <el-menu-item :index="item.index" >
                            <i :class="item.icon"></i>{{ item.title }}
                        </el-menu-item>
                    </router-link>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                items: [
                    {
                        icon: 'el-icon-setting',
                        index: 'readme',
                        name: 'readme',
                        title: '系统简介'
                    },
                    {
                        icon: 'el-icon-menu',
                        index: '2',
                        title: '文档管理',
                        subs: [
                            {
                                index: 'uploadResource',
                                name: 'uploadResource',
                                title: '上传文档'
                            },
                            {
                                index: 'resources',
                                name: 'resources',
                                title: '文档列表'
                            },
                            
                        ]
                    },
                    {
                        icon: 'el-icon-date',
                        index: 'user',
                        name: 'user',
                        title: '用户管理',
                    },
                    {
                        icon: 'el-icon-d-caret',
                        index: 'resource_type',
                        name: 'resourceType',
                        title: '类型管理',
                    },
                    {
                        icon: 'el-icon-document',
                        index: 'suffix',
                        name: 'suffix',
                        title: '后缀名管理',
                    },
                    {
                        icon: 'el-icon-information',
                        index: 'tag',
                        name: 'tag',
                        title: '标签管理',
                    },
                    {
                        icon: 'el-icon-message',
                        index: 'parent_category',
                        name: 'parentCategory',
                        title: '分类管理',
                    },
                    {
                        icon: 'el-icon-star-on',
                        index: '8',
                        title: '数据查询',
                        subs: [
                            {
                                index: 'hello',
                                name: 'hello',
                                title: '下载记录'
                            }
                        ]
                    }
                ]
            }
        },
        computed:{
            onRoutes(){
                return this.$route.path.replace('/','');
            }
        },
        watch: {
           // "$route" : "change"
        },
        methods: {
            change() {
                if (this.$route.path == '/parent_category/10/child_category') {
                    alert('pipei');
                    this.$route.path = '/'
                } else {
                    alert('bupipei');
                }
                
            }
        }
    }
</script>

<style scoped>
    .sidebar{
        display: block;
        position: absolute;
        width: 201px;
        left: 0;
        top: 70px;
        bottom:0;
        background: #2E363F;
    }
    .sidebar > ul {
        height:100%;
    }
</style>
