<template>
<div class="main-top">
  <h1 class="main-intro">다양한 매칭을 통해 인연을 만나세요</h1>
  <div style="display:flex; justify-content:center; margin:3%">
    <i class="fas fa-map-marked-alt match-icon"></i>
    <i class="fas fa-random match-icon"></i>
    <i class="fas fa-thumbs-up match-icon"></i>
  </div>
  <el-tabs class="main-tabs" v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="사용법" name="사용법"><explanation /></el-tab-pane>
    <el-tab-pane label="지역매칭" name="지역매칭"><distance-matching /></el-tab-pane>
    <el-tab-pane label="랜덤매칭" name="랜덤매칭"><random-matching /></el-tab-pane>
    <el-tab-pane label="맞춤매칭" name="맞춤매칭"><recom-matching /></el-tab-pane>
    <ChatModal />
  </el-tabs>
</div>
</template>

<script>
import { reactive, onMounted} from 'vue'
import RandomMatching from './components/RandomMatching.vue'
import DistanceMatching from './components/DistanceMatching.vue'
import RecomMatching from './components/RecomMatching.vue'
import Explanation from './components/Explanation.vue'
import { useStore } from 'vuex'
import router from '../../router'
// import router from '../../router';
import axios from 'axios';
import Swal from 'sweetalert2';
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
const OPENVIDU_SERVER_URL = "https://i5b104.p.ssafy.io:9090";
const OPENVIDU_SERVER_SECRET = "cowdog123";


export default{
    data() {
      return {
        activeName: "사용법"
      };
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      }
    },
    components : { RandomMatching, DistanceMatching, RecomMatching, Explanation },

    setup() {
        const store=useStore()
        const state = reactive({
            random:true,
            recom:Boolean,
            distance:Boolean,
        })
        store.dispatch('AmIFollowed') // 내가 팔로우한 유저 usersIFollowed에 저장
        store.dispatch("getNotification",store.getters.getUserId)//알림 뭐 왔나 백엔드에서 가져오는거
        //store.commit("SET_MATCHSTATUS",false);
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
        
        return{
            state
        }

    },
}
</script>
<style>
.main-top {
    /* background: linear-gradient(90deg, rgba(255,209,221,1) 0%, rgba(228,230,241,1) 100%); */
}
.main-intro {
    margin-top: 0;
    padding-top: 5%;
}
.el-tabs__item:hover {
    color: #FF427E;
}
.el-tabs__item.is-active {
    color: #FF427E;
}
.el-tabs__active-bar {
    background-color: #ff4e7e;
}
.matching_btn{
    color:#FF427E ;
    border-color: #FF427E;
}
.matching_btn:hover{
    color:white ;
    background-color: #FF427E;
}
.matching_btn:focus{
    color: white;
    background-color: #ff427e;
}
.match-icon {
  font-size: 100px;
  margin: 5%;
  color: #323545;
  transform: scale(1);
  -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1);
  transition: all 0.3s ease-in-out;
}
.match-icon:hover {
  color: #FF4e7E;
  transform: scale(1.2);
  -webkit-transform: scale(1.2);
  -moz-transform: scale(1.2);
  -ms-transform: scale(1.2);
  -o-transform: scale(1.2);
  transition: all 0.3s ease-in-out;
}

</style>