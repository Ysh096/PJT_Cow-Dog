<template>
  <div class="article-create-background">    
    <h1>이성에게 매력을 어필해 보세요👍</h1>
    <el-row justify="center">
      <el-col :span="12">
        <el-form label-position="top"
        :model="state.articleForm" 
        :rules="state.rules"
        ref="articleForm">
          <el-form-item label="Title" prop="title">
            <el-input
            class="elinput" 
            v-model="state.articleForm.title" 
            maxlength="20"
            show-word-limit
            autocomplete="on" 
            placeholder="제목"
            style="width: 100%;"
            />
          </el-form-item>
          
          <el-form-item label="Content" prop="content">
            <el-input 
            type="textarea" 
            resize="none"
            maxlength="500" 
            show-word-limit
            rows="15"
            v-model="state.articleForm.content" 
            autocomplete="off"
            placeholder="내용" 
            />
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


          <!-- 이미지 업로드 -->
          <!-- <div class="input-name profile-comment">함께 올릴 사진을 선택하세요</div>
            <div class="button-and-img-name">
            <div class="appeal-img-preview-box"><img class="appeal-img-preview" :src="imagePreview"></div>
            <div class="img-upload-btn-box"><label class="img-upload-btn" for="imgfiles"><i class="fas fa-arrow-circle-up"></i>&nbsp;업로드</label></div>
            <input type="file" id="imgfiles" ref="imgfiles" @change="handleFileUpload" multiple style="display:none"/>
          </div> -->

          <!-- <el-button size="small" type="primary">Click to upload</el-button> -->
          <!-- 하단에 표시할 작은 툴팁 메세지 -->
          <!-- <template #tip>
            <div class="el-upload__tip">
              jpg/png files with a size less than 500kb
            </div>
          </template> -->
        

          <div style="margin-top: 20px;"></div>
          <el-button default type="button" @click="create()" style="width: 100%;">작성</el-button>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { useStore } from 'vuex'
import { reactive, ref } from 'vue'
import router from '../../../router'
import axios from 'axios'
import Swal from 'sweetalert2'

let files=[]

export default {
name: 'AppealCreate',
data(){
    return{
      imagePreview: "#",
      files:[],
      url:null,
    }
  },

setup() {
  const articleForm = ref(null)
  const store = useStore()
  const tagDOM = ref('$tagForm')
  console.log(tagDOM)

  const state = reactive({
    headerInfo: {
        'Access-Control-Allow-Origin': '*',
        Authorization:"Bearer "+store.getters.getUserToken,
        ContentType: 'multipart/form-data'
    },
    fileList: [],
    store: store,
    articleForm: {
      title: '',
      content: '',
      member_id: store.getters.getUserId,
      writer: store.getters.getUserInfo.memberid,
      tagKeyword: '',
      tags: [],
    },
    rules: {
      title: [
        { required: true, message: '제목을 입력하세요', trigger: 'blur' }
      ],
      content: [
        { required: true, message: '내용을 입력하세요', trigger: 'blur' }
      ],
    },
  })

  if (store.getters.getUserToken === '') {
    Swal.fire('FAIL', '로그인 해주세요.')
    router.push("/login")
  }

  return {
    state,
    articleForm,
  }
},
methods: {
  handleFileUpload(e) {
            const file = e.target.files[0]
            this.imagePreview = URL.createObjectURL(file)
            console.log(this.$refs.imgfiles.files)
            files = this.$refs.imgfiles.files;
            this.url=files[0].name
            console.log(files);
          },

  ImageUpload(){
      console.log("이미지 업로드~")
        console.log(files)
        console.log(files[0])
        console.log(this.state.articleForm.member_id)
        for (var i = 0; i < files.length; i++) {
            let formData = new FormData();
            formData.append('title', "profile");
            formData.append('files', files[i]);
            formData.append('userId',this.state.articleForm.writer)
            axios.post('http://localhost:8080/cowdog/appeal/imageListImgaeUpload',
            
            formData, {
                headers: {
                'Content-Type': 'multipart/form-data',
                Authorization:"Bearer "+this.state.store.getters.getUserToken
                },
            }
            )  
            .then(function() {
            console.log('SUCCESS!!');
            })
            .catch(function() {
                console.log('FAILURE!!');
                    });
            }
  },
  create() {
    if (this.state.articleForm.title == '') {
      Swal.fire('FAIL', '제목을 입력해주세요.')
      return
    }
    if (this.state.articleForm.content == '') {
      Swal.fire('FAIL', '내용을 입력해주세요.')
      return
    }
    // this.ImageUpload()
    // 작성한 내용을 axios 요청하기 전에, 태그 리스트를 하나의 String으로 변환한다.
    let result = ''
    for (let i = 0; i < this.state.articleForm.tags.length; i++) {
      // 단어 사이 사이 공백도 제거해야 한다.
      result += this.state.articleForm.tags[i]
    }

    this.state.store.dispatch("createArticle", 
        { title: this.state.articleForm.title, 
        content: this.state.articleForm.content, 
        member_id: this.state.articleForm.member_id ,
        writer: this.state.articleForm.writer,
        tags: result,
        })

    this.ImageUpload()
  },


  enterTag() {
    // 태그가 10개를 초과하는 경우 반환
    const tags = this.state.articleForm.tags

    if ( tags.length === 10 ) {
      Swal.fire('태그는 최대 10개입니다.')
      return
    }

    // 입력하지 않고 엔터 누른 경우 금지
    if (this.state.articleForm.tagKeyword.trim() === '' || this.state.articleForm.tagKeyword.trim() === '#'){
      Swal.fire('추가할 태그를 입력하세요.')
      return
    }

    // #을 포함하지 않거나, #이 가장 앞에 오지 않은 경우
    if ( this.state.articleForm.tagKeyword.trim().indexOf('#') === -1 || this.state.articleForm.tagKeyword.trim().indexOf('#') !== 0 ){
      Swal.fire('올바른 위치에 #태그를 사용해 주세요.')
    }
    // 조건에 부합하는 경우 tags에 추가한다. 
    else {
      // 이미 리스트에 있는지 검사
      for (let i = 0; i < tags.length; i++) {
        if ( tags[i] === this.state.articleForm.tagKeyword.trim() ) {
          Swal.fire('이미 존재하는 태그입니다.')
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
    console.log(tag)
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
.article-create-background{
  
}
</style>