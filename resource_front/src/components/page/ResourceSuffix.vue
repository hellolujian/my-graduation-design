<template>
    <div class="table">
        <div class="handle-box">
            <el-button 
                type="primary" 
                icon="plus" 
                :loading="addLoading"
                @click="dialogFormVisible = true">新增后缀
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
                label="编号"
                width="100">
            </el-table-column>
            <el-table-column
                prop="suffixName"
                label="后缀名称">
            </el-table-column>
            <el-table-column
                label="后缀图标">
                <template slot-scope="scope">
                    <img :src="scope.row.suffixImage" class="image">
                </template>
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
                width="200">
                <template slot-scope="scope">
                    <Upload 
                        :action="updateUrl" 
                        class="upload" 
                        :data="params"
                        :show-upload-list="false"
                        :before-upload="updateBeforeUpload"
                        :on-success="handleSuccess">
                        <el-button
                            size="small"
                            icon="edit"
                            type="primary"
                            @click="handleEdit(scope.$index, scope.row)">更换图标
                        </el-button>
                    </Upload>
                    
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

        <el-dialog title="新增后缀" :visible.sync="dialogFormVisible">
            <el-form label-width="100px" class="demo-ruleForm">
                <el-form-item label="后缀名">
                    <el-input 
                        v-model="newSuffixName" 
                        clearable
                        placeholder="新增的后缀名称" 
                        class="handle-input mr10"
                        @keyup.native="checkInput">
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <Upload
                        :before-upload="beforeUpload"
                        :action="serverUrl">
                        <el-button
                            type="primary" icon="upload">选择图标
                        </el-button>
                    </Upload>
                </el-form-item>
                <el-form-item>
                    <img :src="src" width="100px" height="100px">
                </el-form-item>
        
                <el-form-item>
                    <el-button type="primary" @click="submit">提交</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
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
                getSuffixUrl: '/suffixs/',
                updateSuffixUrl: '/suffixs/updateSuffix',
                addSuffixUrl: '/suffixs/addSuffix',
                removeSuffixUrl: '/suffixs/removeSuffix',
                listLoading: false,
                addLoading: false,
                newSuffixName:'',
                page: 1,
                total: 0,
                size: 8,
                file: null,
                src: 'http://127.0.0.1:8082/image/suffix_icon/unknow.svg',
                serverUrl: 'http://127.0.0.1:8082/suffixs/addSuffix',
                updateUrl: 'http://127.0.0.1:8082/suffixs/updateSuffix',
                params:null,
                dialogFormVisible: false,
            }
        },
        mounted() {
            this.getSuffixData();
        },
        methods: {
            //限制input输入空格
            checkInput() {
                this.newSuffixName = removeTrim(this.newSuffixName);
            },
            //获取后缀名数据
            getSuffixData() {
                this.listLoading = true;
                getRequest(this.getSuffixUrl).then(response => {
                    this.tableData = response.data.data;
                    this.listLoading = false;
                    this.total = this.tableData.length;
                })
                
            },
            //添加后缀名
            addSuffix() {
                let params = {
                    suffixName: this.newSuffixName,
                    suffixImage: this.newSuffixImage
                };
                if (this.newSuffixName.length == 0) {
                    this.$message({
                            message: '后缀名称不能为空',
                            showClose: true,
                            type: 'error'
                        });
                    return;
                }
                else {
                    this.$confirm('确认添加吗？', '提示', {}).then(() => {
                        this.addLoading = true;
                        postRequest(this.addSuffixUrl,params).then(response => {
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
                            this.newSuffixName = '';
                            this.getSuffixData();
                        })
                        
                    });
                }
                
            },
            //删除后缀
            removeSuffix(params) {
                postRequest(this.removeSuffixUrl,params).then(response => {
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
                    this.getSuffixData();
                })
            },
            //处理编辑事件
            handleEdit(index,row) {
                this.params = {
                    suffixId: row.id,
                    suffixName: row.suffixName
                }
            },
            //处理删除事件
            handleDelete(index,row) {
                let params = {
                    suffixId: row.id
                }
                this.$confirm('确认删除吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.removeSuffix(params);
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
            
            submit() {
                if (this.newSuffixName.length == 0) {
                    this.$message({
                        message: '后缀名不能为空',
                        showClose: true,
                        type: 'error'
                    });
                    return;
                }
                this.dialogFormVisible = false;
                var form = new FormData();
                form.append("file",this.file);
                form.append("suffixName",this.newSuffixName);
                let config = {
                    headers: {
                        'Content-Type': 'multipart/form-data'  //之前说的以表单传数据的格式来传递fromdata
                    }
                };
                this.$axios.post('http://127.0.0.1:8082/suffixs/addSuffix',form,config).then((response) => {
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
                        this.newSuffixName = '';
                        this.file = null;
                        this.getSuffixData();
                }).catch((error) => {

                })
                
            },
            cancel() {
                this.dialogFormVisible = false;
            },
            checkImage(file) {
                const isRightType = (file.type === 'image/jpeg') || (file.type === 'image/png') || (file.type === 'image/svg+xml');
                const isLt500KB = file.size / 1024 / 500 < 1;
                if (!isRightType) {
                    this.$message.error('上传头像图片只能是 JPG/png/svg 格式!');
                }
                if (!isLt500KB) {
                    this.$message.error('上传头像图片大小不能超过 500KB!');
                }
                return isRightType && isLt500KB;
            },
            updateBeforeUpload(file) {
                if (!this.checkImage(file)) {
                    return false;
                }
            },
            beforeUpload (file) {
                var isFileRight = this.checkImage(file);
                if (isFileRight) {
                    this.file = file;
                    var _this = this;
                    var reader = new window.FileReader();
                    reader.onload = function () {
                        _this.src = this.result;
                    }
                    console.log(this.file)
                    console.log(reader.readAsDataURL(this.file));
                }
                return false;
            },
            //还要检查格式大小
            handleSuccess(response,file,fileList) {
                if (isSuccess(response.code)) {
                    this.$message({
                        message: '更改成功',
                        showClose: true,
                        type: 'success'
                    });
                    this.getSuffixData();
                } else {
                    this.$message({
                        message: '更改失败',
                        showClose: true,
                        type: 'error'
                    });
                }
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
.pagination{
    text-align: center;
}
.image {
    margin: 0px;
    height: 35px;
    display: block;
}
.upload {
    display: inline-block;
    height: 30px;
}
</style>