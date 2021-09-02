<template>
  <a class="finding-password-btn" @click="dialogVisible = true">비밀번호 찾기</a>

<el-dialog
  title="비밀번호 찾기"
  v-model="dialogVisible"
  width="30%">
  <div class="wrapper">
      <div class="findForm">
          <el-form :model="state.form" :rules="state.rules" ref="findingPasswordForm" :label-position="state.form.align">
              <el-form-item prop="findingId"  :label-width="state.formLabelWidth" >
                  <div class="input-name">가입한 아이디를 입력해주세요</div>
                  <el-input  class="elinput" v-model="state.form.findingId" autocomplete="off" ></el-input>
              </el-form-item>
              <el-form-item prop="email"  :label-width="state.formLabelWidth">
                  <div class="input-name">회원가입시 작성한 이메일을 입력하세요</div>
                  <el-input class="elinput" v-model="state.form.email" autocomplete="off" placeholder="example@example.com"></el-input>
              </el-form-item>
          </el-form>
          <div>
            <el-button class="valid_btn" @click="authentication">인증하기</el-button>
          </div>
      </div>
  </div>
</el-dialog>
</template>

<script>
import { reactive,ref } from 'vue'
import { useStore } from 'vuex'
  export default {
    data() {
      return {
        dialogVisible: false
      };
    },
    setup() {
        const store = useStore()
        const findingPasswordForm=ref(null)
        
        const validMessage=(rule,value,callback)=>{
                if(value!==""){
            store.dispatch('confirmId',{id:value})
            .then(function(result){
                console.log(result)
                if(result.data==="NOT_EXISTS_USERID"){
                callback(new Error("아이디가 존재하지 않습니다."))
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
        
        // const store= userStore()
        const state=reactive({
            form:{
                findingId:'',
                email:'',
            },
            rules: {
                findingId:[
                    {required: true,message:'아이디를 입력하세요', trigger:'blur'},
                    {validator:validMessage}
                ],
                email:[
                    {required: true,message:'이메일을 입력하세요', trigger:'blur'},
                    {pattern:/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/ ,message:"이메일형식이 올바르지 않습니다."}
                ]
            }

        })
        const authentication =function(){
            console.log("이메일 인증")
            findingPasswordForm.value.validate((valid) => {
            if (valid) {
            
                console.log('유효성 검사 통과')
                store.dispatch('Authentication', {id:state.form.findingId
                ,email:state.form.email})
            
            } 
        });
        }
        return {state,findingPasswordForm,authentication}
    }
  };
</script>

<style scoped>
  .wrapper {
    text-align: center;
  }
  .finding-password-btn:hover {
    cursor: pointer;
  }
  .login-background .elinput {
    width: 90%;
    margin: 0 auto; 
  }
  .login-background .el-button {
    width: 90%;
  }
  .input-name {
    text-align: left;
    margin-left: 5%;
  }
  @media (max-width: 1024px) {
    .input-name {
      font-size: 12px;
    }
  }
  @media (max-width: 800px) {
    .input-name {
      font-size: 10px;
    }
  }
</style>

<style>
   .findForm .el-form-item__error {
    left: 5% !important;
  }
  @media (max-width: 1024px) {
    .findForm .el-form-item__error {
      font-size: 10px;
    }
  }

</style>