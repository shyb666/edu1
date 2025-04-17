<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import LayoutHeaderUl from './LayoutHeaderUl.vue'

const router = useRouter()
const searchQuery = ref('')

const handleSearch = () => {
  const query = searchQuery.value.trim()
  if (query) {
    // 跳转到搜索路由，使用课程名称作为参数
    router.push({
      name: 'SeaechByName', // 使用路由名称
      params: {
        courseName: query // 注意参数名与路由定义一致
      }
    })
    // 清空搜索框
    searchQuery.value = ''
  }
}

// 监听键盘事件
const onKeyUp = (e) => {
  if (e.key === 'Enter') {
    handleSearch()
  }
}
</script>

<template>
  <header class='app-header'>
    <div class="container">
      <h1 class="logo">
        <RouterLink to="/">线上教育平台</RouterLink>
      </h1>

      <LayoutHeaderUl />
      <div class="search">
        <i class="iconfont icon-search" @click="handleSearch"></i>
        <input 
          type="text" 
          placeholder="搜一搜" 
          v-model="searchQuery"
          @keyup="onKeyUp"
        >
      </div>
    </div>
  </header>
</template>


<style scoped lang='scss'>
.app-header {
  background: #ffffff;

  .container {
    display: flex;
    align-items: center;
  }

  .logo {
    width: 200px;

    a {
      display: block;
      height: 132px;
      width: 100%;
      text-indent: -9999px;
      background: url('@/assets/images/生成带商标 logo.png') no-repeat center 18px / contain;
    }
  }

  .search {
    width: 170px;
    height: 32px;
    position: relative;
    border-bottom: 1px solid #e7e7e7;
    line-height: 32px;

    .icon-search {
      font-size: 18px;
      margin-left: 5px;
    }

    input {
      width: 140px;
      padding-left: 5px;
      color: #666;
    }
  }

  .cart {
    width: 50px;

    .curr {
      height: 32px;
      line-height: 32px;
      text-align: center;
      position: relative;
      display: block;

      .icon-cart {
        font-size: 22px;
      }

      em {
        font-style: normal;
        position: absolute;
        right: 0;
        top: 0;
        padding: 1px 6px;
        line-height: 1;
        background: $helpColor;
        color: #fff;
        font-size: 12px;
        border-radius: 10px;
        font-family: Arial;
      }
    }
  }
}
</style>