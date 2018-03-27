<template>
    <div>
        <v-head></v-head>
        <v-condition></v-condition>
        <v-resource2 ref="r"></v-resource2>
    </div>
</template>

<script>
    import vHead from '../common/Header';
    import vCondition from '../common/Condition';
    import vResource2 from '../common/Resource2';

    export default {
        data() {
            return {
                parentCategoryId: null,
                childCategoryId: null,
                childCategoryData: [],
            }
        },
        components: {
            vHead,vCondition,vResource2,
        },
        mounted() {
            this.parentCategoryId = this.$route.params.parentCategoryId;
            this.childCategoryId = this.$route.params.childCategoryId;
            this.childCategoryData = this.$route.params.data;
        },
        methods: {
        }
    }
</script>

