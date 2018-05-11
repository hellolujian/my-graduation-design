<style scoped>

</style>

<template>
    <div >
        <Menu 
            theme="dark" 
            accordion 
            width="150px"
            :open-names="['my-account','resource-management']"
            :active-name="activeName"
            @on-select="handleSelect">
            <Submenu name="resource-management">
                <template slot="title">
                    <Icon type="stats-bars"></Icon>
                    资源管理
                </template>
                <MenuItem name="my-upload">我的资源</MenuItem>
                
                <MenuItem name="upload">我要上传</MenuItem>
            </Submenu>
            
            <MenuItem name="my-download">
                <Icon type="ios-people"></Icon>
                下载明细
            </MenuItem>
            <MenuItem name="my-collection">
                    <Icon type="stats-bars"></Icon>
                    我的收藏
            </MenuItem>
            <MenuItem name="my-remark">
                    <Icon type="stats-bars"></Icon>
                    我的评论
            </MenuItem>
            <Submenu name="my-account">
                <template slot="title">
                    <Icon type="stats-bars"></Icon>
                    账户管理
                </template>
                <MenuItem name="my-info">个人信息</MenuItem>
                <MenuItem name="my-login">登录日志</MenuItem>
                <MenuItem name="my-password">修改密码</MenuItem>
            </Submenu>
            -->
        </Menu>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                activeName: 'my-upload',
            }
        },
        
        methods: {
            handleSelect(itemName) {
                
                this.$emit('menuItemSelect',itemName)
            },
            handleActiveName() {
                this.activeName = this.$route.name;
            }
        },
        mounted() {
            
            this.activeName = this.$route.name;
            //alert(this.activeName)
        },
        watch: {
          // 如果路由有变化，会再次执行该方法
          "$route.name": "handleActiveName"
        }
    }
</script>
