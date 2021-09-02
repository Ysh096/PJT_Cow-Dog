<template>
    <el-container class="wrapper">
    
        <div class="findForm">
            <p>비밀번호 찾기</p>
            <el-form :model="state.form" :rules="state.rules" ref="findingPasswordForm" :label-position="state.form.align">
                <el-form-item prop="findingId"  :label-width="state.formLabelWidth" >
                    <label>비밀번호를 찾고자 하는 아이디를 입력해주세요</label>
                    <el-input  class="elinput" v-model="state.form.findingId" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item prop="email"  :label-width="state.formLabelWidth">
                    <label>회원가입시 작성한 이메일을 입력하세요</label>
                    <el-input class="elinput" v-model="state.form.email" autocomplete="off" placeholder="example@example.com"></el-input>
                </el-form-item>
            </el-form>
            <el-button class="valid_btn" @click="authentication">인증하기</el-button>
        </div>

    </el-container>
</template>

<script>
import { reactive,ref } from 'vue'
import { useStore } from 'vuex'
export default {
    name:"FindPassword",
    components: {
        
    },

    methods: {
    
    },

    computed: {
    
    },

    setup() {
        const store = useStore()
        const findingPasswordForm=ref(null)
        
        const validMessage=(rule,value,callback)=>{
                if(value!==""){
            store.dispatch('confirmId',{id:value})
            .then(function(result){
                console.log(result)
                if(result.data==="NOT_EXISTS_USERID"){
                callback(new Error("아이디가 존재하지 않습니다."))
                }
                else{
                callback()
                }
            })
            .catch(function(err){
                console.log(err)
            })
            }
        }
        
        // const store= userStore()
        const state=reactive({
            form:{
                findingId:'',
                email:'',
            },
            rules: {
                findingId:[
                    {required: true,message:'아이디를 입력하세요', trigger:'blur'},
                    {validator:validMessage}
                ],
                email:[
                    {required: true,message:'이메일을 입력하세요', trigger:'blur'},
                    {pattern:/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/ ,message:"이메일형식이 올바르지 않습니다."}
                ]
            }

        })
        const authentication =function(){
            console.log("이메일 인증")
            findingPasswordForm.value.validate((valid) => {
            if (valid) {
            
                console.log('유효성 검사 통과')
                store.dispatch('Authentication', {id:state.form.findingId
                ,email:state.form.email})
            
            } 
        });
        }
        return {state,findingPasswordForm,authentication}
    }
}
</script>
<style >
/* .wrapper{
    width: 700px;
    height: 500px;
    margin-top: 300px;
    margin: 0 auto;
    border: 1px solid #BBABAB;
    border-radius: 5%;
}
.findForm{
    height: 80%;
    margin: 0 auto;
}
.valid_btn{
    margin-top: 50px;
    width:100%;
}
.valid_btn:hover{
    color: white;
    background-color: #EC7357;
} */
</style>