<template>
    <el-header height="70px">
        <div class="logo">
            <img src="static/image/share_logo2.png" title="在线文库分享">
        </div>
        <div class="serachInput">
            <el-input 
                placeholder="请输入内容"
                :autofocus="true">
                <el-button slot="append" icon="el-icon-search">搜索</el-button>
            </el-input>
        </div>
        <div class="head-right">
            <el-button class="head-right-btn">登录</el-button>
            <el-button class="head-right-btn">注册</el-button>
        </div>
    </el-header>
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
    .serachInput {
        width: 400px;
        display: inline-block;
    }
</style>

