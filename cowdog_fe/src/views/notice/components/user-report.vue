<template>

<div class="container">
  <div class="batch" style="display:flex">
      <h1 class="title">유저 신고 관리</h1>
  </div>
  <div v-if="!reportedUsersPaginated.length">
    <h3>유저 신고가 없습니다!</h3>
  </div>
  <div v-else class="reportedUser-list">
    <div v-for="(reportedUser, idx) in reportedUsersPaginated" :key="idx">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>신고 대상 번호: {{ reportedUser.reportedId }}</span>
          </div>
        </template>
        <pre class="card-body">{{ reportedUser.content }}</pre>
        <br>
        <div class="card-footer">
          <el-button class="button" type="text"><reportedDetail :reportedUserData="reportedUser" /></el-button>
          <el-button class="button" type="text" @click="deleteUserReport(reportedUser.userReportNo)">삭제</el-button>
        </div>
      </el-card>
    </div>
    <div class="pagination">
      <el-pagination
      background
      layout="prev, pager, next"
      :page-size=pageSize
      :total="reportedUsers.length"
      v-model:current-page="currentPage">
      </el-pagination>
    </div>  
  </div>
</div>

</template>

<script>
import { mapGetters } from 'vuex'
import reportedDetail from './reported-detail.vue'

export default {

  data() {
    return {
      // for pagination
      pageSize: 5,
      currentPage: 1,
    }
  },
  components: {
    reportedDetail,
  },

  computed: {
    reportedUsersPaginated() {
      var start = 0 + (this.currentPage-1) * this.pageSize
      var end = this.currentPage * this.pageSize
      return this.reportedUsers.slice(start, end) //기본값 0~5번
    },
    ...mapGetters({
      reportedUsers: 'getReportedUsers'
    })
  },

  // 신고 POST 예시
  methods: {
    userReport() {
      this.$store.dispatch('postUserReport', {"title": "정지시켜주세요.", "content": "악질이에요.", "reportedId": "54"})
    },
    deleteUserReport(userReportNo) {
      this.$confirm('정말로 신고를 삭제하시겠습니까?')
        .then(() => {
          this.$store.dispatch('deleteUserReport', userReportNo)
        })
    }
  },
}
</script>

<style scoped>
  .reportedUser-list {
    width: 80vw;
  }

  .reportedUser-list .el-card {
    margin: 10px;
    width: 100%;
    height: 20%;
  }

  .el-pagination.is-background .el-pager li:not(.disabled).active {
    background-color: #FF4E7E;
    color: #FFF;
  }

  .pagination {
    width: 80vw;
  }


  .button {
    color: #323545;
    font-weight: bold;
  }

  .button:hover {
    color: #FF4E7E;
  }

  .create {
    color: #323545;
    font-weight: bold;
  }
  .create:hover {
    background-color: #FFFFFF;
    border-color: #FF4E7E;
    color: #FF4E7E;
  }

  .el-button {
    color: #323545;
    font-weight: bold;
  }

  .el-button:hover {
    color: #FF4E7E;
  }

  .el-button:focus {
    color: #FF4E7E;
  }

</style>