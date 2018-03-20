<template>
<div>
    <el-card class="uploadForm" :body-style="{ padding: '0px' }">
        <el-form 
            :model="ruleForm" 
            :rules="rules" 
            ref="ruleForm" 
            label-width="100px" 
            class="demo-ruleForm">
            <el-form-item label="资源名称" prop="name">
                
                <Upload
                    class="upload"
                    :before-upload="beforeUpload"
                    :action="resourceUrl">
                    <el-input 
                        readonly
                        v-model="ruleForm.name" 
                        class="nameInput">
                    </el-input>
                    <el-tooltip effect="light" placement="right-start">
                        <div slot="content" class="tooltip">您可以上传小于200MB的文件</div>
                        <el-button
                            type="primary" icon="upload">上传文件
                        </el-button>
                    </el-tooltip>
                </Upload>
            </el-form-item>
            <el-form-item label="资源类型" prop="type">
                <el-select 
                    v-model="ruleForm.type" 
                    :loading="typeLoading"
                    placeholder="请选择" 
                    class="typeSelect">
                    <el-option
                        v-for="item in resourceType" 
                        :label="item.typeName" 
                        :value="item.id"  
                        :key="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="关键词" prop="tagList" class="tagItem">
                <el-tag
                    class="myTag"
                    type="success"
                    :key="tag"
                    v-for="tag in ruleForm.tagList"
                    closable
                    :disable-transitions="false"
                    @close="handleClose(tag)">{{tag}}
                </el-tag>
                <el-input
                    class="input-new-tag"
                    v-if="inputVisible"
                    v-model="inputValue"
                    ref="saveTagInput"
                    size="small"
                    @keyup.enter.native="handleInputConfirm"
                    @blur="handleInputConfirm">
                </el-input>
                <el-button 
                    v-else 
                    class="button-new-tag" 
                    size="small" 
                    @click="showInput">+ New Tag
                </el-button>
            </el-form-item>
            <el-form-item label="所属分类" prop="parentCategory" class="categorySelect">
                <el-select 
                    v-model="ruleForm.parentCategory" 
                    placeholder="请选择" 
                    class="typeSelect"
                    @change="parentCategoryChange">
                    <el-option 
                        v-for="item in parentCategory" 
                        :key="item.id" 
                        :label="item.parentCategoryName" 
                        :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item prop="childCategory" class="categorySelect">
                <el-select 
                    v-if="selectVisible"
                    v-model="ruleForm.childCategory" 
                    placeholder="请选择" 
                    class="typeSelect">
                    <el-option 
                        v-for="item in childCategory" 
                        :key="item.id" 
                        :label="item.childCategoryName" 
                        :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="资源分" prop="points">
                <el-select 
                    v-model="ruleForm.points" 
                    placeholder="请选择" 
                    class="typeSelect">
                    <el-option 
                        v-for="item in points" 
                        :key="item.id" 
                        :label="item.point" 
                        :value="item.point">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="资源描述" prop="description" style="width:480px">
                <el-input 
                    type="textarea" 
                    v-model="ruleForm.description">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-checkbox v-model="ruleForm.checked">同意资源上传规则</el-checkbox>
            </el-form-item>
        
            <el-form-item>
                <el-button 
                    type="primary" 
                    @click="submitForm('ruleForm')">立即创建
                </el-button>
                <el-button 
                    type="danger" 
                    @click="resetForm('ruleForm')">重置
                </el-button>
            </el-form-item>
        </el-form>
    </el-card>
    <div class="box-card">
        <el-card>
            <div slot="header" class="clearfix">
                <h2>上传须知</h2>
            </div>
            <div v-for="item in attention" :key="item" class="text item">
                {{item}}
            </div>
        </el-card>
    </div>
    
</div>
    
</template>


