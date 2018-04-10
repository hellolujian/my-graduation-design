<style scoped>
    span {
        display: inline-block;
    }
    button {
        font-size: 14px;
    }
    .remark {
        width: 800px;
        padding: 10px 20px;
        text-align: left;
        border: 1px solid rgb(241, 239, 239);
    }
        .remark .summary {
            font-size: 16px;
            font-weight: bolder;
            color: black;
        }
        .remark .remark-detail {
            margin: 20px;
            padding-bottom: 20px;
            border-bottom: 1px dotted gray;
        }
            .remark .remark-detail .user-avatar {
                width: 50px;
                height: 50px;
                border-radius: 50px;
                vertical-align: top;
            }
            .remark .remark-detail .remark-detail-right {
                display: inline-block;
                margin-left: 10px;
            }
            .remark .remark-detail .remark-detail-right .username {
                margin-bottom: 5px;
                font-size: 14px;
                font-weight: bold;
                margin-right: 20px;
            }
            .remark .remark-detail .remark-detail-right .time {
                float: right;
                padding: 4px;
                margin-left: 20px;
            }
        .remark .page {
            text-align: center;
            margin-bottom: 10px;
        }
        .remark .remark-content {
            background: rgb(252, 251, 251);
            padding: 10px;
        }
</style>

<template>
    <div class="remark">
        <p >
            <span class="summary">用户评价</span>({{total}})
        </p>
        <p class="remark-detail"
            v-for="(item,index) in data"
            :key="index">
            <img class="user-avatar" :src="item.avatar">
            <span class="remark-detail-right">
                <p>
                    <span class="username">{{item.username}}</span>
                    <Rate :value="item.score" disabled></Rate>
                    <span class="time">{{item.createTime}}</span>
                </p>
                <span>{{item.content}}</span>
            </span>
        </p>
        <Page 
            class="page"
            :total="total" 
            :page-size="size"
            show-total
            @on-change="pageChange">
        </Page>
        <p class="remark-content">
            <label v-if="!loginFlag">
                <router-link 
                    :to="{name: 'login'}">登录
                </router-link>后才能评论
            </label>
            <label v-else>
                <label v-if="belongToUserFlag">
                    不能评论自己的资源
                </label>
                <label v-else>
                    <label v-if="!downloadFlag">
                        您还没有下载过该资源
                    </label>
                    <label v-else>
                        <label v-if="remarkFlag">
                            您已经评论过
                        </label>
                        <label v-else>
                            <div>
                                <span>
                                    <h4>评价资源：
                                        <Rate v-model="score"></Rate>
                                    </h4>
                                </span>
                            </div>
                            <div>
                                <span><h4>评论：</h4></span>
                                <span>一个资源可评论一次</span>
                                <Input 
                                    style="margin:5px 0px;" 
                                    v-model="content" 
                                    type="textarea" 
                                    :rows="4" 
                                    placeholder="输入评价内容"
                                    @on-keyup="checkInput">
                                </Input>
                            </div>
                            <div>
                                评论内容不能少于5个字
                                <Button type="primary" style="float:right;" @click="addRemark">发布评论</Button>
                            </div>
                        </label>
                    </label>
                </label>
            </label>
            <label v-if="remarkFlag"></label>
            <label v-if="downloadFlag"></label>
        </p>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                score: 0,//评分
                content: '',//评论内容
            }
        },
        props: {
            total: {//评论总数
                type: Number,
                default: 0,
            },
            size: { //每页数量
                type: Number,
                default: 0
            },
            data: {//评论数据
                type: Array,
            },
            remarkFlag: {
                type: Boolean,//是否已评论
                default: false
            },
            downloadFlag: {
                type: Boolean,//是否已下载
                default: false,
            },
            belongToUserFlag: {
                type: Boolean,//是否属于该用户
                default: false
            },
            loginFlag: {
                type: Boolean,
                default: false,//是否已登录
            }
            
        },
        mounted() {
        },
        methods: {
            //处理分页事件
            pageChange(val) {
                this.$emit('pageChange',val);
            },
            addRemark() {
                this.$emit('addRemark',this.score,this.content);
            },
            checkInput() {
                this.content = this.content.trim();
            }
        },
        
    }
</script>
