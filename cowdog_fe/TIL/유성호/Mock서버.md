# Mock 서버(가짜 서버)를 이용한 프론트엔드 개발



Postman에서 제공하는 Mock 서버 기능을 이용한다.

- 프론트엔드 개발자가 서버 프로그램이 완성되기를 기다리는 상황으로 인해 시간 낭비가 일어나는 것을 방지하기 위해 Mock 서버를 사용한다.
- 기획자 혹은 컨설턴트가 구현해야 하는 화면에 대한 데이터를 정의하면, 화면에서 사용되는 데이터 샘플을 엑셀파일로 만들고, 이를 JSON 형태로 변환해서 서버 개발자와 프론트엔드 개발자에게 전달하면 작업 효율이 높아진다.



1. [포스트맨 설치](https://www.postman.com/downloads/)
2. Workspace 하나 만들기
3. Mock Servers 클릭
4. Create Mock Server
5. Create a new API가 활성화 되면 Request Path에 'test'라고 입력하여 path를 설정한다.
6. Next 버튼이 활성화되면 버튼을 클릭한다.
7. Mock 서버 이름 설정하고 create

다음과 같은 Mock URL을 얻는다.

https://e7d14d3b-41a2-4dc5-874c-a55731c663b9.mock.pstmn.io



Collections에 우리가 생성한 Mock Server와 같은 이름의 콜렉션이 하나 생겼을 것이다. 콜렉션 안에 우리가 입력한 Request Path가 등록되어 있다.

![캡처](C:\Users\multicampus\Desktop\S05P12B104\cowdog_fe\TIL\유성호\Mock서버.assets\캡처.PNG)

위의 No Environment를 클릭하고 만든 Mock 서버를 선택한다. 이렇게 해야 Vue 컴포넌트에서 호출했을 때 실행된다.



![캡처2](C:\Users\multicampus\Desktop\S05P12B104\cowdog_fe\TIL\유성호\Mock서버.assets\캡처2.PNG)

위 그림에서 GET 옆에는 요청을 보내야 할 Mock 서버의 주소를, Params, Headers, Body에는 요청과 함께 전달해주고자 하는 데이터를, 그 밑의 Body에서는 요청 시 Mock 서버로부터 받을 데이터를 설정하면 된다.

