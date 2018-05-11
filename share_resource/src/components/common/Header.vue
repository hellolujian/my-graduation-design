<!--
<template>
    <el-header height="70px">
        <div class="logo">
            <img src="static/image/share_logo2.png" title="在线文库分享">
        </div>
        <div class="serachInput">
            <el-input 
                placeholder="请输入内容"
                :autofocus="true">
                <el-button slot="append" icon="el-icon-search">搜索</el-button>
            </el-input>
        </div>
        <div class="head-right">
            <el-button class="head-right-btn">登录</el-button>
            <el-button class="head-right-btn">注册</el-button>
        </div>
    </el-header>
</template>

<style>
    .logo {
        float: left;
    }
    .head-right {
        float: right;
    }
    .head-right-btn {
        border: none;
    }
    .serachInput {
        width: 400px;
        display: inline-block;
    }
</style>
-->

<style scoped>
Button {
  font-size: 14px;
  outline: none;
}
.head {
  box-shadow: 1px 1px 5px rgb(133, 127, 127);
  color: #333;
  text-align: center;
  height: 70px;
  line-height: 70px;
  box-sizing: border-box;
}
.head .logo {
  float: left;
}
.head .serachInput {
  width: 400px;
  display: inline-block;
  padding: 15px 0px;
}
.head .head-right {
  float: right;
  margin: 0px 40px;
}
.head .head-right .head-right-btn {
  border: none;
  margin: 20px;
}
.head .head-right .user-avatar {
    cursor:pointer;
}
</style>

<template>
    <div class="head">
        <router-link :to="{name: 'index'}" class="logo">
            <img src="static/image/share_logo2.png" title="在线文库分享">
        </router-link>
        <div class="serachInput">
            <i-input
                v-model="keyword"
                size="large" 
                placeholder="请输入内容">
                <Button slot="append" icon="ios-search" @click="search">搜索</Button>
            </i-input>
        </div>
        <div class="head-right">
            <span v-if="loginFlag">
                <Button 
                    class="head-right-btn"
                    @click="login">登录</Button>
                <Button 
                    class="head-right-btn"
                    @click="register">注册</Button>
            </span>
            <Dropdown v-if="!loginFlag" @on-click="handleDropdownClick">
                <Avatar size="large" class="user-avatar" src="../../../static/image/avatar.jpg"></Avatar>
                <DropdownMenu slot="list" >
                    <DropdownItem>驴打滚</DropdownItem>
                    <DropdownItem>炸酱面</DropdownItem>
                    <DropdownItem>冰糖葫芦</DropdownItem>
                    <DropdownItem>北京烤鸭</DropdownItem>
                    <DropdownItem name="loginOut">退出</DropdownItem>
                </DropdownMenu>
            </Dropdown>
        </div>
        
    </div>
</template>

<script>
    import {isUserLogin,loginOut} from '@/utils/userUtil';
    export default {
        data() {
            return {
                keyword: "",
                loginFlag: true,
            };
        },
        methods: {
            search() {
                this.$emit("getKeyword", this.keyword);
            },
            login() {
                this.$router.push({
                    name: "login",
                    params: {
                        redirectPath: 'index',
                        redirectParams: {
                            
                        },
                        loginClick: true,
                    }
                });
            },
            register() {
                this.$router.push({
                    name: 'login',
                    params: {
                        redirectPath: 'index',
                        redirectParams: {
                            
                        },
                        loginClick: false
                    }
                })
            },
            isLogin() {
                let userId = null;
                if(isUserLogin() != null) 
                    userId = isUserLogin().id;
                if(userId == null)
                    this.loginFlag = true;
                else 
                    this.loginFlag = false;
            },
            handleDropdownClick(name) {
                loginOut();
                this.isLogin();
                this.$emit('getUserInfo',isUserLogin());
            }
        },

        mounted() {
            this.isLogin();
            var newKey = this.$route.params.keyword;
            if (newKey != null) {
                this.keyword = newKey;
                this.$emit("getKeyword", this.keyword);
            };
            this.$emit('getUserInfo',isUserLogin());
        }
    };
</script>





