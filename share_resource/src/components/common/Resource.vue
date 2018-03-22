<style>
    .tagbs {
        border:1px gray solid;
    }
    .tab-pane {
        margin-bottom: 20px;
        height:90px;
        width:500px;
        border-bottom: 2px rgba(155, 111, 111, 0.096) dotted;
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
</style>
<template>
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
                <router-link to="www.baidu.com" class="tab-pane-left">
                    <img class="suffix-icon" :src="item.image" />
                </router-link>
                <div class="tab-pane-right">
                    <router-link class="tab-pane-right-title" to="www.baidu.com">
                        <h3>{{item.title}}</h3>
                    </router-link>
                    <p>
                        <span style="padding-right:30px;">上传者：{{item.userName}}</span>
                        <span>上传时间：{{item.uploadTime}}</span>
                    </p>
                </div>
            </div>
        </TabPane>
    </Tabs>
</template>
<script>
    import {getRequest} from '../../utils/api';
    export default {
        data() {
            return {
                getResourceUrl: '/resources/',
                resourceData: [],
                tabPane: [
                    { name:'first', label: '精品资源' },
                    { name:'second', label: '最新上传' },
                    { name: 'third', label: '代码资源' }
                ],
            }
        },
        mounted() {
            this.getResourceData();
        },
        methods: {
            getResourceData() {
                getRequest(this.getResourceUrl).then(response => {
                    this.resourceData = response.data.data;
                }, response => {
                    this.$alert('找不到')
                })
            },
        }
    }
</script>