<script>
    import {getRequest,isSuccess} from '../../utils/api';
    export default {
       mounted() {
          this.getResourceTypeData();
          this.getPointsData();
          this.getParentCategoryData();
        },
        data() {
            return {
                resourceType: [],
                getResourceTypeUrl: '/resourceTypes/',
                getPointsUrl: '/points/',
                points: [],
                getParentCategoryUrl: '/category/parent',
                parentCategory: [],
                childCategory: [],
                selectVisible: false,
                //getChildCategoryUrl: `/category/parent/${this.$route.params.id}/childCategory`,
                ruleForm: {
                    name: '',
                    file: null,
                    type: '',
                    tagList: [],
                    parentCategory: '',
                    childCategory: '',
                    points: '',
                    description: '',
                    checked: true,
                },
                rules: {
                    name: [
                        { required: true, message: '请选择要上传的文件', trigger: 'blur' }
                    ],
                    file: [
                        { required: true, message: '请选择要上传的文件', trigger: 'change' }
                    ],
                    type: [
                        { type:'number', required: true, message: '请选择资源类型', trigger: 'change' }
                    ],
                    childCategory: [
                        { type:'number', required: true, message: '请选择分类', trigger: 'change' }
                    ],
                    parentCategory: [
                        { type:'number', required: true, message: '请选择分类', trigger: 'change' }
                    ],
                    tagList: [
                        { type: 'array', required: true, message: '请至少填写一个关键词', trigger: 'change' }
                    ],
                    points: [
                        { type: 'number', required: true, message: '请选择资源分', trigger: 'change' }
                    ],
                    description: [
                        { required: true, message: '请填写摘要', trigger: 'blur' },
                       // { min: 50, max: 100, message: '摘要在 50 到 100 个字符', trigger: 'blur' }
                    ]
                },
                //关于标签的相关数据
                inputVisible: false,
                inputValue: '',

                file: null,
                resourceUrl: 'http://127.0.0.1:8082/resources/upload',
                typeLoading: false,
                attention: [
                    "* 如涉及侵权内容,您的资源将被移除",
                    "* 请勿上传小说、mp3、图片等与技术无关的内容.一旦发现将被删除",
                    "* 请勿在未经授权的情况下上传任何涉及著作权侵权的资源，除非该资源完全由您个人创作",
                    "* 点击上传资源即表示您确认该资源不违反资源分享的使用条款，并且您拥有该资源的所有版权或者上传资源的授权"
                ],
            };
        },
        methods: {

            //获取数据
            getResourceTypeData() {
                getRequest(this.getResourceTypeUrl).then(response => {
                    this.resourceType = response.data.data;
                })  
            },
            getPointsData() {
                getRequest(this.getPointsUrl).then(response => {
                    this.points = response.data.data;
                })
            },
            //获取分类数据
            getParentCategoryData() {
                getRequest(this.getParentCategoryUrl).then(response => {
                    this.parentCategory = response.data.data;
                })
            },
            getChildCategoryData(url) {
                getRequest(url).then(response => {
                    this.childCategory = response.data.data;
                })
            },
            
            //关于标签的一些函数
            handleClose(tag) {
              this.ruleForm.tagList.splice(this.ruleForm.tagList.indexOf(tag), 1);
            },

            showInput() {
              this.inputVisible = true;
              this.$nextTick(_ => {
                this.$refs.saveTagInput.$refs.input.focus();
              });
            },

            handleInputConfirm() {
              let inputValue = this.inputValue;
              if (inputValue) {
                this.ruleForm.tagList.push(inputValue);
              }
              this.inputVisible = false;
              this.inputValue = '';
            },

            //以下为上传相关
            checkFileSize(file) {
                const isLessThan200MB = file.size / 1024 /1024 / 200 < 1 ;
                if (!isLessThan200MB) {
                    this.$message.error('上传文件的大小不能超过200MB');
                    return false;
                } 
                return true;
            },
            //处理上传文件之前
            beforeUpload(file) {
                if (this.checkFileSize(file)) {
                    this.file = file;
                    this.ruleForm.name = file.name;
                }
                return false;
            },

            //下拉框选中事件
            parentCategoryChange(val) {
                this.ruleForm.childCategory = '';
                if (val != null) {
                    let getChildCategoryUrl =  `/category/parent/${val}/childCategory`;
                    this.getChildCategoryData(getChildCategoryUrl);
                    this.selectVisible = true;
                }
            },
            //检查表单
            submitForm(formName) {
                if (this.ruleForm.checked == false) {
                    this.$notify({
                        title: '注意',
                        type: 'warning',
                        message: '请确认同意上传规则'
                    });
                    return false;
                }
                
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.organizeForm();
                        this.file = null;
                        this.resetForm(formName);
                        return true;
                    } else {
                        this.$notify({
                            title: '错误',
                            type: 'error',
                            message: '请正确填写信息'
                        });
                        return false;
                    }
                });
            },
            //提交表单
            organizeForm() {
                var form = new FormData();
                form.append("file",this.file);
                form.append("userId", 1);
                form.append("categoryId", this.ruleForm.childCategory);
                form.append("typeId", this.ruleForm.type);
                form.append("tagList", this.ruleForm.tagList);
                form.append("points", this.ruleForm.points);
                form.append("description", this.ruleForm.description);
                let config = {
                    headers: {
                        'Content-Type': 'multipart/form-data'  //之前说的以表单传数据的格式来传递fromdata
                    }
                };
                this.$axios.post('http://127.0.0.1:8082/resources/upload',form,config).then((response) => {
                    
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
                        
                }).catch((error) => {

                })
            },
            resetForm(formName) {
              this.$refs[formName].resetFields();
            },
        }
    }
</script>

<style scoped>
.uploadForm {
  width: 500px;
  padding-top: 20px;
  display: inline-block;
}
.el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
  .myTag{
    height: 32px;
    line-height: 30px;
  }
  .typeSelect{
    display: inline-block;
    width: 180px;
    margin-right: 15px;
  }
  .categorySelect {
      width: 200px;
      display: inline-block;
  }
  .upload{
    display: inline-block;
  }
  .nameInput{
    width: 250px;
    margin-right: 15px
  }
  .tooltip {
    color: red;
    font-size: 16px;
  }

.text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
      display: inline-block;
      width: 480px;
      left: 560px;
      position: absolute;
      top: 53px;
  }
</style>
