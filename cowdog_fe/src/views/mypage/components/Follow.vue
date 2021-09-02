<template>
    <div class="follow-view">
        <div v-if="followUsers[0]==null" class="no_user">
            <h1>나를 팔로우 하는 사람이 없습니다.</h1>
        </div>
        <div v-else>
            <div class="random-cards-wrapper">
                <div class="each-card" v-for="user in followUsersPaginated" :key="user.id">
                    <follow-detail :user="user" />
                </div>   
            </div>
            <div class="pagination" style="margin:0 auto; margin-top:10px">
                <el-pagination
                background
                layout="prev, pager, next"
                :page-size=pageSize
                :total="followUsers.length"
                v-model:current-page="currentPage">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive } from "@vue/reactivity"
// import { onMounted } from "@vue/runtime-core"
import { mapGetters } from "vuex"
import router from "../../../router"
import FollowDetail from "./FollowDetail.vue"

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


        followUsersPaginated() {
            var start = 0 + (this.currentPage-1) * this.pageSize
            var end = this.currentPage * this.pageSize
            return this.followUsers.slice(start, end) //기본값 0~5번
        },
        ...mapGetters({
            followUsers: 'getFollowUsers'
        })
    },
    components:{
        FollowDetail
    },

    setup() {
        
        // const store=useStore()
        const state = reactive({
        //    followUsers:store.getters.getFollowUsers
        })
        const back=function(){
            router.push("/main")
        }
        // onMounted(() => {
        //     store.dispatch("getFollowUsers")
        //     state.followUsers=store.getters.getFollowUsers
        // })
        return{
            state, back
        }
    }
})
</script>



<style>
.follow-view {
    width: 100%;
    height: 700px;
    background: #f0f2f5;
}
</style>