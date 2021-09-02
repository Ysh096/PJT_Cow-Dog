<template>
    <div class="result-view">
        <div v-if="state.distanceUsers[0]==null" class="no_user">
            <h1 style="padding-top:10%">거리에 해당되는 사람이 없습니다!!!</h1>
        </div>
        <div v-else>
            <div class="random-cards-wrapper">
                <div class="each-card" v-for="user in distanceUsersPaginated" :key="user.id">
                    <distance-result-view-detail :user="user" />
                </div>   
            </div>
            <div class="pagination" style="margin:0 auto; margin-top:10px">
                <el-pagination
                background
                layout="prev, pager, next"
                :page-size=pageSize
                :total="distanceUsers.length"
                v-model:current-page="currentPage">
                </el-pagination>
            </div>
        </div>
        <div>
            <el-button class="back" @click="back()">뒤로 가기</el-button>
        </div>
    </div>
</template>

<script>

import {  reactive } from 'vue'
import { mapGetters, useStore } from 'vuex'
import DistanceResultViewDetail from './DistanceResultViewDetail.vue'
import router from '../../../router'
export default {
        data() {
        return {
        // for pagination
        pageSize: 8, // 한 페이지에 나오는 게시물 수
        currentPage: 1, // 현재 페이지(v-model 적용)
        }
    },
    methods: {
        moveToNoticeCreate() {
        router.push('/admin/noticeCreate')
        }
    },
    computed: {
        distanceUsersPaginated() {
        var start = 0 + (this.currentPage-1) * this.pageSize
        var end = this.currentPage * this.pageSize
        return this.distanceUsers.slice(start, end) //기본값 0~5번
        },
        ...mapGetters({
        distanceUsers: 'getDistanceUser'
        })
    },
    components:{
        DistanceResultViewDetail
    },

    setup() {
        
        const store=useStore()
         const state = reactive({
           distanceUsers:store.getters.getDistanceUser
        })
         const back=function(){
            router.push("/main")
        }
        return{
            state,back
            
        }
    }
}

</script>
<style>
</style>