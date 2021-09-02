<template>
<el-form :model="ruleForm" :rules="rules" ref="ruleForm">
  <h1>공지사항 수정</h1>
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

  <div class="notice-image">
    <!-- <h3>이미지 첨부 옵션(추가)</h3> -->
  </div>
  <el-form-item>
    <el-button type="primary" @click="submitForm('ruleForm')">수정 완료</el-button>
  </el-form-item>
</el-form>
</template>

<script>
  export default {
    data() {
      return {
        ruleForm: {
          noticeNo: this.$route.params.noticeNo,
          title: this.$route.params.title,
          content: this.$route.params.content
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
            this.$store.dispatch('updateNotice',
            {"noticeNo": this.ruleForm.noticeNo, "title": this.ruleForm.title, "content": this.ruleForm.content})
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
    }
  }
</script>

<style scoped>

.el-button {
  border-color: #323545;
  color: #323545;
}

.el-button:hover {
  border-color: #ff4e7e;
  color: #ff4e7e;
}

.el-button:focus {
  background: #ffffff;
  border-color: #ff4e7e;
  color: #ff4e7e;
}

.notice-image {
  text-align: left;
}
</style>
