<!--
<template>
    <div>
        <v-head></v-head>
        <v-condition></v-condition>
        <v-resource2 ref="r"></v-resource2>
    </div>
</template>

<script>
    import vHead from '../common/Header';
    import vCondition from '../common/Condition';
    import vResource2 from '../common/Resource2';

    export default {
        data() {
            return {
                parentCategoryId: null,
                childCategoryId: null,
                childCategoryData: [],
            }
        },
        components: {
            vHead,vCondition,vResource2,
        },
        mounted() {
            this.parentCategoryId = this.$route.params.parentCategoryId;
            this.childCategoryId = this.$route.params.childCategoryId;
            this.childCategoryData = this.$route.params.data;
        },
        methods: {
        }
    }
</script>
-->

<style scoped>
.page {
  width: 820px;
  text-align: center;
  padding: 10px;
}
</style>

<template>
    <div>
        <v-head @getKeyword="getKey"></v-head>
        <router-view 
            name="condition"
            style="margin-top:10px;"
            @changeCondition="updateResourceData" 
            :conditionQuery="conditionQuery">
        </router-view>
        <div style="width:90%;margin:10px auto;">
            <router-view
                name="resource2" 
                :resourceData="resourceData" 
                @tabPanelChange="changeSortType">
            </router-view>
            <p class="page">
                <Page 
                    style="display:block"
                    :total="total"
                    :current.sync="currentPageNum"
                    :page-size="pageSize"
                    @on-change="pageChange">
                </Page>
            </p>
            
        </div>
    </div>
</template>

<script>
import vHead from "@/components/common/Header";
import vCondition from "@/components/common/Condition";
import vResource2 from "@/components/common/Resource2";

import { getRequest } from "../../utils/api";

export default {
    data() {
        return {
          conditionQuery: {
            parentCategoryId: this.$route.params.parentCategoryId,
            childCategoryId: this.$route.params.childCategoryId,
            resourceTypeId: this.$route.params.resourceTypeId,
            checkStatus: 1,
            sortType: 1,
            keyword: ""
          },

          pageNum: 1,
          pageSize: 3,
          total: 0,
          currentPageNum: 1,

          getResourceUrl: "/resources/2",
          resourceData: [],
          
        };
      },
      components: {
        vHead,
        vCondition,
        vResource2
      },
      mounted() {
        this.getResourceData();
      },
      methods: {
        //更新查询的资源数据
        updateResourceData(data) {
          this.conditionQuery.parentCategoryId = data.parentCategoryId;
          this.conditionQuery.childCategoryId = data.childCategoryId;
          this.conditionQuery.resourceTypeId = data.resourceTypeId;

          this.$router.push({
            name: "searchResource",
            params: {
              parentCategoryId: this.conditionQuery.parentCategoryId,
              childCategoryId: this.conditionQuery.childCategoryId,
              resourceTypeId: this.conditionQuery.resourceTypeId
            }
          });

          this.getResourceData();
        },
        changeSortType(index) {
          index = index + 1;
          this.conditionQuery.sortType = index;
          this.pageNum = 1;
          this.currentPageNum = 1;
          this.getResourceData();
        },
        //获取资源数据
        getResourceData() {
          let params = {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            parentCategoryId: this.conditionQuery.parentCategoryId,
            childCategoryId: this.conditionQuery.childCategoryId,
            resourceTypeId: this.conditionQuery.resourceTypeId,
            checkStatus: this.conditionQuery.checkStatus,
            sortType: this.conditionQuery.sortType,
            keyword: this.conditionQuery.keyword
          };
          getRequest(this.getResourceUrl, params).then(
            response => {
              console.log(response.data.data.data);
              this.resourceData = response.data.data.data;
              this.total = response.data.data.total;
            },
            response => {
              this.$alert("找不到");
            }
          );
        },
        //获取关键字
        getKey(keyword) {
          this.conditionQuery.keyword = keyword;
          this.getResourceData();
        },
        //处理分页事件
        pageChange(val) {
          this.pageNum = val;
          this.getResourceData();
        },
        
    }
};
</script>



