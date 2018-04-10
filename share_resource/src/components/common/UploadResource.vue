<style scoped>
    .uploadForm {
        width: 600px;
        margin: 0px 10px 10px 0px;
    }
    .resource-name-input {
        cursor: pointer;
    } 
    .upload-btn {
        margin-left: 20px;
    }  
    .myTag {
        height:35px;
        line-height:35px;
        font-size:14px;
    } 
    .tagInput {
        font-size: 14px;
    }
    label {
        font-size: 14px;
    }
</style>

<template>
<Card class="uploadForm">
    <Form 
        :model="ruleForm" 
        :rules="rules" 
        ref="ruleForm" 
        :label-width="80">
        <FormItem prop="name">
            <label slot="label">资源名称</label>
            <Upload
                :before-upload="beforeUpload"
                :action="resourceUrl">
                <Row> 
                    <Col span="15">
                        <Input 
                            class="resource-name-input"
                            readonly
                            size="large"
                            v-model="ruleForm.name" >
                        </Input>
                    </Col>
                    <Col span="7">
                        <Poptip trigger="hover" title="提示">
                            <div slot="content">您可以上传小于200MB的文件</div>
                            <Button 
                                class="upload-btn"
                                type="primary"
                                size="large"
                                icon="ios-cloud-upload">上传文件</Button>
                        </Poptip>
                    </Col>
                    </Row>
            </Upload>
        </FormItem>
        <FormItem prop="type">
            <label slot="label">资源类型</label>
            <Row>
                <Col span="11">
                    <Select 
                        size="large"
                        v-model="ruleForm.type" 
                        :loading="typeLoading"
                        placeholder="请选择">
                        <Option
                            v-for="item in resourceType" 
                            :label="item.typeName" 
                            :value="item.id"  
                            :key="item.id">
                        </Option>
                    </Select>
                </Col>
            </Row>
        </FormItem>
        <FormItem prop="tagList">
            <label slot="label">关键词</label>
            <Tag
                class="myTag"
                :key="tag"
                v-for="tag in ruleForm.tagList"
                
                color="red"
                closable
                @on-close="handleClose(tag)">{{tag}}
            </Tag>
            <input 
                class="tagInput"
                v-focus
                v-if="inputVisible" 
                v-model="inputValue"
                @keyup.enter="showNewTag"
                @blur="showNewTag">
            </input>
            <Button 
                v-if="!inputVisible"
                icon="ios-plus-empty"
                size="large"
                type="primary"
                @click="handleAdd">添加标签
            </Button>
        </FormItem>
        
        <FormItem>
            <label slot="label">所属分类</label>
            <Row>
                <Col span="11">
                    <FormItem prop="parentCategory">
                        <Select 
                            v-model="ruleForm.parentCategory" 
                            placeholder="请选择" 
                            size="large"
                            @on-change="parentCategoryChange">
                            <Option 
                                v-for="item in parentCategory" 
                                :key="item.id" 
                                :label="item.parentCategoryName" 
                                :value="item.id">
                            </Option>
                        </Select>
                    </FormItem>
                </Col>
                <Col span="2"><div style="width:1px;height:1px;"></div></Col>
                <Col span="11">
                    <FormItem prop="childCategory">
                        <Select 
                            v-if="selectVisible"
                            v-model="ruleForm.childCategory" 
                            size="large"
                            placeholder="请选择" >
                            <Option 
                                v-for="item in childCategory" 
                                :key="item.id" 
                                :label="item.childCategoryName" 
                                :value="item.id">
                            </Option>
                        </Select>
                    </FormItem>
                </Col>
            </Row>
        </FormItem>    
        
        <FormItem prop="points">
            <label slot="label">资源分</label>
            <Row>
                <Col span="11">
                    <Select 
                        v-model="ruleForm.points" 
                        placeholder="请选择" 
                        size="large">
                        <Option 
                            v-for="item in points" 
                            :key="item.id" 
                            :label="item.point" 
                            :value="item.point">
                        </Option>
                    </Select>
                </Col>
            </Row>
        </FormItem>
        <FormItem prop="description">
            <label slot="label">资源描述</label>
            <Input 
                type="textarea" 
                v-model="ruleForm.description">
            </Input>
        </FormItem>
        <FormItem>
            <Checkbox size="large" v-model="ruleForm.checked">同意资源上传规则</Checkbox>
        </FormItem>
    
        <FormItem>
            <Button 
                type="primary" 
                size="large"
                @click="submitForm('ruleForm')">上传
            </Button>
            <Button 
                type="error" 
                size="large"
                @click="resetForm('ruleForm')">重置
            </Button>
        </FormItem>
    </Form>
    
</Card>
    
</template>


<script>
    import {getRequest,isSuccess,uploadRequest, postRequest} from '../../utils/api';
    //引入自定义指令v-focus使得点击按钮自动获得焦点
    import directive from '@/components/global/directives';
    export default {
        mounted() {
          
        },
        props: {
            resourceUrl: {
                type: String,
            },
            resourceType: {
                type: Array,
            },
            points: {
                type: Array,
            },
            parentCategory: {
                type: Array
            },
            childCategory: {
                type: Array,
                default: function() {
                    return [];
                }
            }
        },
        data() {
            return {
                selectVisible: false, //子分类是否可见
                //表单数据
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
                        { min: 50, max: 100, message: '摘要在 50 到 100 个字符', trigger: 'blur' }
                    ]
                },
                //关于标签的相关数据
                inputVisible: false,
                inputValue: '',

                file: null,
                
                typeLoading: false,
            };
        },
        methods: {
            //添加标签事件
            handleAdd () {
                this.inputVisible = true;
            },
            //输入新的标签值
            showNewTag() {
                this.inputVisible = false;
                let inputValue = this.inputValue;
                if (inputValue.trim().length && this.ruleForm.tagList.indexOf(inputValue) == -1) {
                    this.ruleForm.tagList.push(inputValue);
                }
                this.inputVisible = false;
                this.inputValue = '';
            },
            
            //关闭标签
            handleClose(tag) {
                this.ruleForm.tagList.splice(this.ruleForm.tagList.indexOf(tag), 1);
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
                this.ruleForm.childCategory = ''; //先将子分类置空
                if (val != null && val != 0) {
                    this.selectVisible = true;
                    this.$emit('getChildCategoryData',val);
                }
            },
            //检查表单
            submitForm(formName) {
                if (this.ruleForm.checked == false) {
                    this.$Notice.warning({
                        title: '注意',
                        desc: '请确认同意上传规则'
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
                        this.$Notice.error({
                            title: '错误',
                            desc: '请正确填写信息 '
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
                uploadRequest('/resources/upload',form,config).then(response => {
                    if (isSuccess(response.data.code)) {
                        this.$Message.success({
                            content: '添加成功',
                            closable: true,
                        });
                        this.$emit('uploadSuccess');
                        } else {
                            this.$Message.error({
                                content: '添加失败',
                                closable: true,
                            });
                        }
                })
                /*
                this.$axios.post('/api/resources/upload',form,config).then((response) => {
                    if (isSuccess(response.data.code)) {
                        this.$Message.success({
                            content: '添加成功',
                            closable: true,
                        });
                        } else {
                            this.$Message.error({
                                content: '添加失败',
                                closable: true,
                            });
                        }
                        
                }).catch((error) => {

                })*/
            },
            //重置表单
            resetForm(formName) {
                this.selectVisible = false;
                this.$refs[formName].resetFields();
            },
        }
    }
</script>

