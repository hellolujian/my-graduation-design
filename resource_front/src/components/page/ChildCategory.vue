<template>
    <div class="table">
        <div class="handle-box">
            <el-button type="danger" 
                icon="delete" 
                class="handle-del mr10"
                @click="deleteMultipleTags">批量删除
            </el-button>
            <el-input 
                v-model="newChildCategoryName" 
                clearable
                placeholder="新增的子分类名称" 
                class="handle-input mr10"
                @keyup.native="checkInput">
            </el-input>
            <el-button 
                type="primary" 
                icon="plus" 
                :loading="addLoading"
                @click="addChildCategory">新增子级分类
            </el-button>
            
        </div>
        <el-table
            :data="tableData.slice((page-1)*size,page*size)"
            v-loading="listLoading"
            highlight-current-row
            border
            stripe
            @selection-change="handleSelectionChange">
            <el-table-column 
                type="selection">
            </el-table-column>
            <el-table-column
                prop="id"
                label="编号">
            </el-table-column>
            <el-table-column
                prop="childCategoryName"
                label="子分类名称">
            </el-table-column>
            <el-table-column
                prop="createTime"
                label="创建时间">
            </el-table-column>
            <el-table-column
                prop="updateTime"
                label="更新时间">
            </el-table-column>
            <el-table-column
                prop="createTime"
                label="操作">
                <template slot-scope="scope">
                    <el-button
                        size="small"
                        icon="edit"
                        type="primary"
                        @click="handleEdit(scope.$index, scope.row)">编辑
                    </el-button>
                    <el-button
                        size="small"
                        icon="delete"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
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
    import {getRequest} from '../../utils/api';
    import {postRequest} from '../../utils/api';
    import {removeTrim} from '../../utils/util';
    import {isSuccess} from '../../utils/api';
    import {isNotNullORBlank} from '../../utils/util'

    export default {
        data() {
            return {
                tableData: [],
                getChildCategoryUrl: `/category/parent/${this.$route.params.id}/childCategory`,
                updateChildCategoryUrl: '/category/updateChildCategory',
                addChildCategoryUrl: '/category/addChildCategory',
                removeChildCategoryUrl: '/category/removeChildCategory',
                listLoading: false,
                addLoading: false,
                newChildCategoryName:'',
                multipleSelection: [],//表格选中项
                page: 1,
                total: 0,
                size: 8,
            }
        },
        mounted() {
            this.getChildCategoryData();
        },
        methods: {
            //限制input输入空格
            checkInput() {
                this.newChildCategoryName = removeTrim(this.newChildCategoryName);
            },
            //获取子分类数据
            getChildCategoryData() {
                this.listLoading = true;
                getRequest(this.getChildCategoryUrl).then(response => {
                    this.tableData = response.data.data;
                    this.listLoading = false;
                    this.total = this.tableData.length;
                })
                
            },
            //添加子分类
            addChildCategory() {
                let params = {
                    parentCategoryId: this.$route.params.id,
                    childCategoryName: this.newChildCategoryName
                };
                if (this.newChildCategoryName.length == 0) {
                    this.$message({
                            message: '子分类名称不能为空',
                            showClose: true,
                            type: 'error'
                        });
                    return;
                }
                else {
                    this.$confirm('确认添加吗？', '提示', {}).then(() => {
                        this.addLoading = true;
                        postRequest(this.addChildCategoryUrl,params).then(response => {
                            if (isSuccess(response.data.code)) {
                                this.$message({
                                    message: '添加成功',
                                    showClose: true,
                                    type: 'success'
                                });
                            } else {
                                this.$message({
                                    message: '添加失败',
                                    showClose: true,
                                    type: 'error'
                                });
                            }
                            this.addLoading = false;
                            this.newChildCategoryName = '';
                            this.getChildCategoryData();
                        })
                        
                    });
                }
                
            },
            //删除子级分类
            removeChildCategory(params) {
                postRequest(this.removeChildCategoryUrl,params).then(response => {
                    if (isSuccess(response.data.code)) {
                        this.$message({
                            message: '删除成功',
                            showClose: true,
                            type: 'success'
                        });
                        
                    } else {
                        this.$message({
                            message: '删除失败',
                            showClose: true,
                            type: 'error'
                        });
                    } 
                    this.getChildCategoryData();
                })
            },
            //处理编辑事件
            handleEdit(index,row) {
                this.$prompt('请输入新的子分类名称', '编辑', {
                    confirmButtonText: '更新',
                    cancelButtonText: '取消',
                    inputValue: row.childCategoryName,
                    inputValidator: isNotNullORBlank,
                    inputErrorMessage: '数据不能含有空格，不能为空'
                }).then(({value}) => {
                    let params = {
                        childCategoryId: row.id,
                        childCategoryName: value
                    }
                    postRequest(this.updateChildCategoryUrl,params).then(response => {
                        if (isSuccess(response.data.code)) {
                            this.$message({
                                type: 'success',
                                message: '已修改名称为:' + value,
                                showClose: true
                            });
                            this.getChildCategoryData();
                        } else {
                            this.$message({
                                message: '删除失败',
                                showClose: true,
                                type: 'error'
                            });
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入',
                        showClose: true
                    });
                });
            },
            //处理删除事件
            handleDelete(index,row) {
                let params = {
                    childCategoryId: row.id
                }
                this.$confirm('确认删除吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.removeChildCategory(params);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除',
                        showClose: true
                    });
                });
            },
            //处理选中项改变
            handleSelectionChange(val) {
                this.multipleSelection = val;
                console.log(val);
            },
            //批量删除
            deleteMultipleTags() {
                for(var i = 0; i<this.multipleSelection.length; i++) {
                    let params = {
                        tagId: this.multipleSelection[i].id
                    }
                    this.removeChildCategory(params);
                }
            },
            handleCurrentChange(val){
                this.page = val;
            },
        }
    }
</script>

<style scoped>
.handle-box{
    margin-bottom: 20px;
}
.handle-select{
    width: 120px;
}
.handle-input{
    width: 200px;
    display: inline-block;
}
.pagination{
    text-align: center;
}
</style>