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
    mounted() {
        this.websocketConnection();
    },
    methods: {
        websocketConnection() {
            var websocket = null;
            if('WebSocket' in window){
                websocket = new WebSocket('ws://localhost:8082/websocket');
            }else {
                alert('该浏览器不支持websocket!')
            }
            websocket.onopen = function(event){
                console.log('建立连接')
            }
            websocket.onclose = function(event){
                console.log('连接关闭')
            }
            websocket.onmessage = function(event){
                console.log('收到消息：'+event.data);
                //弹窗提醒，
                alert('有消息来了！')
            }
            websocket.onerror = function(){
                alert('websocket通信发生错误！')
            }
            window.onbeforeunload = function(){
                websocket.close();
            }
        }
    }
    ,
    components:{
        vHead,vSidebar
    }
}
</script>
