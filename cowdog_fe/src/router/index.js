import { createRouter, createWebHistory } from 'vue-router'
import Test from '../components/Test.vue'
import Admin from '../views/notice/Admin.vue'
import NoticeForUser from '../views/notice/notice-for-user.vue'
import Notice from '../views/notice/components/notice.vue'
import NoticeCreate from '../views/notice/components/notice-create.vue'
import NoticeUpdate from '../views/notice/components/notice-update.vue'
import UserReport from '../views/notice/components/user-report.vue'
import ArticleReport from '../views/notice/components/article-report.vue'
import LoginPage from '../views/login/LoginPage.vue'
import RegisterPage from '../views/register/RegisterPage.vue'
import Mypage from '../views/mypage/Mypage.vue'
import MyInfo from '../views/mypage/components/MyInfo.vue'
import Eachother from '../views/mypage/components/Eachother.vue'
import Follow from '../views/mypage/components/Follow.vue'
import FollowDetail from '../views/mypage/components/FollowDetail.vue'
import EachotherDetail from '../views/mypage/components/EachotherDetail.vue'
import Setting from '../views/mypage/components/Setting.vue'
import Update from '../views/mypage/components/Update.vue'
import FindingPassword from '../views/findpassword/FindingPassword.vue'
import Main from '../views/main/Main.vue'
import RandomMatching from '../views/main/components/RandomMatching.vue'
import DistanceMatching from '../views/main/components/DistanceMatching.vue'
import RecomMatching from '../views/main/components/RecomMatching.vue'
import RandomResultView from '../views/main/matchingview/RandomResultView.vue'
import RandomResultViewDetail from '../views/main/matchingview/RandomResultViewDetail.vue'
import DistanceResultView from '../views/main/matchingview/DistanceResultView.vue'
import DistanceResultViewDetail from '../views/main/matchingview/DistanceResultViewDetail.vue'
import RecomResultView from '../views/main/matchingview/RecomResultView.vue'
import RecomResultViewDetail from '../views/main/matchingview/RecomResultViewDetail.vue'
import AppealCreate from '../views/appeal/components/AppealCreate'
import AppealDetail from '../views/appeal/components/AppealDetail'
import AppealList from '../views/appeal/components/AppealList'
import AppealUpdate from '../views/appeal/components/AppealUpdate'
import App from '../App.vue'
import store from '../store/index'
import VideoChat from '../views/main/matchingview/VideoChat.vue'
import Swal from 'sweetalert2'

const routes = [
  {
    path: '',
    name: 'login',
    component: Main,
    meta: { mainCheck: true }
  },
  {
    path:'/app',
    name:'App',
    component:App
  },
  
  {
    path: '/test',
    name: 'Test',
    component: Test
  },
  {
    path:'/login',
    name:'Login',
    component:LoginPage,
    meta: {loginCheck: true}
  },
  {
    path:'/register',
    name:'Register',
    component:RegisterPage,
    meta: { loginCheck: true }
  },
  {
    path:'/findingpassword',
    name:'FindingPassword',
    component:FindingPassword
  },

  {
    path:'/recomResultView',
    name:'RecomResultView',
    component:RecomResultView,
    meta: { isLogin: true }
  },
  {
    path:'/recomResultViewDetail',
    name:'RecomResultViewDetail',
    component:RecomResultViewDetail,
    meta: { isLogin: true }
  },
  
  {
    path:'/randomResultView',
    name:'RandomResultView',
    component:RandomResultView,
    meta: { isLogin: true }
  },
  {
    path:'/randomResultViewDetail',
    name:'RandomResultViewDetail',
    component:RandomResultViewDetail,
    meta: { isLogin: true }
  },
  {
    path:'/distanceResultView',
    name:'DistanceResultView',
    component:DistanceResultView,
    meta: { isLogin: true }
  },
  {
    path:'/distanceResultViewDetail',
    name:'DistanceResultViewDetail',
    component:DistanceResultViewDetail,
    meta: { isLogin: true }
  },
  {
    path:'/main',
    name:'Main',
    component:Main,
    meta: 
      { isLogin: true},

    children:[
      {
        path:'/main/randomMatching',
        name:'RandomMatching',
        component:RandomMatching
      },
      {
        path:'/main/distanceMatching',
        name:'DistanceMatching',
        component:DistanceMatching
      },
      {
        path:'/main/recomMatching',
        name:'RecomMatching',
        component:RecomMatching
      },
    ]
  },
  {
    path:'/videochat',
    name:'VideoChat',
    component:VideoChat,
  },
  {
    path:'/mypage',
    name: 'Mypage',
    meta: { isLogin: true },
    component:Mypage,
    children:[
      {
        path:'/mypage/myinfo',
        name:'MyInfo',
        component:MyInfo
      },
      {
        path:'/mypage/eachother',
        name:'Eachother',
        component:Eachother
      },
      {
        path:'/mypage/eachotherDetail',
        name:'EachotherDetail',
        component:EachotherDetail
      },
      {
        path:'/mypage/follow',
        name:'Follow',
        component:Follow
      },
      {
        path:'/mypage/followDetail',
        name:'FollowDetail',
        component:FollowDetail
      },

      {
        path:'/mypage/setting',
        name:'Setting',
        component:Setting
      },
      {
        path:'/mypage/update',
        name:'update',
        component:Update
      },

    ]
  },

  // 유저가 보는 공지사항 페이지
  {
    path: '/notices',
    name: 'Notices',
    component: NoticeForUser,
  },
  // 관리자 페이지
  {
    path: '/admin',
    name: 'Admin',
    component: Admin,
    meta: { manager: true },
    children: [
        {
          path: '/admin/notice',
          name: 'Notice',
          component: Notice
        },
        {
          path: '/admin/noticeCreate',
          name: 'NoticeCreate',
          component: NoticeCreate
        },
        {
          path: '/admin/noticeDetailUpdate',
          name: 'UpdateNotice',
          component: NoticeUpdate,
        },
        {
          path: '/admin/user-report',
          name: 'UserReport',
          component: UserReport
        },
        {
          path: '/admin/article-report',
          name: 'ArticleReport',
          component: ArticleReport
        },       
    ]
  },
  {
    path: '/appeal',
    name: 'AppealList',
    component: AppealList, 
    // 중첩 라우팅..
    // children: [],
    // props를 true로 설정하면, $route.params가 컴포넌트 props로 설정된다.
    // props: true,
  },
  {
    path: '/appeal/detail',
    name: 'AppealDetail',
    component: AppealDetail,
  },
  {
    path: '/appeal/create',
    name: 'AppealCreate',
    component: AppealCreate,
  },  
  {
    path: '/appeal/update',
    name: 'AppealUpdate',
    component: AppealUpdate,
  }, 
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.mainCheck && !store.state.userId) {
    next('/login');
  }
  if (to.meta.manager && !store.getters.getUserInfo.manager) {
    Swal.fire('FAIL', '관리자만 접근이 가능합니다.')
    next('/main');
    return;
  }
  // 회원가입, 로그인 페이지는 로그인 한 상태에서 접근 불가
  if (to.meta.loginCheck && store.state.userId) {
    Swal.fire('FAIL', '로그아웃 상태에서만 접근이 가능합니다.')
    next('/main');
    return;
  }

  if (to.meta.isLogin && !store.state.userId) {
    Swal.fire('FAIL', '로그인 해주세요.')
    next('/login');
    return;
  }





  next();
});

export default router
