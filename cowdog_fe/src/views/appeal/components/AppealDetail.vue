<template>
  <el-container class="article-container" direction="vertical">

    <!-- 작성자 정보 -->
    <el-row justify="start" align="middle" class="writer-info">
      <!-- 사용자가 등록한 이미지가 존재하는 경우 -->
      <el-col :span="2" v-if="state.file_path !== null" style="align: center;">
        <img class="profile" :src="state.file_path">&nbsp;
      </el-col>
      
      <!-- 사용자가 등록한 이미지가 존재하지 않는 경우 -->
      <el-col :span="2" v-if="state.file_path === null" style="align: center;">
        <img class="profile" :src="require('@/assets/images/defaultProfile.png')"
        >&nbsp;
      </el-col>
      
      <!-- 작성자 이름 -->
      <el-col :span="3">
        <span>{{ this.article.writer }}</span>
      </el-col>
      
      
      <!-- 좋아요 표시 -->
      <el-col :span="2" v-if="!state.like">
        <i class="el-icon-star-off" style="font-size: 30px;"></i>
      </el-col>
      <el-col :span="2" v-else>
        <i class="el-icon-star-on" style="font-size: 30px;"></i>
      </el-col>
      
      
      <!-- 게시글 삭제, 회원 정보 조회 -->
      <el-col :span="17" style="text-align: end; padding-right: 10px;">
        <i class="el-icon-more-outline" style="font-size: 25px; margin-right: 10px;" @click="centerDialogVisible = true"></i>
      </el-col>
      
    </el-row>
    
    <!-- 게시글 제목 -->
    <div class="article-header">
      <div class="article-title">
        {{ this.article.title }}
      </div>
      <!-- 작성일 정보 -->
      <div class="article-created-day">
        {{ this.article.regtime }}
      </div>
    </div>

    <!-- 본문 내용 -->
    <el-main class="el-main">
      <el-row style="text-align: justify; margin-top: 70px; margin-bottom: 70px; white-space:pre-wrap;">
        <!-- {{this.article.file_path}} --> <!-- 이미지는 가능하면 추후 추가 예정 -->
        {{ this.article.content }}
      </el-row>
      <!-- 태그 부분 -->
      <el-row style="color: #9F81F7; font-size: 15px; margin-top: 20px;">
        <div v-for="tag in state.tags" :key="tag" style="cursor: pointer;">
          <strong @click="$emit('send-tag', tag)">#{{ tag }}</strong>&nbsp;&nbsp;
        </div>
      </el-row>
      <!-- 수정 / 삭제 -->
      <el-row class="article-button-group" justify="end" v-if="this.state.loginId === this.article.memberId">
        <!-- Button Group -->
        <div class="article-button" @click="updateArticlePage(this.article)">수정</div>
        <div class="article-button" @click="deleteArticle(this.article)">삭제</div>
      </el-row>
    
      <!-- 댓글 부분 -->
      <el-row style="color: gray; font-size: 15px; margin-top: 20px;">
        <!-- 댓글이 1개 이상 존재하는 경우 -->
        <div v-if="state.comments.length > 0" style="cursor: pointer; margin:3px;">
          <div v-if="!state.default&&(state.comments.length > 3)" @click="state.default = true">
            댓글 {{ state.comments.length }}개 모두 보기 <!-- 댓글이 네 개 이상이면 나타나야 한다. -->
          </div>
          <div v-else-if="state.comments.length > 3 && state.default == true" @click="state.default = false">
            댓글 숨기기
          </div>          
        </div>

        <!-- 댓글이 작성되지 않은 경우 -->
        <div v-else>
          아직 작성된 댓글이 없습니다.
        </div>
      </el-row>
      

      <!-- 기본 3개만 보여주기 -->
      <div v-if="!state.default">
        <el-row v-for="(comment, index) in state.comments" :key="index" justify="space-between">
          <el-col :span="22" v-if="index < 3" style="text-align: start;">
            <strong>{{ comment.id }}</strong> {{ comment.content }} <span style="color: grey; font-size: 12px;">.....{{ comment.regtime }}</span>
          </el-col>
          <el-col :span="2" v-if="index < 3">
            <!-- 내가 작성한 댓글에 대해서만 삭제 가능하다. -->
            <span 
            v-if="comment.id === this.state.store.getters.getUserInfo.memberid" 
            style="color:#ff4e7e; cursor: pointer;"
            @click="deleteComment(comment.no)"
            >x</span>
          </el-col>
        </el-row>
      </div>

      <!-- 모든 댓글 다 보여주기 -->
      <div v-if="state.default">
        <el-row v-for="(comment, index) in state.comments" :key="index" justify="space-between">
          <el-col :span="22" style="text-align: start;">
            <strong>{{ comment.id }}</strong> {{ comment.content }} <span style="color: grey; font-size: 12px;">.....{{ comment.regtime }}</span>
          </el-col>
          <el-col :span="2">
            <span 
            v-if="comment.id === this.state.store.getters.getUserInfo.memberid" 
            style="color:#ff4e7e; cursor: pointer;"
            @click="deleteComment(comment.no)"
            >x</span>
          </el-col>
        </el-row>
      </div>

      <el-divider/>
      <el-row align="middle">
        <el-col :span="22">
          <el-input size="mini" class="elinput" v-model="state.commentContent" placeholder="댓글 달기..." @keyup.enter="createComment()"/>
        </el-col>
        <el-col :span="2">
          <p id="commentCreateBtn" @click="createComment()" style="cursor: pointer;">게시</p>
        </el-col>
        
      </el-row>
    </el-main>
  </el-container>

  <!-- modal창 -->
  <el-dialog
    title=""
    v-model="centerDialogVisible"
    width="30%"
    destroy-on-close
    show-close="false"
    top="17%"
    center
    >
    <div class="dialog" style= "cursor: pointer; font-size: 20px;">
      <el-row @click="centerDialogVisible = false; reportDialogVisible = true">
        <el-col>
          <span style="color: red;">게시글 신고</span>
          <el-divider></el-divider>
        </el-col>
      </el-row>

      <el-row v-if="!state.like" @click="centerDialogVisible = false; articleLike()">
        <el-col>
          <span>게시글 좋아요</span>
          <el-divider></el-divider>
        </el-col>
      </el-row>
      <el-row v-if="state.like" @click="centerDialogVisible = false; articleLike()">
        <el-col>
          <span>좋아요 취소</span>
          <el-divider></el-divider>
        </el-col>
      </el-row>
      <el-row @click="centerDialogVisible = false">
        <el-col>
          <span>취소</span>
        </el-col>
      </el-row>
    </div>
  </el-dialog>

  <!-- 게시글 신고 페이지 modal창 -->
  <el-dialog
    class="reportForm"
    title="게시글 신고"
    v-model="reportDialogVisible"
    width="30%"
    destroy-on-close
    show-close="false"
    top="17%"
    center
    >
    <div class="dialog" style= "cursor: pointer; font-size: 20px;">
      <el-row>
        <el-col>
          <el-input class="elinput" v-model="state.reportArticle.title" placeholder="제목"/>
        </el-col>
      </el-row>
      
      <el-row>
        <el-col>
          <el-input class="textarea" 
          resize="none"
          maxlength="250" 
          show-word-limit
          rows="15"
          v-model="state.reportArticle.content" 
          autocomplete="off"
          placeholder="신고 사유" />
        </el-col>
      </el-row>
      
      <el-row @click="reportArticle(); reportDialogVisible = false">
        <el-col>
          <span>제출</span>
        </el-col>
      </el-row>
    </div>
  </el-dialog>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { useStore } from 'vuex'
