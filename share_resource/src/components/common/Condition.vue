<!--
<style scoped>
    Button {
        border:none;
        font-size: 14px;
        margin-right: 8px;
    }
    .card {
    }
    .category {
        width: 100%;
        background: white;
        padding: 15px 10px;
    }
    .start {
        color:black;
        font-weight: bolder;
        padding: 9px 10px 9px 20px;
        font-size: 14px;
    }
</style>


<template>
    <Card class="card">
        
        <div class="category">
            <span class="start">方向：</span>

            <Button 
                :type="parentCategoryId == 0 ? 'error' : 'ghost'"
                @click="parentAllBtnClick">全部</Button>
            <Button
                v-for="(item,index) in categoryData"
                :key="index"
                :type="parentCategoryId == item.id ? 'error' : 'ghost'"
                @click="getChildCategoryData(index,item.id)">{{item.parentCategoryName}}
            </Button>
        </div>
        <div class="category" v-if="childCategoryShow"> 
            <span class="start">分类：</span>
            <Button 
                :type="childCategoryId == 0 ? 'error' : 'ghost'"
                @click="childAllClick">全部</Button>
            <Button
                v-for="(item,index) in childCategoryData"
                :key="index"
                :type="childCategoryId == item.id ? 'error' : 'ghost'"
                @click="childCategoryClick(item.id)">{{item.childCategoryName}}
            </Button>
        </div>
        <div class="category"> 
            <span class="start">类型：</span>
            <Button 
                :type="typeId == 0 ? 'error' : 'ghost'"
                @click="typeAllClick">全部</Button>
            <Button
                v-for="(item,index) in resourceTypeData"
                :key="index"
                :type="typeId == item.id ? 'error' : 'ghost'"
                @click="typeClick(item.id)">{{item.typeName}}
            </Button>
        </div>
    </Card>
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
                getResourceTypeUrl: '/resourceTypes/',
                resourceTypeData: [],
                childCategoryShow: true,
                getResourceUrl: '/resources/',
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
            this.changeParentCategoryId(this.$route.params.parentCategoryId);
            this.changeChildCategoryId(this.$route.params.childCategoryId);
            this.changeTypeId(this.$route.params.typeId);
            if(this.parentCategoryId == 0)
                this.childCategoryShow = false;
            this.getCategoryData();
            this.getResourceTypeData();
            //this.getResourceData();
        },
        methods: {
            getResourceData() {
                let params = {
                    parentCategoryId: this.parentCategoryId,
                    childCategoryId: this.childCategoryId,
                    resourceTypeId: this.typeId,
                    checkStatus: this.checkStatus,
                    sortType: this.sortType,
                    keyword: this.keyword,/**/
                };
                getRequest(this.getResourceUrl,params).then(response => {
                    this.changeSearchResult(response.data.data);
                    this.changeSearchResultTotal(response.data.data.length);
                    //console.log(response.data.data)
                }, response => {
                    this.$alert('找不到')
                })
            },
            //获取父分类数据
            getCategoryData() {
                getRequest(this.getCategoryUrl).then(response => {
                    this.categoryData = response.data.data;
                    var _this = this;
                    this.categoryData.forEach(function(item){
                        if(item.id == _this.parentCategoryId) {
                            _this.changeChildCategoryData(item.childCategoryList);
                        }
                    })
                })
            },
            getChildCategoryData2(parentCategoryId) {
                let getChildCategoryUrl = `/category/parent/${parentCategoryId}/childCategory`;
                alert(getChildCategoryUrl)
                getRequest(getChildCategoryUrl).then(response => {
                    this.changeChildCategoryData(response.data.data);
                })
            },
            //获得哪个分类
            getChildCategoryData(index,id) {
                //this.parentCategoryId = id;
                //console.log(this.categoryData)
                this.changeParentCategoryId(id);
                this.changeChildCategoryId(0);
                this.changeChildCategoryData(this.categoryData[index].childCategoryList);
                this.childCategoryShow = true;
                this.$router.push({
                    name: 'searchResource',
                    params: {
                        parentCategoryId:this.parentCategoryId,
                        childCategoryId: 0,
                        typeId: this.typeId,
                        data: this.childCategoryData
                    }
                });
                this.getResourceData();
            },
            //获取类型数据
            getResourceTypeData() {
                getRequest(this.getResourceTypeUrl).then(response => {
                    this.resourceTypeData = response.data.data;
                })
                
            },
            ...mapMutations([
                'changeParentCategoryId',
                'changeChildCategoryId',
                'changeChildCategoryData',
                'changeTypeId',
                'changeSearchResult',
                'changeSearchResultTotal',
            ]),
            parentAllBtnClick() {
                this.childCategoryShow = false;
                this.changeParentCategoryId(0);
                this.$router.push({
                    name: 'searchResource',
                    params: {
                        parentCategoryId: 0,
                        childCategoryId: this.childCategoryId,
                        typeId: this.typeId,
                        data: this.childCategoryData
                    }
                });
                this.getResourceData();
            },
            childAllClick() {
                this.changeChildCategoryId(0);
                this.$router.push({
                    name: 'searchResource',
                    params: {
                        parentCategoryId: this.parentCategoryId,
                        childCategoryId: 0,
                        typeId: this.typeId,
                        data: this.childCategoryData
                    }
                });
                this.getResourceData();
            },
            childCategoryClick(id) {
                this.changeChildCategoryId(id);
                this.$router.push({
                    name: 'searchResource',
                    params: {
                        parentCategoryId: this.parentCategoryId,
                        childCategoryId: this.childCategoryId,
                        typeId: this.typeId,
                        data: this.childCategoryData
                    }
                });
                this.getResourceData();
            },
            typeAllClick() {
                this.changeTypeId(0);
                this.$router.push({
                    name: 'searchResource',
                    params: {
                        parentCategoryId: this.parentCategoryId,
                        childCategoryId: this.childCategoryId,
                        typeId: this.typeId,
                        data: this.childCategoryData
                    }
                });
                this.getResourceData();
            },
            typeClick(id) {
                this.changeTypeId(id);
                this.$router.push({
                    name: 'searchResource',
                    params: {
                        parentCategoryId: this.parentCategoryId,
                        childCategoryId: this.childCategoryId,
                        typeId: this.typeId,
                        data: this.childCategoryData
                    }
                });
                this.getResourceData();
            }          
        }
}
</script>
-->

