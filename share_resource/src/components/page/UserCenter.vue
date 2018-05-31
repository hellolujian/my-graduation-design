<style scoped>
    span {
        display: inline-block;
        
    }
    .main {
        padding: 0px;
    }
        .main .main-resource {
            vertical-align:top;
        }
        .main .main-tags {
            vertical-align: top;
            width: 240px;
            margin: 20px 0px 0px 5px;
        }
            .main .main-tags .upload-btn {
                margin-bottom: 10px;
                font-size: 16px;
            }
</style>

<template>
    <div>
        <v-head @getKeyword="getKey" @getUserInfo="getUserInfo"></v-head>
        <v-user-detail-info
            :data="userInfo"
            :uploadAmount="uploadAmount"
            :downloadAmount="downloadAmount"
            :collectAmount="collectAmount"/>
        </v-user-detail-info>
        <div class="main">
            <div>
                <span>
                    <v-user-nav-bar @menuItemSelect="menuItemSelect"></v-user-nav-bar>
                </span>
                <span class="main-resource">
                    <div>
                        <router-view 
                            :resourceData="resourceData"
                            :remarkFlag="remarkFlag"
                            @getTag="getTagValue"
                            @goToRemark="goToRemark"
                            @cancelCollect="cancelCollect">
                        </router-view>
                        <router-view 
                            name="upload"
                            :resourceUrl="resourceUrl"
                            :resourceType="resourceType"
                            :points="points"
                            :parentCategory="parentCategory"
                            :childCategory="childCategory"
                            @getChildCategoryData="getChildCategoryData"
                            @uploadSuccess="uploadSuccess">
                        </router-view>
                        
                    </div>
                </span>
                <span style="vertical-align:top">
                    <router-view name="uploadAttention"></router-view>
                </span>
                <span v-if="$route.name != 'upload'" class="main-tags">
                    <Button 
                        long 
                        size="large"
                        type="error"
                        icon="ios-cloud-upload"
                        class="upload-btn"
                        @click="uploadBtnClick">上传资源
                    </Button>
                    <router-view 
                        name="tags" 
                        title="我的tag" 
                        :tagList="tagList" 
                        @getTag="getTagValue">
                    </router-view>
                </span>
            </div>
        </div>
        
    </div>
</template>

