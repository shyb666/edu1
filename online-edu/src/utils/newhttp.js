// axios基础的封装
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/userStore'
const httpInstance = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000
})

// 拦截器

// axios请求拦截器
httpInstance.interceptors.request.use(config => {
  // 1. 从pinia获取token数据
  const userStore = useUserStore()
  // 2. 按照后端的要求拼接token数据
  const token = userStore.userInfo.token
  if (token) {
    config.headers.Authorization = `${token}`
    config.headers.authentication = `${token}`
    console.log('获取到token',token)
  }
  return config
}, e => Promise.reject(e))


httpInstance.interceptors.response.use(res => res.data, e => {
  // 统一错误提示
  ElMessage({
    type: 'warning',
    message: e.response
  })
  return Promise.reject(e)
})


export default httpInstance