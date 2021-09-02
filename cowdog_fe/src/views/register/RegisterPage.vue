<template>
<div class="register-background">
    <br>
    <br>
    <div class="register-body">
    <el-carousel :interval="1000000" arrow="never" :autoplay="false" trigger="click" v-loading="state.loading">
        <el-carousel-item class="item">
            <div class="register-inside-carousel">
                <div class="register-left-half">
                    <h2>회원 가입</h2>
                    <div>
                        <el-form :model="state.form" :rules="state.rules" ref="registerForm"> 
                            <el-form-item prop="id">
                                <div class="input-name">아이디</div>
                                <el-input  class="elinput" v-model="state.form.id" autocomplete="off" ></el-input>
                            </el-form-item>
                            <el-form-item prop="nickname">
                                <div class="input-name">닉네임</div>
                                <el-input class="elinput" v-model="state.form.nickname" autocomplete="off"  ></el-input>
                            </el-form-item>
                            <el-form-item prop="email">
                                <div class="input-name">이메일</div>
                                <el-input class="elinput" v-model="state.form.email" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item prop="password">
                                <div class="input-name">비밀번호</div>
                                <el-input class="elinput" v-model="state.form.password" autocomplete="off" show-password></el-input>
                            </el-form-item>
                            <el-form-item prop="passwordConfirm">
                                <div class="input-name">비밀번호 확인</div>
                                <el-input class="elinput" v-model="state.form.passwordConfirm" autocomplete="off" show-password></el-input>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
                <div class="register-right-half">
                    <div class="register-img-box">
                        <img class="register-img-item" :src="require('@/assets/images/regist_img1.png')">
                    </div>
                </div>
            </div>
        </el-carousel-item>


        <el-carousel-item class="item">
            <div class="register-inside-carousel">
                <div class="register-left-half">
                    <h2>회원 가입</h2>
                    <div>
                        <el-form :model="state.form" :rules="state.rules"  :label-position="state.form.align">
                            <el-form-item>
                                <div class="input-name">나이가 어떻게 되세요?(숫자 입력)</div>
                                <el-input class="elinput" v-model="state.form.age" autocomplete="off"></el-input>
                            </el-form-item>
                            
                            <el-form-item prop="religion">
                                <div class="input-name">종교를 선택해 주세요</div>
                                    <ul>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.religion" id="무교" value="무교" type="radio">
                                            <span class="icon"></span><span class="text">무교</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.religion" id="기독교" value="기독교" type="radio">
                                            <span class="icon"></span><span class="text">기독교</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.religion" id="천주교" value="천주교" type="radio">
                                            <span class="icon"></span><span class="text">천주교</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.religion" id="불교" value="불교" type="radio">
                                            <span class="icon"></span><span class="text">불교</span>
                                            </label>
                                        </li>
                                    </ul>
                            </el-form-item>
                            <el-form-item prop="hobby">
                                <div class="input-name">취미를 선택해 주세요(3개)</div>
                                <div>
                                    <ul class="ks-cboxtags">
                                        <li><label class="checkbox">
                                            <input v-model="state.form.hobby" id="운동" value="운동" type="checkbox">
                                            <span class="icon"></span><span class="text">운동</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.hobby" id="요리" value="요리" type="checkbox">
                                            <span class="icon"></span><span class="text">요리</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.hobby" id="영화" value="영화" type="checkbox">
                                            <span class="icon"></span><span class="text">영화</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.hobby" id="게임" value="게임" type="checkbox">
                                            <span class="icon"></span><span class="text">게임</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.hobby" id="낚시" value="낚시" type="checkbox">
                                            <span class="icon"></span><span class="text">낚시</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.hobby" id="음악감상" value="음악감상" type="checkbox">
                                            <span class="icon"></span><span class="text">음악감상</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.hobby" id="제빵" value="제빵" type="checkbox">
                                            <span class="icon"></span><span class="text">제빵</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.hobby" id="사진" value="사진" type="checkbox">
                                            <span class="icon"></span><span class="text">사진</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.hobby" id="코딩" value="코딩" type="checkbox">
                                            <span class="icon"></span><span class="text">코딩</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.hobby" id="그림그리기" value="그림그리기" type="checkbox">
                                            <span class="icon"></span><span class="text">그림그리기</span>
                                            </label>
                                        </li>
                                    </ul>
                                </div>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
                <div class="register-right-half">
                    <div class="register-img-box">
                        <img class="register-img-item" alt="cow dog logo" :src="require('@/assets/images/regist_img2.png')">
                    </div>
                </div>
            </div>
        </el-carousel-item>

        
        <el-carousel-item class="item">
            <div class="register-inside-carousel">
                <div class="register-left-half">
                    <h2>회원 가입</h2>
                    <div>
                        <el-form :model="state.form" :rules="state.rules" :label-position="state.form.align">
                            <el-form-item prop="personality">
                                <div class="input-name">본인의 성격을 선택해 주세요(최소 3개)</div>
                                <div>
                                    <ul class="ks-cboxtags">
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="활기찬" value="활기찬" type="checkbox">
                                            <span class="icon"></span><span class="text">활기찬</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="진지한" value="진지한" type="checkbox">
                                            <span class="icon"></span><span class="text">진지한</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="재미있는" value="재미있는" type="checkbox">
                                            <span class="icon"></span><span class="text">재미있는</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="웃음많은" value="웃음많은" type="checkbox">
                                            <span class="icon"></span><span class="text">웃음많은</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="장난끼많은" value="장난끼많은" type="checkbox">
                                            <span class="icon"></span><span class="text">장난끼많은</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="꼼꼼한" value="꼼꼼한" type="checkbox">
                                            <span class="icon"></span><span class="text">꼼꼼한</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="세심한" value="세심한" type="checkbox">
                                            <span class="icon"></span><span class="text">세심한</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="차분함" value="차분함" type="checkbox">
                                            <span class="icon"></span><span class="text">차분함</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="책임감" value="책임감" type="checkbox">
                                            <span class="icon"></span><span class="text">책임감</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="솔직한" value="솔직한" type="checkbox">
                                            <span class="icon"></span><span class="text">솔직한</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="정열적인" value="정열적인" type="checkbox">
                                            <span class="icon"></span><span class="text">정열적인</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="조용한" value="조용한" type="checkbox">
                                            <span class="icon"></span><span class="text">조용한</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.personality" id="다정한" value="다정한" type="checkbox">
                                            <span class="icon"></span><span class="text">다정한</span>
                                            </label>
                                        </li>
                                    </ul>
                                </div>
                            </el-form-item>

                            <el-form-item prop="interest">
                                <div class="input-name">선호하는 MBTI를 골라주세요</div>
                                <div>
                                    <ul class="ks-cboxtags">
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="ISTJ" value="ISTJ" type="checkbox">
                                            <span class="icon"></span><span class="text">ISTJ</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="ISFJ" value="ISFJ" type="checkbox">
                                            <span class="icon"></span><span class="text">ISFJ</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="INFJ" value="INFJ" type="checkbox">
                                            <span class="icon"></span><span class="text">INFJ</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="INTJ" value="INTJ" type="checkbox">
                                            <span class="icon"></span><span class="text">INTJ</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="ISTP" value="ISTP" type="checkbox">
                                            <span class="icon"></span><span class="text">ISTP</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="ISFP" value="ISFP" type="checkbox">
                                            <span class="icon"></span><span class="text">ISFP</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="INFP" value="INFP" type="checkbox">
                                            <span class="icon"></span><span class="text">INFP</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="INTP" value="INTP" type="checkbox">
                                            <span class="icon"></span><span class="text">INTP</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="ESTP" value="ESTP" type="checkbox">
                                            <span class="icon"></span><span class="text">ESTP</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="ENFP" value="ENFP" type="checkbox">
                                            <span class="icon"></span><span class="text">ENFP</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="ESFP" value="ESFP" type="checkbox">
                                            <span class="icon"></span><span class="text">ESFP</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="ENTP" value="ENTP" type="checkbox">
                                            <span class="icon"></span><span class="text">ENTP</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="ESTJ" value="ESTJ" type="checkbox">
                                            <span class="icon"></span><span class="text">ESTJ</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="ESFJ" value="ESFJ" type="checkbox">
                                            <span class="icon"></span><span class="text">ESFJ</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="ENFJ" value="ENFJ" type="checkbox">
                                            <span class="icon"></span><span class="text">ENFJ</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.mbti" id="ENTJ" value="ENTJ" type="checkbox">
                                            <span class="icon"></span><span class="text">ENTJ</span>
                                            </label>
                                        </li>
                                    </ul>
                                
                                </div>
                            </el-form-item>
                            
                        </el-form>
                    </div>
                </div>
                <div class="register-right-half">
                    <div class="register-img-box">
                        <img class="register-img-item" :src="require('@/assets/images/regist_img3.png')">
                    </div>
                </div>
            </div>
        </el-carousel-item>
        <el-carousel-item class="item">
            <div class="register-inside-carousel">
                <div class="register-left-half">
                    <h2>회원 가입</h2>
                    <div>
                        <el-form :model="state.form" :rules="state.rules">
                            <el-form-item>
                                <div class="input-name">나의 MBTI</div>
                                <el-input class="elinput" v-model="state.form.mymbti" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item prop="gender">
                                <div class="input-name">성별</div>
                                <div>
                                    <ul class="ks-cboxtags">
                                        <li><label class="checkbox">
                                            <input v-model="state.form.gender" id="남자" value="남자" type="radio">
                                            <span class="icon"></span><span class="text">남자</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.gender" id="여자" value="여자" type="radio">
                                            <span class="icon"></span><span class="text">여자</span>
                                            </label>
                                        </li>
                                    </ul>
                                </div>
                            </el-form-item>
                            <el-form-item prop="smoking">
                                <div class="input-name">흡연을 하시나요?</div>
                                <div>
                                    <ul class="ks-cboxtags">
                                        <li><label class="checkbox">
                                            <input v-model="state.form.smoking" id="예" value="예" type="radio">
                                            <span class="icon"></span><span class="text">예</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.smoking" id="아니오" value="아니오" type="radio">
                                            <span class="icon"></span><span class="text">아니오</span>
                                            </label>
                                        </li>
                                    </ul>
                                </div>
                            </el-form-item>
                            <el-form-item prop="alcohol">
                                <div class="input-name">음주는 얼마나 하시나요?</div>
                                <div>
                                    <ul class="ks-cboxtags">
                                        <li><label class="checkbox">
                                            <input v-model="state.form.alcohol" id="안함" value="안함" type="radio">
                                            <span class="icon"></span><span class="text">안함</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.alcohol" id="거의 안 마신다" value="거의 안마신다" type="radio">
                                            <span class="icon"></span><span class="text">거의 안마신다</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.alcohol" id="가끔 마신다" value="가끔 마신다" type="radio">
                                            <span class="icon"></span><span class="text">가끔 마신다</span>
                                            </label>
                                        </li>
                                        <li><label class="checkbox">
                                            <input v-model="state.form.alcohol" id="자주 마신다" value="자주 마신다" type="radio">
                                            <span class="icon"></span><span class="text">자주 마신다</span>
                                            </label>
                                        </li>
                                    </ul>
                                </div>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
                <div class="register-right-half">
                    <div class="register-img-box">
                        <img class="register-img-item" :src="require('@/assets/images/regist_img4.png')">
                    </div>
                </div>
            </div>
        </el-carousel-item>
        
        <el-carousel-item class="item">
            <div class="register-inside-carousel">
                <div class="register-left-half">
                    <h2>회원 가입</h2>
                    <div>
                    <div class="input-name profile-comment">상대방에게 보여줄 프로필 이미지를 선택하세요</div>
                    <div class="button-and-img-name">
                        <div class="img-preview-box"><img class="img-preview" :src="imagePreview"></div>
                        <div class="img-upload-btn-box"><label class="img-upload-btn" for="imgfiles"><i class="fas fa-arrow-circle-up"></i>&nbsp;업로드</label></div>
                        <input type="file" id="imgfiles" ref="imgfiles" @change="handleFileUpload" multiple style="display:none"/>
                    </div>
                    </div>
                    <br>
                    <br>
                    <div class="input-name">자신의 위치를 선택하세요</div>
                    <br>
                    <div id="map" ref="map" style="width:90%;height:250px; margin:0 auto;">
                        <span id="centerAddr"></span>
                    </div>
                    <br><br>
                    <div class="input-name distance-comment">이성을 찾고자 하는 거리 제한을 설정해주세요</div>
                    <div class="distance-radio">
                        <ul class="ks-cboxtags">
                            <li class="distance-radio"><label class="checkbox">
                                <input v-model="state.form.distance" value="2" type="radio">
                                <span class="icon"></span><span class="text">2km</span>
                                </label>
                            </li>
                            <li class="distance-radio"><label class="checkbox">
                                <input v-model="state.form.distance" value="4" type="radio">
                                <span class="icon"></span><span class="text">4km</span>
                                </label>
                            </li>
                            <li class="distance-radio"><label class="checkbox">
                                <input v-model="state.form.distance" value="6" type="radio">
                                <span class="icon"></span><span class="text">6km</span>
                                </label>
                            </li>
                            <li class="distance-radio"><label class="checkbox">
                                <input v-model="state.form.distance" value="8" type="radio">
                                <span class="icon"></span><span class="text">8km</span>
                                </label>
                            </li>
                            <li class="distance-radio"><label class="checkbox">
                                <input v-model="state.form.distance" value="10" type="radio">
                                <span class="icon"></span><span class="text">10km</span>
                                </label>
                            </li>
                        </ul>
                    </div>
                    <el-button class="register-btn" @click="clickRegister">회원가입</el-button>
                    </div>
                <div class="register-right-half">
                    <div class="register-img-box">
                        <img class="register-img-item" :src="require('@/assets/images/regist_img5.png')">
                        <el-button class="register-btn2" @click="clickRegister">회원가입</el-button>
                    </div>
                </div>
            </div>            
        </el-carousel-item>
    </el-carousel>
    </div>
