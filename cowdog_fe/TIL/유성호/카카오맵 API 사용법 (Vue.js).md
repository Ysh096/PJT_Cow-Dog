# 카카오맵 API 사용법 (Vue.js)



카카오맵 API 사용시 지도가 잘려서 일부만 보이고, 화면 크기를 줄이거나 키우는 등의 작업을 하면 정상적으로 보이는 상황 발생

구글링 결과 처음에 지도를 만든 후 사이즈를 변경한 후 relayout이 이루어지지 않아 발생하는 것으로 파악

div 태그의 크기 변경 후 relayout()을 수차례 시도하였으나 잘 되지 않음

결국 해결하였는데 솔직히 이게 어떻게 해결책이 되는지는 모르겠음





## 1. 사용법

1. 템플릿에 지도를 넣을 div 태그를 id와 함께 작성한다.

   ```vue
   <template>
   	<div id="map" style="width: 450px; height: 450px;"></div>
   </template>
   ```

   width, height는 밑에 style에 적어줘도 됨

   

2. vue3의 setup() 내에서 지도를 로드하는 함수를 작성한다.

   ```vue
   <script>
   setup() {
       const store = useStore()
       const myinfo = store.state.myinfo // myinfo 가져오기
       // 지도 관련 setup
       const initMap = () => {
           var container = document.getElementById('map');
           var options = {
             // center에 위도, 경도 입력
             center: new kakao.maps.LatLng(myinfo.memberinfo.latitude, myinfo.memberinfo.longitude),
             level: 8
           };
   
           const map = new kakao.maps.Map(container, options); // 여기까지 하면 map이 그려짐
           
   		// 지도에 원을 그리기 위한 circle 변수(?)
           var circle = new kakao.maps.Circle({
               center: new kakao.maps.LatLng(myinfo.memberinfo.latitude, myinfo.memberinfo.longitude),
               radius: myinfo.memberinfo.distance * 1000,
               strokeWeight: 5, // 선의 두께
               strokeColor: '#ff4e7e', // 선의 색깔
               strokeOpacity: 1, // 불투명
               strokeStyle: 'line',
               fillColor: '#CFE7FF', // 채우기 색깔
               fillOpacity: 0.4 // 채우기 불투명도
           })
           circle.setMap(map); // 원을 지도에 그려넣는다.
           console.log('done!!')
       }
       
   </script>
   ```

   여기까지만 쓰면 당연히 함수가 실행된 적 없으므로 지도가 나타나지 않는다.

   따라서 지도를 실행하는 과정을 넣어야 하는데, 이 과정을 setup내에 있는 onMounted 사이클에서 하는 것이 발생한 문제의 해결 방법이었던 것 같다.

   

3. initMap 실행

   ```vue
   <script>
   ...이전 코드에 이어서...
   
       onMounted(() => {
         if (window.kakao && window.kakao.maps) {
             initMap();
         } else {
             const script = document.createElement('script');
             script.onload = () => kakao.maps.load(initMap);
             script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=/카카오맵키/';
             document.head.appendChild(script);
         }
       })
       
   </script>
   ```

   위 코드는 구글에 '카카오맵 vue 사용' 이런 식으로 치면 나오는 코드를 onMounted 라이프사이클에 붙여넣은 것으로, 원래는 vue2에서 사용하다보니 그냥 setup 없이 mounted 라이프사이클에 실행되게 해놓았다. 이를 간과하고 **setup에서 그냥 if~else 문을 그대로 사용**했었는데, setup의 라이프사이클이 created까지기 때문에 dom에 접근할 수 없는데 script를 추가하거나 엘리먼트를 가져오고, 컴포넌트에 접근하는 등의 동작을 하여 문제가 발생한 것 같다.

여기서 created와 mounted의 차이를 다시 알아보고 넘어가야겠다.



setup에 아무 표시 없이 적는 코드는 기존의 created까지를 대체하는 것으로 볼 수 있다. created는 **data, method, computed, watch** 등의 요소에 접근할 수 있으며, 가상돔에는 아직 접근할 수 없다. 따라서 가지고 있는 데이터나 함수같은건 실행이 가능하지만 그게 컴포넌트와 관련이 되어 있다면 오류가 날 것이다.



mounted는 가상DOM이 실제 DOM에 부착되고 난 후의 상태로 this.$el을 비롯한 위의 data, method, computed, watch등에 모두 접근이 가능하다고 하는데, 그냥 템플릿 부분에 쓴 요소에 접근이 가능하다는 정도로 이해해도 될 것 같다.



## 2. 제대로 되지 않은 시도들



- 컴포넌트로 만들어 <KakaoMap /> 형태로 추가하기
- relayout() 사용하기
  - 위치 선정하느라 고생했는데 결국 상관 없는듯?



## 3. 맞닥뜨린 오류들

- 새로고침시 페이지 삑
  - kakao is not defined
    - mounted 라이프사이클에서 relayout을 사용하려고 var map을 script 전역 변수가 되도록 가장 바깥쪽에 정의하였을 때 발생
    - 해결하기 위해 index.html에 스크립트 //dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=... 를 추가하였으나 해결되지 않음
  - currentStyle null
- 변경사항 발생시 지도 사라짐(새로고침하면 돌아옴)
  - 컴포넌트를 사용했더니 이렇게 돼버림. 컴포넌트 말고 그냥 Myinfo 자체에 지도를 박아놨더니 해결됨



그 외에도 뭐가 많았는데 기억이 잘 안남





## 성공 코드(Myinfo.vue)

