<style scoped>
    button {
        font-size: 14px;
    }
    .card {
        width:270px;
        text-align:center
    }
        .card p {
            border-bottom: 1px solid rgb(241, 239, 239);
            margin-bottom: 15px;
            padding-bottom: 5px;
        }
        .card .score {
            font-size:30px;
            color:pink;
            font-weight:bolder
        }
        .card h3 {
            margin-top: 15px;
        }
</style>

<template>
    <Card class="card">
        <router-link 
            :to="{name: data.id == null ? 'login' : 'userCenter',
                        params: {
                            redirectPath: path,
                            redirectParams: redirectParams,
                        }}">
            <Avatar 
                size="large" 
                :src="data.avatar">登录
            </Avatar>
        </router-link>
        <h3 v-if="data.id == null">Hi,欢迎来到这里</h3>
        <h3>{{data.username}}</h3>
        <p>
            <label 
                class="download-score" 
                v-if="data.id != null">
                下载积分：<span class="score">{{data.points}}</span>
            </label>
        </p>
        <Button 
            v-if="pageName == 'index'"
            type="info" 
            @click="uploadClick">上传资源
        </Button>
        <Button 
            v-else
            type="text">查看全部{{userUploadCount}}个资源
        </Button>
        
    </Card>
</template>

<script>
    import {login} from '@/utils/userUtil';
    export default {
        props: {
            data: {
                type: Object,
                default: function() {
                    return {
                    }
                }
            },
            pageName: {//判断是哪个页面使用了该组件
                type: String,
                default: 'index'
            },
            userUploadCount: {//该用户上传的资源总数
                type: Number,
                default: 0,
            },
            path: {//该组件所在页面
                type: String,
            },
            redirectParams: {//跳转参数
                type: Object,
                default: function() {
                    return {

                    }
                }
            }
        },
        methods: {
            uploadClick() {
                this.$emit('upload')
            }
        },
        mounted() {
        }
    }
</script>