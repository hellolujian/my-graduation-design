<style scoped>
span {
  display: inline-block;
}
.middle {
  width: 1100px;
  margin: 0px auto;

}
.middle h3 {
  text-align: center;
}
.middle .middle-right {
  width: 270px;
  height: 100px;
  vertical-align: top;
  margin-left: 6px;
}
.bottom {
  height: 5px;
}
.fixed {
  position: fixed;
  top: 0px;
}
</style>

<template>
    <div>
        
        <BackTop></BackTop>
        <v-head @getKeyword="getKey" @getUserInfo="getUserInfo"></v-head>
        <div class="middle">
            <router-view 
                name="sideBar" 
                style="margin-bottom:10px;"
                :categoryData="categoryData">
            </router-view>
            <span>
                <router-view 
                    name="resource" 
                    :resourceData="resourceData"
                    @tabPanelChange="handleTabPanelChange">
                </router-view> 
            </span>
            <span
                v-bind:class="[middleRightFixed == true ? 'fixed' : '','middle-right']">
                <router-view 
                    style="margin-bottom:10px;"
                    name="tags" 
                    title="热门标签" 
                    :tagList="tagList" 
                    @getTag="getTagValue">
                </router-view>
                <router-view 
                    name="sideUserInfo" 
                    :data="userInfo"
                    pageName="index"
                    @upload="upload">
                </router-view>
            </span>
            <Spin v-if="showLoading" >
                <Icon 
                    type="load-c" 
                    size=18 
                    class="demo-spin-icon-load"></Icon>
                <div>加载中</div>
            </Spin>
            <h3 v-if="showBottom">已经到底啦!</h3>
        </div>
        <div class="bottom"></div>
        
        
        <!--
        <transition name="move" mode="out-in">
            <router-view name="middle"></router-view>
        </transition>
            <router-view name="resource"></router-view>
            <router-view style="margin-left:20px;" name="sideUserInfo"></router-view>
        -->
    </div>
</template>
<script>
import vHead from "@/components/common/Header.vue";
import vSideBar from "@/components/common/SideBar.vue";
import vResource from "@/components/common/Resource.vue";
import vTags from "@/components/common/Tags.vue";
import vSideUserInfo from "@/components/common/SideUserInfo.vue";

import {isUserLogin} from '@/utils/userUtil';
import { getRequest } from "../../utils/api";
import {
  getWindowHeight,
  getScrollHeight,
  getScrollTop
} from "../../utils/util";

//import vResource from './Resource.vue';
//

export default {
  data() {
    return {
      pageNum: 1,
      pageSize: 8,
      getCategoryUrl: "/category/",
      categoryData: [],
      getResourceUrl: "/resources/",
      resourceData: [],
      showLoading: false,
      showBottom: false,
      middleRightFixed: false,
      flag: true,
      middleRightHeight: 0,
      getHotTagUrl: "/tags/",
      tagList: [],
      userInfo: {
      }
    };
  },
  components: {
    vHead,
    vSideBar,
    vResource,
    vTags,
    vSideUserInfo
  },
  methods: {
    //获取父分类数据
    getCategoryData() {
      getRequest(this.getCategoryUrl).then(response => {
        this.categoryData = response.data.data;
      });
    },
    //获取资源数据
    getResourceData(tabPanelIndex) {
      var resourceTypeId = 0;
      if (tabPanelIndex == 2) 
        resourceTypeId = 3;
      let params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        resourceTypeId: resourceTypeId,
        sortType: tabPanelIndex
      };
      getRequest(this.getResourceUrl, params).then(
        response => {
          this.resourceData = response.data.data;
        },
        response => {
          this.$alert("找不到");
        }
      );
    },
    //获取用户信息
    getUserInfo(userInfo) {
      if(userInfo == null) 
        userInfo = {};
      this.userInfo = userInfo;
      console.log('userInfo:'+this.userInfo);
    },
    //获取标签数据
    getHotTagData() {
      getRequest(this.getHotTagUrl).then(response => {
        this.tagList = response.data.data;
        console.log(this.tagList)
      });
    },
    //滚动固定
    scrollFixed() {
      var scrollTop =
        window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop;
      var offsetTop;
      if (this.flag) {
        this.middleRightHeight = document.querySelector(
          ".middle-right"
        ).offsetTop;

        this.flag = false;
      }
      offsetTop = this.middleRightHeight;
      if (scrollTop >= offsetTop) {
        console.log("scrollTop:", scrollTop);
        console.log("offsetTop:", offsetTop);
        this.middleRightFixed = true;
      } else {
        this.middleRightFixed = false;
      }
    },
    //滚动加载
    menu() {
      this.scrollFixed();
      //console.log('scrolltop:',getScrollTop().toFixed(0));
      //console.log('windowheight:',getWindowHeight());
      //console.log('scrollheight:',getScrollHeight())
      if (
        parseInt(getScrollTop().toFixed(0)) + parseInt(getWindowHeight()) ==
        parseInt(getScrollHeight())
      ) {
        let params = {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };
        var nextPageData;
        getRequest(this.getResourceUrl, params).then(
          response => {
            nextPageData = response.data.data;
            if (nextPageData.length) {
              this.showLoading = true;
              setTimeout(() => {
                this.resourceData.push(...nextPageData);
                this.showLoading = false;
              }, 1000);
            } else {
              this.showLoading = false;
              this.showBottom = true;
            }
          },
          response => {
            this.$alert("找不到");
          }
        );
        this.pageNum++;
      }
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
    //处理tab选项卡改变
    handleTabPanelChange(tabPanelIndex) {
      
      this.pageNum = 1;
      this.showBottom = false;
      this.getResourceData(tabPanelIndex);
    },
    upload() {
      this.$router.push({
        name: 'userCenter'
      })
    }
  },
  mounted() {
    this.getCategoryData();
    this.getResourceData();
    this.getHotTagData();
    window.addEventListener("scroll", this.menu);
  }
};
</script>


