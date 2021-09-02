<template>
  <div class="login-background">
    <br>
    <div class="login-modal">
      <div class="img-box">
        <img class="cowdog-logo" alt="cow dog logo" :src="require('@/assets/images/onlyLogo.png')">
      </div>
      <div class="login-box">
        <div class="login-form">
          <el-form :model="state.form" :rules="state.rules" ref="loginForm" :label-position="state.form.align">
            <div class="title-box">
              <img class="cowdog-title" alt="cow dog logo" :src="require('@/assets/images/onlyTitle.png')">
            </div>
            <el-form-item prop="id" :label-width="state.formLabelWidth" @keyup.enter="clickLogin">
              <el-input class="elinput" v-model="state.form.id" autocomplete="on"  placeholder="아이디"></el-input>
            </el-form-item>
            <el-form-item prop="password" :label-width="state.formLabelWidth" @keyup.enter="clickLogin">
              <el-input class="elinput" v-model="state.form.password" autocomplete="off" show-password  placeholder="비밀번호"></el-input>
            </el-form-item>
          </el-form>
          <el-button class="loginBtn" @click="clickLogin">로그인</el-button>
          <div class="finding-password">
            <span>비밀번호를 잊으셨나요? </span><FindingPasswordModal />
          </div>
        </div>

        <div class="register-form">
          <span>계정이 없으신가요? </span>
          <router-link to='/Register'>가입하기</router-link>
        </div>
      </div>
    </div>
  </div>
</template>



<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .login-background{
    /* background: #e2e2e2; */
    /* background: rgb(255,209,221); */
    background: #f0f2f5;
    /* background: linear-gradient(90deg, rgba(255,209,221,1) 0%, rgba(228,230,241,1) 100%); */
    height: 50vw;
    }

  .login-modal {
    display: flex;
    margin: 5rem auto;
    width: 70%;
    height: 70%;
    border-radius: 10px;
    background: #ffffff;
  }

  .login-modal .img-box {
    width: 50%;
  }

  .img-box .cowdog-logo {
    width: 100%;
    height: 60%;
    margin: 3rem auto;
  }

  .title-box {
    width: 80%;
    margin: auto;
  }

  .title-box .cowdog-title {
    width: 100%;
  }
  .login-modal .login-box {
    width: 50%;
    margin: 2rem auto;
  }

  .el-input::placeholder{
    color: black;
  }

  .login-background .el-form-item__error {
    font-weight: bold;
    left: 15%;
  }

  .login-background .el-button{
    width: 70%;
    margin: 1rem 0 2rem 0;
  }

  .login-background .el-button--default {
    border-color: #323545;
  }

  .login-background .el-button--default:hover {
    /* background: #ff4e7e; */
    color: #ff4e7e;
    border-color: #ff4e7e;
  }

  .login-background .el-button--default:focus {
    background: #ff4e7e;
    border-color: #ff4e7e;
    color: white;
  }

  .login-background .elinput {
    width: 70%;
  }

  .finding-password {
    margin-bottom: 1rem;
    font-size: 12px;
    font-weight: bold;
  }

  .finding-password a {
    text-decoration: none; 
    color: #ff4e7e;
  }

  .register-form {
    padding: 5px 0 5px 0;
    font-size: 12px;
    font-weight: bold;
  }

  .register-form a {
    text-decoration: none;
    color: #ff4e7e;
  }
@media (max-width: 1024px) {
  .login-background{
    /* background: #e2e2e2; */
    /* background: rgb(255,209,221); */
    background: linear-gradient(90deg, rgba(255,209,221,1) 0%, rgba(228,230,241,1) 100%);
    height: 70vw;
    }

  .login-modal {
    height: 55%;
  }

  .img-box .cowdog-logo {
    height: 50%;
  }
  .title-box {
    width: 70%;
  }
  .title-box .cowdog-title {
    width: 100%;
  }
  .login-modal .login-box {
    margin: 1rem auto;
  }
  .login-background .el-button{
    margin: 0 0 1rem 0;
  }
}

@media (max-width: 768px) {
  .login-background{
    /* background: #e2e2e2; */
    /* background: rgb(255,209,221); */

    background: linear-gradient(90deg, rgba(255,209,221,1) 0%, rgba(228,230,241,1) 100%);
    height: 100vw;
    }

  .login-modal {
    display: block;
    margin: 3rem auto;
    width: 60%;
    border-radius: 10px;
    background: #ffffff;
  }
  .login-form {
    margin: 0 auto;
  }
  .login-modal .img-box {
    margin: 0 auto;
  }
  .img-box .cowdog-logo {
    margin: 0;
  }
  .login-modal .login-box {
    margin: 0 auto;
  }
  .login-background .el-button{
    width: 100%;
    margin: 0 0 5px 0;
  }
  .login-background .elinput {
    width: 100%;
  }
}
</style>



<script>
import { reactive,ref } from 'vue'
import { useStore } from 'vuex'
import router from "@/router"
import Swal from 'sweetalert2'
import FindingPasswordModal from "../findpassword/FindingPasswordModal.vue"
export default {
  name: "LoginPage",
  components: {
    FindingPasswordModal
  },
  props: {
    msg: String,
  },
 
  setup(){
    const loginForm = ref(null)
    const store = useStore()
    const state = reactive({
      form: {
        id: '',
        password: '',
        align: 'left',
        isLoading:Boolean
      },
      rules: {
        id: [
          { required: true, message: '아이디를 입력하세요', trigger: 'blur' }
        ],
        password: [
          {required: true,message:'비밀번호를 입력하세요', trigger:'blur'},
          { message: '최대 16 글자까지 입력 가능합니다', trigger: 'blur', max:16 },
          { message: '최소 9 글자를 입력해야 합니다.', trigger: 'blur', min:9},
          { pattern:/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]/, message:"비밀번호는 영문, 숫자, 특수문자가 조합되어야합니다." ,trigger:'blur'}
        ],
      },
    })

    const clickLogin = function () {
      state.form.isLoading=true;
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      loginForm.value.validate((valid) => {
        console.log("로그인 검사 통과")
        if (valid) {
          console.log('submit')
          store.dispatch('requestLogin', { id: state.form.id, password: state.form.password })
          .then(function (result) {
            console.log(result.data)
           if(result.data.message==="NOT_EXISTS_USER"){
             Swal.fire('FAIL' ,'회원이 아닙니다.');
            }
            else if(result.data.message==="PASSWORD_INCORRECT"){
              Swal.fire('FAIL' ,'아이디나 비밀번호를 확인해주세요');
            }else if(result.data.message==="ISSUSPENDED"){
              Swal.fire('ISSUSPENDED' ,'정지된 계정입니다.');
            }
            else{
              // var login_user={}
              // login_user.userId=result.data.id
              // login_user.accessToken=result.data.accessToken

              // 유저 PK와 토큰 정보를 store에 저장한다.
              store.commit("GET_LOGIN_USER",{userId:result.data.id,accessToken:result.data.accessToken})
              // 유저 정보(myPage에 출력되는 정보)를 store에 저장한다.

              store.dispatch("setUserInfo")

              router.push({name:"Main"}) //로그인 성공하면 메인페이지로 이동
            }
          })
          .catch(function () {
            Swal.fire('FAIL' ,'회원정보가 없습니다.');
          })
        } else {
          state.form.isLoading=false
          Swal.fire('FAIL' ,'로그인 형식이 올바르지 않습니다.');
        }
      });
    }
    return { state,clickLogin,loginForm }
  }
};
</script>