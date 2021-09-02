import router from '../router'
export const GET_NOTICES = (state, noticeDatas) => {
    state.notices = noticeDatas
    console.log(noticeDatas)
    router.push('/admin/notice') // Admin 클릭 => notices가 로드되고 난 후에 notice 페이지로 push
}
export const GET_NOTICES_FOR_USER = (state, noticeDatas) => {
    state.notices = noticeDatas
}
export const GET_MYINFO = (state, myinfoData) => {
    state.myinfo = myinfoData
    router.push('/mypage')
}
export const GET_REPORTED_USERS = (state, reportedUserDatas) => {
    state.reportedUsers = reportedUserDatas
    router.push('/admin/user-report')
}

export const GET_REPORTED_ARTICLES = (state, reportedArticleDatas) => {
    state.reportedArticles = reportedArticleDatas
    router.push('/admin/article-report')
}
export const GET_LOGIN_USER=(state,login_user)=>{
    console.log(login_user)
    state.userId=login_user.userId
    state.accessToken=login_user.accessToken

    console.log(state.userId)
    console.log(state.accessToken)
}

export const USER_LOGOUT=(state)=>{
    state.articles= "기본값",
    state.notices= "기본값",
    state.reportedUsers= '기본값',
    state.reportedArticles= '기본값',
    state.noticeDetailData= '기본값',
    state.myinfo=[],//유저 정보
    state.accessToken='',// 토큰
    state.userId='',//유저 pk 값
    state.randomUsers=[],
    state.distanceUsers=[],
    state.recomUsers=[],
    state.followUsers=[],
    state.eachOtherFollowUsers=[],
    state.usersIFollowed= [],
    state.notifications=[],
    router.push('/login')
}

export const SET_ARTICLES = (state, articles) => {
    state.articles = articles
    router.push({name: "AppealList"})
}
export const SET_TOTAL_PAGE_NUMBER=(state, totalPageNumber)=>{
    state.totalPageNumber=totalPageNumber
}

export const SET_ARTICLES_AFTER_DELETE = (state, articles) => {
    // 페이지 이동 없음 ( 동일 페이지이기 때문에 )
    state.articles = articles
}

export const SET_USERINFO = (state, data) => {
    state.myinfo = data
}

export const SET_RANDOM_USER=(state,randomUsers)=>{
    console.log(randomUsers)
    state.randomUsers=randomUsers;
    console.log(state.randomUsers)
    router.push('/randomResultView')
}
export const SET_RECOM_USER=(state,recomUsers)=>{
    console.log(recomUsers)
    state.recomUsers=recomUsers;
    console.log(state.recomUsers)
    console.log("Here is SET_RECOM_USER!!!!!!!!!!!!!!!!!")
    router.push('/recomResultView')
}

export const SET_DISTANCE_USER=(state,distanceUsers)=>{
    console.log(distanceUsers)
    state.distanceUsers=distanceUsers;
    console.log(state.distanceUsers)
    router.push('/distanceResultView')
}

export const SET_FOLLOW_USER=(state,followUsers)=>{
    console.log(followUsers)
    state.followUsers=followUsers;
    console.log(state.followUsers)
}

export const SET_EACH_OTHER_FOLLOW_USER=(state,eachOtherFollowUsers)=>{
    console.log(eachOtherFollowUsers)
    state.eachOtherFollowUsers=eachOtherFollowUsers;
    console.log(state.eachOtherFollowUsers)
}
export const AM_I_FOLLOWED = (state, usersIFollowed) => {
    state.usersIFollowed = usersIFollowed;
}

export const SET_NOTIFICATION=(state, notifications) => {
    state.notifications = notifications;
}
export const SET_SEARCH_RESULT= (state, searchResults) => {
    state.searchResults = searchResults
}

export const SET_MATCHSTATUS= (state, matchStatus) => {
    state.matchStatus = matchStatus;
}
export const SET_CAHTROOM_ID=(state,chatRoomId)=>{
    console.log(chatRoomId)
    state.chatRoomId=chatRoomId;
    console.log(state.chatRoomId)
}