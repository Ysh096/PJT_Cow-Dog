<template>
    <div class="random-container">
        <div class="random-comments">
            <br>
            <h2>남자 여자 상관없이 친구를 만나보세요</h2>
            <!-- <i class="fas fa-mars match-icon"></i>
            <i class="fas fa-venus match-icon"></i> -->
            <i class="fas fa-venus-mars match-icon"></i>

        </div>
        <!-- <el-button class="circle" @click="randomMatching">RANDOM</el-button> -->
        <a href="#" class="neon-btn" @click="randomMatching">
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            RANDOM</a>
    </div>
</template>
<script lang="ts">
import { useStore } from 'vuex'
import { reactive } from 'vue'
export default{




     setup() {
        const store=useStore()
        const state = reactive({
           




        })
        const randomMatching=function(){
            console.log("램덤매팅!")
            store.dispatch("randomMatching")

        }



        return {
            state,randomMatching
        }
    },
    
}

</script>

<style >
.random-container{
    border-radius: 10px;
    width: 50%;
    height: 350px;
    margin: 5% auto;
    background: white;
}
.circle{
    width: 200px;
    height: 200px;
    border-radius: 50%;
    background-color: #FF4E7E;
    margin-top: 100px;
    font-size: 30px;
    color: white;
}
.circle span:hover{
    color: #FF4E7E;
    background-color: white;
}
.random-comments .match-icon {
    font-size: 70px;
}
</style>