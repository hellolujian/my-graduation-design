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
                :type="parentCategoryId == null ? 'error' : 'ghost'"
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
                :type="childCategoryId == null ? 'error' : 'ghost'"
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
                :type="typeId == null ? 'error' : 'ghost'"
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
            }
        },
        
        computed: {
            ...mapState({
                parentCategoryId: state => state.parentCategoryId,
                childCategoryId: state => state.childCategoryId,
                childCategoryData: state => state.childCategoryData,
                typeId: state => state.typeId,
            }),
        },
        mounted() {
            this.getCategoryData();
            this.getResourceTypeData();
        },
        methods: {
            //获取父分类数据
            getCategoryData() {
                getRequest(this.getCategoryUrl).then(response => {
                    this.categoryData = response.data.data;
                })
            },
            //获得哪个分类
            getChildCategoryData(index,id) {
                //this.parentCategoryId = id;
                //console.log(this.categoryData)
                this.changeParentCategoryId(id);
                this.changeChildCategoryData(this.categoryData[index].childCategoryList);
                this.childCategoryShow = true;
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
            ]),
            parentAllBtnClick() {
                this.childCategoryShow = false;
                this.changeParentCategoryId(null);
            },
            childAllClick() {
                this.changeChildCategoryId(null);
            },
            childCategoryClick(id) {
                this.changeChildCategoryId(id)
            },
            typeAllClick() {
                this.changeTypeId(null);
            },
            typeClick(id) {
                this.changeTypeId(id);
            }          
        }
}
</script>
