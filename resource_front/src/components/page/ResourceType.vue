<template>
    <div class="table">
        <div class="handle-box">
            <el-input 
                v-model="newTypeName" 
                clearable
                placeholder="新增的类型名称" 
                class="handle-input mr10"
                @keyup.native="checkInput">
            </el-input>
            <el-button 
                type="primary" 
                icon="plus" 
                :loading="addLoading"
                @click="addResourceType">新增类型</el-button>
        </div>
        <el-table
            :data="tableData"
            v-loading="listLoading"
            highlight-current-row
            border
            stripe>
            <el-table-column
                prop="id"
                label="编号">
            </el-table-column>
            <el-table-column
                prop="typeName"
                label="类型名称">
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
                        type="primary"
                        @click="handleEdit(scope.$index, scope.row)">编辑
                    </el-button>
                    <el-button
                        size="small"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
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
                getResourceTypeUrl: '/resourceTypes/',
                updateResourceTypeUrl: '/resourceTypes/updateType',
                addResourceTypeUrl: '/resourceTypes/addType',
                removeResourceTypeUrl: '/resourceTypes/removeType',
                listLoading: false,
                addLoading: false,
                newTypeName:''
            }
        },
        mounted() {
            this.getResourceTypeData();
        },
        methods: {
            //限制input输入空格
            checkInput() {
                this.newTypeName = removeTrim(this.newTypeName);
            },
            //获取类型数据
            getResourceTypeData() {
                this.listLoading = true;
                getRequest(this.getResourceTypeUrl).then(response => {
                    this.tableData = response.data.data;
                    this.listLoading = false;
                })
                
            },
            //添加资源类型
            addResourceType() {
                let params = {
                    typeName: this.newTypeName
                };
                if (this.newTypeName.length == 0) {
                    this.$message({
                            message: '类型名称不能为空',
                            showClose: true,
                            type: 'error'
                        });
                    return;
                }
                else {
                    this.$confirm('确认添加吗？', '提示', {}).then(() => {
                        this.addLoading = true;
                        postRequest(this.addResourceTypeUrl,params).then(response => {
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
                            this.newTypeName = '';
                            this.getResourceTypeData();
                        })
                        
                    });
                }
                
            },
            //删除资源类型
            removeResourceType() {

            },
            //处理编辑事件
            handleEdit(index,row) {
                this.$prompt('请输入新的类型名称', '编辑', {
                    confirmButtonText: '更新',
                    cancelButtonText: '取消',
                    inputValue: row.typeName,
                    inputValidator: isNotNullORBlank,
                    inputErrorMessage: '数据不能含有空格，不能为空'
                }).then(({value}) => {
                    let params = {
                        typeId: row.id,
                        typeName: value
                    }
                    postRequest(this.updateResourceTypeUrl,params).then(response => {
                        if (isSuccess(response.data.code)) {
                            this.$message({
                                type: 'success',
                                message: '已修改名称为:' + value,
                                showClose: true
                            });
                            this.getResourceTypeData();
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
                    typeId: row.id
                }
                this.$confirm('确认删除吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    postRequest(this.removeResourceTypeUrl,params).then(response => {
                        if (isSuccess(response.data.code)) {
                            this.$message({
                                message: '删除成功',
                                showClose: true,
                                type: 'success'
                            });
                            this.getResourceTypeData();
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
                        message: '已取消删除',
                        showClose: true
                    });
                });
            }
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
</style>