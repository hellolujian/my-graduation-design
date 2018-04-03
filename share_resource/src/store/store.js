import Vue from 'vue';
import Vuex from 'vuex';
import { stat } from 'fs';

Vue.use(Vuex);

const state = {
    count: 1,
    parentCategoryId: 0,
    childCategoryId: 0,
    childCategoryData: [],
    typeId: 0,
    checkStatus: 1,
    sortType: 0, //0默认排序，1最新上传，2组多下载
    keyword: null,
    searchResult: [],
    searchResultTotal: 0,
};
const mutations = {
    /**/
    add (state) {
        state.count += 1;
    },
    add2 (state,n) {
        state.count += n;
    },
    reduce (state) {
        state.count -= 1;
    },

    changeParentCategoryId(state,newParentCategoryId) {
        state.parentCategoryId = newParentCategoryId;
    },
    changeChildCategoryId(state,newChildCategoryId) {
        state.childCategoryId = newChildCategoryId;
    },
    changeChildCategoryData(state,newChildCategoryData) {
        state.childCategoryData = newChildCategoryData;
    },
    changeTypeId(state,newTypeId) {
        state.typeId = newTypeId;
    },
    changeSearchResult(state,newSearchResult) {
        state.searchResult = newSearchResult;
    },
    changeSearchResultTotal(state,newSearchResultTotal) {
        state.searchResultTotal = newSearchResultTotal;
    }
};
var vuexStore = new Vuex.Store({
    state,
    mutations
});


export default vuexStore;