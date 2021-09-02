<template>
  <div class="chatRoom">
    <h1>{{title}}</h1>
        {{myinfo.nickname}} <br/>
        {{chatRoomId}} 번 방 <br/>
    <hr />
    
    <div v-for="(m, idx) in msg" :key="idx">
      <div v-bind:class="m.style">
      <h5 style="margin:3px">
        {{myinfo.nickname}}
        </h5>
      {{m.content}}
      </div>
    </div>
    <hr />
    <input type="text" v-model="content" placeholder="보낼 메세지" size="100" />
    <button @click="sendMessage()">SEND</button>
  </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { useStore } from 'vuex'
// import axios from 'axios'


export default {
  // name: "chatRoom",
  //   data: () => {
  //   return {
  //     id: -1,
  //     nickname: '',
  //     title:'',
  //     roomid:-1,
  //     idx:0,
  //     msg:[],
  //     content:"",
  //     stompClient:null
  //   }
  // },
  setup(){
    const store = useStore()
    const myinfo = store.state.myinfo
    const chatRoomId = store.state.chatRoomId
    store.dispatch('getMyInfo')

    //let socket = new SockJS('http://192.168.0.103:8092/cowdog/chat')
    // let socket = new SockJS('http://172.30.1.48:8092/chat')
    let socket = new SockJS('http://localhost:8080/chat')
  
    // this.stompClient = Stomp.over(socket)
    // this.stompClient.connect({}, frame=>{
    const stompClient = Stomp.over(socket)
    stompClient.connect({}, frame=>{
      console.log("success", frame)
      stompClient.subscribe("/sub/"+chatRoomId, res=>{
        let jsonBody = JSON.parse(res.body)
             let m={
            // 'senderNickname':jsonBody.senderNickname,
            // 'content': jsonBody.content,
            // 'style': jsonBody.senderId == this.id ? 'myMsg':'otherMsg'
            'senderNickname':"김남자",
            'content': "테스트 메세지 초기값",
            'style': jsonBody.senderId == this.id ? 'myMsg':'otherMsg'
          }
          this.msg.push(m)
      })
    }, err=>{
      console.log("fail", err)
    })

    return { myinfo, chatRoomId,
      getMyInfo:()=>{
        store.dispatch('getMyInfo')
      },
      GetFollowUsers:()=>{
        store.dispatch("getFollowUsers")
      },
      stompClient,
    }
  },
    // this.id = this.$route.params.id
    // this.roomid = this.$route.params.roomid
    // this.roomid = "testroom";
    // this.nickname = store.nickname;

    //  if (this.id == -1 || typeof this.id === "undefined") {
    //    this.$router.push({ name: "Home" });
    // }
    //  if (this.roomid == -1 || typeof this.roomid === "undefined") {
    //    this.$router.push({ name: "Home" });
    // }
  // 방 제목 가져오기
    // axios({
    //     method:'get',
    //     url:'/chat/sub/'+this.roomid,
    //     baseURL:'http://localhost:8080/'
    //   }).then(res=>{
    //     this.title = res.data
    //   }, err=>{
    //     console.log(err)
    //     this.$router.push({ name: "Home" });
    //   })

  // 채팅방 내용 불러오기
      //  axios({
      //    method:'get',
      //   url:'/chat/room/message/'+this.roomid+"?page="+this.idx,
      //   baseURL:'http://localhost:8080/'
      // }).then(res=>{
      //   this.msg = []
      //   for(let i=res.data.length-1; i>-1; i--){
      //     let m={
      //       'senderNickname':res.data[i].senderNickname,
      //       'content':res.data[i].content,
      //       'style': res.data[i].senderId == this.id ? 'myMsg':'otherMsg'
      //     }
      //     this.msg.push(m)
      //   }
      // }, err=>{
      //   console.log(err)
      //   alert("error : 새로고침하세요")
      // })

    // socket 연결
    //  let socket = new SockJS('http://localhost:8080/chat')


  methods:{
    sendMessage(){
      if(this.stompClient!=null) {
          let chatMessage = {
            'chatroomId' : "1",
            'sender': "skk7541",
            'receiver': "kkk7541",
            'message': "테스트 메세지"
          }
          this.stompClient.send("/pub/chat/send", JSON.stringify(chatMessage),{})

      }
    }
  }
  
};
</script>
<style scoped>
.myMsg{
text-align: right;
color : gray;
}
.otherMsg{
  text-align: left;
}
</style>
