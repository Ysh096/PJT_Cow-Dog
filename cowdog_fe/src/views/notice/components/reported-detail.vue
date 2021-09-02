<template>
  <el-button class="admin-component-detail" type="text" @click="dialogVisible = true">자세히</el-button>
  <el-dialog
    class="reported-dialog"
    title="상세보기"
    v-model="dialogVisible"
    width="70%"
    >
    <div class="reported_id">신고 대상 ID: {{ reportedUserData.reportedId }}</div>
    <br>
    <br>
    <div class="reported_reason">신고 사유</div>
    <br>
    <pre class="reported_content">{{ reportedUserData.content }}</pre>
    <br>
    <br>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">취소</el-button>
        <el-button @click="deleteUser(reportedUserData.reportedId)">사용자 삭제</el-button>
        <el-button @click="suspendUser(reportedUserData.reportedId)">사용자 정지</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        dialogVisible: false
      };
    },

    props: {
      reportedUserData: {
        type: Object
      }
    },
    methods: {
      handleClose(done) {
        this.$confirm('Are you sure to close this dialog?')
          .then(res => {
            done();
            console.log(res)
            this.dialogVisible = false
          })
          .catch(res => {
            console.log(res)
          });
      },
      deleteUser(reportedId) {
        this.$confirm('정말로 제재하시겠습니까?')
          .then(res => {
            console.log(res)
            this.$store.dispatch('deleteReportedUser', {"user_id": reportedId})
            this.dialogVisible = false
          })
          .catch(err => {
            console.log(err)
          })
      },
      suspendUser(reportedId) {
        this.$confirm('정말로 제재하시겠습니까?')
          .then(res => {
            console.log(res)
            this.$store.dispatch('suspendReportedUser', {"user_id": reportedId})
            this.dialogVisible = false
          })
          .catch(err => {
            console.log(err)
          })
      }
    }
  };
</script>

<style>
.admin-component-detail {
  color: #323545;
  font-weight: bold;
}
.el-dialog {
  text-align: left;
  border-radius: 10px;
}
.reported_id {
  font-size: 16px;
  color: black;
}
.reported_reason {
  font-size: 16px;
  color: black;
}
.reported_content {
  font-weight: normal;
}
.reported_img_box {
  width: 100%;
  border: 1px solid black;
  border-radius: 10px;
}

.el-button--primary {
  background: #ffffff;
  border-color: #ff4e7e;
}
.el-button--primary:hover {
  background: #ffffff;
  border-color: #ff4e7e;
}

.el-button--default {
  background: #ffffff;
  border-color: #324545;
}
.el-button--default:hover {
  background: #ffffff;
  border-color: #ff4e7e;
}
img {
  width: 300px;
  height: 100%;
}
</style>