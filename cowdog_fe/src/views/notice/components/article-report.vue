<template>

<div class="container">
  <div class="batch" style="display:flex">
      <h1 class="title">게시글 신고 관리</h1>
  </div>
  <div v-if="!reportedArticlesPaginated.length">
    <h3>게시글 신고가 없습니다!</h3>
  </div>
  <div v-else class="reportedArticle-list">
    <div v-for="(reportedArticle, idx) in reportedArticlesPaginated" :key="idx">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>신고 대상 번호: {{ reportedArticle.reportedArticleNo }}</span>
          </div>
        </template>
        <pre class="card-body">{{ reportedArticle.content }}</pre>
        <br>
        <div class="card-footer">
          <el-button class="button" type="text"><reportedArticleDetail :reportedArticleData="reportedArticle" /></el-button>
          <el-button class="button" type="text" @click="deleteArticleReport(reportedArticle.articleReportNo)">삭제</el-button>
        </div>
      </el-card>
    </div>
    <div class="pagination">
      <el-pagination
      background
      layout="prev, pager, next"
      :page-size=pageSize
      :total="reportedArticles.length"
      v-model:current-page="currentPage">
      </el-pagination>
    </div> 
  </div> 
</div>

</template>

<script>
import { mapGetters } from 'vuex'
import reportedArticleDetail from './reported-article-detail.vue'

export default {

  data() {
    return {
      // for pagination
      pageSize: 5,
      currentPage: 1,
    }
  },
  components: {
    reportedArticleDetail,
  },

  computed: {
    reportedArticlesPaginated() {
      var start = 0 + (this.currentPage-1) * this.pageSize
      var end = this.currentPage * this.pageSize
      return this.reportedArticles.slice(start, end) //기본값 0~5번
    },
    ...mapGetters({
      reportedArticles: 'getReportedArticles'
    })
  },

  // 신고 POST 예시
  methods: {
    // 게시글 신고 삭제(게시글 삭제 아님)
    deleteArticleReport(articleReportNo) {
      this.$confirm('정말로 신고를 삭제하시겠습니까?')
        .then(res => {
          res
          this.$store.dispatch('deleteArticleReport', articleReportNo)
        })
    }
  },
}
</script>

<style scoped>
  .reportedArticle-list {
    width: 80vw;
  }

  .box-card {
    width: 100%;
  }

  .reportedArticle-list .el-card {
    width: 100%;
    height: 20%;
    margin: 10px;
  }
  .el-button {
    color: #323545;
    font-weight: bold;
  }
  .pagination {
    margin: 0 auto;
  }
</style>