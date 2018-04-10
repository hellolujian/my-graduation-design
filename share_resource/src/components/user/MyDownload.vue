<style scoped>
    .resource {
        width: 830px;
        border: 1px solid rgba(10, 10, 10, 0.219);
        padding: 10px 10px 0px 10px;
        margin: 20px 10px;
    }
    .item {
        border-bottom: 2px rgba(184, 179, 179, 0.219) dotted;
        margin-bottom: 10px;
    }
    .iteme-left {
        display:inline-block;
        vertical-align:top;
        padding: 10px;
    }
    .suffix-icon {
        vertical-align:top;
        height:50px;
        width: 50px;
        margin-top:10px;
    }
    .item-right {
        width: 750px;
        display:inline-block;
        vertical-align:top;
        padding: 5px;
    }
    .item-right-title {
        display: inline-block;
        color: black;
    }
    .item-right-title:hover {
        color: red;
    }
    span {
        padding-right: 20px;
        display: inline-block;
        margin: 0px 0px 10px 0px;
    }
    .tag {
        margin: 10px;
    }
    .page {
        text-align: center;
        margin-bottom: 10px;
    }
    .remark {
        float: right;
    }
    .remark-no {
        cursor: pointer;
        color: blue;
        text-decoration:underline;
    }
    .remark-no:hover {
        color: red;
    }
    .warning {
        width:100%;
        height:300px;
        border:1px solid red;
        text-align: center;
        padding-top: 80px;
    }
</style>
<template>
    <div class="resource">
        <div 
            v-if="total == 0"
            class="warning">
            <img src="/static/image/empty_icon.png">
            <p>提示：你还没有下载资源</p>
        </div>
        <div
            v-for="(item,index) in resourceData.slice((pageNum-1)*pageSize,pageNum*pageSize)" 
            :key="index"
            class="item">
            <router-link 
                :to="{name: 'download', params: { resourceId: item.id }}" 
                class="item-left">
                <img class="suffix-icon" :src="item.image" />
            </router-link>
            <div class="item-right">
                <p>
                    <router-link 
                        class="item-right-title" 
                        :to="{name: 'download', params: { resourceId: item.id }}">
                        <h3>{{item.title}}</h3>
                    </router-link>
                </p>
                
                <p>{{item.description}}</p>
                <p>
                    <Tag class="tag"
                        v-for="(itemTag,index) in item.tagList"
                        :key="index"
                        type="border" 
                        color="red"
                        @click.native="tagClick(itemTag)">
                        {{itemTag}}
                    </Tag>
                </p>
                <p style="width:100%">
                    <span>下载个数：{{item.downloads}}</span>
                    <span>上传时间：{{item.uploadTime}}</span>
                    <span>所需积分：{{item.points}}</span>
                    <span 
                        :class="[remarkFlag == false ? 'remark-no' : '', 'remark']"
                        @click="goToRemark(item.id)">
                        {{remarkFlag == true ? '已评价' : '去评价'}}
                    </span>
                </p>
            </div>
            
        </div>
        <p v-if="total != 0" class="page">
            <Page 
                :total="total"
                :current.sync="currentPageNum"
                :page-size="pageSize"
                @on-change="pageChange">
            </Page>
        </p>
        
    </div>
</template>
<script>
    
    export default {
        data() {
            return {
                pageSize: 3,//
                pageNum: 1,
                currentPageNum: 1,//当前页
            }
        },
        computed: {
            total: function() {
                return this.resourceData.length;//获取总数，用计算属性而不是放进data是因为同步，异步
            }
        },
        props: {
            resourceData: {
                type: Array,
                default: function() {
                    
                }
            },
            remarkFlag: {//是否已评价
                type: Boolean,
                default: false,
            }
        },
        
        mounted() {
        },
        methods: {
            tagClick(itemTag) {
                this.$emit('getTag',itemTag);
            },
            pageChange(val) {
                this.pageNum = val;
            },
            goToRemark(resourceId) {
                this.$emit('goToRemark',resourceId);
            }
        },
        watch: {//监听数据，使当前页回到1
            resourceData: function() {
                this.currentPageNum = 1;
            }
        }
    }
</script>