</div>
</template>
<script>
import { reactive,ref } from 'vue'
import { useStore } from 'vuex'
import router from "@/router";
import axios from 'axios'
import Swal from 'sweetalert2'
var geocoder
let road=''
let old=''
let latitude=0
let longitude=0
let files=[]

function searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청합니다
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
}

function searchDetailAddrFromCoords(coords, callback) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
function displayCenterInfo(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        var infoDiv = document.getElementById('centerAddr');

        for(var i = 0; i < result.length; i++) {
            // 행정동의 region_type 값은 'H' 이므로
            if (result[i].region_type === 'H') {
                infoDiv.innerHTML = result[i].address_name;
                break;
            }
        }
    }    
}
function displayMarker(map,locPosition) {

    
    
    // 지도 중심좌표를 접속위치로 변경합니다
    map.setCenter(locPosition);      
}    

export default {
    
    data () {
      return {
        imagePreview: "#",
        interest:[],
        marker:Object,
        title: "",
        files: [],
        galleryDatas: [],
        url:null,
      }
    },
    methods:{
        handleFileUpload(e) {
            const file = e.target.files[0]
            this.imagePreview = URL.createObjectURL(file)
            console.log(this.$refs.imgfiles.files)
            files = this.$refs.imgfiles.files;
            this.url=files[0].name
            console.log(files);
        },
        initMap() {
                var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
                    mapOption = {
                        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
                        level: 7 // 지도의 확대 레벨
                    };  
                // 지도를 생성합니다    
                var map = new kakao.maps.Map(mapContainer, mapOption); 
                if (navigator.geolocation) {    
                    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
                    navigator.geolocation.getCurrentPosition(function(position) {  
                        var lat = position.coords.latitude, // 위도
                            lon = position.coords.longitude; // 경도
                        var locPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
                        // 마커와 인포윈도우를 표시합니다
                        displayMarker(map,locPosition);
                    });
                } else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
                }
                // 주소-좌표 변환 객체를 생성합니다
                 geocoder = new kakao.maps.services.Geocoder();
                var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
                    infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다
                // 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
                searchAddrFromCoords(map.getCenter(), displayCenterInfo);
                // 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
                kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
                    searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
                        if (status === kakao.maps.services.Status.OK) {
                            var detailAddr = result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
                            detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';
                            
                            var content = '<div class="bAddr">' +
                                            '<span class="title">법정동 주소정보</span>' + 
                                            detailAddr + 
                                        '</div>';
                            
                            // 마커를 클릭한 위치에 표시합니다 
                            marker.setPosition(mouseEvent.latLng);
                            marker.setMap(map);

                            // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
                            infowindow.setContent(content);
                            infowindow.open(map, marker);
                            if(result[0].road_address!=null){
                                road=result[0].road_address.address_name
                            }
                            old=result[0].address.address_name
                            latitude=mouseEvent.latLng.getLat()
                            longitude=mouseEvent.latLng.getLng()
                            console.log(latitude)
                            console.log(longitude)
                            console.log(old)
                            console.log(road)
                        } 
                    });
                });
                
                // 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
                kakao.maps.event.addListener(map, 'idle', function() {
                    searchAddrFromCoords(map.getCenter(), displayCenterInfo);
                });

            setTimeout(() => {
                map.relayout();
            }, 1000)
        },
    },
    mounted() {
         if (window.kakao && window.kakao.maps) {
            this.initMap();
        } else {
            const script = document.createElement('script');
            script.onload = () => kakao.maps.load(this.initMap);
            script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=0f0de04704d7e38d69c53bd08ce9a2b8&libraries=services';
            document.head.appendChild(script);
        }

        // axios.get('http://localhost:8080/cowdog/mem/getImageList')
        //             .then(res => {
        //                 console.log(res.data);
        //                 this.galleryDatas = res.data
        //             })
        //             .catch(error => console.log(error));
    },
    setup(){
    const registerForm = ref(null)
    const store = useStore()

    const confirmId = function(rule, value, callback) {
      if(value!==""){
      store.dispatch('confirmId',{id:value})
      .then(function(result){
        console.log(result)
        if(result.data==="EXIST_USERID"){
          callback(new Error("이미 있는 아이디입니다."))
        }
        else{
          callback()
        }
      })
      .catch(function(err){
        console.log(err)
      })
      }
    }
    const confirmNickname = function(rule, value, callback) {
      if(value!==""){
      store.dispatch('confirmNickname',{nickName:value})
      .then(function(result){
        console.log(result)
        if(result.data==="EXIST_NICKNAME"){
          callback(new Error("이미 있는 닉네임입니다."))
        }
        else{
          callback()
        }
      })
      .catch(function(err){
        console.log(err)
      })
      }
    }
    const validPass = (rule, value, callback) => {
     if(value != state.form.password){
       callback(new Error('입력한 비밀번호와 일치하지 않습니다.'))
     }
     else{
       callback()
     }

   }
    const state = reactive({  
        loading: false,
        form: {
            mbti:[],
            interest:[],
            personality:[],
            hobby:[],
            gender:[],
            id: '',
            password: '',
            nickname: '',
            email: '',
            passwordConfirm:'',
            age:'',
            align: 'left',
            smoking:[],
            alcohol:[],
            religion:[],
            address:'',
            distance:0,
            mymbti:'',
          },
        rules: {
            id: [
                { required: true, message: '아이디를 입력하세요', trigger: 'blur' },
                { message: '최대 15 글자까지 입력 가능합니다', trigger: 'blur', max:15 },
                { message: '최소 4 글자를 입력해야 합니다.', trigger: 'blur', min:4},
                {validator:confirmId}
            ],
            age: [
              { required: true, message: '나이를 입력하세요', trigger: 'blur' }
            ],
            email: [
              { required: true, message: '이메일을 입력하세요 입력하세요', trigger: 'blur' },
              {pattern:/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/ ,message:"이메일형식이 올바르지 않습니다."}
            ],
            password: [
              {required: true,message:'비밀번호를 입력하세요', trigger:'blur'},
              { message: '최대 16 글자까지 입력 가능합니다', trigger: 'blur', max:16 },
              { message: '최소 9 글자를 입력해야 합니다.', trigger: 'blur', min:9},
              { pattern:/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]/, message:"비밀번호는 영문, 숫자, 특수문자가 조합되어야합니다." ,trigger:'blur'}
            ],
            nickname:[
                { required: true, message: '닉네입을 입력하세요', trigger: 'blur' },
                { message: '최소 2 글자를 입력해야 합니다.', trigger: 'blur', min:2},
                {validator:confirmNickname}
            ],
            passwordConfirm:[
                {required: true,message:'비밀번호를 입력하세요', trigger:'blur'},
                { message: '최대 16 글자까지 입력 가능합니다', trigger: 'blur', max:16 },
                { message: '최소 9 글자를 입력해야 합니다.', trigger: 'blur', min:9},
                { pattern:/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]/, message:"비밀번호는 영문, 숫자, 특수문자가 조합되어야합니다." ,trigger:'blur'},
                {validator:validPass}
            ],
             
        },
    })
    
    const profileImageUpload=function(){  
        console.log("이미지 업로드~")
        console.log(files)
        console.log(files[0])
        for (var i = 0; i < files.length; i++) {
            let formData = new FormData();
            formData.append('title', "profile");
            formData.append('files', files[i]);
            formData.append('userId',state.form.id)
            axios.post('https://i5b104.p.ssafy.io/cowdog/mem/profileImgaeUpload',
            formData, {
                headers: {
                'Content-Type': 'multipart/form-data',
                "Access-Control-Allow-Origin": "*",
                },
            }
            )  
            .then(function() {
            console.log('SUCCESS!!');
            })
            .catch(function() {
                console.log('FAILURE!!');
                    });
            }
    }
    const clickRegister = function () {
        
        var dist=0
        if(state.form.distance==2){
            console.log(state.form.distance)
            dist=2
        }else if(state.form.distance==4){
            console.log(state.form.distance)
            dist=4
        }else if(state.form.distance==6){
            console.log(state.form.distance)
            dist=6
        }else if(state.form.distance==8){
            console.log(state.form.distance)
            dist=8
        }else{
            dist=10
        }
        console.log(dist)
        registerForm.value.validate((valid) => {
            if(old==''){
                state.form.address=road
                
            }else{
                state.form.address=old
            
            }
        console.log("회원가입 검사")
        if (valid) {
           if(latitude===0 && longitude===0){
               Swal.fire('!!!' ,'지도에서 자신의 위치를 클릭하세요!!!');
           }
            else if(latitude!==0 && longitude!==0){
                console.log('submit')
                state.loading = true
                store.dispatch('requestRegister', { id:state.form.id, nickname:state.form.nickname, password:state.form.password, 
                                                    email:state.form.email, age:state.form.age,  religion:state.form.religion, mbti:state.form.mbti,
                                                    personality:state.form.personality, hobby:state.form.hobby, gender:state.form.gender, smoking:state.form.smoking,
                                                    alcohol:state.form.alcohol,address:state.form.address,latitude:latitude,longitude:longitude,distance:dist,mymbti:state.form.mymbti})
                .then(function () {
                    profileImageUpload()
                    state.loading = false
                    Swal.fire('SUCCESS' ,'회원가입 성공');
                    router.push({name:"Login"})
                })
                .catch(function (err) {
                    console.log(err)
                })
            } 
        }else {
          Swal.fire('FAIL' ,'회원가입 형식이 올바르지 않습니다.');  
        }
      });
    }
    return { state,clickRegister,registerForm }
  }
}
</script>
<style>
/* 이미지 미리보기 */
.img-preview-box {
    width: 200px;
    height: 200px;
    border-radius: 70%;
    overflow: hidden;
    border: 2px dashed #ff4e7e;
    margin: 10px auto;
}
/* 젤리 체크박스 */
.checkbox input {
    display:none;
}
.checkbox span {
    display: inline-block;
    vertical-align: middle;
    cursor: pointer;
}
.checkbox .icon {
    position:relative;
    width: 17px;
    height: 17px;
    border: 1px solid #c8ccd4;
    border-radius: 3px;
    transition: background 0.1s ease;
}
.checkbox .icon::after {
    content: '';
    position: absolute;
    top: 1px;
    left: 5px;
    width: 5px;
    height: 11px;
    border-right: 2px solid #fff;
    border-bottom: 2px solid #fff;
    transform: rotate(45deg) scale(0);
    transition: all 0.3s ease;
    transition-delay: 0.15s;
    opacity: 0;
}
.checkbox .text {
    margin-left: 5px;
}
.checkbox input:checked ~ .text {
    color: #ff4e7e;
}
.checkbox input:checked ~ .icon {
    border-color: transparent;
    background: #ff4e7e;
    animation: jelly 0.6s ease;
}
.checkbox input:checked ~ .icon::after{
    opacity: 1;
    transform: rotate(45deg) scale(1);
}
@keyframes jelly {
    from {transform: scale(1, 1);}
    30% {transform: scale(1.25, 0.75);}
    40% {transform: scale(0.75, 1.25);}
    50% {transform: scale(1.15, 0.85);}
    65% {transform: scale(0.95, 1.05);}
    75% {transform: scale(1.05, 0.95);}
    90% {transform: scale(1.02, 0.97);}
    to {transform: scale(1, 1);}
}