import router from '../../../router'
import Swal from 'sweetalert2'

export default {  
  name: 'ArticleDetail',
  props: {
    article: Object,
  },
  
  setup(props) {
    const store = useStore()
    const state = reactive({
      store: store,
      loginId: store.getters.getUserId,
      articleNo: props.article.articleNo,
      file_path: '',
      commentContent: '',
      comments: [],
      default: false,
      tags: props.article.tags,
      reportArticle: {
        title: '',
        content: '',
      },
      like: false,
      
    })  

    // 해당 게시글을 이미 좋아요 눌렀는지 확인
    state.store.dispatch("articleLikeCheck", {id: state.loginId, articleNo: state.articleNo})
      .then(resp => {
        // console.log("좋아요 확인! ", resp.data)
        if (resp.data === "NO") {
          state.like = false;
        } else {
          state.like = true;
        }
      })
      .catch(err => {
        console.log(err)
      })
    
    // 게시글마다 댓글 조회한다.
    state.store.dispatch("findComments", { articleNo: props.article.articleNo })
    .then(resp => {
      state.comments = resp.data
    })
    .catch(err => {
      console.log(err)
    })
    // 게시글 마다 회원 정보를 조회한다. 회원이 등록한 이미지 정보를 출력하기 위해!
    state.store.dispatch("getUserInfo", { userId : props.article.memberId })
    .then(resp => {
      // 회원이 등록한 프로필 이미지 정보를 불러온다.
      state.file_path = resp.data.file_path
    })
    .catch(err => {
      console.log(err)
    })
    return {
      state,
      centerDialogVisible: ref(false),
      reportDialogVisible: ref(false),
    }
  },

  methods: {
    deleteArticle() {
      this.state.store.dispatch("deleteArticle", { articleNo: this.article.articleNo, memberId: this.article.memberId })
      .then(resp => {
        console.log("삭제 완료!", resp.data.content) // 삭제 후 남은 객체
        const result = resp.data.content
        // 태그 전처리하기 -> 태그 개별 항목 검색 위해...
        for (let i = 0; i < result.length; i++) {
          if (result[i].tags !== null) {
            const tags = result[i].tags.split('#').slice(1)
            result[i].tags = tags
          }
        }

        // 날짜 전처리
        for (let i = 0; i < result.length; i++) {
          const date = new Date(result[i].regtime).toDateString()
          result[i].regtime = date
        }

        this.$emit("delete-article", result)
      })
      .catch(err => {
        console.log(err)
      })
    },
    // 페이지 갱신
    updateArticlePage(article) {
      this.state.store.dispatch("updateArticlePage", { article : article })
    },
    
    // 댓글 작성
    createComment() {
      const comment = {
        articleNo: this.article.articleNo,
        memberId: this.state.store.getters.getUserId,
        content: this.state.commentContent,
      }

      // 빈칸인 경우 입력 방지
      if ( this.state.commentContent === '') {
        Swal.fire('내용을 입력해주세요.')
        return
      }
      
      this.state.store.dispatch("createArticleComment", { comment: comment })
      .then(resp => {
        console.log(resp)
        // 댓글 작성후 작성한 내용 없애준다.
        this.state.commentContent = ''
      })
      .catch(err => {
        console.log(err)
      })
      setTimeout(() => {
        this.state.store.dispatch("findComments", { articleNo: this.state.articleNo })
        .then(resp => {
          console.log(resp.data)
          this.state.comments = resp.data
        })
        .catch(err => {
          console.log(err)
        })
      }, 100)
    },

    deleteComment(commentNo) {
      console.log("삭제하고자 하는 댓글 : ", commentNo)
      const result = confirm("댓글을 삭제하시겠습니까?")
      if (result) {
        this.state.store.dispatch("deleteComment", { commentNo: commentNo })
        .then(resp => {
          console.log(resp.data)
        })
        .catch(err => {
          console.log(err)
        })
      setTimeout(() => {
        this.state.store.dispatch("findComments", { articleNo: this.state.articleNo })
        .then(resp => {
          console.log(resp.data)
          this.state.comments = resp.data
        })
        .catch(err => {
          console.log(err)
        })
      }, 100)
      }
    },

    goToReportPage(){
      router.push("/admin/article-report")
    },

    reportArticle() {
      this.state.store.dispatch("postArticleReport", { 
        title: this.state.reportArticle.title, 
        content: this.state.reportArticle.content, 
        reportedArticleNo: this.state.articleNo, 
        articleUrl: '', 
      })
      Swal.fire('신고 완료되었습니다.')
      return
    },

    articleLike() {
      // 사용자 PK
      const id = this.state.loginId
      // 게시글 PK
      const articleNo = this.state.articleNo

      console.log("사용자 PK: ", id, ", 게시글 PK: ", articleNo)
      this.state.store.dispatch("articleLike", {id: id, articleNo: articleNo})
        .then(() =>{
          this.state.like = !this.state.like;
        })
        .catch((err) => {
          console.log(err)
        })
    },


  },

  }