```vue
<template>
<div class="myinfo-detail" style="display:flex">
  <div class="detail-box">
    <ul class="detail-info">
      <li>나이: {{myinfo.memberinfo.age}}</li>
      <li>성별: {{myinfo.memberinfo.gender[1]+myinfo.memberinfo.gender[2]}}</li>
      <li>이메일: {{myinfo.email}}</li>
      <li>성격: {{myinfo.memberinfo.personality.replace('[','').replace(']','')}}</li>
      <li>종교: {{myinfo.memberinfo.religion.replace('[','').replace(']','')}}</li>
      <li>취미: {{myinfo.memberinfo.hobby.replace('[','').replace(']','')}}</li>
      <li>흡연: {{myinfo.memberinfo.smoking}}</li>
      <li>음주: {{myinfo.memberinfo.alcohol.replace('[', '').replace(']', '')}}</li>
      <li>MBTI: </li>
      <li>지역: {{ myRegion }}</li>
    </ul>
  </div>
  <div class="map-box">
  <div class="map-title">만남을 원하는 위치는?</div>
    <div id="map" style="width: 450px; height: 450px;"></div>
  </div>
</div>


</template>


<script>
import { useStore } from 'vuex'
import { onMounted } from 'vue'
let files=[]
export default {
  data () {
    return {
      interest:[],
      title: "",
      files: [],
      galleryDatas: [],
      url:null,
    };
  },
  
  methods:{
    handleFileUpload() {
      console.log(this.$refs.imgfiles.files)
      files = this.$refs.imgfiles.files;
      this.url=files[0].name
      console.log(files);
    },
  },
  computed: {
    myRegion() {
      var myRegionSplit = this.myinfo.memberinfo.address.split(' ')
      var myRegion = myRegionSplit[0] + ' ' + myRegionSplit[1]
      return myRegion
    }
  },
  setup() {
    const store = useStore()
    const myinfo = store.state.myinfo

    // 지도 관련 setup
    const initMap = () => {
        var container = document.getElementById('map');
        var options = {
          center: new kakao.maps.LatLng(myinfo.memberinfo.latitude, myinfo.memberinfo.longitude),
          level: 8
        };

        const map = new kakao.maps.Map(container, options);

        var circle = new kakao.maps.Circle({
            center: new kakao.maps.LatLng(myinfo.memberinfo.latitude, myinfo.memberinfo.longitude),
            radius: myinfo.memberinfo.distance * 1000,
            strokeWeight: 5, // 선의 두께
            strokeColor: '#ff4e7e', // 선의 색깔
            strokeOpacity: 1, // 불투명
            strokeStyle: 'line',
            fillColor: '#CFE7FF', // 채우기 색깔
            fillOpacity: 0.4 // 채우기 불투명도
        })
        circle.setMap(map);
        // setTimeout이 없어도 잘 작동하지만 그냥 주석으로 놔둠
        // setTimeout(() => {
            // map.relayout()
            // map.setCenter(new kakao.maps.LatLng(myinfo.memberinfo.latitude, myinfo.memberinfo.longitude))
            console.log('done!!')
        // }, 0)
    }

    onMounted(() => {
      if (window.kakao && window.kakao.maps) {
          initMap();
      } else {
          const script = document.createElement('script');
          script.onload = () => kakao.maps.load(initMap);
          script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=카카오맵키';
          document.head.appendChild(script);
      }
    })

    return { myinfo }
  }
}
</script>
<style>

  .profile{
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  .detail-box {
    width: 30%;
    margin-top: 3%;
    margin-left: 20%;
    margin-bottom: 3%;
    border: 1px solid white;
    border-radius: 10px;
    background: white;
  }
  .detail-info {
    list-style: none;
    text-align: left;
    font-weight: bold;
    font-size: 14px;
  }
  
  .detail-info li {
    margin-bottom: 2rem;
  }

  .el-tabs__content {
    background: #f0f2f5;
  }
  .map-title {
    font-weight: bold;
    margin-bottom: 5px;
  }
  .map-box {
    margin: 4rem 3rem;
  }
</style>
```





## 제대로 안된 코드(KakaoMap 컴포넌트)

latitude, longitude를 props로 받아서 setup할 때 지도를 렌더링

- setTimeout으로 mypage를 클릭하고 1초 안에 재빠르게 상세 정보 탭을 누르면 지도가 정상적으로 출력되나, 새로고침하면 먹통이 되고 변경사항이 발생하면 지도가 자꾸 사라짐

```vue
<template>
  <div id="map" style="width: 500px; height: 500px;"></div>
</template>
<script>
export default {
    props: {
        lat: String,
        lng: String,
        meters: Number,
    },
    setup(props) {
        const initMap = () => {
            var container = document.getElementById('map');
            var options = {
              center: new kakao.maps.LatLng(props.lat, props.lng),
              level: 8
            };

            const map = new kakao.maps.Map(container, options);

            var circle = new kakao.maps.Circle({
                center: new kakao.maps.LatLng(props.lat, props.lng),
                radius: props.meters * 1000,
                strokeWeight: 5, // 선의 두께
                strokeColor: '#ff4e7e', // 선의 색깔
                strokeOpacity: 1, // 불투명
                strokeStyle: 'line',
                fillColor: '#CFE7FF', // 채우기 색깔
                fillOpacity: 0.7 // 채우기 불투명도
            })
            circle.setMap(map);
            setTimeout(() => {
                map.relayout()
                map.setCenter(new kakao.maps.LatLng(props.lat, props.lng))
                console.log('done!!')
            }, 1000)
        }
        
        if (window.kakao && window.kakao.maps) {
            initMap();
        } else {
            const script = document.createElement('script');
            script.onload = () => kakao.maps.load(initMap);
            script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=/////';
            document.head.appendChild(script);
        }
    },

}
</script>
<style>
</style>
```

