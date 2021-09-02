<template>
	<div id="" class="main-container" :class="{mainCss:startStatus2}">
		<div v-if="session && invited">
			<br><br>
			<div class="result-card">
				<h2>만남을 원하시나요?</h2>
				<div class="result-card-img">
					<img :src=file_path class="profiile_image"> 
				</div>
				<div class="result-card-main-info">
					<div> {{memberid}} ({{memberinfo.age}}세, 
						<span v-if="genderIcon"><i class="fas fa-mars"></i></span>
            <span v-else><i class="fas fa-venus"></i></span>)
					</div>  
				</div>
				<div class="result-card-body">
					<div class="body-content">주량: {{memberinfo.alcohol.replace('[','').replace(']','')}}</div>
					<div class="body-content">종교: {{memberinfo.religion.replace('[','').replace(']','')}}</div>
					<div class="body-content">성격: {{memberinfo.personality.replace('[','').replace(']','')}}</div>
					<div class="body-content">취미: {{memberinfo.hobby.replace('[','').replace(']','')}}</div>
				</div>
				<div class="meeting-btns" style="display:flex; justify-content:center">
					<div class="meeting-agree-btn" @click="agreeMeeting">수락하기</div>
					<div class="meeting-deny-btn" @click="refuseMeeting">거절하기</div>
				</div>
			</div> 
		</div>

		<div id="session" v-if="session && !invited" class="video-container" v-bind:class="{chatCreate:!chatStatus&&!gameStatus, cmCreate:startStatus2}">
			<div class="video-chat-box">
				<div class="header">
					<div class="video-header">화상채팅</div>
					<div class="chat-header">채팅 & 게임</div>
				</div>
				<!-- 비디오 부분 + 채팅 body -->
				<div class="bodies">
					<!-- Video1 -->
					<div class="videos">
						<div class="video">
							<user-video :stream-manager="mainStreamManager"/>
						</div>
						<!-- Video2 -->
						<div class="video">
							<!-- <user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/> -->
							<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click="updateMainVideoStreamManager(sub)"/>
						</div>			
					</div>
					<div class="chat-body">
						<div id="chatbox" class="chat-container" v-if="chatStatus && session && !invited">
								<div class="chat-detail">
									
									<div ref="chatmain" :class="{chatmain : !this.startStatus2 , 'smChat': this.startStatus2}">
										<div v-for="(chat,index) in chatdata" :key="index">
											
											<div v-if="myUserName == chat.userId" class="my-chat-box">
												<div class="chat-block">
													<div class="my-chat-time">{{ chat.now }}</div>
													<div class="my-chat">{{chat.msg}}</div>
												</div>
											</div>
											<div v-else class="opp-chat-box" >
												<div class="opp-name">{{chat.userId}}</div>
												<div class="opp-block">
													<div class="opp-chat">{{chat.msg}}</div>
													<div class="opp-chat-time">{{chat.now}}</div>
												</div>
											</div>
										</div>
									</div>		
							</div>
						</div>
						<div class="game-container" v-if="gameStatus && session && !invited">
							<h3 v-if="!startStatus || !startStatus2">게임을 골라주세요!</h3>
							<div class="" v-if="!startStatus && !startStatus2" @click="startGame">
								<div v-if="!isReadyBal" class="start-btn">밸런스 게임</div>
								<div v-else class="start-btn">준비완료</div>
							</div>
							<div class="" v-if="!startStatus2 && !startStatus" @click="startGame2">
								<div v-if="!isReadyCat" class="start-btn">캐치마인드</div>
								<div v-else class="start-btn">준비완료</div>
							</div>

							<div class="a-or-b-game" v-if="startStatus">
								<h3>밸런스 게임</h3>
								<h5>바꿀 수 없습니다. 신중히 골라주세요!</h5>
								<div class="select-box" @click="selectA">
									{{A_item}}
								</div>
								<div class="select-box" @click="selectB">
									{{B_item}}
								</div>

								<div class="select-result">
									<div v-for="(game,index) in gamedata" :key="index">
										<div class="select-comment">
											<span>{{game.userId}}님이 {{game.select}}를 선택하셨습니다.</span>
										</div>
									</div>
								</div>	
								<div class="next-problem" @click="nextProblem">다음문제</div>
							</div>
						</div>
					</div>
				</div>

				<div class="footers">
					<div class="nav">
						<div class="main-btn" style="display:flex">
							<!-- <div class="btn-css" @click="videoControll">
								<i class="far fa-grin-stars"></i>
								<div class="btn-exp">얼굴변조</div>
							</div> -->

							<div class="btn-css" @click="voiceControll">
								<Icon v-if="voiceStatus" icon="ic:baseline-record-voice-over"/>
								<Icon v-else icon="ic:round-voice-over-off"/>
								<div class="btn-exp">목소리 필터</div>
							</div>

							<div class="btn-css" @click="micControll">
								<i v-if="micStatus" class="fas fa-microphone"></i>
								<i v-else class="fas fa-microphone-slash"></i>
								<div v-if="micStatus">음소거 ON</div>
								<div v-else>음소거 OFF</div>
							</div>

							<div class="btn-css" @click="vidControll">
								<i v-if="vidStatus" class="fas fa-video"></i>
								<i v-else class="fas fa-video-slash"></i>
								<div v-if="vidStatus" class="btn-exp">카메라 OFF</div>
								<div v-else class="btn-exp">카메라 ON</div>
							</div>

							<div class="btn-css" @click="chatControll">
								<i class="far fa-comment-dots"></i>
								<div class="btn-exp">채팅</div>
							</div>
							<div class="btn-css" @click="gameControll">
								<i class="fas fa-gamepad"></i>
								<div class="btn-exp">게임</div>
							</div>
							<div class="btn-css" @click="reportControll">
								<Icon icon="icon-park-outline:report" color="red"/>
								<div class="btn-exp">신고</div>
							</div>
						</div>
						<div class="chat-exit">
							<div class="exit-btn" @click="exitChat">
								<!-- <Icon icon="ant-design:close-circle-filled"/> -->
								<div class="exit-exp">나가기</div>
							</div>
						</div>
					</div>
					<div class="send-box">
						<input class="send-input" v-model="msg" type="text" placeholder="메세지를 입력해주세요" @keydown.enter="sendMsg">
						<i v-if="!startStatus2" class="send-icon fas fa-reply" @click="sendMsg"></i>
					</div>
				</div>
			</div>
		</div>
	
	
	<div class="draw-box">
	<Slider class="time-slider" v-if="startStatus2 && catchMindStatus" v-model="countView" :max="60" />
	<div class="pb-div" v-if="startStatus2 && myTurn && catchMindStatus">{{problem}}</div>
	<div class="draw-container" v-bind:class="{catDis:!startStatus2}">

    <canvas
      id="myCanvas"
      width="560"
      height="360"
      @mousedown="beginDrawing"
      @mousemove="keepDrawing"
      @mouseup="stopDrawing"
    />
    <div v-if="!catchMindStatus || myTurn" class="draw-tool">
		<table class="draw-opt">
			<tr>
				<td><Icon class="colorBtn" icon="akar-icons:circle-fill" color="black" @click="colorChange('black')" width="24" height="24"/></td>
				<td><Icon class="colorBtn" icon="akar-icons:circle-fill" color="red" @click="colorChange('red')" width="24" height="24"/></td>
				<td><Icon class="colorBtn" icon="akar-icons:circle-fill" color="blue" @click="colorChange('blue')" width="24" height="24"/></td>
				<td><Icon class="colorBtn" icon="akar-icons:circle-fill" color="yellow" @click="colorChange('yellow')" width="24" height="24"/></td>
				<td><Icon class="colorBtn" icon="akar-icons:circle-fill" color="green" @click="colorChange('green')" width="24" height="24"/></td>
				<td><Icon class="colorBtn" icon="bi:eraser" @click="colorChange('white')" width="24" height="24"/></td>
				<td><button class="removeBtn" @click="allDelete">전체 지우기</button></td>
		</tr>
		</table>
    </div>
	<div v-if="catchMindStatus && !myTurn">
		<input class="ans-block" type="text" v-model="answer" placeholder="정답을 입력하세용" @keydown.enter="sendAns">
	</div>
	<button @click="startCM" v-if="!catchMindStatus && host">시작</button>
	</div>
	</div>
	</div>
	<el-dialog v-model="dialogStatus" title="신고">
		<el-form>	
			<el-form-item  label="title" :label-width="'120px'" >
				<el-input v-model="this.title"  autocomplete="off"></el-input>
			</el-form-item>

			<el-form-item  class="rep-m" label="신고 내용" :label-width="'120px'" >
				<el-input v-model="this.content" class="rep-ct" type="textarea" autocomplete="off"></el-input>
			</el-form-item>

		<div class="report-btn-box">
			<div class="report-btn" @click="report()">
				<div class="report-btn-text">신고하기</div>
			</div>
		</div>
		</el-form>
	</el-dialog>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/views/main/components/UserVideo';
