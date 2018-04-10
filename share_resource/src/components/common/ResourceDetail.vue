<style scoped>
    button {
        font-size: 14px;
    }
    span {
        display: inline-block;
    }
    .detail-out {
        padding: 20px;
        width: 800px;
        border: 1px solid rgb(241, 239, 239);
        text-align: left
    }
        .detail-out p {
            width: 100%;
        }
        .detail-out .detail-base {
            height: 60px;
        }
            .detail-out .detail-base .suffix-icon {
                vertical-align: top;
                width: 50px;
                height:50px;
                display: inline-block;
            }
            .detail-out .detail-base .detail-right {
                height: 100%;
                margin-left: 10px; 
            }
                .detail-out .detail-base .detail-right h3 {
                    color: black;
                }
                .detail-out .detail-base .detail-right label span {
                    padding-right: 10px;
                }
                    .detail-base .detail-right label span Tag {
                        margin: 10px;
                    }
        .detail-out .detail-description {
            background: rgb(241, 239, 239);
            padding: 10px;
            color: black;
        }
        .detail-out .detail-remark {
            padding: 10px 0px;
            margin: 10px 0px;
            border-top: 1px solid rgb(241, 239, 239);
            border-bottom: 1px solid rgb(241, 239, 239);
        }
            .detail-out .detail-remark .collect {
                cursor: pointer;
                float: right;
                margin:0px 20px;
            }
            .detail-out .detail-remark .pinglun {
                
                float: right;
                margin:0px 20px;
            }
            .detail-out .detail-remark .pinglun:hover {
                cursor: pointer;
            }
            .detail-out .detail-remark span {
                margin-right: 30px;
            }
            .detail-out .detail-remark .vertical {
                vertical-align: middle;
            }
        .detail-out .detail-last {
            padding: 10px 0px;
            color: black;
        }
            
            .detail-out .detail-last .points {
                color: yellow;
                font-weight: bolder;
                font-size: 20px;
            }
            .detail-out .detail-last label {
                margin-right: 30px;
            }
            .detail-out .detail-last .download-btn {
                float: right;
            }
</style>

<template>
    <div class="detail-out">
        <p class="detail-base">
            <img 
                :src="resourceDetailData.image"
                class="suffix-icon">
            <span class="detail-right">
                <h3>{{resourceDetailData.title}}</h3>
                <label>
                    <span>{{resourceDetailData.uploadTime}}上传</span>
                    <span>大小：{{resourceDetailData.size}}</span>
                    <span>
                        
                        <Tag class="tag"
                            v-for="(item,index) in resourceDetailData.tagList"
                            :key="index"
                            type="border" 
                            color="red"
                            @click.native="tagClick(item)">
                            {{item}}
                        </Tag>
                    </span>
                </label>
            </span>
            
        </p>
        <p class="detail-description">{{resourceDetailData.description}}</p>
        <p class="detail-remark">
            <Rate disabled v-model="averageScore"></Rate>
            <span>综合评分：{{averageScore}}</span>
            <span>({{contentAmount}}位用户评分)</span>
            <label class="pinglun" @click="remark">
                <Icon 
                    type="chatbubble-working" 
                    size="20" 
                    class="vertical"></Icon>
                <span class="vertical">评论({{contentAmount}})</span>
            </label>
            <label class="collect" @click="collect" >
                <Icon 
                    class="vertical"
                    type="star" 
                    :color="collectFlag == true ? 'red' : 'gray'" 
                    size="20" >
                </Icon>
                <span class="vertical">收藏({{collectAmount}})</span>
            </label>
            
        </p>
        <p class="detail-last">
            <label>
                所需： <span class="points">{{resourceDetailData.points}}</span>积分
            </label>
            <label>
                下载个数：<span class="points">{{resourceDetailData.downloads}}</span>
            </label>
            <label class="download-btn">
                    <Button 
                        type="primary" 
                        icon="ios-cloud-download"
                        @click="download">立即下载
                    </Button>
            </label>
        </p>
        <div style="clear:both;"></div>
    </div>
</template>

<script>
    export default {
        data() {
            return {

            }
        },
        props: {
            resourceDetailData: {
            },
            collectFlag: {
                type: Boolean,//是否已被收藏
                default: false
            },
            contentAmount: {//评论数量
                type: Number,
                default: 0
            },
            scoreAmount: {//评分数量
                type: Number,
                default: 0
            },
            collectAmount: {//收藏数量
                type: Number,
                default: 0
            },
            averageScore: {//平均分
                type: Number,
                default: 0
            },
            href: {
                type: String,//下载地址
                default: '',
            }
        },
        mounted() {
        },
        methods: {
            collect() {
                this.$emit('collect');//点击收藏事件
            },
            remark() {
                this.$emit('remark');//点击评论事件
            },
            tagClick(keyword) {
                this.$emit('getKeyword',keyword);//点击标签事件
            },
            download() {
                this.$emit('download');
            }
        }
    }
</script>

