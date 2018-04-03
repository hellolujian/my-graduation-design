<!--
<style>
    .tabs {
        width: 820px;
    }
    .tab-pane {
        margin-bottom: 20px;
        height:90px;
        width:800px;
        border-bottom: 2px rgba(10, 10, 10, 0.219) dotted;
        
    }
    .tab-pane-left {
        height:80px;
        width: 80px;
        display:inline-block;
        vertical-align:middle
    }
    .suffix-icon {
        vertical-align:middle;
        margin:0 20px; 
        height:60px;
    }
    .tab-pane-right {
        width: 80%;
        height:100px;
        display:inline-block;
        vertical-align:middle;
        margin-left: 10px;
    }
    .tab-pane-right-title {
        display: inline-block;
        color: black;
        margin: 10px 0; 
    }
    .demo-spin-icon-load{
        animation: ani-demo-spin 1s linear infinite;
    }
    @keyframes ani-demo-spin {
        from { transform: rotate(0deg);}
        50%  { transform: rotate(180deg);}
        to   { transform: rotate(360deg);}
    }
    .demo-spin-col{
        border: 1px solid #eee;
    }
    .score {
        font-size:30px;
        color:pink;
        font-weight: bolder;
    }
</style>
<template>
    <div>
        <Tabs type="card" class="tabs">
            <TabPane 
                v-for="(item,index) in tabPane"
                :key="index"
                :name="item.name"
                :label="item.label">
                <div
                    v-for="(item,index) in resourceData" 
                    :key="index"
                    class="tab-pane">
                    <router-link 
                        :to="{name: 'resourceDetail', params: { resourceId: item.id }}" 
                        class="tab-pane-left">
                        <img class="suffix-icon" :src="item.image" />
                    </router-link>
                    <div class="tab-pane-right">
                        <router-link
                            :to="{name: 'resourceDetail', params: { resourceId: item.id }}"
                            class="tab-pane-right-title">
                            <h3>{{item.title}}</h3>
                        </router-link>
                        <p style="width:100%">
                            <span style="padding-right:30px;">上传者：{{item.userName}}</span>
                            <span>上传时间：{{item.uploadTime}}</span>
                            <span style="float:right;">积分：
                                <span class="score">{{item.points}}</span>
                            </span>
                        </p>
                    </div>
                </div>
            </TabPane>
        </Tabs>
        <Spin v-if="showLoading" >
            <Icon 
                type="load-c" 
                size=18 
                class="demo-spin-icon-load"></Icon>
            <div>加载中</div>
        </Spin>
    </div>
</template>
<script>
    import {getRequest} from '../../utils/api';
    import {getWindowHeight,getScrollHeight,getScrollTop} from '../../utils/util';
    export default {
        data() {
            return {
                pageNum: 1,
                pageSize: 8,
                getResourceUrl: '/resources/',
                resourceData: [],
                tabPane: [
                    { name:'first', label: '精品资源' },
                    { name:'second', label: '最新上传' },
                    { name: 'third', label: '代码资源' }
                ],
                showLoading: false,
            }
        },
        mounted() {
            this.getResourceData();
            window.addEventListener('scroll', this.menu)
        },
        methods: {
            getResourceData() {
                let params = {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                };
                getRequest(this.getResourceUrl,params).then(response => {
                    this.resourceData = response.data.data;
                }, response => {
                    this.$alert('找不到')
                })
            },
            //滚动加载
            menu() {
                if(parseInt(getScrollTop().toFixed(0)) + parseInt(getWindowHeight()) == parseInt(getScrollHeight())){
                    let params = {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                    };
                    var nextPageData;
                    getRequest(this.getResourceUrl,params).then(response => {
                        nextPageData = response.data.data;
                        if(nextPageData.length) {
                            this.showLoading = true;
                            setTimeout(() => {
                                this.resourceData.push(...nextPageData);
                                this.showLoading = false;
                            }, 1000);
                        } else {
                            this.$Notice.open({
                                title: '已经到底啦~~~'
                            });
                        }
                    }, response => {
                        this.$alert('找不到')
                    });
                    this.pageNum++ ;
                    
                }
            }
        }
    }
</script>
-->

<style scoped>
    .tabs {
        width: 820px;
        
    }
    .tab-pane {
        margin-bottom: 20px;
        height:90px;
        width:800px;
        border-bottom: 2px rgba(10, 10, 10, 0.219) dotted;
        
    }
    .tab-pane-left {
        height:80px;
        width: 80px;
        display:inline-block;
        vertical-align:middle
    }
    .suffix-icon {
        vertical-align:middle;
        margin:0 20px; 
        height:60px;
    }
    .tab-pane-right {
        width: 80%;
        height:100px;
        display:inline-block;
        vertical-align:middle;
        margin-left: 10px;
    }
    .tab-pane-right-title {
        display: inline-block;
        color: black;
        margin: 10px 0; 
    }
    .demo-spin-icon-load{
        animation: ani-demo-spin 1s linear infinite;
    }
    @keyframes ani-demo-spin {
        from { transform: rotate(0deg);}
        50%  { transform: rotate(180deg);}
        to   { transform: rotate(360deg);}
    }
    .demo-spin-col{
        border: 1px solid #eee;
    }
    .score {
        font-size:30px;
        color:pink;
        font-weight: bolder;
    }
</style>
<template>
    <div>
        <Tabs 
            type="card" 
            class="tabs"
            @on-click="tabsClick">
            <TabPane 
                v-for="(item,index) in tabPane"
                :key="index"
                :label="item.label">
                <div
                    v-for="(item,index) in resourceData" 
                    :key="index"
                    class="tab-pane">
                    <router-link 
                        :to="{name: 'download', params: { resourceId: item.id }}" 
                        class="tab-pane-left">
                        <img class="suffix-icon" :src="item.image" />
                    </router-link>
                    <div class="tab-pane-right">
                        <router-link
                            :to="{name: 'download', params: { resourceId: item.id }}"
                            class="tab-pane-right-title">
                            <h3>{{item.title}}</h3>
                        </router-link>
                        <p style="width:100%">
                            <span style="padding-right:30px;">上传者：{{item.userName}}</span>
                            <span>上传时间：{{item.uploadTime}}</span>
                            <span style="float:right;">积分：
                                <span class="score">{{item.points}}</span>
                            </span>
                        </p>
                    </div>
                </div>
            </TabPane>
        </Tabs>
        
    </div>
</template>
<script>
    
    export default {
        data() {
            return {
                tabPane: [
                    { name: 0, label: '精品资源' },
                    { name: 1, label: '最新上传' },
                    { name: 2, label: '代码资源' }
                ],
                
            }
        },
        props: {
            resourceData: {
                type: Array,
            }
        },
        mounted() {
            
        },
        methods: {
            tabsClick(index) {
                this.$emit('tabPanelChange',index)
            }
        }
    }
</script>