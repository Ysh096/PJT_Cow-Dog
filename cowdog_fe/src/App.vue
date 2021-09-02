<template>
  <div>
    <el-menu
      :router="true"
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      text-color="#323545"
      active-text-color="#ff4e7e"
      >
      <div v-if="!this.$store.state.userId" style="display:flex; justify-content: space-between">
        <el-menu-item index="/login" class="navMenu-logo"><img class="nav-logo" :src="require('@/assets/images/cowanddog.png')" alt="logo" style="max-width: 100%; height:auto;"></el-menu-item>
        <div style="display:flex">
          <el-menu-item index="/login" class="navMenu">로그인</el-menu-item>
          <el-menu-item index="/register" class="navMenu">회원가입</el-menu-item>
        </div>
      </div>
      <div v-else style="display:flex; justify-content:space-between">
        <div style="display:flex" v-if="!matchStatus">
          <el-menu-item index="/main" class="navMenu-logo"><img class="nav-logo" :src="require('@/assets/images/cowanddog.png')" alt="logo" style="max-width: 100%; height:auto;"></el-menu-item>
          <el-menu-item index="/main" class="navMenu">매칭</el-menu-item>
          <el-menu-item index="/notices" class="navMenu" @click="getNoticesForUser">공지사항</el-menu-item>
          <el-menu-item index="###"  class="navMenu" @click="getArticles">게시판</el-menu-item>
          <el-menu-item v-if="this.$store.state.myinfo.manager" index="####" class="navMenu" @click="getNotices">관리자</el-menu-item> <!-- 배포 전 숨기기 -->
        </div>
        <div style="display:flex; margin-right: 3%" v-if="!matchStatus">
          <el-menu-item v-if="notifications.length==0"  class="navMenu" @click="state.dialogVisible = true"><i class="fas fa-envelope-open"></i></el-menu-item>
          <el-menu-item v-else class="navMenu" @click="state.dialogVisible = true"><i class="fas fa-envelope"></i> {{notifications.length}}</el-menu-item>
          <el-menu-item index="/mypage" class="navMenu" @click="getMyInfo">마이페이지</el-menu-item>
          <el-menu-item index="#" class="navMenu" @click="logout()">로그아웃</el-menu-item>
        </div>
      </div>
    </el-menu>
          <el-dialog
          class="notice-detail"
          title="알림"
          v-model="state.dialogVisible"
          width="70%"
          >
            <div  v-for="user in notifications" :key="user.id">
                <div style="display:flex; justify-content:space-between; margin:1%">
                  <p>{{user.mem.memberid}} 님이 팔로우를 걸었습니다.</p>
                  <el-button @click="checkNotification(user.id)">확인</el-button>
                </div>
            </div>   
            <br>
            <br>
            
            <br>


        </el-dialog>
  </div>

  
  
  <router-view/>
</template>

<script>
import { reactive } from '@vue/reactivity';
import { mapGetters, useStore } from 'vuex'
export default ({

  data() {
    return {
      activeIndex: '1',
      dialogVisible: false
    };
     
  },

 
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  },
  computed: {
    ...mapGetters({
      notifications: 'getNotifications', // 알림
      matchStatus: 'getMatchStatus'
    }),
  },
  setup() {
    const state=reactive({
      dialogVisible:false,
    })
    const store = useStore()

    // watch(store.getters.getMatchStatus,()=>{
    //   state.matchStatus= store.getters.getMatchStatus
    // })
    const logout =function(){
      console.log("로그아웃")
      store.dispatch("userLogout",{id:store.getters.getUserId})
    }
     const checkNotification=function(id){
      console.log(id)
      console.log("알림 확인")
      store.dispatch("checkNotification",id)
      // state.dialogVisible=false
      setTimeout(() => {
        store.dispatch("getNotification",store.getters.getUserId)
      }, 100)
      }
    return {
      logout,
      getNotices: () => {
        store.dispatch('getNotices') // actions/getNotices에 dispatch
      },
      getNoticesForUser: () => {
        store.dispatch('getNoticesForUser')
      },
      getMyInfo:()=>{
        store.dispatch('getMyInfo')
        store.dispatch('getFollowEachOther')
        store.dispatch("getFollowUsers")
        console.log("실행 완료입니다")
      },
      GetFollowUsers:()=>{
          store.dispatch("getFollowUsers")
      },
      getArticles: () => {
        store.dispatch('getArticles',{page:0, size:7})
      },
     
     state,
     checkNotification
     
    }
  },
})
</script>


<style>
#app {
  font-family: SeoulNamsanM;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #323545;
}

pre {
  font-size: 16px;
  font-family: SeoulNamsanM;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #323545;
  display: inline;
  margin-top: 0;
  margin-bottom: 0;
}
span {
  font-family: SeoulNamsanM;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #323545;
  display: inline;
  margin-top: 0;
  margin-bottom: 0;
}

body {
  margin: 0;
  padding: 0;
}
.navMenu-logo {
  text-decoration: none;
  width: 100px;
  border-bottom-color: #ffffff !important;
}
.navMenu {
  text-decoration: none;
  font-weight: bold;
  color: inherit;
}
.el-menu.el-menu--horizontal {
    border-bottom: 0px;
}
.el-menu-item:hover {
  background: #f0f2f5;
}
.el-menu-item:focus {
  background: #fff;
}
html::-webkit-scrollbar {
  width: 10px;
}
html::-webkit-scrollbar-thumb {
  width: 5px;
  background-color: #ffe7e7;
  border-radius: 5px;
}
html::-webkit-scrollbar-track {
  background-color: #ffffff;
}
@font-face {
    font-family: 'SeoulNamsanM';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/SeoulNamsanM.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
</style>