<script>
    import {getRequest,postRequest} from '../../utils/api';
    import {setActiveName} from '@/utils/userUtil';
    import vHead from '@/components/common/Header';
    import vUserNavBar from '@/components/common/UserNavBar';
    import vUserDetailInfo from '@/components/common/UserDetailInfo';
    
    export default {
        data() {
            return {
                tagList: [],//我的标签
                userInfo: {},//用户信息
                uploadAmount: 0, //用户上传数量
                collectAmount: 0, //用户收藏数量
                downloadAmount: 0, //用户下载数量
                getMyResourceUrl: '/resources/users/',
                resourceData: [],//我的资源
                getCollectionDataUrl: '/collections/users/',//
                getDownloadUrl: '/downloads/users/',
                isRemarkUrl: '/remarks/user/',//判断用户是否评价某资源url
                remarkFlag: false,//是否已评价
                cancelCollectionUrl: '/collections/cancelCollection',

                //关于上传表单的一些数据
                getResourceTypeUrl: '/resourceTypes/', //类型数据url
                resourceType: [],//类型数据
                getPointsUrl: '/points/', //分数数据url
                points: [],//分数数据
                getParentCategoryUrl: '/category/parent', //父分类数据url
                parentCategory: [],//父分类数据
                childCategory: [],//子分类数据

                resourceUrl: '/resources/upload',//上传路径
            }
        },
        components: {
            vHead,vUserNavBar,vUserDetailInfo,
        },
        mounted() {
            this.getMyCollect(this.getCollectionDataUrl);
            this.getMyUpload(this.getMyResourceUrl);
            this.getMyDownload(this.getDownloadUrl);
            var initDataUrl = this.getMyResourceUrl;
            //alert(this.$route.name)
            if(this.$route.name == 'my-collection')
                initDataUrl = this.getCollectionDataUrl;
            else if(this.$route.name == 'my-upload')
                initDataUrl = this.getMyResourceUrl;
            else if(this.$route.name == 'my-download')
                initDataUrl = this.getDownloadUrl;
            //alert(initDataUrl)
            this.getResourceData(initDataUrl);
            this.getResourceTypeData();
            this.getPointsData();
            this.getParentCategoryData();
        },
        methods: {
            //获取数据
            getResourceTypeData() {
                getRequest(this.getResourceTypeUrl).then(response => {
                    this.resourceType = response.data.data;
                })  
            },
            //获取分数
            getPointsData() {
                getRequest(this.getPointsUrl).then(response => {
                    this.points = response.data.data;
                })
            },
            //获取父分类数据
            getParentCategoryData() {
                getRequest(this.getParentCategoryUrl).then(response => {
                    this.parentCategory = response.data.data;
                })
            },
            //获得子分类数据
            getChildCategoryData(val) {
                let url =  `/category/parent/${val}/childCategory`;
                getRequest(url).then(response => {
                    this.childCategory = response.data.data;
                })
            },
            //获取我的资源数据
            getResourceData(url) {
                if('id' in this.userInfo) {
                    url += this.userInfo.id;
                    getRequest(url).then(response => {
                        this.resourceData = response.data.data;
                    },response => {
                        this.$alert("找不到");
                    });
                }
            },
            //获取我的收藏
            getMyCollect(url) {
                if('id' in this.userInfo) {
                    url += this.userInfo.id;
                    getRequest(url).then(response => {
                        this.collectAmount = response.data.data.length;
                    },response => {
                        this.$alert("找不到");
                    });
                }
            },
            //获取我的下载
            getMyDownload(url) {
                if('id' in this.userInfo) {
                    url += this.userInfo.id;
                    getRequest(url).then(response => {
                        this.downloadAmount = response.data.data.length;
                    },response => {
                        this.$alert("找不到");
                    });
                }
            },
            //获取我的上传
            getMyUpload(url) {
                if('id' in this.userInfo) {
                    url += this.userInfo.id;
                    getRequest(url).then(response => {
                        this.uploadAmount = response.data.data.length;
                    },response => {
                        this.$alert("找不到");
                    });
                }
            },
            //获取用户信息
            getUserInfo(userInfo) {
                if(userInfo == null) {
                    this.$router.push({
                        name: 'login',
                    });
                    return;
                }
                this.userInfo = userInfo;
                this.tagList = this.userInfo.tags;
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
            //获取选中的标签
            getTagValue(tagValue) {
                this.$router.push({
                    name: "searchResource",
                    params: {
                        parentCategoryId: 0,
                        childCategoryId: 0,
                        resourceTypeId: 0,
                        keyword: tagValue
                    }
                });
            },
            //点击菜单触发事件
            menuItemSelect(itemName) {
                this.$router.push({
                    name: itemName
                });

                if(itemName == 'my-upload') {
                    this.getResourceData(this.getMyResourceUrl);
                }
                if(itemName == 'my-download') {
                    this.getResourceData(this.getDownloadUrl);
                }
                if(itemName == 'my-collection') {
                    this.getResourceData(this.getCollectionDataUrl);
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
            //点击评论事件
            goToRemark(resourceId) {
                this.$router.push({
                    name: 'download',
                    params: {
                        resourceId: resourceId
                    }
                })
            },
            //点击取消收藏事件
            cancelCollect(resourceId) {
                let params = {
                    userId: this.userInfo.id,
                    resourceId: resourceId,
                }
                postRequest(this.cancelCollectionUrl,params).then(response => {
                    console.log(response.data.data);
                    this.getResourceData(this.getCollectionDataUrl);
                })
            },

            //上传成功
            uploadSuccess() {
                //alert('s')
                this.getResourceData(this.getMyResourceUrl);
                this.getMyUpload(this.getMyResourceUrl);
            },
            uploadBtnClick() {
                setActiveName('upload');
                this.$router.push({
                    name: 'upload',
                })
            }
        },
            
            
    }
</script>
