<style scoped>
    Button {
        font-size: 16px;
        outline: none;
    }
    .category-out {
        display: inline-block;
        width: 1100px;
        position: relative;
    }
    .category {
        width: 200px;
        background: gray;
        padding-left: 10px;
        float: left;
    }
        .category .category-btn {
            border: none;
            width:100%;
            text-align:left;
            height: 50px;
            background: none;
            border-radius: 5px 0px 0px 5px;
            color: white;
        }
        .category .category-btn:hover {
            background: rgb(219, 212, 212);
        }
        .category .category-btn-first {
            margin-top:20px;
        }
        .category .category-btn-last {
            margin-bottom: 20px; 
        }
        .category .btn-icon {
            float: right;
        }

    .box-card {
        width: 480px;
        height: 440px;
        top: 0px;
        display: none;
        position: absolute;
        left: 200px;
        z-index: 100;
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
        margin: 10px;
        outline: none;
    }

    .carousel {
        width: 900px;
        float: left;
    }
    .carousel img {
        height: 100%;
        width: 100%;
    }
    
</style>
<template>
    <div class="category-out">
        <div class="category">
            <Button 
                v-for="(item,index) in categoryData"
                :key="index"
                v-bind:class="[(index == 0) ? 'category-btn-first' : '', 
                                (index == (categoryData.length-1)) ? 'category-btn-last' : '', 
                                'category-btn']"
                @mouseover.native="getChildCategoryData(index,item.id)"
                @click="parentCategoryClick(item.id)">
                {{item.parentCategoryName}}
                <Icon class="btn-icon" type="chevron-right"></Icon>
            </Button>
        </div>
        <Card class="box-card">
            <span slot="title">全部</span>
            <Button
                v-for="(item,index) in childCategoryData" 
                :key="index"
                class="childCategory"
                @click="childCategoryClick(item.id)">{{item.childCategoryName}}
            </Button>
        </Card>  
        <Carousel 
            class="carousel"
            loop
            :height="440"
            :autoplay="true"
            trigger="hover"
            arrow="hover"
            :radius="true">
            <CarouselItem>
                <img  src="static/image/banner/banner1.jpg">
            </CarouselItem>
            <CarouselItem>
                <img  src="static/image/banner/banner2.jpg">
            </CarouselItem>
            <CarouselItem>
                <img src="static/image/banner/banner3.jpg">
            </CarouselItem>
            <CarouselItem>
                <img  src="static/image/banner/banner4.jpg">
            </CarouselItem>
            <CarouselItem>
                <img  src="static/image/banner/banner5.jpg">
            </CarouselItem>
        </Carousel> 
        <div style="clear:both;"></div>
    </div>   
</template>

<script>
    export default {
        data() {
            return {
                categoryIndex: 0,
                parentCategoryId: 0,
                childCategoryData: [],
            }
        },
        
        mounted() {
        },
        methods: {
            //获取子分类索引
            getChildCategoryData(index,parentCategoryId) {
                this.categoryIndex = index;
                this.childCategoryData = this.categoryData[index].childCategoryList;
                this.parentCategoryId = parentCategoryId;
            },
            
            parentCategoryClick(parentCategoryId) {
                this.$router.push({
                    name: 'searchResource',
                    params: {
                        parentCategoryId:parentCategoryId,
                        childCategoryId: 0,
                        resourceTypeId: 0,
                    }
                });
            },

            childCategoryClick(childCategoryId) {
                
                this.$router.push({
                    name: 'searchResource',
                    params: {
                        parentCategoryId:this.parentCategoryId,
                        childCategoryId: childCategoryId,
                        resourceTypeId: 0,
                    }
                });
            }
        },
        props: {
            categoryData: {
                type: Array,
                default: function() {
                    return [
                        {
                            id: 10,
                            parentCategoryName: "前端开发",
                            childCategoryList: [
                                {
                                id: 100,
                                childCategoryName: "HTML/CSS",
                                createTime: "2018-03-05 19:17:08",
                                updateTime: "2018-03-05 19:17:08"
                                }
                            ],
                            createTime: "2018-03-05 19:17:07",
                            updateTime: "2018-03-05 19:17:07"
                        }]
                }
            }
        },
}
</script>


