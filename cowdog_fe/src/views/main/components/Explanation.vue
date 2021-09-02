<template>
  <div class="explain-container">
    <div class="random-comments">
      <br>
      <h1><i class="fas fa-heartbeat" style="color:#ff4e7e">&nbsp;</i>COW & DOG 사용자 가이드</h1>
    </div>
    <div class="explain-content" style="text-align:left; padding:5%">
      <h2><i class="fas fa-check" style="color:#ff4e7e"></i> COW & DOG 에서는 크게 세 가지 매칭 시스템이 제공됩니다.</h2>
      <div style="margin-left:3%">
        <h3>1. 지역 매칭: 사용자의 위치 주변에 있는 접속중인 이성을 확인할 수 있습니다.</h3>
        <h3>2. 랜덤 매칭: 남/녀 구분없이 랜덤 매칭됩니다.</h3>
        <h3>3. 맞춤 매칭: 취미, 성격, MBTI 등 원하는 조건을 선택하면 그에 맞게 매칭됩니다.</h3>
      </div>
      <br>
      <h2><i class="fas fa-check" style="color:#ff4e7e"></i> 원하는 매칭을 선택하면, 다음과 같은 매칭 상대 정보를 확인할 수 있습니다.</h2>
    
      <div class="explain-img">
        <div class="result-card">
          <div class="result-card-img">
            <img :src="require('@/assets/images/regist_img1.png')">
          </div>
          <div class="result-card-main-info">
            <div>닉네임
              (<i class="fas fa-venus"></i>, 26세)</div>
              <!-- <i class="fas fa-heart"></i> -->
              <i class="far fa-heart" style="color: #ff4e7e"></i>
          </div>
          <div class="result-card-body">
            <div class="body-content">주량: 가끔 마심</div>
            <div class="body-content">종교: 무교</div>
            <div class="body-content">성격: 활기찬, 가벼운, 재미있는</div>
            <div class="body-content">취미: 음악감상, 요리, 운동</div>
            <div class="body-content">사는곳: 대전광역시 유성구</div>
          </div>
          <div class="card-footer">
            <div class="card-button">
              <div class="card-matcing-btn"><i class="fas fa-video"></i> 화상챗 요청</div>
            </div>
          </div>
        </div>
      </div>
      <br>
      <h2><i class="fas fa-check" style="color:#ff4e7e"></i> 상대방이 궁금하다면 즉시 화상 채팅을 요청할 수 있습니다.</h2>
      <div style="margin-left: 3%">
        <h3>상대방이 수락하면 화상 채팅이 가능합니다.</h3>
        <img :src="require('@/assets/images/explanation1.png')">
      </div>
      <br>
      <h2><i class="fas fa-check" style="color:#ff4e7e"></i> 어색한 분위기를 간단한 게임을 통해 녹여보세요.</h2>
        <div style="margin-left: 3%">
          <img :src="require('@/assets/images/explanation2.png')">
        </div>
      <br>
      <h2><i class="fas fa-check" style="color:#ff4e7e"></i> 상대방이 마음에 들었다면, 하트를 눌러 팔로우하세요.</h2>
      <div style="margin-left:3%">
        <h3>팔로우를 하면 상대방에게 팔로우 알림이 가게 됩니다.</h3>
        <h3>상대방은 마이페이지-팔로워에서 이를 확인할 수 있고, 팔로우 역시 가능합니다.</h3>
        <h3>상대방이 팔로우를 누르면, 서로 맞팔 상태로 바뀌게 됩니다.</h3>
      </div>
      <br>
      <h2><i class="fas fa-check" style="color:#ff4e7e"></i> 맞팔로우 상태인 인연과 DM을 할 수 있습니다.</h2>
      <div style="margin-left:3%">
        <h3>DM을 통해 연락을 유지하며 만남을 기약하세요.</h3>
        <img :src="require('@/assets/images/explanation3.png')">
      </div>
    </div>

  </div>
</template>
<script>
export default{
    
}

</script>

<style scoped>
.explain-container{
    border-radius: 10px;
    width: 80%;
    margin: 5% auto;
    background: white;
}
.result-card {
  border: 1px solid #f0f2f5;
  margin: 0 auto;
}
.explain-img {
  justify-content: left;
}
</style>