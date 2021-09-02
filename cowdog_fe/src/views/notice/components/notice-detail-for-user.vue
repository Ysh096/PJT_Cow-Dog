<template>
<div>
  <el-button class="admin-component-detail" type="text" @click="dialogVisible = true">자세히</el-button>
  <el-dialog
    class="notice-detail"
    title="공지사항"
    v-model="dialogVisible"
    width="70%"
    >
    <div class="reported_id">{{ noticeDetailData.title }}</div>
    <br>
    <br>
    <pre class="reported_reason">내용</pre>
    <br>
    <div class="reported_content">{{ noticeDetailData.content }}</div>
    <br>
    <br>

    <!-- <div class="reported_reason">첨부파일</div> -->
    <br>
    <!-- image box -->
    <!-- <div class="reported_img_box"> -->
      <!-- 첨부 이미지가 있으면 보여주기 -->
      <!-- <img :src="require('@/assets/images/user_report_example_image.png')" alt="reportedScreenImage"> -->
    <!-- </div> -->
  </el-dialog>
</div>
</template>

<script>
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
  font-weight: bold;
}
.reported_reason {
  font-size: 16px;
  color: black;
}
.reported_content {
  margin: 2% 0;
  border: 2px solid #f0f2f5;
  padding-top: 3%;
  padding-bottom: 3%;
  padding-left: 1%;
  padding-right: 1%;
  border-radius: 5px;
  font-weight: normal;
  color: black;
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