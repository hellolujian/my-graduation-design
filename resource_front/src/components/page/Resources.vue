<template>
    <div>
        <el-tabs 
            v-model="activeName"
            type="border-card" 
            @tab-click="tabClick">
            
            <el-tab-pane 
                v-for="item in tabPane"
                :name="item.name"
                :key="item.label">
                <Badge slot="label" :count="item.count">
                    <span class="badgeSpan">{{item.label}}</span></Badge>
                <el-table
            tooltip-effect="light"
            :data="resourceData.slice((page-1)*size,page*size)"
            v-loading="listLoading"
            border
            style="width: 100%">
            <el-table-column type="expand">
                <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="id">
                            <span>{{ props.row.id }}</span>
                        </el-form-item>
                        <el-form-item label="资源名称">
                            <span>{{ props.row.name }}</span>
                        </el-form-item>
                        <el-form-item label="上传者">
                            <span>{{ props.row.userName }}</span>
                        </el-form-item>
                        <el-form-item label="资源分">
                            <span>{{ props.row.points }}</span>
                        </el-form-item>
                        <el-form-item label="标签">
                            <el-tag 
                                style="margin-right: 10px"
                                v-for="item in props.row.tagList"
                                :key="item"
                                type="success">{{ item }}
                            </el-tag>
                        </el-form-item>
                        <el-form-item label="上传时间">
                            <span>{{ props.row.uploadTime }}</span>
                        </el-form-item>
                        <el-form-item label="修改时间">
                            <span>{{ props.row.updateTime }}</span>
                        </el-form-item>
                        <el-form-item label="摘要">
                            <span>{{ props.row.description }}</span>
                        </el-form-item>
                        <el-form-item label="资源分类">
                            <span>{{ props.row.category }}</span>
                        </el-form-item>
                        <el-form-item label="资源大小">
                            <span>{{ props.row.size }}</span>
                        </el-form-item>
                        <el-form-item label="资源位置">
                            <span>{{ props.row.position }}</span>
                        </el-form-item>
                        <el-form-item label="审核状态">
                            <span>{{ props.row.checkStatus === 1 ? '审核通过' : '未审核'}}</span>
                        </el-form-item>
                        <el-form-item label="上传者">
                            <span>{{ props.row.userName }}</span>
                        </el-form-item>
                        <el-form-item label="资源类型">
                            <span>{{ props.row.type }}</span>
                        </el-form-item>
                        
                    </el-form>
                </template>
            </el-table-column>
            <el-table-column
                width="70"
                label="id"
                prop="id">
            </el-table-column>
            <el-table-column
                label="资源名称"
                prop="name">
            </el-table-column>
            <el-table-column
                label="上传者"
                prop="userName">
            </el-table-column>
            <el-table-column
                sortable
                :show-overflow-tooltip="true"
                label="上传时间"
                prop="uploadTime">
            </el-table-column>
            <el-table-column
                prop="checkStatus"
                label="审核状态">
                <template slot-scope="scope">
                    <el-tag
                        :type="scope.row.checkStatus === 1 ? 'success' : 'danger'"
                        close-transition>{{scope.row.checkStatus === 1 ? '审核通过' : '未审核'}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                label="操作">
                <template slot-scope="scope">
                    <el-button
                        v-if="!scope.row.checkStatus"
                        size="small"
                        type="success"
                        @click="agree(scope.$index, scope.row)">通过
                    </el-button>
                    <el-button
                        v-if="!scope.row.checkStatus"
                        size="small"
                        type="danger"
                        @click="disagree(scope.$index, scope.row)">删除
                    </el-button>
                    <el-button
                        v-if="scope.row.checkStatus"
                        :disabled="true"
                        size="small"
                        type="primary">已审核
                    </el-button>
                    
<!--
                    <el-switch
                        on-text="通过"
                        off-text="已审核"
                        :width=75
                        :disabled="scope.row.checkStatus === 1 ? true : false"
                        v-model="scope.row.checkStatus === 0 ? true : false"
                        on-color="#13ce66"
                        off-color="#ff4949"
                        @change="switchChange(scope.$index, scope.row)">
                    </el-switch>
-->
                </template>
                
            </el-table-column>
        </el-table>
            </el-tab-pane>
        </el-tabs>
        <div class="page">
            <el-pagination
                @current-change ="handleCurrentChange"
                layout="prev, pager, next"
                :total="total"
                :page-size="size">
            </el-pagination>
        </div>
    </div>
    
</template>

<script>
    import {getRequest,postRequest,isSuccess} from '../../utils/api';

    export default {
        data() {
            return {
                tabPane: [
                    { name:'first', label: '全部', count: 0 },
                    { name:'second', label: '未审核', count : 0 },
                    { name: 'third', label: '已审核', count: 0 }
                ],
                activeName: 'first',
                getResourceUrl: '/resources/',
                listLoading: false,
                tableData: [],//所有的数据
                resourceData: [],//经过滤的数据
                filters: [
                    { text: '未审核', value: 0 },
                    { text: '审核通过', value: 1 }
                ],
                page: 1,
                total: 0,
                size: 8,
            }
        },
        mounted() {
            this.getResourceData();
            
        },
        methods: {
            getResourceData() {
                this.listLoading = true;
                getRequest(this.getResourceUrl, { sortType: 1}).then(response => {
                    this.tableData = response.data.data;
                    this.total = this.tableData.length;
                    this.resourceData = this.tableData;
                    this.listLoading = false;
                    //设置未审核数量
                    this.tabPane[1].count = this.getNotCheckCount(this.tableData);
                }, response => {
                    this.$alert('找不到')
                })
            },
            //获取未审核的数量(后续改进)
            getNotCheckCount(arr) {
                var notCheckCount = 0;
                for(var index in arr) {
                    if(arr[index].checkStatus == 0) {
                        notCheckCount++;
                    }
                }

                return notCheckCount;
            },

            //当前页改变
            handleCurrentChange(val){
                this.page = val;
            },
            agree(index,row) {
                let url = `/resources/update/${row.id}`;
                let params = {
                    checkStatus: row.checkStatus == 0 ? 1 : 0,
                }
                postRequest(url,params).then(response => {
                    
                    if (isSuccess(response.data.code)) {
                        if (row.checkStatus == 1) {
                            row.checkStatus = 0;
                        } else {
                            row.checkStatus = 1
                        }
                        //未审核数量减一
                        this.tabPane[1].count--;
                        this.$message.success('审核成功')
                    } else {
                        this.$message.error('审核失败');
                    }  
                },response => {
                    //console.log(response)
                    this.$message.error('找不到服务器')
                })
            },
            disagree(index,row) {
                let url = `/resources/remove/${row.id}`;
                this.$confirm('是否确认删除？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    postRequest(url).then(response => {
                        console.log(response)
                        if(isSuccess(response.data.code)) {
                            this.$message({
                                type: 'success',
                                message: '删除成功',
                                showClose: true
                            });
                            this.getResourceData();
                            this.tabPane[1].count--;
                        } else {
                            this.$message({
                                type: 'error',
                                message: '删除失败',
                                showClose: true
                            })
                        }
                    }, response => {
                        this.$alert.error('找不到服务器');
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
                
            },
            /*
            switchChange(index,row) {
                let url = `/resources/update/${row.id}`;
                let params = {
                    checkStatus: row.checkStatus == 0 ? 1 : 0,
                }
                postRequest(url,params).then(response => {
                    
                    if (isSuccess(response.data.code)) {
                        if (row.checkStatus == 1) {
                            row.checkStatus = 0;
                        } else {
                            row.checkStatus = 1
                        }
                        this.$message.success('通过')
                    } else {
                        this.$message.error('审核失败');
                    }  
                },response => {
                    //console.log(response)
                    this.$message.error('找不到服务器')
                })
                
            }
            */
           tabClick(val) {
               //index为0表示选中第一个tab
               //alert(this.activeName)
               if(val.index == 1) {
                   //过滤函数
                   var notCheckData = this.tableData.filter(function(item) {
                       return item.checkStatus == 0;
                   });
                   console.log(notCheckData)
                   this.resourceData = notCheckData;
               } else if(val.index == 2) {
                   var checkData = this.tableData.filter(function(item) {
                       return item.checkStatus == 1;
                   });
                   this.resourceData = checkData;
               } else {
                   this.resourceData = this.tableData;
               }
           }
        }
    }
</script>


<style>
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
        
    }
    .badgeSpan {
        display:inline-block;
        width:55px;
        height:24px;
        line-height:24px;
    }
    .page {
        text-align: center;
        margin: 20px auto;
    }
</style>