</script>
<style>
.article-container{
  background-color: white;
  margin: 0 auto;
  margin-top: 2%;
  margin-bottom: 2%;
  padding: 1%;
  width: 700px;
  border: 5px solid #f0f2f5;
  border-radius: 0.45rem;
  height: auto;
}

.writer-info {
  font-weight: bold;
  font-size: 16px;
  padding: 1%;
}

.article-header{
  display: flex;
  margin-top: 10px;
  justify-content: space-between;
}
.article-title {
  font-weight: bold;
  font-size: 24px;
  margin-left: 3%;
}
.article-created-day {
  margin-right: 3%;
  font-size: 12px;
  font-weight: bold;
  align-items: flex-end;
}
.el-main{
  padding: 15px;
}

.el-main > *{
  margin-top: 7px;
}

.article-button-group .el-button--default {
  border: 1px solid #323545;
  color: #323545;
}
.article-button-group .el-button--default:hover {
  border: 1px solid #ff4e7e;
}
.article-button {
  font-weight: bold;
  font-size: 14px;
  padding: 1%;
  margin: 1%;
  border-radius: 5px;
}
.article-button:hover {
  background: #ff4e7e;
  color: white;
  cursor: pointer;
}
#commentCreateBtn{
  color: #323545;
  font-weight: bold;
}
.writer-info .profile {
  position: relative;
  top: 8px;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin: 5px;
}
.dialog > *{
  margin: 5px;
  text-align: center;
}
.reportForm *{
  margin-top: 10px;
}
.el-icon-more-outline:hover {
  color: #ff4e7e;
  cursor: pointer;
}
</style>