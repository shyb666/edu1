// 管理用户数据相关

import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getUserInfoByIdAPI } from '@/apis/user'
import { loginAPI } from '@/apis/user'
import { registerAPI } from '@/apis/user'
export const useUserStore = defineStore('user', () => {
  // 1. 定义管理用户数据的state
  const userInfo = ref({})
  // 定义管理用户详细信息数据的state
  const userDetailInfo = ref({})
//用户登录函数，获取用户数据
const getLoginInfo = async ({ phone, password }) => {
  const res = await loginAPI({ phone, password })
  userInfo.value = res.data
  console.log('后端res：',res)
  console.log('测试登录接口，用户信息为',userInfo.value)
  
}
//注册用户
const postUserInfo = async ({ phone, password, identity, email,name }) => {
  const res = await registerAPI({ phone, password, identity, email,name })
  console.log('成功注册用户:',phone,"/",password,"/",email,"/",identity)
  console.log('注册返回结构:',res)
}

//根据id获取用户详细信息
const getUserInfoById = async( id )=>{
  const res =await getUserInfoByIdAPI(id)
  userDetailInfo.value=res.data
  console.log('获取指定用户信息:',res)
  console.log('获取指定用户信息:',userDetailInfo.value)
}


  // 退出时清除用户信息
  const clearUserInfo = () => {
    userInfo.value = {}
  }
  // 3. 以对象的格式把state和action return
  return {
    userInfo,
    userDetailInfo,
    clearUserInfo,
    postUserInfo,
    getLoginInfo,
    getUserInfoById,
  }
}, {
  persist: true,
})