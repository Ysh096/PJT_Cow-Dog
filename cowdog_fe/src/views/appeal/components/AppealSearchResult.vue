<template>
    <el-menu
      :router="true"
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="white"
      style="position: sticky; top: 0; z-index: 1;"
    >
      <el-row justify="center" align="middle" style="margin: 10px;">
        <el-col :span="8">
          <el-input 
          class="elinput" 
          placeholder="검색할 태그를 입력하세요." 
          v-model="state.searchKeyword" 
          @keyup.enter="appealSearch()">
          </el-input>
        </el-col>
      </el-row>
    </el-menu>
  
  <el-row justify="center" v-if="state.articleList.length !== 0" style="margin-bottom: 10px;">
    <el-col :span="12" style="text-align:start;">
      <span style="font-size: 30px; font-weight: bold;">#{{ state.lastKeyword }}&nbsp;</span>에 대한 검색 결과 :
      <strong>{{ this.state.articleList.length }}</strong> 개
    </el-col>
  </el-row>

  <!-- 검색 결과가 존재하는 경우 -->
  <el-row v-if="state.articleList.length !== 0">
    <el-col :span="12" :offset="6"> <!-- offset 설정하면 왼쪽 기준으로 공백 크기 설정 -->
      <div v-for="article in state.articleList" :key="article.articleno"> <!-- 왜 key에다가 콜론을 해줘야 하지..? -->
        <appealDetail :article="article"/>
      </div>
    </el-col>
  </el-row>

  <!-- 검색 결과가 존재하지 않는 경우 -->
  <div v-else style="margin: 50px;">
    <el-row justify="center">
      <i class="el-icon-document-delete" style="font-size: 200px;"></i>
    </el-row>
    <el-row justify="center" style="margin: 25px;">
      검색 결과가 존재하지 않습니다😥
    </el-row>
  </div>

  <el-row style="margin-top: 50px;">
    <el-button 
    type="danger" plain 
    @click="createArticle()" 
    style="color: black; width: 100%; position: fixed; bottom: 0;">새 게시글 작성하기</el-button>
  </el-row>

  
</template>
<script>
import { useRoute } from 'vue-router'
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import router from '../../../router'
import appealDetail from './AppealDetail.vue'
import Swal from 'sweetalert2'

export default {
  name: 'AppealSearchResult',  
  components: {
    appealDetail,
  }, 
  setup() {
    const store = useStore()
    const state = reactive({
      searchKeyword: '',
      lastKeyword: '',
      articleList: {
        type: Array,
      },
      store: store
    })

    if (store.getters.getUserToken === '') {
      Swal.fire('로그인 해주세요.')
      router.push("/login")
    }
    
    // 검색 결과를 articleList에 추가한다.
    state.articleList = store.getters.getSearchResults

    // 검색한 키워드를 표시하기 위해 ...
    const route = useRoute()
    state.lastKeyword = route.params.searchKeyword

    return {
      state
    }
    
  },
  methods: {
    createArticle() {
      router.push("/appeal/create")
    },
    appealSearch() {
      this.state.store.dispatch("appealSearch", { searchKeyword: this.state.searchKeyword })
    },
  }
}
</script>

<style>
</style>