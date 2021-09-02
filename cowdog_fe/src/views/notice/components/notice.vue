<template>
<div>
  <div class="container">
    <div class="batch" style="display:flex">
        <h1 class="title">공지사항</h1>
        <div><el-button class="create" @click="moveToNoticeCreate">Create</el-button></div>
    </div>
    <div v-if="!noticesPaginated.length"><h3>공지사항이 없습니다!</h3></div>
    <div v-else class="notice-list">
      <div v-for="(notice, idx) in noticesPaginated" :key="idx">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>{{ notice.title }}</span>
            </div>
          </template>
          <pre class="card-body">{{ notice.content }}</pre>
          <br>
          <div class="card-footer">
            <noticeDetail :noticeDetailData="notice" />
          </div>
        </el-card>
      </div>
      <div class="pagination">
        <el-pagination
        background
        layout="prev, pager, next"
        :page-size=pageSize
        :total="notices.length"
        v-model:current-page="currentPage">
        </el-pagination>
      </div>
    </div>
  </div>
</div>
</template>

<script>
// import { useStore } from 'vuex'
import { mapGetters } from 'vuex'
import router from '../../../router'
import noticeDetail from './notice-detail.vue'
export default {
  data() {
    return {
      // for pagination
      pageSize: 5, // 한 페이지에 나오는 게시물 수
      currentPage: 1, // 현재 페이지(v-model 적용)
    }
  },

  components: {
    noticeDetail,
  },

  methods: {
    moveToNoticeCreate() {
      router.push('/admin/noticeCreate')
    },
  },

  computed: {
    noticesPaginated() {
      var start = 0 + (this.currentPage-1) * this.pageSize
      var end = this.currentPage * this.pageSize
      return this.notices.slice(start, end) //기본값 0~5번
    },
    ...mapGetters({
      notices: 'getNotices'
    })
  },
}
</script>

<style>
  .title {
    text-align: left;
    margin-left: 10px;
  }

  .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
  }

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .container {
    width: 100%;
  }

  .batch {
    justify-content: space-between;
    align-items: baseline;
  }
  .batch .el-button {
    color: #323545;
    font-weight: bold;
    border: 2px solid #323545;
  }
  .batch .el-button:hover {
    color: #FF4E7E;
    border: 2px solid #FF4E7E;
  }
  .notice-list {
    width: 100%;
  }

  .box-card {
    width: 100%;
  }

  .notice-list .el-card {
    width: 100%;
    height: 30%;
  }
  .notice-list .el-card.is-always-shadow {
    margin: 10px auto;
  }

  .el-pagination.is-background .el-pager li:not(.disabled).active {
    background-color: #FF4E7E;
    color: #FFF;
  }

  .pagination {
    margin: 0 auto;
  }

  .card-body {
    /* 글자 수 제한을 넘어가면 ... 처리, 좌측 정렬 /
    / https://junistory.blogspot.com/2017/06/css-ellipsis.html 참조 */
    text-align: left;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 4;
    -webkit-box-orient: vertical;
    word-wrap:break-word;
    line-height: 1.5rem;
    height: 6rem;
  }

  .card-footer {
    text-align: right;
  }
  .el-button:hover {
    color: #FF4E7E;
  }

  .el-button--primary {
    color: #323545;
    font-weight: bold;
  }
  .el-button--primary:hover {
    color: #ff4e7e;
    border: 1px solid #ff4e7e;
  }
  .el-button--default {
    color: #323545;
    font-weight: bold;
  }
  .el-button--default:hover {
    color: #ff4e7e;
    border: 1px solid #ff4e7e;
  }
</style>