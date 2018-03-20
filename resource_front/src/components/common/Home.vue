<template>
    <div class="wrapper">
        <v-head></v-head>
        <v-sidebar></v-sidebar>
        <div class="content">
            <div class="crumbs">
                <el-breadcrumb separator="/">
			        <el-breadcrumb-item :to="{ path: '/readme' }">首页</el-breadcrumb-item>
			        <el-breadcrumb-item 
                        v-for="(item, index) in $route.meta" 
                        :key="index">{{item}}
                    </el-breadcrumb-item>
		        </el-breadcrumb>
            </div>
            <transition name="move" mode="out-in"><router-view></router-view></transition>
        </div>
    </div>
</template>

<script>
    import vHead from './Header.vue';
    import vSidebar from './Sidebar.vue';
    export default {
        components:{
            vHead,vSidebar
        }
    }
</script>
