# 이중 라우터(router children)

참고: https://velog.io/@soojung61/router-children



만약 navbar가 다음과 같이 router-link와 router-view로 연결되어 있다고 해보자.

```vue
<template>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>|
    <router-link to="/test">Test</router-link>|
    <router-link to="/admin">Admin</router-link>
  </div>
  <router-view/>
</template>
```



여기서 admin 페이지에 들어가면 sidebar가 나와서 공지사항, 사용자, 게시글 중 고를 수 있게 하려고 한다.

이 때 다음과 같은 템플릿을 작성하고 해당 link를 누르면 위에 있던 router view 부분(Home, About, Test, Admin)은 사라지고 새로운 페이지가 렌더된다.

```vue
<template>
<el-container>
  <el-aside width="240px">
    <el-row class="tac">
      <el-col>
        <el-menu
          default-active="1"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          text-color="#324545"
          background-color="#ffffff"
          active-text-color="#ff4e7e">      
          <el-menu-item index="1">
            <i class="el-icon-menu"></i>
            <span><router-link to="/admin/notice">공지사항</router-link></span>
          </el-menu-item>
          <el-submenu index="2">
            <template #title>
              <i class="el-icon-location"></i>
              <span>신고관리</span>
            </template>
              <router-link to="/admin/user-report"><el-menu-item index="2-1">사용자</el-menu-item></router-link>
              <el-menu-item index="2-2"><router-link to="/admin/writing-report">게시글</router-link></el-menu-item>
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
  @footer
</el-footer>
</template>
```

 

내가 원하는 것은 공지사항, 신고관리 등을 선택했을 때 위에 있는 Home, About, Test, Admin은 놔두고 그 밑에(router-view 표시한 부분) 정보를 표시하는 것이다. 이는 router/index.js 를 조금 수정하여 할 수 있다.

**router/index.js**

```js
  {
    path: '/admin',
    name: 'Admin',
    component: Admin,
    children: [
        {
          path: '/admin/notice',
          name: 'Notice',
          component: Notice
        },
        {
          path: '/admin/user-report',
          name: 'UserReport',
          component: UserReport
        },
        {
          path: '/admin/writing-report',
          name: 'WritingReport',
          component: WritingReport
        }
    ]
  }
```

이렇게 admin 아래에 notice, report 들이 있는 것을 표시하기 위해 children 속성을 사용하면 내가 원하는 방식으로 동작한다.