<style scoped>
Button {
  border: none;
  font-size: 14px;
  margin-right: 8px;
}
.card {
}
.category {
  width: 100%;
  background: white;
  padding: 15px 10px;
}
.start {
  color: black;
  font-weight: bolder;
  padding: 9px 10px 9px 20px;
  font-size: 14px;
}
</style>


<template>
    <Card class="card">
        <div class="category">
            <span class="start">方向：</span>
            <Button 
                :type="conditionQuery.parentCategoryId == 0 ? 'error' : 'ghost'"
                @click="parentAllBtnClick">全部</Button>
            <Button
                v-for="(item,index) in categoryData"
                :key="index"
                :type="conditionQuery.parentCategoryId == item.id ? 'error' : 'ghost'"
                @click="parentCategoryClick(index,item.id)">{{item.parentCategoryName}}
            </Button>
        </div>
        <div class="category" v-if="childCategoryShow"> 
            <span class="start">分类：</span>
            <Button 
                :type="conditionQuery.childCategoryId == 0 ? 'error' : 'ghost'"
                @click="childAllClick">全部</Button>
            <Button
                v-for="(item,index) in childCategoryData"
                :key="index"
                :type="conditionQuery.childCategoryId == item.id ? 'error' : 'ghost'"
                @click="childCategoryClick(item.id)">{{item.childCategoryName}}
            </Button>
        </div>
        <div class="category"> 
            <span class="start">类型：</span>
            <Button 
                :type="conditionQuery.resourceTypeId == 0 ? 'error' : 'ghost'"
                @click="typeAllClick">全部</Button>
            <Button
                v-for="(item,index) in resourceTypeData"
                :key="index"
                :type="conditionQuery.resourceTypeId == item.id ? 'error' : 'ghost'"
                @click="typeClick(item.id)">{{item.typeName}}
            </Button>
        </div>
    </Card>
</template>

<script>
import { getRequest } from "../../utils/api";
export default {
  data() {
    return {
      getCategoryUrl: "/category/",
      categoryData: [],
      childCategoryData: [],

      resourceTypeData: [],
      childCategoryShow: true,

      getResourceTypeUrl: "/resourceTypes/"
    };
  },
  props: {
    conditionQuery: {
      type: Object
    }
  },
  mounted() {
    this.getCategoryData();
    this.getResourceTypeData();
    if (this.conditionQuery.parentCategoryId == 0) {
      this.childCategoryShow = false;
    }
  },
  methods: {
    //获取父分类与子分类数据
    getCategoryData() {
      getRequest(this.getCategoryUrl).then(response => {
        this.categoryData = response.data.data;
        var _this = this;
        this.categoryData.forEach(function(item) {
          if (item.id == _this.conditionQuery.parentCategoryId) {
            _this.childCategoryData = item.childCategoryList;
          }
        });
      });
    },
    //获取类型数据
    getResourceTypeData() {
      getRequest(this.getResourceTypeUrl).then(response => {
        this.resourceTypeData = response.data.data;
      });
    },
    getResourceData() {
      let params = {
        parentCategoryId: this.parentCategoryId,
        childCategoryId: this.childCategoryId,
        resourceTypeId: this.typeId,
        checkStatus: this.checkStatus,
        sortType: this.sortType,
        keyword: this.keyword /**/
      };
      getRequest(this.getResourceUrl, params).then(
        response => {
          this.changeSearchResult(response.data.data);
          this.changeSearchResultTotal(response.data.data.length);
          //console.log(response.data.data)
        },
        response => {
          this.$alert("找不到");
        }
      );
    },

    parentCategoryClick(index, parentCategoryId) {
      this.childCategoryData = this.categoryData[index].childCategoryList;
      this.childCategoryShow = true;
      this.conditionQuery.parentCategoryId = parentCategoryId;
      this.conditionQuery.childCategoryId = 0;
      this.$emit("changeCondition", this.conditionQuery);
    },
    parentAllBtnClick() {
      this.childCategoryShow = false;
      this.conditionQuery.childCategoryId = 0;
      this.conditionQuery.parentCategoryId = 0;
      this.$emit("changeCondition", this.conditionQuery);
    },
    childAllClick() {
      this.conditionQuery.childCategoryId = 0;
      this.$emit("changeCondition", this.conditionQuery);
    },
    childCategoryClick(childCategoryId) {
      this.conditionQuery.childCategoryId = childCategoryId;
      this.$emit("changeCondition", this.conditionQuery);
    },
    typeAllClick() {
      this.conditionQuery.resourceTypeId = 0;
      this.$emit("changeCondition", this.conditionQuery);
    },
    typeClick(resourceTypeId) {
      this.conditionQuery.resourceTypeId = resourceTypeId;
      this.$emit("changeCondition", this.conditionQuery);
    }
  }
};
</script>
