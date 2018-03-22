<template>
    <el-container style="text-align:center"  direction="vertical">
        <v-head></v-head>
        <transition name="move" mode="out-in">
            <router-view name="middle"></router-view>
        </transition>
        <router-view name="resource"></router-view>
    </el-container>
</template>
<script>
    import vHead from './Header.vue';
    import vResource from './Resource.vue';
    export default {
        components: {
            vHead,vResource
        },
    }
</script>


