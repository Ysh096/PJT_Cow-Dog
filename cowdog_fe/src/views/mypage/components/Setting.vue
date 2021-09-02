<template>
  <div class="account-box">
    <div class="password-change-box">
      <el-form :model="state.form" :rules="state.rules" ref="changePasswordForm" :label-position="state.form.align">
        <el-form-item prop="curPassword"  :label-width="state.formLabelWidth" >
          <div style="font-weight:bold">비밀번호 설정</div>
          <div class="setting-label">현재 비밀번호</div>
          <el-input class="elinput" v-model="state.form.curPassword" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item prop="newPassword"  :label-width="state.formLabelWidth">
          <div class="setting-label">새로운 비밀번호</div>
          <el-input class="elinput" v-model="state.form.newPassword" autocomplete="off"  show-password></el-input>
        </el-form-item>
        <el-form-item prop="newPasswordConfirm"  :label-width="state.formLabelWidth">
          <div class="setting-label">새로운 비밀번호 확인</div>
          <el-input class="elinput" v-model="state.form.newPasswordConfirm" autocomplete="off" show-password ></el-input>
        </el-form-item>
          <div>
            <el-button class="paasword_update_btn" @click="change_password">비밀번호 변경</el-button>
          </div>
      </el-form>
    </div>

    <div class="account-withdrawal-box">
      <div class="account-withdrawal">
        <el-form :model="state.form" :rules="state.rules" ref="deleteForm" :label-position="state.form.align"> 
          <el-form-item prop="confirmMessage"  :label-width="state.formLabelWidth" >
              <div style="font-weight:bold">회원 탈퇴</div>
              <label style="font-weight:bold">탈퇴하시겠습니까?</label>
              <el-input  class="elinput" v-model="state.form.confirmMessage" autocomplete="off" placeholder="탈퇴하겠습니다"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="withdrawal-btn">
        <el-button class="delete_user" @click="delete_user">계정 탈퇴</el-button>
      </div> 
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex'
import { reactive,ref } from 'vue'
// import router from '../../../router'
export default {
   setup() {
    const changePasswordForm=ref(null)
    const deleteForm=ref(null)
    const store = useStore()
    const validPass = (rule, value, callback) => {
        if(value != state.form.newPassword){
        callback(new Error('입력한 비밀번호와 일치하지 않습니다.'))
        }
        else{
        callback()
        }
    }
    const validMessage=(rule,value,callback)=>{
        if(value != "탈퇴하겠습니다"){
        callback(new Error('"탈퇴하겠습니다"를 올바르게 써주세요'))
        }
        else{
        callback()
        }
    }
    const state = reactive({
      form: {
        curPassword:'',
        newPassword: '',
        newPasswordConfirm:'',
        confirmMessage:''
      },
      rules: {
        curPassword:[
            {required: true,message:'비밀번호를 입력하세요', trigger:'blur'},
          { message: '최대 16 글자까지 입력 가능합니다', trigger: 'blur', max:16 },
          { message: '최소 9 글자를 입력해야 합니다.', trigger: 'blur', min:9},
          { pattern:/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]/, message:"비밀번호는 영문, 숫자, 특수문자가 조합되어야합니다." ,trigger:'blur'},
          
        ],
        newPassword: [
          {required: true,message:'새로운 비밀번호를 입력하세요', trigger:'blur'},
          { message: '최대 16 글자까지 입력 가능합니다', trigger: 'blur', max:16 },
          { message: '최소 9 글자를 입력해야 합니다.', trigger: 'blur', min:9},
          { pattern:/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]/, message:"비밀번호는 영문, 숫자, 특수문자가 조합되어야합니다." ,trigger:'blur'}
        ],
        newPasswordConfirm:[
            {required: true,message:'새로운 비밀번호를 입력하세요', trigger:'blur'},
            { message: '최대 16 글자까지 입력 가능합니다', trigger: 'blur', max:16 },
            { message: '최소 9 글자를 입력해야 합니다.', trigger: 'blur', min:9},
            { pattern:/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]/, message:"비밀번호는 영문, 숫자, 특수문자가 조합되어야합니다." ,trigger:'blur'},
            {validator:validPass}
          ],
          confirmMessage:[
              {validator:validMessage}
          ]
          
      },
    
    })
    const delete_user = function () {
        console.log("회원 탈퇴")
        deleteForm.value.validate((valid) => {
        if (valid) {
           
          console.log('delete')
          store.dispatch('deleteMember', {id:store.getters.getUserId})
          
        } 
      });
    }
    const change_password = function () {
        console.log("비밀번호 변경")
        changePasswordForm.value.validate((valid) => {
        if (valid) {
           
          console.log('submit')
          store.dispatch('changePassword', {newPassword:state.form.newPassword
          , id:store.getters.getUserId, curPassword:state.form.curPassword})
          
        } 
      });
    }




    return { state, change_password,changePasswordForm,delete_user,deleteForm} 
  }
}
</script>

<style>
.account-box {
  display: flex;
  justify-content: center;
}
.account-box .elinput {
  width: 90%;
}
.setting-label {
  text-align: left;
  font-weight: bold;
  margin-left: 1rem;
}
.password-change-box {
  width:20%; height:400px;
  background: white;
  margin: 5rem;
  border-radius: 10px;
}
.password-change-box .el-input__inner {
  background-color: #f0f2f5;
}

.password-change-box label {
  font-weight: bold;
}

.password-change-box .el-button:focus {
  color: #ff4e7e;
  border-color: #ff4e7e;
  background: white;
}

.account-box .el-form-item__error {
  margin-left: 1rem;
}

.account-withdrawal-box {
  margin: 5rem;
  width: 20%;
  background-color: white;
  height: 200px;
  border-radius: 10px;
}

.account-withdrawal-box .el-input__inner {
  background-color: #f0f2f5;
}

.withdrawal-btn .el-button {
  color: red;
  border-color: red;
}

.withdrawal-btn .el-button:hover {
  color: white;
  background: red;
}
.withdrawal-btn .el-button:focus {
  color: white;
  background: red;
}
</style>