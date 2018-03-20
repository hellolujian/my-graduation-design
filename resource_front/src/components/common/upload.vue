<template>
    <div>
        <Upload
            :before-upload="handleUpload"
            :action="serverUrl"
            :format="['jpg','jpeg','png']"
            :max-size="0"
            :on-format-error="handleFormatError"
            :on-exceeded-size="handleMaxSize">
            <el-button
                type="primary" icon="upload">选择图标
            </el-button>
        </Upload>
        <div v-if="file !== null">
            Upload file: {{ file.name }} 
            <Button 
                type="text" 
                @click="upload" 
                :loading="loadingStatus">{{ loadingStatus ? 'Uploading' : 'Click to upload' }}
            </Button>
        </div>
        <img :src="src" width="200px" height="200px">
    </div>
</template>
<script>
    export default {
        data () {
            return {
                file: null,
                loadingStatus: false,
                serverUrl: 'http://127.0.0.1:8082/suffixs/icon',
                src: '',
            }
        },
        methods: {
            handleUpload (file) {
                this.file = file;
                return false;
            },

            upload () {
                this.loadingStatus = true;
                this.ajaxUpload();
                    this.file = null;
                    this.loadingStatus = false;
            },
            ajaxUpload() {
                var form = new FormData();
                form.append("file",this.file);
                var _this = this;
                var reader = new window.FileReader();
                reader.onload = function () {
                    alert(this.result)
                    _this.src = this.result;
                }
                console.log(this.file)
                console.log(reader.readAsDataURL(this.file));
                let config = {
            headers: {
                'Content-Type': 'multipart/form-data'  //之前说的以表单传数据的格式来传递fromdata
            }
        };
        this.$axios.post(this.serverUrl,form,config).then((res) => {

        }).catch((error) => {

        })

            },
        }
    }
</script>
