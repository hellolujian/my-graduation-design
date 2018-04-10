<style scoped>
    button {
        font-size: 14px;
    }
    span {
        display: inline-block;

    }
    .out {
        background-image: url(/static/image/userDetailInfo_backImg.png);
    }
    .detail {
        color: white;
        margin: 0 auto;
        padding: 20px 0px;
        margin: 0px auto;
        width: 90%;
    }
        .detail .detail-avatar {
            width: 100px;
            height: 100px;
            border-radius: 100px;
            vertical-align: middle;
            margin-right: 20px;
        }
        .detail .detail-info {
            float: right;
            padding: 22px;
        }
            .detail .username {
                
            } 
            .detail .detail-info span {
                text-align: center;
                padding: 0px 20px;
                
            }
                .detail .detail-info span h2 {
                    color: rgb(243, 142, 142);
                }
        .detail .user-setting-btn {
            float: right;
            padding: 30px;
        }
</style>

<template>
<div class="out">
    <div class="detail">
        <span>
            <img :src="data.avatar" class="detail-avatar">
        </span>
        
        <span class="username">
            <h2 >{{data.username}}</h2>
        </span>
        <span class="user-setting-btn">
            <Button shape="circle" icon="ios-gear">个人设置</Button>
        </span>
        <span class="detail-info">
            <span>
                <p>积分</p>
                <h2>{{data.points}}</h2>
            </span>
            <span>
                <p>上传资源</p>
                <h2>{{uploadAmount}}</h2>
            </span>
            <span>
                <p>下载资源</p>
                <h2>{{downloadAmount}}</h2>
            </span>
            <span>
                <p>收藏资源</p>
                <h2>{{collectAmount}}</h2>
            </span>
        </span>
        <div style="clear:both"></div>
    </div>
</div>
</template>

<script>
    export default {
        props: {
            data: {
                type: Object,
                default: function() {
                    return {
                    id: 1,
                    username: "陆健",
                    email: "1370256381@qq.com",
                    phone: "18852852189",
                    avatar: "http://127.0.0.1:8082/image/user_avatar/girl.jpg",
                    points: 0,
                    tags: [
                        "mysql",
                        "java"
                    ],
                    createTime: null,
                    updateTime: null
                }
                }
            },
            uploadAmount: {
                type: Number,
                default: 0,
            },
            downloadAmount: {
                type: Number,
                default: 0,
            },
            collectAmount: {
                type: Number,
                default: 0,
            }
        }
    }
</script>