import router from '../../../router';
import { Icon } from '@iconify/vue';
import { mapGetters} from 'vuex';
import Swal from 'sweetalert2';
import Slider from '@vueform/slider'

axios.defaults.headers.post['Content-Type'] = 'application/json';
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
const OPENVIDU_SERVER_URL = "https://i5b104.p.ssafy.io:9090";
const OPENVIDU_SERVER_SECRET = "cowdog123";

//const store = useStore();
const instance = axios.create({
	baseURL: OPENVIDU_SERVER_URL,
	headers: {'Content-Type': 'application/json'},
})
export default {
	name: 'VideoChat',

	components: {
		UserVideo,
		Icon,
		Slider,
	},
	data () {
		return {
			OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
			mySessionId: '',
			myUserName: '',
			vsStatus:false,
			micStatus:false,
			vidStatus:false,
			chatStatus:true,
			gameStatus:false,
			chatdata: [],
			msg:'',
			A_item_list: ["토맛 토마토","평생 샤워안하기","카레맛 똥", "팔만대장경 다 읽기","겨울 에어컨" ,"월 200 백수", "볼빨간 사춘기"],
			B_item_list: ["토마토맛 토","평생 양치안하기","똥맛 카레", "대장내시경 팔만번 하기","여름 히터", "월 500 직장인", "사촌간 볼빨기"],
			A_item:'',
			B_item:'',
			startStatus:false,
			gIndex:0,
			gamedata:[],
			isSelected:false,
			gameStart:[],
			gameStart2:[],
			isReadyBal:false,
			nextStatus:false,
			voiceStatus:false,
			matchAccept:false,
			invited:false,
			user:undefined,
			file_path:'',
			memberinfo:'',
			memberid:'',
			dist:'',
			x: 0,
			y: 0,
			isDrawing: false,
			canvas: null,
			color: "black",
			penSize:2,
			cmStatus:false,
			isReadyCat:false,
			catchProblem:["샴푸","송아지","강아지","소방관","포크레인","돈다발","철학","코딩","싸피","뚱이","스폰지밥","삼성","애플","아이폰","에어팟","경찰","신과함께","아이언맨","캡틴아메리카","토르","헐크"],
			problem:'',
			catchMindStatus:false,
			myTurn:false,
			count:60,
			catchIndex:0,
			answer:'',
			inProgress:false,
			timerInit:null,
			turn: 1,
			host:true,
			countView:'',
			dialogStatus:false,
			title:'',
			content:'',
			reportedId:'',
		}
	},
	computed: {
    ...mapGetters(['getUserInfo','getUserToken','getMatchStatus']),
	},
	created(){
		this.mySessionId = this.$route.query.opp;
		this.memberid=this.mySessionId;
		this.myUserName = this.getUserInfo.memberid;
		this.getData(this.mySessionId);			
	},
	mounted() {
		console.log("마운트 확인하기");
		var c = document.getElementById("myCanvas");
		this.canvas = c.getContext(`2d`);
	},
	methods: {
		reportControll(){
			this.dialogStatus=true;
		},
		report(){
			
			this.$store.dispatch('postUserReport', {"title": this.title, "content": this.content, "reportedId": this.memberid})
			this.dialogStatus=false;
		},
		videoControll(){
			this.publisher.stream.applyFilter("FaceOverlayFilter")
    .then(filter => {
        filter.execMethod(
            "setOverlayedImage",
            {
                "uri":"https://cdn.pixabay.com/photo/2013/07/12/14/14/derby-148046_960_720.png",
                "offsetXPercent":"0.7F",
                "offsetYPercent":"-0.8F",
                "widthPercent":"2.0F",
                "heightPercent":"2.0F"
            });
    });
		},
		agreeMeeting(){
			this.$store.dispatch("isMatching",{from:this.memberid,to:this.$store.state.myinfo.memberid,message:"liveOn"})
			this.matchAccept=true;
			this.$store.state.matchStatus=true;
			this.invited=false;
			this.session.signal({
				data: 'agree',
				to:[],
				type: 'match'
			})
			
		},
		refuseMeeting(){
			this.$store.state.matchStatus=false;
			this.matchAccept=false;
			this.$store.state.matchAccept = false;
			this.session.signal({
				data: 'refuse',
				to:[],
				type: 'match'
			})

			this.leaveSession();
			router.push({name:'Main'});
			
		},
		voiceControll(){
			this.voiceStatus=!this.voiceStatus;
			let pitchs = ['0.76', '0.77', '0.78', '0.79', '0.80', '1.3', '1.4', '1.5', '1.6', '1.7']
            let pitch = pitchs[Math.floor(Math.random() * pitchs.length)]
			if(this.voiceStatus){
				this.publisher.stream.applyFilter("GStreamerFilter", {"command": `pitch pitch=${pitch}`});
			}
			else{
				this.publisher.stream.removeFilter();
			}
		},
		chatscroll(){
			this.$refs.chatmain.scrollTop = this.$refs.chatmain.scrollHeight;
		},
		sendMsg(){
			this.msg = this.msg.trim();
			let date = new Date();
			let now = '';
			let min = date.getMinutes();
				if(min < 10){
					min = "0"+date.getMinutes();
				}
			if(date.getHours()>12){
				now ="오후 "+ (date.getHours()-12) +":"+min+"";
			}
			else{
				now ="오전 "+ date.getHours()+":"+min+"";
			}
			if(this.msg==''){
				return;
			}
			const sdata = {
				userId : this.myUserName,
				msg : this.msg,
				now : now
			};
			
			this.session.signal({
				data: JSON.stringify(sdata),
				to:[],
				type: 'my-chat'
			})
			this.msg='';
		},
		chatControll(){
			this.chatStatus = true;
			this.gameStatus = false;
			if(this.chatStatus){
				setTimeout(this.chatscroll,10);
			}
		},
		micControll () {
			this.micStatus = !this.micStatus;
			this.publisher.publishAudio(this.micStatus);	
		},
		vidControll(){
			this.vidStatus = !this.vidStatus;
			this.publisher.publishVideo(this.vidStatus);
		},
		gameControll(){
			if(this.startStatus2){
				this.startStatus2 = !this.startStatus2;
			}
			this.gameStatus = !this.gameStatus;
			this.chatStatus = false;
		},
		startGame(){
			this.isReadyBal=!this.isReadyBal;
			this.session.signal({
				data: this.isReadyBal,
				to:[],
				type: 'game-start'
			})
		},
		startGame2(){
			this.isReadyCat=!this.isReadyCat;
			this.session.signal({
				data: this.isReadyCat,
				to:[],
				type: 'game-start2'
			})
		},
		nextProblem(){
			if(!this.nextStatus && this.isSelected){ 
				Swal.fire('상대방이 선택하지 않았습니다')
				return;
			}
			else if(!this.nextStatus && !this.isSelected){
				Swal.fire('둘 중 하나를 선택하세용')
			}
			this.nextStatus=false;
			this.isSelected=false;

			this.session.signal({
				data: "next",
				to:[],
				type: 'game-next'
			})
			
		},
		selectA(){
			if(this.isSelected) return;
			this.isSelected = true;
			const sdata = {
				userId : this.myUserName,
				select : this.A_item,
			};
			
			this.session.signal({
				data: JSON.stringify(sdata),
				to:[],
				type: 'my-game'
			})
		},
		selectB(){
			if(this.isSelected) return;
			this.isSelected = true;
			const sdata = {
				userId : this.myUserName,
				select : this.B_item,
			};
			
			this.session.signal({
				data: JSON.stringify(sdata),
				to:[],
				type: 'my-game'
			})
			
		},
		joinSession () {
			// --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

			// --- Init a session ---
			this.session = this.OV.initSession();

			// --- Specify the actions when events take place in the session ---

			// On every new Stream received...
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				console.log("streamCreated 테스트");
				this.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
				
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
			});

			this.session.on('signal:my-chat', (event) => {
				this.chatdata.push(JSON.parse(event.data));
				setTimeout(this.chatscroll,10);
			});

			this.session.on('signal:my-game', (event) => {
				this.gamedata.push(JSON.parse(event.data));
				if(this.gamedata.length==2){
					this.nextStatus=true;
				}
			});

			this.session.on('signal:game-start',(event) => {
				if(event.data){
					this.gameStart.push(event.data);
				}
				else{
					this.gameStart.pop();
				}
				if(this.gameStart.length==2){
					this.startStatus=true;
					this.A_item = this.A_item_list[this.gIndex];
					this.B_item = this.B_item_list[this.gIndex];
				}
			});

			this.session.on('signal:game-start2',(event) => {
				if(event.data){
					this.gameStart2.push(event.data);
				}
				else{
					this.gameStart2.pop();
				}
				if(this.gameStart2.length==2){
					this.startStatus2=true;
					this.gameStatus=false;
					this.chatStatus=true;
				}
			});
			this.session.on('signal:game-next',() =>{ 
				if(this.gamedata.length==2){
					if(this.gIndex<this.A_item_list.length-1){
						this.nextStatus=false;
						this.isSelected=false;
						this.gIndex++;
						this.A_item = this.A_item_list[this.gIndex];
						this.B_item = this.B_item_list[this.gIndex];
						this.gamedata=[];
					}
					else{
						Swal.fire('게임 종료!');
						this.gameStart=[];
						this.gamedata=[];
						this.startStatus = false;
						this.isReadyBal = false;
						this.gIndex=0;
					}
				}
			});
			this.session.on('signal:match',(event)=>{
				//this.AcceptInfo.push("reject");
				
				if(this.mySessionId!=this.myUserName && event.data=="refuse"){
					Swal.fire({
					title:"상대방이 거절하였습니다",
					showConfirmButton: false,
                      timer: 1000
					}).then(()=>{
						this.leaveSession();
						router.push({name:'Main'});
					})
				}
				else if(!this.invited && event.data=="agree" &&(this.mySessionId != this.myUserName)){
					Swal.fire({
					title:"상대방이 수락하였습니다",
					showConfirmButton: false,
                      timer: 1000
					})
					this.matchAccept=true;
				}
				
				
			});
			this.session.on('signal:catch-start',(event)=>{
				this.catchMindStatus=true;
				this.count=60;
				this.myTurn = !this.myTurn;
				let pb = JSON.parse(event.data);
				this.problem = pb.problem;
				if(this.turn > 4){
					this.endCM();
					return;
				}


				if(this.myTurn){
					Swal.fire({
					title:"내 차례에요!",
					showConfirmButton: false,
                      timer: 1500
					})
				}
				else{
					Swal.fire({
					title:"상대방 차례에요!",
					showConfirmButton: false,
                      timer: 1500
					})
				}
			})
			this.session.on('signal:catch-end',(event)=>{
				let val = JSON.parse(event.data);
				this.turn = val.turn;
				if(this.timerInit!=null){
					clearInterval(this.timerInit);
					this.timerInit=null;
				}
				this.allDelete();
				if(this.turn>4){
					Swal.fire({
					title:"게임 끝!",
					showConfirmButton: false,
                      timer: 1500
					}).then(()=>{
						this.catchMindStatus=false;
						this.turn=1;
						this.count=60;
					})
					return;
				}

				if(!this.myTurn && this.turn<5){
					setTimeout(() => {
						this.startCM();
					}, 1000);

				}
			})
			this.session.on('signal:timer',(event)=>{
				this.count = event.data;
				this.count = +this.count || 0;
				this.countView = (this.count<10 ? '0' : '') + this.count;
				
			})
			this.session.on('signal:start-draw',(event)=>{
				let sm = JSON.parse(event.data);
				this.x = sm.x;
				this.y = sm.y;
				this.isDrawing=sm.isDrawing;
				console.log("그리기 스타트")
				
			})
			this.session.on('signal:drawing',(event)=>{
				let sm = JSON.parse(event.data);
				console.log("그리기 세션 콘솔 창창")
				this.drawLine(this.x, this.y, sm.x, sm.y, this.color);
				this.x = sm.x;
				this.y = sm.y;
				this.isDrawing=sm.isDrawing;
			})
			
			this.session.on('signal:drawing-opt',(event)=>{
				
				if(event.data=="all"){
					this.canvas.clearRect(0, 0, 560, 360);
				}
				else if(event.data=="white"){
					this.penSize = 4;
					this.color = event.data;
				}
				else{
					this.color = event.data;
					this.penSize = 2;
				}
				
			})

			this.session.on('signal:send-answer', (event)=>{
				let info = JSON.parse(event.data);
				if(this.problem==info.ans){
					Swal.fire({
					title:`${info.name}님이 정답을 맞추셨습니다.`,
					showConfirmButton: false,
                      timer: 1500
					}).then(()=>{	
						this.allDelete();
					})
				}
			})

			this.session.on('signal:exit',()=>{
				this.leaveSession();
			})
			// On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			axios.get(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,'',{
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},}).then(response=>{
			console.log(response.data);
		})

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(this.mySessionId).then(token => {
				this.session.connect(token, { clientData: this.myUserName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---
						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: false,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: false,  	// Whether you want to start publishing with your video enabled or not
							resolution: '400x360',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: true       	// Whether to mirror your local video or not
						});

						this.mainStreamManager = publisher;
						this.publisher = publisher;

						// --- Publish your stream ---

						this.session.publish(this.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			window.addEventListener('beforeunload', this.leaveSession)
		},

		leaveSession () {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			this.$store.dispatch("isMatching",{from:this.memberid,to:this.$store.state.myinfo.memberid,message:"liveOff"})
			clearInterval(this.timerInit);
			this.$store.state.matchStatus=false;
			this.invited=false;
			
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;
			window.removeEventListener('beforeunload', this.leaveSession);

			router.push({name:"Main"});
		},

		updateMainVideoStreamManager (stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},

		/**
		 * --------------------------
		 * SERVER-SIDE RESPONSIBILITY
		 * --------------------------
		 * These methods retrieve the mandatory user token from OpenVidu Server.
		 * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
		 * the API REST, openvidu-java-client or openvidu-node-client):
		 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
		 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
		 *   3) The Connection.token must be consumed in Session.connect() method
		 */

		getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				instance
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},
		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
		createToken (sessionId) {
			return new Promise((resolve, reject) => {
				instance
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, JSON.stringify({
            "session": sessionId,
            "kurentoOptions": {
              "allowedFilters": ["GStreamerFilter", "FaceOverlayFilter"]
            }
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},

		getData (sessionId) {
			return new Promise((resolve, reject) => {
				instance
					.get(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => {
						let content = response.data.content;
						for(let i=0;i<content.length;i++){
							if(content[i].id==this.myUserName){
								let userid = content[i].connections.content[0].clientData.replace(/"/g,"").replace(":","").replace("clientData","").replace("{","").replace("}","");
								axios.get("http://i5b104.p.ssafy.io/cowdog/mem/getOppInfo/?userId="+userid,{
									headers:{
										Authorization:"Bearer "+ this.getUserToken,
										"Access-Control-Allow-Origin": "*",
										"Access-Control-Allow-Methods": "GET, POST, PATCH, PUT, DELETE, OPTIONS",
										"Access-Control-Allow-Headers": "Origin, Content-Type, X-Auth-Token"
									}
						
								})
								.then(res=>{
									this.file_path=res.data.file_path;
									this.memberinfo = res.data.memberinfo;
									this.memberid = res.data.memberid;
									this.dist = res.data.dist;
								})
								this.mySessionId = this.myUserName;
								this.host=false;
								this.joinSession();
								this.invited=true;
								this.myTurn=true;
								// // this.SET_MATCHSTATUS(true);
								//this.$store.commit("SET_MATCHSTATUS",true);
								return;
							
                            }
						}
						this.joinSession();
						
						this.$store.state.matchStatus=true;
						

					})
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},
		drawLine(x1, y1, x2, y2, color) {
			console.log("그리기 함수안에 들어옴")
			let ctx = this.canvas;
			ctx.beginPath();
			ctx.strokeStyle = color;
			ctx.lineWidth = this.penSize;
			ctx.moveTo(x1, y1);
			ctx.lineTo(x2, y2);
			ctx.stroke();
			ctx.closePath();
		},
	beginDrawing(e) {
		//if(!this.myTurn)return;
		this.x = e.offsetX;
		this.y = e.offsetY;
		this.isDrawing = true;

		const sdata = {
			x : e.offsetX,
			y : e.offsetY,
			isDrawing : true,
		}; 
		this.session.signal({
				data: JSON.stringify(sdata),
				to:[],
				type: 'start-draw'
		})
    },
    keepDrawing(e) {
      if (this.isDrawing) {
		const sdata = {
			x : e.offsetX,
			y : e.offsetY,
			isDrawing : true,
		}; 
		this.session.signal({
				data: JSON.stringify(sdata),
				to:[],
				type: 'drawing'
		})
      }
    },
    stopDrawing(e) {
      if (this.isDrawing) {
		const sdata = {
			x : e.offsetX,
			y : e.offsetY,
			isDrawing : false,
		};
		this.session.signal({
				data: JSON.stringify(sdata),
				to:[],
				type: 'drawing'
		})
        //this.x = 0;
        //this.y = 0;
        this.isDrawing = false;
      }
    },
	colorChange(col) {
		this.session.signal({
			data: col,
			to:[],
			type: 'drawing-opt'
		})
    },
    allDelete() {
		this.session.signal({
			data: "all",
			to:[],
			type: 'drawing-opt'
		})
    },
	startCM(){
		if(this.turn>4){
			return;
		}
		let pb = this.catchProblem[Math.floor(Math.random() * this.catchProblem.length)]
		const sdata={
			problem : pb,
		}
		this.session.signal({
			data: JSON.stringify(sdata),
			to:[],
			type: 'catch-start'
		})

		this.timerInit = setInterval(()=>{
			this.timer();
		},1000)
	},
	endCM(){
		this.turn++;

		const sdata = {
			turn : this.turn,
		}
		this.session.signal({
			data: JSON.stringify(sdata),
			to:[],
			type: 'catch-end'
		})
	},
	timer(){
		if(this.count>1){
			this.count--;
			this.session.signal({
				data: this.count,
				to:[],
				type: 'timer'
			})
		}
		else{
			this.session.signal({
				data: 0,
				to:[],
				type: 'timer'
			})
			this.endCM();
		}
	},
	exitChat(){
		this.session.signal({
			data: '',
			to:[],
			type: 'exit'
		})
	},
	sendAns(){
		
		const sdata = {
			name: this.myUserName,
			ans : this.answer
		}
		this.session.signal({
			data: JSON.stringify(sdata),
			to:[],
			type: 'send-answer'
		})
		if(this.answer==this.problem){
			this.answer='';
			setTimeout(() => {
				this.endCM();
			}, 1000);
		}
		this.answer='';
	},
	}
}
</script>
<style>
.result-card {
	border: 2px solid #f0f2f5;
	margin: 0 auto;
}
.meeting-agree-btn {
	padding: 3%;
	margin: 2%;
	border: 1px solid #FF4e7e;
	border-radius: 5px;
	font-weight: bold;
}
.meeting-agree-btn:hover {
	background: #ff4e7e;
	cursor: pointer;
	color: #fff;
}
.meeting-deny-btn {
	padding: 3%;
	margin: 2%;
	border: 1px solid #323545;
	border-radius: 5px;
	font-weight: bold;
}
.meeting-deny-btn:hover {
	cursor: pointer;
	background: #323545;
	color: #f0f2f5;
}
.result-card-body {
	padding: 4%;
}
.result-card-body .body-content {
	font-weight: bold;
	margin: 2%;
}

.main-container {
	height: 100%;
}
.mainCss{
	display: flex;
}
.video-container {
	height: 100%;
	padding: 2%;
}
.video-chat-box {
	height: 100%;
}
/* header css */
.header{
	display: flex;
	padding: 5px;
	font-weight: bold;
}
.video-header {
	width: 75%;
	text-align: center;
}
.chat-header{
	width: 25%;
	text-align: center;
}

/* body css */
.bodies {
	display: flex;
	height: 80%;
	background: #f0f2f5;
}
.videos {
	display: flex;
	width: 75%;
	height: 100%;
	background: #f0f2f5;
}
.video {
	width: 50%;
	height: 100%;
	margin: 3px;
}
.chat-body {
	width: 25%;
	height: 100%;
}
.chat-block {
	display: flex;
	justify-content: flex-end;
	padding: 3px;
	margin: 10px;
}
.chatmain{
	max-height: 500px;
	overflow: auto;
}
.my-chat-time {
	font-size: 10px;
	font-weight: bold;
	width: 25%;
	text-align: right;
	margin-right: 5px;
	align-self: flex-end;
}
.my-chat {
	/* background: #fef01b; */
	background: #ff4e7e;
	color: white;
	padding: 2%;
	border-radius: 5px;
	text-align: left;
	word-break: break-all;
}
.opp-chat-box {
	padding: 3px;
	margin: 10px;
}
.opp-block {
	display: flex;
}
.opp-name {
	font-size: 12px;
	font-weight: bold;
	text-align: left;
	margin-bottom: 2px;
}
.opp-chat {
	background: white;
	padding: 2%;
	border-radius: 7px;
	text-align: left;
	word-break: break-all;
}
.opp-chat-time {
	font-size: 10px;
	font-weight: bold;
	text-align: left;
	margin-left: 5px;
	width: 25%;
	align-self: flex-end;
}

/* footers css */
.footers {
	display: flex;
	width: 98%;
	background: #f0f2f5;
	padding: 1%;
}
.nav{
	width: 75%;
	display: flex; justify-content: space-between;
}
.send-box {
	width: 25%;
}
.send-input {
	width: 85%;
	height: 90%;
	border: 0;
	border-radius: 5px;
	margin: 1%;
	padding-left: 3%;
}
.send-input:focus {
	outline: none;
}
.send-icon {
	font-size: 20px;
	color: #ff4e7e;
}
.send-icon:hover {
	transform: scale(1.1);
	cursor: pointer;
}
.btn-css {
	padding: 1%;
	background: white;
	width: 70px;
	border-radius: 5px;
	color: #323545;
	font-size: 14px;
	font-weight: bold;
}
.main-btn {
	width: 60%;
	justify-content: space-between;
	align-self: center;
	margin:0 1%;
}
.chat-exit {
	width: 7%;
	align-self: center;
	margin-right: 1%;
}
.exit-btn {
	display: flex;
	background: red;
	color: white;
	font-weight: bold;
	justify-content: center;
	border-radius: 5px;
	height: 45px;
}
.exit-exp {
	align-self: center;
	width:150px;
}
.exit-btn:hover {
	transform: scale(1.1);
	cursor:pointer;
}
.btn-css:hover{
	transform: scale(1.1);
    cursor:pointer;
}

/* game css */
.game-container{
	width:100%;
}

.start-btn {
	padding: 5%;
	width: 80%;
	margin: 10% auto;
	font-weight: bold;
	font-size: 16px;
	border: 0;
	border-radius: 5px;
	background: #fff;
	color: #323545
}
.start-btn:hover {
	cursor: pointer;
	transform: scale(1.1);
}

/* game1 - A or B Game */
.select-box {
	padding: 5%;
	margin: 5%;
	background: white;
	font-weight: bold;
	border-radius: 5px;
}
.select-box:hover{
	transform: scale(1.1);
	cursor: pointer;
	border: 1px solid #ff4e7e;
}

.select-result {
	margin: 5%;
}
.select-comment {
	border: 2px solid #ff4e7e;
	font-size: 12px;
	font-weight: bold;
	padding: 5px;
	border-radius: 5px;
	margin: 5%;
}
.next-problem {
	width: 80%;
	margin: 10% auto;
	padding: 3%;
	border: 2px solid #323545;
	background: #fff;
	border-radius: 5px;
	color: #323545;
	font-weight: bold;
}
.next-problem:hover {
	cursor: pointer;
	color: #ff4e7e;
	border: 2px solid #ff4e7e;
}

#myCanvas {
  border: 1px solid grey;
  margin-bottom: 10px;
}
.draw-container{
	text-align: center;
}
.colorBtn{
	margin-right:4px;
    cursor:pointer;
	margin-top:4px;
}
.colorBtn:hover{
	transform: scale(1.1);
    cursor:pointer;
} 
.draw-tool{
	vertical-align: middle;
}
.removeBtn{
	color: #FF427E;
	background-color: white ;
	border : 1px solid #FF427E;
	border-radius: 4px;
}
.removeBtn:hover{
	transform: scale(1.1);
    cursor:pointer;
}
.draw-opt{
	margin: auto;
}
.cmCreate{
	margin-top:7%;
	width:50%;
}
.videoSet{
	max-width:60%;
	height:auto;
}
.catDis{
	visibility: hidden;
}
.draw-box{
	float:right;
	margin-top:5%;
	width:48%;
}
.smChat{
	max-height: 250px;
	overflow: auto;
}
.time-slider{
	width:70%;
	margin: auto;
	margin-bottom: 20px;
}
.slider-connect{
	background: #FF427E;
}
.pb-div{
	margin:auto;
	margin-bottom: 10px;
	width:auto;
	font-size:24px;
}
.ans-block{
	border-radius: 5px;
	height:20px;
}
.el-dialog{
	height:45%;
}
.rep-ct{
	height:100px;
}
.rep-m{
	margin-bottom: 10px;
}
.report-btn-box {
	width: 50%;
	border: 2px solid red;
	padding: 3%;
	margin: 0 auto;
	font-size: 16px;
	color: red;
	text-align: center;
	font-weight: bold;
	border-radius: 5px;
}
.report-btn-box:hover {
	background: red;
	color: white;
	cursor: pointer;
}
</style>
<style src="@vueform/slider/themes/default.css"></style>