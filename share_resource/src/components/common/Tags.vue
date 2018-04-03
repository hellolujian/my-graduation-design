<style scoped>
    .tag-card {
        width:270px;
    }
        .tag-card .tag {
            margin:5px;

        }
</style>

<template>
    <Card class="tag-card">
        <p slot="title">{{title}}</p>
        <Tag
            v-for="(item,index) in tagList" 
            :key="index"
            color="red"
            type="border"
            class="tag"
            @click.native="tagClick(item.tagName)">
            {{item.tagName}}
        </Tag>
        
    </Card>
</template>

<script>
    export default {
        props: {
            title: {
                //default: '热门标签'
            },
            tagList: {
                type: Array,
            }
        },
        methods: {
            tagClick(tagName) {
                this.$emit('getTag',tagName)
            }
        }
    }
</script>

