<template>
  <el-button class="admin-component-detail" type="text" @click="dialogVisible = true">자세히</el-button>
  <el-dialog
    class="reported-article-dialog"
    title="상세보기"
    v-model="dialogVisible"
    width="70%"
    >
    <div class="reported_id">신고 대상 게시글 번호: {{ reportedArticleData.reportedArticleNo }}</div>
    <br>
    <br>
    <div class="reported_reason">신고 사유</div>
    <br>
    <pre class="reported_content">{{ reportedArticleData.content }}</pre>
    <br>
    <br>
    <br>
    <!-- image box -->
    <!-- <div class="reported_url_box">
      <a :href="reportedArticleData.articleUrl">{{ reportedArticleData.articleUrl }}</a>
    </div> -->

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">취소</el-button>
        <el-button @click="deleteReportedArticle(reportedArticleData.reportedArticleNo)">게시글 제재</el-button>
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
      reportedArticleData: {
        type: Object
      }
    },
    methods: {
      deleteReportedArticle(reportedArticleNo) {
        this.$confirm('정말로 제재하시겠습니까?')
          .then(() => {
            var reportedArticleLongNo = reportedArticleNo
            var reportedArticleArticleNo = reportedArticleNo
            this.$store.dispatch('deleteReportedArticle', 
            {"reportedArticleNo": reportedArticleNo, "reportedArticleLongNo": reportedArticleLongNo, "reportedArticleArticleNo": reportedArticleArticleNo})
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
.reported_url_box {
  width: 50%;
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
</style>