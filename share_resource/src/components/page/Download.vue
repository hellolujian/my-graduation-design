<style scoped>
    .header {
        display:inline-block;
        width:100%
    }
    .main {
        margin: 0 auto;
        text-align: center
    }
        .main .main-left {
            display: inline-block;
            margin: 0px 10px 10px 0px;
        }
            .main .main-left .main-left-top {
                margin-bottom: 10px;
            }
        .main .main-right {
            display: inline-block;
            vertical-align: top;
        }
            .main .main-right .main-right-upload {
                margin-bottom: 10px;
                font-size: 16px;
            }
        .modal-message {
            height:40px;
            line-height:40px;
            
        }
            .modal-message .icon {
                vertical-align:middle;
            }
            .modal-message .text {
                margin: 0px 10px;
                font-size: 18px;
            }
        .modal-content {
            text-align: center;
            font-size: 16px;
        }

</style>
<template>
    <div>
        <p class="header">
            <v-head @getKeyword="getKey" @getUserInfo="getUserInfo"></v-head>
        </p>
        <div class="main">
            <span class="main-left">
                <router-view 
                    class="main-left-top"
                    name="resourceDetail" 
                    :collectFlag="collectFlag" 
                    :resourceDetailData="resourceDetailData"
                    :contentAmount="contentAmount"
                    :scoreAmount="scoreAmount"
                    :collectAmount="collectAmount"
                    :averageScore="averageScore"
                    :href="downloadUrl"
                    @remark="handleRemark"
                    @download="handleDownload"
                    @collect="handleCollect"
                    @getKeyword="getKey">
                </router-view>
                <div ref="remarkDiv">
                    <router-view 
                        name="remark"
                        :total="contentAmount"
                        :data="remarkData"
                        :size="pageSize"
                        :downloadFlag="downloadFlag"
                        :remarkFlag="remarkFlag"
                        :belongToUserFlag="belongToUserFlag"
                        :loginFlag="'id' in userInfo ? true : false"
                        @pageChange="pageChange"
                        @addRemark="addRemark">
                    </router-view>
                </div>
            </span>
            
        
        <span class="main-right">
            <Button 
                long 
                size="large"
                type="error"
                icon="ios-cloud-upload"
                class="main-right-upload">上传资源
            </Button>
            <router-view
                name="sideUserInfo"
                :data="userInfo"
                :userUploadCount="userUploadCount"
                pageName="download"
                @upload="upload">
            </router-view>
        </span>
    </div>
    <Modal v-model="showModal">
        <p slot="header" class="modal-message">
            <span>
                <Icon 
                    size="40" 
                    color="green" 
                    type="checkmark-circled" 
                    class="icon">
                </Icon>
            </span>
            <span class="text">收藏成功</span>
        </p>
        <p class="modal-content">
            点击
            <router-link :to="{ name: 'userCenter' }">
                我的收藏
            </router-link>
            查看全部收藏资源
        </p>
        <p slot="footer">
            
        </p>
    </Modal>
    </div>
</template>

