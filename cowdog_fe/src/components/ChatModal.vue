<template>
<div class="chat-modal">
  <el-dialog
    title="화상채팅 요청"
    v-model="centerDialogVisible"
    width="30%"
    destroy-on-close
    center>
    <!-- <span>{{ file_path }}</span>
    <span>{{ memberinfo }}</span> -->
    <span>{{ memberid }}</span>
    <!-- <span>{{ dist }}</span> -->
    <div>
      <strong>Extra content (Not rendered)</strong>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closeDialog">Cancel</el-button>
        <el-button type="primary" @click="toVideoChat">Confirm</el-button>
      </span>
    </template>

  </el-dialog>
</div>
</template>

<script>
import router from '../router';
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://i5b104.p.ssafy.io:9090";
const OPENVIDU_SERVER_SECRET = "cowdog123";
const instance = axios.create({
  baseURL: OPENVIDU_SERVER_URL,
	headers: {'Content-Type': 'application/json'},
})

  export default {

    data() {
      return {
        file_path: '',
        memberinfo: '',
        memberid: '',
        dist: '',
      }
    },
    methods: {
      closeDialog() {
        this.$store.state.centerDialogVisible = false
        // router.push({name:'VideoChat'})
        // this.emitter.emit("agree-meeting", false)
      },
      getData () {
              instance
                .get(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, {
                  auth: {
                    username: 'OPENVIDUAPP',
                    password: OPENVIDU_SERVER_SECRET,
                  },
                })
                .then(response => {
                  let content = response.data.content;
                  console.log("CONTENT입니다.")
                  console.log(content);
                  console.log("myUserName입니다.")
                  console.log(this.myUserName)
                  
                  for(let i=0;i<content.length;i++){
                    if(content[i].id==this.myUserName){ // 내 아이디가 상대방이 요청한 대상과 같으면
                      console.log(content[i].connections.content[0].clientData);
                      let userid = content[i].connections.content[0].clientData.replace(/"/g,"").replace(":","").replace("clientData","").replace("{","").replace("}",""); // 화상채팅 요청한 사람의 아이디
                      console.log(userid);
                      axios.get("http://localhost:8080/cowdog/mem/getOppInfo/?userId="+userid,{
                        headers:{
                          Authorization:"Bearer "+ this.getUserToken
                        }
                      })
                      .then(res=>{
                        console.log(res.data);
                        this.file_path=res.data.file_path;
                        this.memberinfo = res.data.memberinfo;
                        this.memberid = res.data.memberid;
                        this.dist = res.data.dist;
                      })
                    }
                  }
                })
      },
      toVideoChat() {
        router.push({name:'VideoChat'})
        // this.emitter.emit("agree-meeting", true)
      }

    
    },

    computed: {
      centerDialogVisible() {
        return this.$store.state.centerDialogVisible
      },
      myUserName() {
        return this.$store.state.myinfo.memberid
      },
      getUserToken() {
        return this.$store.state.accessToken
      }
    },
    mounted() {
      this.getData();
    }
  };
</script>

<style>
  .chat-modal .el-dialog__close {
    display: none;
  }
</style>