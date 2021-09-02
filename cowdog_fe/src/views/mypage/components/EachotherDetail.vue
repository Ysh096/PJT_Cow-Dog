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
            <div class="body-content">MBTI: {{this.user.memberinfo.mymbti}}</div>
        </div>
        <div class="card-footer">
            <div class="card-button">
                <div class="card-matcing-btn" @click="liveOn(this.user.memberid)"><i class="fas fa-video"></i> 화상챗 요청</div>
                <div class="card-matcing-btn" @click="create_room(this.user.memberid);openDialog();"><i class="fas fa-video"></i> DM </div>
            </div>
        </div>  
    </div>

    <!-- DM 모달 창 -->
    <div v-if="this.dialogVisible" class="DM-modal-box">
        <div class="DM-modal-header">
        <!-- 상대방 닉네임만 나오면 됨, chatRommId는 방이 맞는지 확인하기 위한 용도(나중에 삭제) -->
        <div class="DM-header-name">{{ this.user.memberid }} {{this.$store.state.chatRoomId}}</div>
        <div class="DM-close-btn" @click="DMClose">X</div>
        </div>
        <div class="DM-modal-content">
            <!-- content(대화 내용)
            get Messages 한 후 state에 모든 데이터 저장하여 보여줄 것 -->
            <div v-for="(message, idx) in messageEx" :key="idx">
                <div v-if="message.member_id == this.user.id" class="chat-box">
                        <div class="chat-block">
                            <div class="my-chat-time">{{ message.time.slice(11, 16) }}</div>
                            <div class="my-chat">{{ message.message }}</div>
                        </div>
                    </div>
                <div v-else class="opp-chat-box">
                        <div class="opp-block">
                            <div class="opp-chat">{{ message.message }}</div>
                            <div class="opp-chat-time">{{ message.time.slice(11, 16) }}</div>
                        </div>
                </div>
            </div>
        </div>
        <div class="DM-modal-footer">
            <input class="DM-input" v-model="this.content" @keyup.enter="sendMessage()" placeholder="메세지를 입력하세요" />
            <div class="DM-send-btn" @click="sendMessage()">전송</div>
        </div>
    </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import router from '../../../router'
