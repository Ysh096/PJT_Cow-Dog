[toc]

# Vue 새로 알게 된 점



## 1. 명령어

- `-g`: 설치할 패키지를 global 패키지로 등록하여 앞으로 생성하는 모든 프로젝트에서 사용할 수 있음.
  - `npm install -g 패키지명`
- `--save`: 현재 작업 중인 디렉토리 내에 있는 ./node_modulus에 패키지를 설치한다. 그 다음 package.json 파일에 있는 dependencies 객체에 지금 설치한 패키지 정보를 추가한다.
  - 새로 설치하는 패키지가 생기면 패키지 자체가 아니라 package.json 파일의 변화를 통해 패키지 정보를 공유한다. `npm install`을 하면 그냥 받아진다.
- 포트번호 변경은 다음과 같이 한다.
  - `npm run serve -- --port 3000` 3000번 포트로 실행



## 2. 폴더 구조

- public: 웹팩(webpack)을 통해 관리되지 않는 정적 리소스가 모여 있는 디렉토리
- src/assets: 이미지, css, 폰트 등을 관리하는 디렉토리
- App.vue: 최상위(Root) 컴포넌트
- main.js: **가장 먼저 실행되는** 자바스크립트 파일로써, Vue 인스턴스를 생성하는 역할
- babel.config.js: 바벨(Babel) 설정 파일 => 자바스크립트 코드를 ES5 문법으로 바꿔주는 역할 
- package-lock.json: 설치된 package의 dependency 정보를 관리하는 파일
- package.json: 프로젝트에 필요한 package를 정의하고 관리하는 파일



## 3. package.json

```json
{
  "name": "vuejs3x",
  "version": "0.1.0",
  "private": true,
  "scripts": {
    "serve": "vue-cli-service serve",
    "build": "vue-cli-service build",
    "lint": "vue-cli-service lint"
  },
  "dependencies": {
    "axios": "^0.21.1",
    "core-js": "^3.6.5",
    "vue": "^3.0.0",
    "vue-router": "^4.0.0-0",
    "vuex": "^4.0.0"
  },
  "devDependencies": {
    "@vue/cli-plugin-babel": "~4.5.0",
    "@vue/cli-plugin-eslint": "~4.5.0",
    "@vue/cli-plugin-router": "^4.5.11",
    "@vue/cli-service": "~4.5.0",
    "@vue/compiler-sfc": "^3.0.0",
    "babel-eslint": "^10.1.0",
    "eslint": "^6.7.2",
    "eslint-plugin-vue": "^7.0.0-0"
  },
  "eslintConfig": {
    "root": true,
    "env": {
      "node": true
    },
    "extends": [
      "plugin:vue/vue3-essential",
      "eslint:recommended"
    ],
    "parserOptions": {
      "parser": "babel-eslint"
    },
    "rules": {}
  },
  "browserslist": [
    "> 1%",
    "last 2 versions",
    "not dead"
  ]
}
```

- dependencies: 사용 중인 패키지 정보를 입력
- devDependencies: 프로젝트 배포 시 필요 없는, 개발 시에만 필요한 패키지 정보가 등록되는 곳
- eslintConfig: ESLint는 일관성 있게 코드를 작성하고 버그를 식별하고 회피할 목적으로 ECMAScript/Javascript 코드에서 발견된 패턴을 개발자에게 알려주는 플러그인이다. babel-eslint을 parser로 사용
- browserlist: 전 세계 사용 통계 속에서 상위 1% 이상 사용된 브라우저, 각 브라우저의 최신 버전 2개를 지원하도록 한다.





## 4. 데이터 바인딩

### 4-1. v-model

- v-model.number 를 사용하면 input type="number"의 입력값을 숫자로 처리할 수 있다.

  - `<input type="number" v-model.number="numberModel" />`

- textarea 태그는 기존에 `<textarea>텍스트 내용</textarea>` 형식으로 사용되었지만 v-model을 사용하게 되면 다음과 같이 사용한다.

  - `<textarea v-model="message"></textarea>`

- select에 적용하는 방법은 다음과 같다.

  - ```vue
    <template>
      <div>
        <select v-model="city">
            <option value="02">서울</option>
            <option value="21">부산</option>
            <option value="064">제주</option>
        </select>
      </div>
    </template>
    
    <script>
      export default {
          data() {
              return {
                  city: "064"
              };
          }
      }
    </script>
    ```

    이렇게 코드를 작성하면 select의 기본값이 제주가 표시된다. 다른걸 선택하면 city 값이 변경된다.

- checkbox에도 v-model을 사용할 수 있다.

  - 체크박스의 checked 속성에 v-model 값이 할당된다. true 또는 false를 대입시키면 된다.
  - value에 바인딩하려면 v-bind:value를 사용해야 한다.
  - 여러 개의 체크박스를 사용할 때는 배열을 이용해서 데이터를 바인딩하여 한 번에 처리할 수 있다.

  ```vue
  <template>
    <div>
      <label><input type="checkbox" value="서울" v-model="checked">서울</label>
      <label><input type="checkbox" value="부산" v-model="checked">부산</label>
      <label><input type="checkbox" value="제주" v-model="checked">제주</label>
      <br>
      <span>체크한 지역: {{ checked }}</span>
    </div>
  </template>
  <script>
    export default {
        data() {
            return {
                checked: []
            }
        }
    }
  </script>
  ```

  왜 이게 되는지는 잘 모르겠는데, check 표시를 만약 서울과 제주에만 했다면 checked 배열에 "서울", "제주" 와 같이 값이 들어가게 된다.

- radio에도 v-model은 checked와 바인딩이 이루어진다.

  - value에 바인딩하려면 v-bind:value를 사용해야 한다.

  ```vue
  <template>
    <div>
      <label><input type="radio" v-bind:value="radioValue1" v-model="picked">서울</label>
      <label><input type="radio" v-bind:value="radioValue2" v-model="picked">부산</label>
      <label><input type="radio" v-bind:value="radioValue3" v-model="picked">제주</label>
      <br>
      <span>선택한 지역: {{ picked }}</span>
    </div>
  </template>
  <script>
    export default {
        data() {
            return {
                picked: '',
                radioValue1: '서울',
                radioValue2: '부산',
                radioValue3: '제주',
            };
        }
    }
  </script>
  ```

  마찬가지로 왜 되는지는 모르겠지만 만약 제주를 선택하면 picked가 true같은게 아니라 "제주" 가 입력된다.



### 4-2. v-bind

미완
