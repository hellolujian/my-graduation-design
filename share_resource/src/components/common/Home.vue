<template>
    <el-container direction="vertical">
        <v-head></v-head>
        <transition name="move" mode="out-in">
            <router-view name="middle"></router-view>
        </transition>
        <el-container style="margin:30px 0px 0px 62px">
            <router-view name="resource"></router-view>
            <router-view style="margin-left:20px;" name="sideUserInfo"></router-view>
        </el-container>
        
    </el-container>
</template>
<script>
    import vHead from './Header.vue';
    import vResource from './Resource.vue';
    import vSideUserInfo from './SideUserInfo.vue';

    export default {
        components: {
            vHead,vResource,vSideUserInfo,
        },
    }
</script>


