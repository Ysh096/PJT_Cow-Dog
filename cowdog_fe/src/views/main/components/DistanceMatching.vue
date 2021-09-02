<template>
    <div class="distance-container">
        <div class="random-comments">
            <br>
            <h2>롱디는 그만, 나와 가까운 사람 찾기!</h2>
        </div>
        <br><br>
        <h3>매칭을 원하는 거리를 선택해주세요</h3>
        <div class="block">
            <el-slider
            v-model="state.distance"
            :min="2"
            :max="10"
            :step="2"
            :marks="state.marks"
            >
            </el-slider>
        </div>
        <div>
            <a href="#" class="neon-btn" @click="distanceMathcing">
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                MATCHING
            </a>
        </div>
    </div>
</template>
<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'

export default{    
    setup() {
        const store=useStore()
        const state = reactive({
            distance:4,
            marks:{
                2:"2km",
                4:"4km",
                6:"6km",
                8:"8km",
                10:"10km",
            }
        })
        const distanceMathcing =function(){
            console.log(state.distance)
            store.dispatch("disatnceMatching",state.distance)



        }

        return{
            state,distanceMathcing
        }
    },
    
}

</script>

<style >
.distance-container{
    border-radius: 10px;
    width: 50%;
    height: 400px;
    margin: 5% auto;
    background-color: white;
}
.block{
    width: 80%;
    margin: 5% auto;
}
.distance-circle{
    display: inline-block;
    padding: 12px 36px;
    margin: 10px 0;
    color: #fff;
    text-decoration: none;
    text-transform: uppercase;
    font-size: 18px;
    font-weight: bold;
    letter-spacing: 2px;
    border-radius: 40px;
    background: linear-gradient(90deg, #755bea, #ff72c0);
    color: white;
}
.distance-circle span:hover{
    color: #FF4E7E;
    background-color: white;
}
/* 슬라이더 색깔 */
.el-slider__bar {
    background-color: #ff4e7e;
}
.el-slider__button {
    border-color: #ff4e7e;
}

/* 네온 버튼 */
.neon-btn {
    margin-top: 5%; /* 버튼 위치 조절 */
    position: relative;
    display: inline-block;
    padding: 15px 30px;
    border: 3px solid #323545;
    border-radius: 10px;
    color: #323545;
    font-weight: bold;
    text-transform: uppercase;
    letter-spacing: 4px;
    text-decoration: none;
    font-size: 24px;
    overflow: hidden;
    transition: 0.2s;
}
.neon-btn:hover {
    color: white;
    border: 0;
    background: #ff4e7e;
    box-shadow: 0 0 10px #ff4e7e, 0 0 20px #ff4e7e, 0 0 20px #ff4e7e;
    transition-delay: 1s;
}
a span {
    position: absolute;
    display: block;
}
.neon-btn span:nth-child(1){
    top: 0;
    left: -100%;
    width: 100%;
    height: 3px;
    background: linear-gradient(90deg, transparent, #ff4e7e);
}
.neon-btn:hover span:nth-child(1){
    left: 100%;
    transition: 1s;
}
.neon-btn span:nth-child(3){
    bottom: 0;
    right: -100%;
    width: 100%;
    height: 3px;
    background: linear-gradient(270deg, transparent, #ff4e7e);
}
.neon-btn:hover span:nth-child(3){
    right: 100%;
    transition: 1s;
    transition-delay: 0.5s;
}
.neon-btn span:nth-child(2){
    top: -100%;
    right: 0;
    width: 3px;
    height: 100%;
    background: linear-gradient(180deg, transparent, #ff4e7e);
}
.neon-btn:hover span:nth-child(2){
    top: 100%;
    transition: 1s;
    transition-delay: 0.25s;
}
.neon-btn span:nth-child(4){
    bottom: -100%;
    left: 0;
    width: 3px;
    height: 100%;
    background: linear-gradient(360deg, transparent, #ff4e7e);
}
.neon-btn:hover span:nth-child(4){
    bottom: 100%;
    transition: 1s;
    transition-delay: 0.75s;
}
</style>