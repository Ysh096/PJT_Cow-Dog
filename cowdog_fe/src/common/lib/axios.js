
import VueAxios from 'vue-axios'
import axios from 'axios'
// import config from '../config.j'

const BASE_URL = 'http://i5b104.p.ssafy.io/cowdog'
// const BASE_URL = 'http://localhost:8080/cowdog'
const DEFAULT_ACCEPT_TYPE = 'application/json'

axios.defaults.baseURL = BASE_URL
axios.defaults.headers['Content-Type'] = DEFAULT_ACCEPT_TYPE
axios.defaults.headers['Access-Control-Allow-Origin'] = '*';
axios.defaults.headers['Authorization'] = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW1zc2FmeSIsImV4cCI6MTYyNzc4NzM3NCwiaWF0IjoxNjI3NzUxMzc0fQ.34-5qvP1EHYwGhH2Qf1ESfcrWKAl_n21q7E7dolwES4';
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';
axios.defaults.headers.get['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.headers.get['Access-Control-Allow-Origin'] = '*';
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';
export default { VueAxios, axios }
