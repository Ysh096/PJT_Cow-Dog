<template>
    <div class="result-view">
        <div v-if="randomUsersPaginated[0]==null">
            <h1 style="padding-top:10%">접속중인 유저가 없습니다.</h1>
        </div>
        <div v-else>
            <div class="random-cards-wrapper">
                <div class="each-card" v-for="user in randomUsersPaginated" :key="user.id">
                    <random-result-view-detail :user="user" />
                </div>   
            </div>
            <div>
                <div class="pagination" style="margin:0 auto; margin-top:10px;">
                    <el-pagination
                        background
                        layout="prev, pager, next"
                        :page-size=pageSize
                        :total="randomUsers.length"
                        v-model:current-page="currentPage">
                    </el-pagination>
                </div>
            </div>
        </div>
        <el-button class="back" @click="back">뒤로 가기</el-button>
    </div>
</template>

<script>

import {  reactive } from 'vue'
import { mapGetters, useStore } from 'vuex'
import RandomResultViewDetail from './RandomResultViewDetail.vue'
import router from '../../../router'
export default {

    data() {
    return {
      // for pagination
      pageSize: 8, // 한 페이지에 나오는 게시물 수
      currentPage: 1, // 현재 페이지(v-model 적용)
    }
  },
  computed:{
      randomUsersPaginated() {
      var start = 0 + (this.currentPage-1) * this.pageSize
      var end = this.currentPage * this.pageSize
      return this.randomUsers.slice(start, end) //기본값 0~5번
    },
     ...mapGetters({
      randomUsers: 'getRandomUser'
    })
  },
    components:{
        RandomResultViewDetail
    },

    setup() {
        
        const store=useStore()
         const state = reactive({
           randomUsers:store.getters.getRandomUser
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
.result-view {
    width: 100%;
    background: #f0f2f5;
    height: 60vw;
}
.random-cards-wrapper {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
}
.each-card {
    margin: 1%;
}
.back {
    margin: 5%;
    width: 20%;
    border: 2px solid #323545;
}
.back:hover {
    border: 2px solid #ff4e7e
}
</style>