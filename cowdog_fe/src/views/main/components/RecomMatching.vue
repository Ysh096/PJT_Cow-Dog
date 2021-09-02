<template>
    <div class="recom-container">
        <div class="recom-comments">
            <br>
            <h2>지금 만나보고 싶은 이성을 골라본다면?</h2>
        </div>
        <div class="recom-btns">

            <div class="간격">
                <a class="neon-btn" @click="alcohol">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <i class="fas fa-wine-glass-alt"></i> 주량이 맞는 사람
                </a>
            </div>

            <div class="간격">
                <a class="neon-btn" @click="sameHobby">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <i class="fas fa-utensils"></i> 취미가 비슷한 사람
                </a>
            </div>
            
            <div class="간격">
                <a class="neon-btn" @click="wantMale">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <i class="fas fa-male"></i> 멋진 남성을 만나고 싶은 사람
                </a>
            </div>
            <div class="간격">
                <a class="neon-btn" @click="wantFemale">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <i class="fas fa-female"></i> 아름다운 여성을 만나고 싶은 사람
                </a>
            </div>
            <div class="간격">
                <a class="neon-btn" @click="nonSmoking">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <i class="fas fa-smoking-ban"></i> 비흡연자를 만나고 싶은 사람
                </a>
            </div>
            <div class="간격">
                <a class="neon-btn" @click="sameReligion">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <i class="fas fa-pray"></i> 종교가 같은 사람
                </a>
            </div>
            <div class="간격">
                <a class="neon-btn" @click="sameMbti">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <i class="fas fa-file-alt"></i> MBTI가 나와 맞는 사람
                </a>
            </div>
            <div class="간격">
                <a class="neon-btn" @click="samePersonality">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <i class="fas fa-equals"></i> 성격이 나와 맞는 사람
                </a>
            </div>
        </div>
    </div>
</template>
<script lang="ts">
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'


export default{
    setup() {
        const store=useStore()
        const state = reactive({
            
            
            


        })
        const alcohol=function(){
            console.log("주량이 비슷한 사람!")
            store.dispatch("alcohol")
            
        }
        const sameHobby=function(){
            console.log("취미!")
            store.dispatch("sameHobby")
            
        }
        const wantMale=function(){
            console.log("남자 원함!")
            store.dispatch("wantMale")
            
        }
        const wantFemale=function(){
            console.log("여자 원함!")
            store.dispatch("wantFemale")
            
        }
        const nonSmoking=function(){
            console.log("비흡연자!")
            store.dispatch("nonSmoking")
            
        }
        const sameReligion=function(){
            console.log("종교!")
            store.dispatch("sameReligion")
            
        }
        const sameMbti=function(){
            console.log("mbti!")
            store.dispatch("sameMbti")
            
        }
        const samePersonality=function(){
            console.log("성격!")
            store.dispatch("samePersonality")
            
        }
       

        return {
            state,sameMbti,sameReligion,nonSmoking,wantFemale,wantMale,sameHobby,alcohol,samePersonality
        }
    },
}

</script>

<style >
.recom-container{
    
    border-radius: 10px;
    width: 50%;
    margin: 5% auto;
    background-color: white
}
.recom-btns {
    padding: 3%;
}
.recom-btns .neon-btn {
    width: 70%;
    font-size: 16px;
}
.recom-btns .neon-btn:hover {
    cursor: pointer;
}

.간격 {
    height: 5rem;
}
</style>