<template>
    <div class="table">
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
                prop="username"
                label="用户名">
            </el-table-column>
            <el-table-column
                prop="email"
                :show-overflow-tooltip="true"
                label="邮箱">
            </el-table-column>
            <el-table-column
                prop="points"
                label="积分">
            </el-table-column>
            <el-table-column
                prop="tags"
                label="标签"
                width="150">
                
                <template slot-scope="scope">
                    <el-tag
                        style="margin-right:5px"
                        v-for="(item,index) in scope.row.tags.slice(0,2)"
                        :key="index"
                        type="success">{{item}}
                    </el-tag>
                    <el-popover v-if="scope.row.tags.length > 2"
                        placement="right"
                        title="标签"
                        width="200"
                        trigger="hover">
                        <el-tag
                            style="margin-right:10px;margin-bottom:10px;"
                            v-for="(item,index) in scope.row.tags"
                            :key="index"
                            type="success">{{item}}
                        </el-tag>
                        <el-button slot="reference" class="el-icon-more" type="text"></el-button>
                    </el-popover>
                </template>
                
            </el-table-column>
            <el-table-column
                prop="createTime"
                :show-overflow-tooltip="true"
                sortable
                label="创建时间">
            </el-table-column>
            <el-table-column
                prop="updateTime"
                :show-overflow-tooltip="true"
                label="更新时间">
            </el-table-column>
            <el-table-column
                prop="createTime"
                label="操作">
                <template slot-scope="scope">
                    <el-button
                        size="small"
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
    import {isSuccess} from '../../utils/api';

    export default {
        data() {
            return {
                tableData: [],
                userTags: [],
                getUsersPageUrl: '/users/page',
                getUsersUrl: '/users/',
                removeUserUrl: '/users/remove/',
                listLoading: false,
                page: 1,
                total: 0,
                size: 6,
            }
        },
        mounted() {
            this.getUsersPageData();
        },
        methods: {   
            //分页获取数据
            getUsersPageData() {
                //this.listLoading = true;
                let params = {
                    pageSize: this.size,
                    pageNum: this.page,
                    
                };
                let url = this.getUsersPageUrl + '/' +this.page+'/' + this.size;
                //alert(url)
                getRequest(url).then(response => {
                    this.tableData = response.data.data.data;
                    this.total = response.data.data.count;
                    //this.listLoading = false;
                })
            },
            
            //处理删除事件
            handleDelete(index,row) {
                let url = this.removeUserUrl + row.id;
                this.$confirm('确认删除吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    postRequest(url).then(response => {
                        if (isSuccess(response.data.code)) {
                            this.$message({
                                message: '删除成功',
                                showClose: true,
                                type: 'success'
                            });
                            this.getUsersData();
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
            },
            //当前页改变
            handleCurrentChange(val){
                this.page = val;
                this.getUsersPageData();
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