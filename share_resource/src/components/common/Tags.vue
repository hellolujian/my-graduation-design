<style scoped>
    .tag-card {
        width:100%;
        
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
            @click.native="tagClick(item)">
            {{item.tagName || item}}
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
            tagClick(item) {
                if(item.tagName)
                    this.$emit('getTag',item.tagName);
                else 
                    this.$emit('getTag',item)
            }
        }
    }
</script>