@media (max-width: 2400px) {
    .register-background {
        width: 100%;
        background: #f0f2f5;
    }
    .register-body{
        background: white;
        width: 60%;
        margin: auto;
        border-radius: 10px;
    }
    .el-carousel {
        height: 1000px;
    }
    .el-carousel__container {
        height: 100%;
    }
    .register-inside-carousel {
        display: flex;
    }
    .register-left-half {
        width: 50%;
    }
    .register-right-half {
        width: 50%;
    }
    .el-carousel .input-name {
        text-align: left;
        margin-left: 1rem;
        font-weight: bold;
    }
    .el-carousel .el-form-item__error {
        margin-left: 1rem;
    }
    .el-carousel .register-img-box {
        width: 100%;
    }
    .el-carousel .register-img-item {
        width: 100%;
        border: 1px solid white;
    }
    .el-carousel__button {
        background-color: #ff4e7e;
        width: 40px;
        height: 5px;
    }
    .register-body .elinput{
        margin: 0 1rem;
        display: block;
        width: 90%;
    }

    /* 이미지 업로드 버튼 커스텀 */
    .img-upload-btn {
        width: 80%;
        border: 1px solid #ff4e7e;
        border-radius: 5px;
        padding: 5px;
        background: #ff4e7e;
        color: white;
        font-weight: bold;
        margin: 1rem auto;
        display: block;
    }
    .img-upload-btn:hover {
        cursor: pointer;
    }
    .img-file-uploaded {
    margin-top: 1.5rem;   
    }

    /* 체크박스 배치 */
    .register-body ul {
        list-style-type: none;
        display: flex;
        flex-wrap: wrap;
        margin: 0;
        padding: 0;
    }

    .register-body .el-carousel__indicators {
        list-style-type: none;
        display: flex;
        flex-wrap: nowrap;
        margin: 0;
        padding: 0;
    }

    .register-body li {
        font-weight: bold;
        padding: 3px;
        border-radius: 15px;
        margin: 1rem;
    }
    .register-body .distance-radio {
        margin: 0.5rem;
    }

    /* 회원가입 버튼 */
    .register-btn {
        display: none;
        width: 90%;
        border: 1px solid #ff4e7e;
        font-weight: bold;
    }
    .register-btn:hover {
        background: #ff4e7e;
        color: white;
    }
    .register-btn:focus {
        background: white;
        border: 1px solid #ff4e7e;
        color: #323545;
    }
    .register-btn2 {
        width: 95%;
        margin-top: 10%;
        border: 1px solid #ff4e7e;
        font-weight: bold;
    }
    .register-btn2:hover {
        background: #ff4e7e;
        color: white;
    }
    .register-btn2:focus {
        background: white;
        border: 1px solid #ff4e7e;
        color: #323545;
    }
}

@media (max-width: 840px) {
    .el-carousel {
        height: 850px;
    }
    .el-carousel__container {
        height: 100%;
    }
    .register-inside-carousel {
        display: block;
    }
    .register-left-half {
        width: 100%;
    }
    .register-right-half {
        display: none;
    }
    .el-carousel .input-name {
        text-align: left;
        margin-left: 1rem;
        font-weight: bold;
    }
    .el-carousel .profile-comment {
        text-align: center;
    }
    .el-carousel .distance-comment {
        text-align: center;
    }
    .el-carousel__button {
        width: 20px;
        height: 3px;
    }
    .distance-radio .ks-cboxtags {
        justify-content: center;
    }
    .register-btn {
        display: block;
        margin: 0 auto;
    }
}
.img-preview {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

</style>
