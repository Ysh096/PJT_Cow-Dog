<template>
<div class="follow-view">

    <div v-if="eachOtherFollowUsers[0]==null" class="no_user">
        <h1>맞팔로우한 사람이 없습니다.</h1>
    </div>
    <div v-else class="follow-view">
        <div class="random-cards-wrapper">
            <div class="each-card" v-for="user in eachOtherFollowUsersPaginated" :key="user.id">
                <eachother-detail :user="user" />
            </div>   
        </div>
        <div class="pagination" style="margin:0 auto; margin-top:10px">
        <el-pagination
        background
        layout="prev, pager, next"
        :page-size=pageSize
        :total="eachOtherFollowUsers.length"
        v-model:current-page="currentPage">
        </el-pagination>
        </div>
    </div>

 
</div>
</template>

<script>
import { reactive } from "@vue/reactivity"
import { onMounted } from "@vue/runtime-core"
import { mapGetters, useStore } from "vuex"
import router from "../../../router"
import EachotherDetail from "./EachotherDetail.vue"

export default ({

     data() {
        return {
            // for pagination
            pageSize: 4, // 한 페이지에 나오는 게시물 수
            currentPage: 1, // 현재 페이지(v-model 적용)
        }
    },
    methods: {
       
    },
   
    computed: {


        eachOtherFollowUsersPaginated() {
            var start = 0 + (this.currentPage-1) * this.pageSize
            var end = this.currentPage * this.pageSize
            return this.eachOtherFollowUsers.slice(start, end) //기본값 0~5번
        },

        ...mapGetters({
            eachOtherFollowUsers: 'getEachOtherFollowUsers'
        })
    },
    components:{
        EachotherDetail
    },

    setup() {
        
        const store=useStore()
        const state = reactive({
           eachOtherFollowUsers:store.getters.getEachOtherFollowUsers
        })
        const back=function(){
            router.push("/main")
        }
        onMounted(() => {
            store.dispatch("getFollowEachOther")
            state.eachOtherFollowUsers=store.getters.getEachOtherFollowUsers
        })
        return{
            state,back
            
        }
    }
})
</script>



<style>
.follow-view {
    position: relative;
}
</style>