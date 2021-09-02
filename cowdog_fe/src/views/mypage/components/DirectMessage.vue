<template>
  <div class="home">
    <h2>DM</h2>
      <div class="nickname">
        nickname : {{myinfo.nickname}}
      </div>
    <input type="text" v-model="id" placeholder="TOPIC" />
    <input type="text" v-model="nickname" placeholder={{myinfo.nickname}} />
    <br />

    <input type="button" @click="enterChatRoom()" value="ENTER LIST" />
    <input type="button" @click="enterRoom(nickname)" value="ENTER ROOM" />
    <!-- <input type="button" @click="createRoom()" value="CREATE ROOM" /> -->
    <input type="button" @click="create_room" value="CREATE ROOM" />
    <br />

  </div>
</template>

<script>
import { useStore } from 'vuex'
import axios from 'axios'
// import Stomp from 'webstomp-client'
// import SockJS from 'sockjs-client'
export default {
  //   data: () => {
  //   return {
  //     id: 0,
  //     nickname: ''
  //   }
  // },
  
  setup(){
    const store = useStore()
    const myinfo = store.state.myinfo
    const create_room = function(){
          console.log("새 채팅룸 생성")
          store.dispatch('createRoom', {user1:"test123", user2:"test1234"})
          console.log("Mypage에서의 콘솔로그 : "+store.chatRoomId)
    }

    return { myinfo,
      getMyInfo:()=>{
        store.dispatch('getMyInfo')
      },
      GetFollowUsers:()=>{
        store.dispatch("getFollowUsers")
      },
      create_room,
    }
  },
  methods:{
    enterChatRoom(state){
       axios({
        method:'post',
        url:'/chat/login',
        baseURL:'http://localhost:8080/',
        headers:{
          Authorization:"Bearer "+state.accessToken,
          'content-type': 'application/json'},
        data : {"id":this.id,"nickname":this.nickname}
      }).then(res=>{
        this.nickname = res.data
          this.$router.push({name:"Roomlist",params:{id : this.id, nickname: this.nickname}})
      }, err=>{
        alert("id, nickname error")
      console.log(err)
      })
    },

    enterRoom(id){
      this.$router.push({name:"Room",params:{roomid : id, nickname:this.nickname, id : this.id}})
    },

    //actions 이용하기 전
    // createRoom(state){
    //   axios({
    //   method:'post',
    //   // "/mem/mypage/?userId="+state.userId
    //   // url:'http://localhost:8080/chat/newChat/?receiver=test1234?sender=test123',
    //   url:'/chat/newChat/',
    //   // baseURL:'http://localhost:8080/',
    //   headers:{
    //   Authorization:"Bearer "+state.accessToken,
    //   'content-type': 'application/json',
    //   },
    //   // data : {"id":this.id,"title":this.title, "masterId":this.id}
    //   // data : {"receiver":state.myinfo.nickname, "sender":"test1234"}
    //   data : {user1:"test123", user2:"test1234"}
    //   }).then(res=>{
    //     console.log("방 생성 성공"+res.data)
    //     // this.$router.push({name:"Room",params:{roomid : res.data, nickname:this.nickname, id : this.id}})
    //   }, err=>{
    //     console.log(err)
    //     this.$router.push({ name: "Home" });
    //   })
    // }

  }
};
</script>
