<template>
  <div class="article-update-background">
    <h1>게시글 수정</h1>
    <div class="article-update-container">
      <el-form class="article-update-form" :model="state.articleForm" :rules="state.rules" ref="articleForm">
        <el-form-item prop="title">
          <label for="title" class="title-label">제목</label>
          <el-input 
          type="text" 
          class="article-title-update" 
          v-model="state.articleForm.title"
          maxlength="20"
          show-word-limit></el-input>
        </el-form-item>
        <br>
        <el-form-item prop="content">
          <label for="content" class="content-label">내용</label>
          <el-input 
          type="textarea" 
          class="article-content-update"
          resize="none"
          v-model="state.articleForm.content" 
          rows="15"
          maxlength="500"
          show-word-limit></el-input>
        </el-form-item>
        
        <!-- 태그 -->
        <el-form-item label="Tags" prop="tag" id="tagForm">
          <el-input
          class="elinput"
          v-model="state.articleForm.tagKeyword"
          placeholder="#태그를 입력해 주세요. (최대 10개, 태그당 최대 길이 10자)"
          maxlength="10"
          @keyup.enter="enterTag()"
          />
        </el-form-item>
        
        <!-- 추가한 태그를 표시하는 곳.. -->
        <el-row style="min-height: 60px; margin-bottom: 20px;" justify="start" align="top">
          <el-col class="tag-box" 
          :span="3" 
          v-for="tag in state.articleForm.tags" :key="tag"
          @click="removeTag(tag)"
          >
            {{ tag }}
          </el-col>
        </el-row>

        <div class="article-update-button" @click="update()" style="width: 95%;">수정</div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { useRoute } from 'vue-router'
import { useStore } from 'vuex'
import { reactive, ref } from 'vue'
import router from '../../../router'
import Swal from 'sweetalert2'
export default {
name: 'AppealUpdate',
setup() {
  const articleForm = ref(null)
  const store = useStore()
  const state = reactive({
    articleForm: {
      articleNo: '',
      title: '',
      content: '',
      tags: [],
      member_id: store.getters.getUserId,
      writer: store.getters.getUserInfo.memberid
    },
    rules: {
      title: [
        { required: true, message: '제목을 입력하세요', trigger: 'blur' }
      ],
      content: [
        { required: true, message: '내용을 입력하세요', trigger: 'blur' }
      ],
    },
    store: store,
  })

  if (store.getters.getUserToken === '') {
    Swal.fire('로그인 해주세요.')
    router.push("/login")
  }

  // 대상 게시글 정보를 불러온다. 
  const route = useRoute()
  state.articleForm.articleNo = route.params.articleNo

  store.dispatch("getArticle", { articleNo: state.articleForm.articleNo })
  .then(function (result) {
    console.log("수정 게시글 불러온 결과: ", result.data)

    // 태그 전처리 - 단일 게시글이므로 getArticles에서 했던 반복문 처리 불필요
    if (result.data.tags !== null) {
      const tags = result.data.tags.split('#').slice(1)
      result.data.tags = tags
    }
    
    // 태그 개별항목에 대해, 다시 #붙여준다.
    for (let i = 0; i < result.data.tags.length; i++) {
      const temp = '#' + result.data.tags[i]
      result.data.tags[i] = temp
    }
    console.log("태그 처리후: ", result.data.tags)
    
    state.articleForm.title = result.data.title
    state.articleForm.content = result.data.content
    state.articleForm.tags = result.data.tags
  })

  return {
    state,
    articleForm
  }
},
methods: {
  update() {
    if (this.state.articleForm.title == '') {
      Swal.fire("제목 입력")
      return
    }
    if (this.state.articleForm.content == '') {
      Swal.fire("내용 입력")
      return
    }

    // 작성한 내용을 axios 요청하기 전에, 태그 리스트를 하나의 String으로 변환한다.
    let result = ''
    for (let i = 0; i < this.state.articleForm.tags.length; i++) {
      // 사이사이 공백 제거
      result += this.state.articleForm.tags[i]
    }

    this.state.store.dispatch("updateArticle", 
        { articleNo: this.state.articleForm.articleNo,
        title: this.state.articleForm.title, 
        content: this.state.articleForm.content, 
        member_id: this.state.articleForm.member_id,
        writer: this.state.articleForm.writer,
        tags: result,
        }) 
  },

  enterTag() {
    // 태그가 10개를 초과하는 경우 반환
    const tags = this.state.articleForm.tags

    if ( tags.length === 10 ) {
      Swal.fire("태그는 최대 10개 입력 가능합니다")
      return
    }

    // 입력하지 않고 엔터 누른 경우 금지
    if (this.state.articleForm.tagKeyword.trim() === '' || this.state.articleForm.tagKeyword.trim() === '#'){
      Swal.fire("추가할 태그를 입력해주세요")
      return
    }

    // #을 포함하지 않거나, #이 가장 앞에 오지 않은 경우
    if ( this.state.articleForm.tagKeyword.trim().indexOf('#') === -1 || this.state.articleForm.tagKeyword.trim().indexOf('#') !== 0 ){
      Swal.fire("올바른 위치에 #태그를 사용해주세요")
    }
    // 조건에 부합하는 경우 tags에 추가한다. 
    else {
      // 이미 리스트에 있는지 검사
      for (let i = 0; i < tags.length; i++) {
        if ( tags[i] === this.state.articleForm.tagKeyword.trim() ) {
          Swal.fire("이미 존재하는 태그입니다")
          this.state.articleForm.tagKeyword = ''
          return
        }
      }

      tags.push(this.state.articleForm.tagKeyword.trim().replace(/(\s*)/g, ""))
      this.state.articleForm.tagKeyword = ''
      console.log(tags)
    }
  },

  removeTag(tag) {
    console.log("제거할 태그:", tag)
    // 찾아서 없애기
    for (let i = 0; i < this.state.articleForm.tags.length; i++) {
      if (tag === this.state.articleForm.tags[i]) {
        this.state.articleForm.tags.splice(i, 1)
      }
    }
  }
},
}
</script>

<style>
.tag-box {
  color: black; 
  margin: 1px; 
  font-size:13px; 
  padding: 2px; 
  border-radius: 0.2rem; 
  cursor: pointer;
}
.article-update-background {
  background: #f0f2f5;
  padding: 2%;
}
.article-update-container {
  width: 60%;
  margin: 0 auto;
  padding: 3%;
  border-radius: 10px;
  background: #fff;
}
.title-label {
  display: block;
  font-weight: bold;
  font-size: 20px;
  text-align: left;
}
.content-label {
  display: block;
  font-weight: bold;
  font-size: 20px;
  text-align: left;
}
.article-update-container .el-input__inner {
  border: 0;
  border-bottom: 1px solid #f0f2f5;
  border-radius: 0;
  padding: 0;
}
.article-update-button {
  width: 90%;
  margin: 0 auto;
  padding: 1%;
  font-weight: bold;
  font-size: 14px;
  border: 2px solid #ff4e7e;
  border-radius: 5px;
}
.article-update-button:hover {
  cursor: pointer;
  background: #ff4e7e;
  color: #fff;
}
</style>