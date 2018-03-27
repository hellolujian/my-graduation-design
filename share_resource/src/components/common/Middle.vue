<template>
    <el-container class="middle">
        <div class="category">
            <el-button 
                v-for="(item,index) in categoryData"
                :key="index"
                v-bind:class="[(index == 0) ? 'category-btn-first' : '', 
                                (index == (categoryData.length-1)) ? 'category-btn-last' : '', 
                                'category-btn']"
                @mouseover.native="getChildCategoryData(index)"
                @click="parentCategoryClick(item.id)">
                {{item.parentCategoryName}}
                <i style="float:right;" class="el-icon-arrow-right el-icon--right"></i>
            </el-button>
        </div>
        <el-card class="box-card">
            <span slot="header">全部</span>
            <el-button
                v-for="(item,index) in childCategoryData" 
                :key="index"
                class="childCategory"
                @click="childCategoryClick(item.id)">{{item.childCategoryName}}
            </el-button>
        </el-card>
        <el-main class="main">
            <el-carousel height="440px">
                <el-carousel-item >
                    <img style="width:100%;height:100%;" src="static/image/banner/banner1.jpg">
                </el-carousel-item>
                <el-carousel-item>
                    <img style="width:100%;height:100%;" src="static/image/banner/banner2.jpg">
                </el-carousel-item>
                <el-carousel-item>
                    <img style="width:100%;height:100%;" src="static/image/banner/banner3.jpg">
                </el-carousel-item>
                <el-carousel-item>
                    <img style="width:100%;height:100%;" src="static/image/banner/banner4.jpg">
                </el-carousel-item>
                <el-carousel-item>
                    <img style="width:100%;height:100%;" src="static/image/banner/banner5.jpg">
                </el-carousel-item> <!---->
            </el-carousel>
        </el-main>
    
        <el-card style="width:300px;height:440px;">
            <span slot="header">热门标签</span>
            <router-link to="www.baidu.com">
                <el-tag
                    v-for="(item,index) in tagList" 
                    :key="index"
                    type="danger"
                    class="tag">
                    {{item.tagName}}
                </el-tag>
            </router-link>
        </el-card>
        
    </el-container>
</template>

<script>
    import {getRequest} from '../../utils/api';
    import store from "@/store/store.js";
    import {mapState,mapMutations} from 'vuex';
    export default {
        store,
        data() {
            return {
                getCategoryUrl: '/category/',
                categoryData: [],
                childCategoryData: [],
                parentCategoryId: 0, //保存子分类的父类别id
                getHotTagUrl: '/tags/',
                tagList: [],
            }
        },
        mounted() {
            this.getCategoryData();
            this.getHotTagData()
        },
        methods: {
            ...mapMutations([
                'changeParentCategoryId',
                'changeChildCategoryId',
                'changeChildCategoryData',
            ]),
            //获取父分类数据
            getCategoryData() {
                getRequest(this.getCategoryUrl).then(response => {
                    this.categoryData = response.data.data;
                })
                
            },
            //获得哪个分类
            getChildCategoryData(index) {
                this.parentCategoryId = this.categoryData[index].id;
                this.childCategoryData = this.categoryData[index].childCategoryList;
            },
            //获取标签数据
            getHotTagData() {
                getRequest(this.getHotTagUrl).then(response => {
                    this.tagList = response.data.data;
                })
            },
            parentCategoryClick(parentCategoryId) {
                this.$router.push({
                    name: 'searchResource',
                    params: {
                        parentCategoryId:parentCategoryId,
                        childCategoryId: 0,
                        typeId: 0,
                    }
                }),
                this.changeParentCategoryId(parentCategoryId);
                this.changeChildCategoryId(null);
                this.changeChildCategoryData(this.childCategoryData);
            },
            childCategoryClick(childCategoryId) {
                
                this.$router.push({
                    name: 'searchResource',
                    params: {
                        parentCategoryId:this.parentCategoryId,
                        childCategoryId: childCategoryId,
                        typeId: 0,
                        data: this.childCategoryData
                    }
                });
                this.changeChildCategoryId(childCategoryId);
                this.changeParentCategoryId(this.parentCategoryId);
                this.changeChildCategoryData(this.childCategoryData);
            }
        }
}
</script>

<style>
    .middle {
        width:90%;
        margin:0 auto;
        box-shadow:1px 1px 10px rgb(133, 127, 127);
    }
    .category {
        background: rgba(247, 170, 97, 0.13);
        width: 220px;
    }
    .category-btn {
        width:200px;
        text-align:left;
        border:none;
        background: none;
        float: right;
        height: 50px;
        border-radius: 5px 0px 0px 5px;
    }
    .category-btn-first {
        margin-top:20px;
    }
    .category-btn-last {
        margin-bottom: 20px; 
    }
    .main {
        padding: 0px;
    }

  .box-card {
    width: 480px;
    height: 440px;
    position: absolute;
    z-index: 100;
    left: 283px;
    display: none;
  }
  .category:hover+.box-card {
      display: block;
  }
  .box-card:hover {
      display: block;
  }
  .childCategory {
      border: none;
      float: left;
  }
  .tag {
      float: left;
      margin:5px;
  }
  
</style>
