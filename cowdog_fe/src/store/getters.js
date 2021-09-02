export function getNotices (state) {
	return state.notices
}
export function getNoticesForUser (state) {
	return state.notices
}

export function getUserId(state){
	return state.userId
}

export function getUserInfo(state){
	return state.myinfo
}

export function getUserToken(state){
	return state.accessToken
}

export function getReportedUsers (state) {
	return state.reportedUsers
}

export function getReportedArticles (state) {
	return state.reportedArticles
}

export function getArticles (state) {
	return state.articles
}

export function getRandomUser(state){
	return state.randomUsers
}
export function getTotalPageNumber(state){
	return state.totalPageNumber
}

export function getDistanceUser(state){
	return state.distanceUsers
}
export function getRecomUser(state){
	return state.recomUsers
}

export function getFollowUsers(state){
	return state.followUsers
}
export function getEachOtherFollowUsers(state){
	return state.eachOtherFollowUsers
}
export function getUsersIFollowed(state){
	return state.usersIFollowed
}

export function getNotifications(state){
	return state.notifications;
}

export function getMatchStatus(state){
	return state.matchStatus;
}
