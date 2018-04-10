<style scoped>
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
      position: absolute;
  }
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }
</style>

<template>
    <div class="box-card">
        <Card>
            <div slot="title" class="clearfix">
                <h2>上传须知</h2>
            </div>
            <div v-for="item in attention" :key="item" class="text item">
                {{item}}
            </div>
        </Card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                attention: [
                    "* 如涉及侵权内容,您的资源将被移除",
                    "* 请勿上传小说、mp3、图片等与技术无关的内容.一旦发现将被删除",
                    "* 请勿在未经授权的情况下上传任何涉及著作权侵权的资源，除非该资源完全由您个人创作",
                    "* 点击上传资源即表示您确认该资源不违反资源分享的使用条款，并且您拥有该资源的所有版权或者上传资源的授权"
                ],
            }
        }
    }
</script>
