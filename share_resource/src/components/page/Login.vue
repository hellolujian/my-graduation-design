<style scoped>
    .login {
        width:320px; 
        border: 1px solid red;
        padding: 10px 30px;
    }
        .login .forgetPassword {
            float:right;
            color: rgba(128, 128, 128, 0.76);
        }
        .login .forgetPassword:hover {
            color: red;
        }
        .login .login-header {
            width: 100%;
            margin-bottom: 30px;
            height: 40px;
            border-bottom: 1px solid rgba(128, 128, 128, 0.76);
        }
            .login .login-header span {
                display: inline-block;
                font-size: 16px;
                height: 40px;
                line-height: 40px;
                padding: 0px 20px;
            }
            .login .login-header .login-register:hover {
                color: red;
                border-bottom: 1px solid red;
                cursor: pointer;
            }  
            .login-register-click {
                color: red;
                border-bottom: 1px solid red;
            }

</style>


<template>
<div class="login">
    <p class="login-header">
        <span 
            :class="[loginClick == true ? 'login-register-click' : '',
                    'login-register']" @click="login">登录
        </span>
        <span :class="[loginClick == false ? 'login-register-click' : '',
                    'login-register']" @click="register">注册</span>
    </p>
    <Form ref="formInline" :model="formInline" :rules="ruleInline" >
        <FormItem prop="username">
            <i-input size="large" type="text" v-model="formInline.username" placeholder="Username">
                <Icon type="ios-person-outline" slot="prepend"></Icon>
            </i-input>
        </FormItem>
        <FormItem prop="password">
            <i-input size="large" type="password" v-model="formInline.password" placeholder="Password">
                <Icon type="ios-locked-outline" slot="prepend"></Icon>
            </i-input>
        </FormItem>
        <FormItem>
            <Checkbox v-model="agree">7天内自动登录</Checkbox>
            <router-link class="forgetPassword" to="www.baidu.com">忘记密码</router-link>
        </FormItem>
        <FormItem>
            <Button 
                size="large" 
                long 
                type="error" 
                @click="handleSubmit('formInline')">登录
            </Button>
        </FormItem>
    </Form>
    <p class="login-bottom">
        <span>
            
        </span>
    </p>
</div>
</template>
<script>
    import {getRequest} from '../../utils/api';
    import {login} from '@/utils/userUtil';
    export default {
        data () {
            
            return {
                agree: true,//checkbox结果
                loginClick: true,//判断是否是登录操作还是注册操作
                loginUrl: '/users/login',
                formInline: {
                    username: '陆健',
                    password: '123456'
                },
                ruleInline: {
                    username: [
                        { required: true, message: '请填写用户名', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请填写密码', trigger: 'blur' },
                        { type: 'string', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            handleSubmit(name) {
                const self = this;
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        let params = {
                            username: self.formInline.username,
                            password: self.formInline.password,
                        };
                        getRequest(self.loginUrl, params).then(response => {
                            let userInfo = response.data.data;
                            console.log(userInfo)
                            if(userInfo != null) {
                                login(userInfo);
                                self.$router.push('/');
                            } else {
                                this.$Message.error('用户名或密码错误');
                            }
                        }); 
                    } 
                })
            },
            login() {
                this.loginClick = true;
            },
            register() {
                this.loginClick = false;
            }
        }
    }
</script>
