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
  name: 'RandomResultViewDetail.vue',
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
    // const requestLiveMessage=function(memberid){
    //     console.log(store.getters.getUserInfo.memberid)
    //     console.log(memberid)
    //     store.dispatch("requestLiveMessage",{from:store.getters.getUserInfo.memberid,to:memberid})
    // }
    const like=function(memberid){//팔로우~
        console.log("팔로우~~")
        console.log(memberid)
        store.dispatch("like",memberid)//팔로우를 당하는 사람
    }
    const unlike = function(memberid) {
        console.log("언팔")
        store.dispatch('unlike', memberid)
    }
    const liveOn=function(id){//화상 요청~
        console.log("들억자ㅣ나")
        router.push({name: 'VideoChat',query:{opp:id}})
    }
    return {
      state, back, like, unlike, liveOn
    }
  },
}
</script>

<style>
.result-card {
    background: #fff;
    width: 300px;
    border-radius: 10px;
    padding: 2%;
}
.result-card-main-info {
    display: flex;
    justify-content: center;
    font-weight: bold;
    font-size: 20px;
}
.result-card-img {
    margin: 5% auto;
    width: 250px;
    height: 250px;
    border-radius: 50%;
    overflow: hidden;
}
.result-card-body {
    text-align: left;
    margin: 5%;
}
.body-content {
/* 글자 수 제한을 넘어가면 ... 처리, 좌측 정렬 */
overflow: hidden;
text-overflow: ellipsis;
display: -webkit-box;
-webkit-line-clamp: 1;
-webkit-box-orient: vertical;
word-wrap:break-word;
line-height: 1.2em;
}

/* 카드 버튼 */
.card-button {
    display: block;
}
.like_btn {
    color: #ff4e7e;
}
.like_btn:hover {
    cursor: pointer;
}
.card-matcing-btn{
    color:#FF427E;
    border: 3px solid #ff427e;
    padding: 5px 10px;
    border-radius: 5px;
    font-weight: bold;
    text-align: center;
    width: 50%;
    margin: 0 auto;
}
.card-matcing-btn:hover{
    color:white ;
    background-color: #FF427E;
    cursor: pointer;
}
</style>