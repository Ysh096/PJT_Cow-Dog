<template>
  <div class="result-card">
    <div class="result-card-img">
      <img :src="this.user.file_path" class="profiile_image">
    </div>
    <div class="result-card-main-info">
        <div>{{this.user.memberid}}
            (<span v-if="genderIcon"><i class="fas fa-mars"></i></span>
            <span v-else><i class="fas fa-venus"></i></span>
            , {{this.user.memberinfo.age}}세)</div>
            <div v-if="followedOrNot" class="like_btn" @click="unlike(this.user.id)"><i class="fas fa-heart"></i></div>    
            <div v-else class="like_btn" @click="like(this.user.memberid)"><i class="far fa-heart"></i></div>    
    </div>
    <div class="result-card-body">
            <div class="body-content">주량: {{this.user.memberinfo.alcohol.replace('[','').replace(']','')}}</div>
            <div class="body-content">종교: {{this.user.memberinfo.religion.replace('[','').replace(']','')}}</div>
            <div class="body-content">성격: {{this.user.memberinfo.personality.replace('[','').replace(']','')}}</div>
            <div class="body-content">취미: {{this.user.memberinfo.hobby.replace('[','').replace(']','')}}</div>
            <div class="body-content">사는곳: {{userRegion}}</div>
            <div class="body-content">거리: {{this.user.dist}}km 이내에 있습니다.</div>
    </div>
    <div class="card-footer">
        <div class="card-button">
            <div class="card-matcing-btn" @click="liveOn(this.user.memberid)"><i class="fas fa-video"></i> 화상챗 요청</div>
        </div>
    </div>                    
  </div>   
</template>

<script>
import { reactive } from '@vue/reactivity'
import router from '../../../router'
import { useStore, mapGetters } from 'vuex'

export default {  
  name: 'DistanceResultViewDetail.vue',
  props: {
      user: Object,
  },
  computed: {
    userRegion() {
        var userRegionSplit = this.user.memberinfo.address.split(' ')
        var userRegion = userRegionSplit[0] + ' ' + userRegionSplit[1]
        return userRegion
    },
    genderIcon() {
        if(this.user.memberinfo.gender == "남자"){
            return true
        } else {
            return false
        }
    },
    followedOrNot() {
        var flag = false;
        this.followed.forEach(element => {
            console.log(element)
            if(element.member_id==this.user.id) {
                console.log("일치")
                flag = true
            }
        });
        if(flag) {
            return true
        } else {
            return false
        }
    },
    ...mapGetters({
    followed: 'getUsersIFollowed'
    })
  },
  setup() {
    const store=useStore()
    const state = reactive({
      
    })
    const back=function(){
        router.push("/main")
    }
    const like=function(memberid){//팔로우~
        console.log("팔로우~~")
        console.log(memberid)
        store.dispatch("like",memberid)//팔로우를 당하는 사람
    }
    // const requestLiveMessage=function(memberid){
    //     console.log(store.getters.getUserInfo.memberid)
    //     console.log(memberid)
    //     store.dispatch("requestLiveMessage",{from:store.getters.getUserInfo.memberid,to:memberid})
    // }

    const unlike = function(memberid) {
        console.log("언팔")
        store.dispatch('unlike', memberid)
    }
    const liveOn=function(id){//화상 요청~
        console.log("들억자ㅣ나")
        router.push({name: 'VideoChat',query:{matchType: '1',opp:id}})
    }
    return { state,back,like,liveOn, unlike }
  },
  methods: {
      
  },
  }
</script>

<style>
</style>