<script>
    import {getRequest,postRequest,getRequest2} from '../../utils/api';
    import vHead from '@/components/common/Header';

    export default {
        components: {
            vHead,
        },
        data() {
            return {
                getResourceDetailUrl: '/resources/detail/',//获取资源详细信息url
                resourceDetailData: {},//资源详细信息
                contentAmount: 0,//评论数量
                scoreAmount: 0,//评分数量
                collectAmount: 0,//收藏数量
                averageScore: 0,//平均分
                resourceId: this.$route.params.resourceId,//获取该资源编号
                isCollectUrl: '/collections/user/',
                collectFlag: false,//是否收藏
                isDownloadUrl: '/downloads/user/',
                downloadFlag: false,//是否下载
                isRemarkUrl: '/remarks/user/',
                remarkFlag: false,//是否评论
                isBelongToUserUrl: '',
                belongToUserFlag: false,//是否属于该用户
                getRemarkDataUrl: '/remarks/page/resourceId/',//评论数据url
                remarkData: [],//评论数据
                pageNum: 1,
                pageSize: 3,
                userInfo: {},//获取该资源所属用户信息
                getUserUploadCountUrl: '/resources/count/userId/',//获取该资源所属用户上传资源数url
                userUploadCount: 0,//该资源所属用户上传资源数
                addCollectionUrl: '/collections/addCollection',//添加收藏记录url
                showModal: false,//是否显示对话框
                addDownloadUrl: '/downloads/addDownload/',//添加下载记录url
                downloadUrl: 'http://127.0.0.1:8082/resources/download/',//下载url(下载至用户本地)
                addRemarkUrl: '/remarks/addRemark'
            }
        },
        mounted() {
            this.isCollect(this.resourceId);
            this.isRemark(this.resourceId);
            this.isDownload(this.resourceId);
            //this.isBelongToUser();  //由于getUserInfo中已有此判断，所以这里不需要了,但是又由于异步延迟，所以有点麻烦，后续改进
            this.getResourceDetailData();
            this.getRemarkData();
            this.getUserUploadCount();
            this.getDownloadUrl();
        },
        methods: {
            //获取该资源所属用户上传资源数
            getUserUploadCount() {
                let url = this.getUserUploadCountUrl + this.resourceId;
                getRequest(url).then(response => {
                    this.userUploadCount = response.data.data;
                })
            },
            //获取资源详细信息
            getResourceDetailData() {
                let url = this.getResourceDetailUrl + this.resourceId;
                getRequest(url).then(response => {
                    //console.log(response.data.data);
                    this.resourceDetailData = response.data.data.data;
                    this.contentAmount = response.data.data.contentAmount;
                    this.scoreAmount = response.data.data.scoreAmount;
                    this.collectAmount = response.data.data.collectAmount;
                    this.averageScore = response.data.data.averageScore;
                    this.isBelongToUser();
                }, response => {
                    this.$alert('找不到')
                })
            },
            //判断是否收藏
            isCollect(resourceId) {
                if('id' in this.userInfo) {
                    let userId = this.userInfo.id;
                    let url = this.isCollectUrl + userId + '/resource/' + resourceId;
                    getRequest(url).then(response => {
                        this.collectFlag = response.data.data;
                    })
                } 
            },
            //判断是否下载
            isDownload(resourceId) {
                if('id' in this.userInfo) {
                    let userId = this.userInfo.id;
                    let url = this.isDownloadUrl + userId + '/resource/' + resourceId;
                    getRequest(url).then(response => {
                        this.downloadFlag = response.data.data;
                    })
                } 
            },
            //判断是否评论
            isRemark(resourceId) {
                if('id' in this.userInfo) {
                    let userId = this.userInfo.id;
                    let url = this.isRemarkUrl + userId + '/resource/' + resourceId;
                    getRequest(url).then(response => {
                        this.remarkFlag = response.data.data;
                    })
                } 
            },
            //判断是否属于该用户
            isBelongToUser() {
                //alert(this.resourceDetailData.userId)
                console.log(this.resourceDetailData)
                if('id' in this.userInfo && this.resourceDetailData.userId == this.userInfo.id) {

                    this.belongToUserFlag = true;
                } else {
                    this.belongToUserFlag = false;
                }
            },
            //获取关键字
            getKey(keyword) {
                this.$router.push({
                    name: "searchResource",
                    params: {
                        parentCategoryId: 0,
                        childCategoryId: 0,
                        resourceTypeId: 0,
                        keyword: keyword
                    }
                });
            },
            //获取评论数据
            getRemarkData() {
                let url = this.getRemarkDataUrl + this.resourceId;
                let params = {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                }
                getRequest(url, params).then(response => {
                    this.remarkData = response.data.data;
                    this.contentAmount = this.remarkData.length
                    console.log(response.data.data);
                }, response => {
                    this.$alert('找不到')
                })
            },
            pageChange(val) {
                this.pageNum = val;
                this.getRemarkData();
            },
            //获取用户信息
            getUserInfo(userInfo) {
                //console.log(userInfo)
                if(userInfo == null) 
                    userInfo = {};
                this.userInfo = userInfo;
                this.isBelongToUser();//退出后就不属于该用户了
                //console.log('userInfo:'+this.userInfo);
            },
            //点击上传按钮
            upload() {
                this.$router.push({
                    name: 'userCenter'
                })
            },
            //收藏
            handleCollect() {
                if('id' in this.userInfo) {
                    if(!this.collectFlag) {
                        let params = {
                            userId: this.userInfo.id,
                            resourceId: this.resourceId
                        };
                        postRequest(this.addCollectionUrl,params).then(response => {
                            console.log(response.data.data);
                            this.collectFlag = true;
                            this.showModal = true;
                            this.collectAmount++;
                        }, response => {

                        })
                    }
                } else {
                    this.$router.push({
                        name: 'login'
                    })
                }
                
            },
            //跳转到评论处
            handleRemark() {
                let remarkHeight = this.$refs.remarkDiv.offsetHeight;
                window.scrollTo(0,remarkHeight);
            },
            //发布评论
            addRemark(score,content) {
                if(score == 0) {
                    this.$Message.warning({
                        content: '您还没有评分',
                        closable: true
                    });
                    return;
                }
                let contentLength = content.trim().length;
                if(contentLength != 0 && contentLength < 5) {
                    this.$Message.warning({
                        content: '评论内容不得少于五个字',
                        closable: true
                    });
                    return;
                }
                let params = {
                    userId: this.userInfo.id,
                    resourceId: this.resourceId,
                    score: score,
                    content: content
                };
                postRequest(this.addRemarkUrl,params).then(response => {
                    console.log(response.data.data);
                    this.$Message.success({
                        content: '评论发布成功',
                        closable: true
                    });
                    this.getRemarkData();
                    this.remarkFlag = true;
                })
            },
            //下载
            handleDownload() {
                if('id' in this.userInfo) {
                    //window.open(this.downloadUrl,'','',true)
                    getRequest2(this.downloadUrl).then(response => {
                        if(response.headers['download-status'] != 0) {
                            alert(decodeURI(response.headers['download-message']))

                        } else {
                            alert('开始下载')
                            let url = window.URL.createObjectURL(new Blob([response.data]));
                            let link = document.createElement('a');
                            link.style.display = 'none';
                            link.href = url;
                            link.setAttribute('download',this.resourceDetailData.name);
                            document.body.appendChild(link);
                            link.click();
                        }
                        console.log(response.data)
                        console.log(response.headers);
                        console.log(response.status)
                        console.log(decodeURI(response.headers['download-message']))
                        this.downloadFlag = true;
                        this.getResourceDetailData();
                    })
                }
            },
            //获取下载地址
            getDownloadUrl() {
                if('id' in this.userInfo) {
                    this.downloadUrl = `${this.downloadUrl}${this.resourceId}/users/${this.userInfo.id}`;
                }
                else 
                    this.downloadUrl = '#/login'
            },
            //
        }
    }
</script>
