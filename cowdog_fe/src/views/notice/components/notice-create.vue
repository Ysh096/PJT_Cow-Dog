<template>
<el-form :model="ruleForm" :rules="rules" ref="ruleForm">
  <h1>공지사항 작성</h1>
  <div class="notice-title">
    <el-form-item prop="title">
      <el-input v-model="ruleForm.title" placeholder="제목을 입력해주세요."></el-input>
    </el-form-item>
  </div>
  <div class="notice-content">
    <el-form-item prop="content">
      <el-input type="textarea" v-model="ruleForm.content" placeholder="내용을 입력해주세요." rows="20"></el-input>
    </el-form-item>
  </div>

  <el-form-item>
    <el-button @click="submitForm('ruleForm')">작성</el-button>
  </el-form-item>
</el-form>
</template>

<script>
import Swal from 'sweetalert2'
  export default {
    data() {
      return {
        ruleForm: {
          title: '',
          content: ''
        },
        rules: {
          title: [
            { required: true, message: '제목을 입력해주세요.', trigger: 'blur' },
            { min: 3, max: 100, message: '3글자 이상, 100글자 이하로 입력해주세요.', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '내용을 입력해주세요.', trigger: 'blur' },
            { min: 3, message: '3글자 이상 입력해주세요.', trigger: 'blur' }
          ]
        },
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            Swal.fire('작성 완료', '공지사항 작성이 완료되었습니다.')
            this.$store.dispatch('postNotice', {"title": this.ruleForm.title, "content": this.ruleForm.content})
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
</script>

<style scoped>
.el-button {
  width: 10%;
}
.el-button:focus {
  background: #ffffff;
  border-color: #ff4e7e;
  color: #ff4e7e;
}
</style>
