<template>
<el-container>
  <!-- sidebar -->
  <el-aside style="width:15vw">
    <el-row>
      <el-col>
        <el-menu
          :router="true"
          default-active="/admin/notice"
          class="el-menu-vertical-demo"
          text-color="#324545"
          background-color="#ffffff"
          active-text-color="#ff4e7e">      
          <el-menu-item index="/admin/notice" @click="getNotices">
            <i class="fas fa-clipboard-list" style="font-size: 16px"></i>공지사항
          </el-menu-item>
          <el-submenu index="2" style="font-weight:bold">
            <template #title>
              <i class="far fa-trash-alt" style="font-size: 16px"></i>
              <span>신고관리</span>
            </template>         
              <el-menu-item index="/admin/user-report" @click="getReportedUsers"><i class="fas fa-user"></i>사용자</el-menu-item>
              <el-menu-item index="/admin/article-report" @click="getReportedArticles"><i class="far fa-file-alt"></i>게시글</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-col>
    </el-row>
  </el-aside>
  <el-main>
    <router-view></router-view>
  </el-main>
</el-container>
<el-footer>
</el-footer>
</template>

<script>
import { useStore } from 'vuex'
export default {
  name: 'Admin',
  components: {
  },

  setup() {
    const store = useStore()
    return {
      getReportedUsers: () => {
        store.dispatch('getReportedUsers') //actions/getReportedUsers에 dispatch
      },
      getReportedArticles: () => {
        store.dispatch('getReportedArticles') // actions/getReportedArticles에 dispatch
      },
      getNotices: () => {
        store.dispatch('getNotices') // actions/getNotices에 dispatch
      }
    }
  },


}
</script>

<style scoped>
.el-aside {
  text-align: left;
}
.el-row {
  display: inline-block;
  height: 100vh;
  width: 15vw;
}
.el-menu-item {
  width: 15vw;
  font-weight: bold;
}
a {
  text-decoration: none;
  color: inherit;
}

i {
  margin: 0 10px 0 0;
}

.el-main {
    /* for Firefox */
    scrollbar-width: none;
    /* for IE, Edge */
    -ms-overflow-style: none;
}

/* For Chrome, Safari and Opera */
.el-main::-webkit-scrollbar {
    display: none;
}
</style>