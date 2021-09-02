<template>
<div>
  <el-button class="admin-component-detail" type="text" @click="dialogVisible = true">자세히</el-button>
  <el-dialog
    class="notice-detail"
    title="상세보기"
    v-model="dialogVisible"
    width="70%"
    >
    <div class="reported_id">제목: {{ noticeDetailData.title }}</div>
    <br>
    <br>
    <div class="reported_reason">내용</div>
    <br>
    <pre class="reported_content">{{ noticeDetailData.content }}</pre>
    <br>
    <br>

    <!-- <div class="reported_reason">첨부파일</div> -->
    <br>
    <!-- image box -->
    <!-- <div class="reported_img_box"> -->
      <!-- 첨부 이미지가 있으면 보여주기 -->
      <!-- <img :src="require('@/assets/images/user_report_example_image.png')" alt="reportedScreenImage"> -->
    <!-- </div> -->

    <template #footer>
      <div class="dialog-footer" style="display:flex">
        <div style="margin-right:5px">
          <el-button @click="moveToDetailUpdate(noticeDetailData)">수정</el-button>
        </div>
        <div>
          <el-button @click="handleDelete(noticeDetailData.noticeNo)">삭제</el-button>
        </div>
      </div>
    </template>
  </el-dialog>
</div>
</template>

<script>
import router from '../../../router'
  export default {
    data() {
      return {
        dialogVisible: false
      };
    },
    props: {
      noticeDetailData: {
        type: Object
      }
    },
    methods: {
      moveToDetailUpdate(noticeDetailData) {
        this.dialogVisible = false
        router.push( 
          {name: 'UpdateNotice', params: { noticeNo: noticeDetailData.noticeNo, title: noticeDetailData.title, content:noticeDetailData.content }})
      },
      handleDelete(noticeNo) {
        this.$confirm('정말로 삭제하시겠습니까?')
          .then(() => {
            this.dialogVisible = false
            this.$store.dispatch('deleteNotice', noticeNo)
            this.$router.push('/admin/notice')
          })
          .catch(err => {
            console.log(err)
          })
      }
    }
  };
</script>

<style scoped>
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
  font-weight: bold;
}
.reported_content {
  font-weight: normal;
  line-height: 1.5rem;
  height: 6rem;
}
/* .reported_img_box {
  width: 100%;
  border: 1px solid black;
  border-radius: 10px;
} */

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

.dialog-footer {
  justify-content: flex-end;
}
.dialog-footer .el-button {
  color: #323545;
  font-weight: bold;
  border: 1px solid #323545;
}
.dialog-footer .el-button:hover {
  color: #ff4e7e;
  font-weight: bold;
  border: 1px solid #ff4e7e;
}
</style>