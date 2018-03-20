<template>
    <div class="table">
        <div class="handle-box">
            <el-input 
                v-model="newParentCategoryName" 
                clearable
                placeholder="新增的分类名称" 
                class="handle-input mr10"
                @keyup.native="checkInput">
            </el-input>
            <el-button 
                type="primary" 
                icon="plus" 
                :loading="addLoading"
                @click="addParentCategory">新增分类
            </el-button>
            
        </div>
        <el-table
            :data="tableData.slice((page-1)*size,page*size)"
            v-loading="listLoading"
            highlight-current-row
            border
            stripe>
            <el-table-column
                prop="id"
                label="编号">
            </el-table-column>
            <el-table-column
                prop="parentCategoryName"
                label="分类名称">
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
                label="操作"
                width="300">
                <template slot-scope="scope">
                    <router-link :to="{name: 'childCategory', params: {id: 10}}">
                        <el-button 
                            size="small"
                            icon="view"
                            type="success">查看子分类
                        </el-button>
                    </router-link>
                    
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
                getParentCategoryUrl: '/category/parent',
                updateParentCategoryUrl: '/category/updateParentCategory',
                addParentCategoryUrl: '/category/addParentCategory',
                removeParentCategoryUrl: '/category/removeParentCategory',
                listLoading: false,
                addLoading: false,
                newParentCategoryName:'',
                page: 1,
                total: 0,
                size: 8,
            }
        },
        mounted() {
            this.getParentCategoryData();
        },
        methods: {
            //限制input输入空格
            checkInput() {
                this.newParentCategoryName = removeTrim(this.newParentCategoryName);
            },
            //获取父分类数据
            getParentCategoryData() {
                this.listLoading = true;
                getRequest(this.getParentCategoryUrl).then(response => {
                    this.tableData = response.data.data;
                    this.listLoading = false;
                    this.total = this.tableData.length;
                })
                
            },
            //添加父级分类
            addParentCategory() {
                let params = {
                    parentCategoryName: this.newParentCategoryName
                };
                if (this.newParentCategoryName.length == 0) {
                    this.$message({
                            message: '分类名称不能为空',
                            showClose: true,
                            type: 'error'
                        });
                    return;
                }
                else {
                    this.$confirm('确认添加吗？', '提示', {}).then(() => {
                        this.addLoading = true;
                        postRequest(this.addParentCategoryUrl,params).then(response => {
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
                            this.newParentCategoryName = '';
                            this.getParentCategoryData();
                        })
                        
                    });
                }
                
            },
            //删除父级分类
            removeParentCategory(params) {
                postRequest(this.removeParentCategoryUrl,params).then(response => {
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
                    this.getParentCategoryData();
                })
            },
            //处理编辑事件
            handleEdit(index,row) {
                this.$prompt('请输入新的分类名称', '编辑', {
                    confirmButtonText: '更新',
                    cancelButtonText: '取消',
                    inputValue: row.parentCategoryName,
                    inputValidator: isNotNullORBlank,
                    inputErrorMessage: '数据不能含有空格，不能为空'
                }).then(({value}) => {
                    let params = {
                        parentCategoryId: row.id,
                        parentCategoryName: value
                    }
                    postRequest(this.updateParentCategoryUrl,params).then(response => {
                        if (isSuccess(response.data.code)) {
                            this.$message({
                                type: 'success',
                                message: '已修改名称为:' + value,
                                showClose: true
                            });
                            this.getParentCategoryData();
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
                    parentCategoryId: row.id
                }
                this.$confirm('确认删除吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.removeParentCategory(params);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除',
                        showClose: true
                    });
                });
            },
            //当前页改变
            handleCurrentChange(val){
                this.page = val;
            },
            //转到子分类
            viewChildCategory(index,row) {
                this.$router.push({
                    name: 'childCategory',
                    params: {
                        id: row.id
                    }
                })
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