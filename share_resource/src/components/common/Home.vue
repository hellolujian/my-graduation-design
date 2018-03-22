<template>
    <el-container style="text-align:center">
        <el-header height="70px">
            <div class="logo">
                <img src="static/image/share_logo2.png" title="在线文库分享">
            </div>
            <div class="serachInput">
                <el-input 
                    placeholder="请输入内容"
                    autofocus="true">
                    <el-button slot="append" icon="el-icon-search">搜索</el-button>
                </el-input>
            </div>
            <div class="head-right">
                <el-button class="head-right-btn">登录</el-button>
                <el-button class="head-right-btn">注册</el-button>
            </div>
            <!--
            <div class="login"><el-button type="text" style="color:red">登录</el-button></div>
            <div class="register"><el-button type="primary">注册</el-button></div>-->
        </el-header>
        <el-container class="middle">
            <el-aside>
                <el-button style="width:200px;text-align:left;border:none;">下一页<i style="float:right;" class="el-icon-arrow-right el-icon--right"></i></el-button>
                <el-button class="category-btn">下gdsgds一页<i style="float:right;" class="el-icon-arrow-right el-icon--right"></i></el-button>
            </el-aside>
            <el-main>Main</el-main>
            <el-aside>Aside</el-aside>
        </el-container>
        <el-footer class="footer"><el-popover
  ref="popover1"
  placement="top-start"
  title="标题"
  width="200"
  trigger="hover"
  :visible-arrow="false"
  popper-class="popper"
  content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">
</el-popover>

<el-button v-popover:popover1>hover 激活</el-button>Footer</el-footer>
    </el-container>
          
</template>

<style>
    .logo {
        float: left;
    }
    .head-right {
        float: right;
    }
    .head-right-btn {
        border: none;
    }
    .footer {
        margin-top: 20px; 
    }
    .serachInput {
        width: 400px;
        display: inline-block;
    }
    .middle {
        width:90%;
        margin:0 auto
    }
    .category-btn {
        width:200px;text-align:left;border:none;background:#D3DCE6
    }
    .category-btn:hover {
        background: red;
    }
    .popper {
        border:1px solid red;
        left: 100px;
        position: absolute;
    }
</style>
