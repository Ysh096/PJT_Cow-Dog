<template>
<div class="mypage">
  <div class="upward">
    <div class="profile-img-box">
      <img class="profile" :src="myinfo.file_path"><br>
    </div>
    <div class="profile-info">
      <div class="nickname">
        {{myinfo.nickname}}
      </div>
      <div class="email-address">
        {{myinfo.email}}
      </div>
      <div class="follow" style="display:flex">
        <div class="followers">
          팔로워 {{followers.length}}
        </div>
        <div class="followings">
          팔로잉 {{followings.length}}
        </div>
        <div class="follow-each-other">
          맞팔 {{eachOtherFollowUsers.length}}
        </div>

      </div>
      <div class="hashtag" style="display:flex">
        <div class="personality-item">
          # {{myinfo.memberinfo.personality.replace('[','').replace(']','').split(',')[0]}}
        </div>
        <div class="personality-item">
          #{{myinfo.memberinfo.personality.replace('[','').replace(']','').split(',')[1]}}
        </div>
        <div class="personality-item">
          #{{myinfo.memberinfo.personality.replace('[','').replace(']','').split(',')[2]}}
        </div>  
      </div>
      <div class="hashtag" style="display:flex">
        <div class="personality-item">
          # {{myinfo.memberinfo.hobby.replace('[','').replace(']','').split(',')[0]}}
        </div>
        <div class="personality-item">
          #{{myinfo.memberinfo.hobby.replace('[','').replace(']','').split(',')[1]}}
        </div>
        <div class="personality-item">
          #{{myinfo.memberinfo.hobby.replace('[','').replace(']','').split(',')[2]}}
        </div> 
      </div>
    </div>
  </div>
  <!-- tabs -->
  <div>
    <el-tabs v-model="activeName">
      <!-- <el-tab-pane label="ChatRoom" name="chatroom"><ChatRoom /></el-tab-pane> -->
      <!-- <el-tab-pane label="DM" name="directmessage"><DirectMessage /></el-tab-pane> -->
      <el-tab-pane label="상세정보" name="myinfo"><my-info /></el-tab-pane>
      <el-tab-pane label="팔로워" name="follow"><follow /></el-tab-pane>
      <el-tab-pane label="맞팔로우" name="eachother"><each-other /></el-tab-pane>
      <el-tab-pane label="계정설정" name="account-setting"><setting /></el-tab-pane>
    </el-tabs>
  </div>
</div>
</template>

<script>
import { useStore } from 'vuex'
// import DirectMessage from "./components/DirectMessage.vue"
// import ChatRoom from "./components/ChatRoom.vue"
import MyInfo from "./components/MyInfo.vue"
import Follow from "./components/Follow.vue"
import EachOther from "./components/Eachother.vue"
import Setting from "./components/Setting.vue"
import { mapGetters } from 'vuex'
import { onMounted } from '@vue/runtime-core'
import axios from 'axios'
import router from '../../router'
import Swal from 'sweetalert2';
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
const OPENVIDU_SERVER_URL = "https://i5b104.p.ssafy.io:9090";
const OPENVIDU_SERVER_SECRET = "cowdog123";
export default {
  name: 'Mypage',
  components: {
    // ChatRoom,
    // DirectMessage,
    MyInfo,
    Follow,
    EachOther,
    Setting
  },

  data() {
    return {
      // activeName: 'myinfo'
      activeName: 'myinfo'
    }
  },
  computed: {
    ...mapGetters({
      followings: 'getUsersIFollowed', // 내가 팔로우하는 사람들
      followers: 'getFollowUsers', // 나를 팔로우하는 사람들
      eachOtherFollowUsers: 'getEachOtherFollowUsers' // 맞팔한 사람들
    })
  },
  setup() {
    const store = useStore()
    const myinfo = store.state.myinfo
    store.dispatch('AmIFollowed') // 내가 팔로우한 유저 usersIFollowed에 저장
    store.dispatch("getNotification",store.getters.getUserId)//알림 뭐 왔나 백엔드에서 가져오는거
    onMounted(() => {
          setTimeout(() => {  
            axios.get(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, {
              auth: {
                username: 'OPENVIDUAPP',
                password: OPENVIDU_SERVER_SECRET,
              },
            }).then(response=>{
              let content = response.data.content;
              
              for(let i=0;i<content.length;i++){
                if(content[i].id==store.getters.getUserInfo.memberid){
                  Swal.fire({
                      position: 'middle',
                      icon: 'question',
                      title: '누군가가 화상회의를 걸어왔어요!',
                      showConfirmButton: false,
                      timer: 1500
                  }).then(response=>{
                    router.push({name:'VideoChat'});
                    console.log(response);
                    // store.state.centerDialogVisible = true
                    
                  })
                  return;                            
                }
              }
              
            })
            }, 1500);
        })
    return { myinfo,
      getMyInfo:()=>{
        store.dispatch('getMyInfo')
      },
    }
  },


}
</script>

<style>
.upward {
  display: flex;
  justify-content: center;
}
.profile-img-box {  
  margin: 5% auto;
  margin-left: 20%;
  width: 250px;
  height: 250px;
  border-radius: 70%;
  overflow: hidden;
}

.el-tabs__nav {
  float: none;
  margin: 0 auto;
  display: inline-block
}

.el-tabs__header {
  margin: 0;
}
.profile-info {
  text-align: left;
  width: 50%;
  margin: 5% 0;
}

.profile-info .nickname {
  margin: 1rem;
  font-weight: bold;
  font-size: 2rem;
}
.profile-info .email-address {
  margin: 1rem;
  font-weight: bold;
  font-size: 1.2rem;
}
.profile-info .followers {
  margin: 0 0.5rem 0 1rem;
  font-weight: bold;
}
.profile-info .followings {
  margin: 0 0.5rem;
  font-weight: bold;
}
.profile-info .follow-each-other {
  margin: 0 0.5rem;
  font-weight: bold;
}
.hashtag {
  margin: 1rem;
}
.personality-item {
  border: 1px solid;
  font-weight: bold;
  margin: 3px;
  padding: 2px;
  border-radius: 7px;
}
.el-tabs__item {
  font-weight: bold;
}

@media (max-width: 860px) {
  .upward {
    display: block;
  }
  .profile-img-box {
    margin: 5% auto;
    width: 250px;
    height: 250px;
    border-radius: 70%;
    overflow: hidden;
  }
  .profile-info {
    display: block;
    text-align: center;
    width: 50%;
    margin: 5% auto;
  }
  .follow {
    justify-content: center;
  }
  .hashtag {
    justify-content: center;
  }
}
</style>