import { useStore, mapGetters } from 'vuex'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
    export default {  
        name: 'FollowDetail.vue',
        props: {
            user: Object,
        },
        data(){
            return{
                content:'',
                dialogVisible:false
            }

        },

        methods:{
            sendMessage(){
            if(this.stompClient!=null) {
                let chatMessage = {
                    'chatroomId' : this.$store.state.chatRoomId,
                    'sender': this.$store.state.myinfo.memberid,
                    'receiver': this.user.memberid,
                    'message': this.content
                }
               
                this.stompClient.send("/pub/chat/send", JSON.stringify(chatMessage),{})
                 this.content=''
            }
            },
            
            DMClose () {
                this.dialogVisible = false;
            },
            openDialog(){
                 setTimeout(() => {
                    this.dialogVisible = true;
                }, 1000)
            },
            create_room(user2){ //user2 = 상대방
                // console.log(myinfo.memberid)
                console.log("새 채팅룸 생성")
                this.$store.dispatch('createRoom', {user1:this.$store.state.myinfo.memberid, user2:user2})
                // console.log("Mypage에서의 콘솔로그 : "+store.chatRoomId)
                    .then(() => {
                        console.log("성공!")
                        // messages 불러오기
                        // 코드 작성(메세지 불러와서 store.state에 저장하여 사용)
                        // 불러온 메세지에서 message_id는 제외하고 store에 저장하는게 좋을듯?
                        // 지금은 일단 messageEx가 store.state에 저장되었다고 치고 코드 작성합니다.

                        // modal 창 열기
                        this.dialogVisible = true;
                        
                    })
            }
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
            // messageTime(messageTime) {
            //     var time = messageTime.split()
            //     return time
            // },
            ...mapGetters({
            followed: 'getUsersIFollowed'
            })

        },


        setup() {
            const store = useStore()
            const state = reactive({
            })
            const chatRoomId = store.state.chatRoomId
            // const myinfo = store.state.myinfo
            let socket = new SockJS('https://i5b104.p.ssafy.io:8092/chat')
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

            const back=function(){
                router.push("/main")
            }
            const liveOn=function(id){//화상 요청~
                console.log("들억자ㅣ나")
                router.push({name: 'VideoChat',query:{opp:id}})
            }
            

            const like=function(memberid){//팔로우~
                console.log("팔로우~~")
                console.log(memberid)
                store.dispatch("like",memberid)
                setTimeout(() => {
                    store.dispatch("getFollowEachOther")
                    store.dispatch("getFollowUsers")
                }, 50)
            }
            const unlike = function(memberid) {
                console.log("언팔")
                store.dispatch('unlike', memberid)
                setTimeout(() => {
                    store.dispatch("getFollowEachOther")
                    store.dispatch("getFollowUsers")
                }, 50)
            }
           

            const messageEx = [
                {
                    message_id: "1",
                    message: "테스트 메시지",
                    time: "2021-08-18 19:04:16.6",
                    room_id: "1",
                    member_id: "2"
                },
                {
                    message_id: "2",
                    message: "테스트 메시지2",
                    time: "2021-08-18 19:04:16.6",
                    room_id: "1",
                    member_id: "4"
                },
                {
                    message_id: "3",
                    message: "테스트 메시지3",
                    time: "2021-08-18 19:04:16.6",
                    room_id: "1",
                    member_id: "2"
                },
                {
                    message_id: "4",
                    message: "테스트 메시지4",
                    time: "2021-08-18 19:04:16.6",
                    room_id: "1",
                    member_id: "4"
                },
                {
                    message_id: "5",
                    message: "테스트 메시지4",
                    time: "2021-08-18 19:04:16.6",
                    room_id: "1",
                    member_id: "4"
                },
                {
                    message_id: "6",
                    message: "테스트 메시지4",
                    time: "2021-08-18 19:04:16.6",
                    room_id: "1",
                    member_id: "4"
                },
                {
                    message_id: "6",
                    message: "테스트 메시지4",
                    time: "2021-08-18 19:04:16.6",
                    room_id: "1",
                    member_id: "4"
                },
            ]
            return { state, back, like, unlike, stompClient, messageEx ,liveOn}
        },
    }
</script>

<style>
.like-btn {
    color: #ff4e7e;
}
.DM-modal-box {
    position: fixed;
    width: 20%;
    height: 350px;
    bottom: 0;
    right: 0;
    margin: 2%;
    background: #ffffff;
    border-radius: 10px;
    z-index: 1;
}
.DM-modal-header {
    display: flex;
    justify-content: space-between;
    padding: 3%;
    font-weight: bold;
    -webkit-box-shadow: 0 4px 6px -6px #222;
    -moz-box-shadow: 0 4px 6px -6px #222;
    box-shadow: 0 4px 6px -6px #222;
}
.DM-header-name {
    margin-left: 2%;
}
.DM-close-btn {
    padding: 1%;
    font-weight: bold;
}
.DM-close-btn:hover {
    cursor: pointer;
}
.DM-modal-content {
    max-height: 250px;
    overflow: auto;
}
.DM-modal-content .opp-chat {
    background: #f0f2f5;
}
.DM-modal-footer {
    display: flex;
    justify-content: center;
    margin-left: 5%;
    margin-top: 5%;
}
.DM-input {
    background-color: #f0f2f5;
    border: none;
    color: #323545;
    outline: none;
    width: 70%;
    resize: none;
    border-radius: 5px;
}
.DM-send-btn {
    padding: 2%;
    margin: auto;
    border: 1px solid #323545;
    font-weight: bold;
    border-radius: 5px;
    
}
.DM-send-btn:hover {
    cursor: pointer;
    color: #fff;
    background: #ff4e7e; 
    border: 1px solid white;
}
.DM-modal-content::-webkit-scrollbar {
  width: 10px;
}
.DM-modal-content::-webkit-scrollbar-thumb {
  width: 5px;
  background-color: #f0f2f5;
  border-radius: 5px;
}
.DM-modal-content::-webkit-scrollbar-track {
  background-color: #ffffff;
}
</style>