// Vuex 조립
import { createStore } from 'vuex'
import * as getters from './getters'
import * as mutations from './mutations'
import * as actions from './actions'
import createPersistedState from 'vuex-persistedstate'
const state = {
  articles: [],
  totalPageNumber:'',
  notices: "기본값",
  reportedUsers: '기본값',
  reportedArticles: '기본값',
  noticeDetailData: '기본값',
  myinfo:[],//유저 정보
  accessToken:'',// 토큰
  userId:'',//유저 pk 값
  randomUsers:[],
  distanceUsers:[],
  recomUsers:[],
  followUsers:[],
  eachOtherFollowUsers:[],
  usersIFollowed: [],
  notifications:[],
  centerDialogVisible: false,
  matchStatus:false,
  chatRoomId:'', //챗룸Id
}

const root = createStore({
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
  plugins: [
    createPersistedState()
  ]
})

export default root