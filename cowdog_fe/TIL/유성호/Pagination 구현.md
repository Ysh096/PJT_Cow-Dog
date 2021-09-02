# Pagination 구현

공지사항 및 유저, 게시글 신고 관리 수가 길어질 경우 pagination을 통해 나눠서 보여주려 한다.

element ui에는 pagination이 잘 나타나있다. 사용 방법을 알아보자..



현재 notice  component에 notices라는 공지사항 전체 데이터를 가지고 있다. notices는 다음과 같이 구성된다.

[{notice}, {notice}, {notice}, {notice}, {notice}, ..., {notice}]

이 notices를 5개 단위로 잘라서 page가 바뀔 때마다 뿌려주려고 한다. (매번 서버에 요청하는 방식은 아님)



우선 원하는 위치에 el-pagination을 작성한다.

```vue
<template>
<div v-for="(notice, idx) in noticesPaginated" :key="idx">
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>{{ notice.title }}</span>
        <el-button class="button" type="text">자세히</el-button>
      </div>
    </template>
    <div>{{ notice.content }}</div>
  </el-card>
</div>

<!-- 페이지네이션 -->
<el-pagination
background
layout="prev, pager, next"
:page-size=pageSize
:total="notices.length"
v-model:current-page="currentPage">
</el-pagination>

</template>
```

나는 공지 밑에 페이지네이션을 위치시켰다. 여기서 각각의 attribute가 의미하는 바를 살펴보면,

- page-size: 페이지에 뿌려줄 게시물의 개수
- total: 게시물의 전체 개수를 입력하면 page-size를 바탕으로 pagination을 총 몇 페이지로 할지 계산해줌
  - ex) page-size가 5이고 전체 게시물이 7개면 total에 7을 입력 시 2개의 페이지 버튼이 출력됨
- v-model: current-page  : 현재 페이지가 몇 번째 페이지인지 v-model로 양방향 바인딩 할 수 있음.

이제 스크립트를 살펴보자.

```js
<script>
import { useStore } from 'vuex'

export default {

  data() {
    return {
      // for pagination
      pageSize: 5,
      currentPage: 1,
    }
  },
  computed: {
    noticesPaginated() {
      var start = 0 + (this.currentPage-1) * this.pageSize
      var end = this.currentPage * this.pageSize
      return this.notices.slice(start, end) //기본값 0~5번
    }
  },

  setup() {
    const store = useStore()
    const notices = store.state.notices
    return { notices }
  }
```

data에는 페이지에 뿌려줄 게시물의 수인 pageSize와 현재 페이지를 v-model로 바인딩할 변수 currentPage를 만들어놓았고, computed에는 이 두 가지 변수를 바탕으로 시작점과 끝점을 정해서 notices를 잘라준 후 반환하는 noticesPaginated 를 만들어놓았다.



element plus를 통해 매우 쉽게 pagination을 구현할 수 있었다.

