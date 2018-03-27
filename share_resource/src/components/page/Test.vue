
<template>
    
    <div style="border:1px solid #eee;width:200px;">
        <div>{{count}}</div>
        <button @click="add">加一</button>
        <button @click="add3(10)">加10</button>
        <button @click="click">alert</button>

        <button @click="$store.commit('reduce')">减一</button>
    </div>
</template>
<script>
    import store from "@/store/store.js";
    import {mapState,mapMutations} from 'vuex';
    export default {
        data() {
            return {

            }
        },
        computed:mapState({
            count: state => state.count
        }),
        methods: {
            ...mapMutations([
                'add','reduce','add2'
            ]),
            click() {
                alert(this.count)
            },
            add3(val) {
                this.add2(val);
            }
        },
        store
    }
</script>
<!---->
