<!--
<style scoped>
    .tabs {
        width: 820px;
    }
    .tab-pane {
        border-bottom: 2px rgba(10, 10, 10, 0.219) dotted;
        
    }
    .tab-pane-left {
        display:inline-block;
        vertical-align:top;
        padding: 10px;
    }
    .suffix-icon {
        vertical-align:top;
        height:50px;
        width: 50px;
        
    }
    .tab-pane-right {
        width: 80%;
        display:inline-block;
        vertical-align:top;
        padding: 5px;
    }
    .tab-pane-right-title {
        display: inline-block;
        color: black;
    }
    span {
        padding-right: 20px;
        display: inline-block;
        margin: 10px 0px;
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
                    v-for="(item,index) in searchResult.slice(0,pageSize)" 
                    :key="index"
                    class="tab-pane">
                    <router-link 
                        :to="{name: 'resourceDetail', params: { resourceId: item.id }}" 
                        class="tab-pane-left">
                        <img class="suffix-icon" :src="item.image" />
                    </router-link>
                    <div class="tab-pane-right">
                        <router-link 
                            class="tab-pane-right-title" 
                            :to="{name: 'resourceDetail', params: { resourceId: item.id }}">
                            <h3>{{item.title}}</h3>
                        </router-link>
                        <p>{{item.description}}</p>
                        <p style="width:100%">
                            <span>下载个数：{{item.downloads}}</span>
                            <span>资源大小：{{item.size}}</span>
                            <span>上传时间：{{item.uploadTime}}</span>
                            <span>上传者：{{item.userName}}</span>
                        </p>
                    </div>
                </div>
            </TabPane>
        </Tabs>
        <Page 
            :total="searchResultTotal"
            :page-size="pageSize"
            @on-change="pageChange"></Page>
    </div>
</template>
<script>
    import {getRequest} from '../../utils/api';
    import store from "@/store/store.js";
    import {mapState,mapMutations} from 'vuex';
    export default {
        store,
        data() {
            return {
                pageNum: 1,
                pageSize: 3,
                total: 0,
                getResourceUrl: '/resources/2',
                resourceData: [],
                tabPane: [
                    { name:'first', label: '最新上传' },
                    { name:'second', label: '最多下载' },
                ],
                showLoading: false,
            }
        },
        computed: {
            ...mapState({
                parentCategoryId: state => state.parentCategoryId,
                childCategoryId: state => state.childCategoryId,
                childCategoryData: state => state.childCategoryData,
                typeId: state => state.typeId,
                checkStatus: state => state.checkStatus,
                sortType: state => state.sortType,
                keyword: state => state.keyword,
                searchResult: state => state.searchResult,
                searchResultTotal: state => state.searchResultTotal,
            }),
        },
        mounted() {
            console.log('parent',this.$route.params.parentCategoryId);
            console.log('child',this.$route.params.childCategoryId)
            console.log('type',this.$route.params.typeId)
            this.getResourceData();
        },
        methods: {
            ...mapMutations([
                'changeSearchResult',
                'changeSearchResultTotal',
            ]),
            getResourceData() {
                let params = {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    
                    parentCategoryId: this.parentCategoryId,
                    childCategoryId: this.childCategoryId,
                    resourceTypeId: this.typeId,
                    checkStatus: this.checkStatus,
                    sortType: this.sortType,
                    keyword: this.keyword,/**/
                };
                getRequest(this.getResourceUrl,params).then(response => {
                    console.log(response.data.data.data)
                    this.changeSearchResult(response.data.data.data);
                    this.resourceData = response.data.data.data;
                    this.changeSearchResultTotal(response.data.data.total);
                }, response => {
                    this.$alert('找不到')
                })
            },
            pageChange(val) {
                this.pageNum = val;
                this.getResourceData();
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
        border-bottom: 2px rgba(10, 10, 10, 0.219) dotted;
        
    }
    .tab-pane-left {
        display:inline-block;
        vertical-align:top;
        padding: 10px;
    }
    .suffix-icon {
        vertical-align:top;
        height:50px;
        width: 50px;
        
    }
    .tab-pane-right {
        width: 80%;
        display:inline-block;
        vertical-align:top;
        padding: 5px;
    }
    .tab-pane-right-title {
        display: inline-block;
        color: black;
    }
    span {
        padding-right: 20px;
        display: inline-block;
        margin: 10px 0px;
    }
</style>
<template>
    <div>
        <Tabs type="card" class="tabs" @on-click="tabsClick">
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
                            class="tab-pane-right-title" 
                            :to="{name: 'download', params: { resourceId: item.id }}">
                            <h3>{{item.title}}</h3>
                        </router-link>
                        <p>{{item.description}}</p>
                        <p style="width:100%">
                            <span>下载个数：{{item.downloads}}</span>
                            <span>资源大小：{{item.size}}</span>
                            <span>上传时间：{{item.uploadTime}}</span>
                            <span>上传者：{{item.userName}}</span>
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
                    { name:'first', label: '最新上传' },
                    { name:'second', label: '最多下载' },
                ],
                showLoading: false,
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