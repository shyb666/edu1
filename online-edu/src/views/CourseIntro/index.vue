<template>
    <div class="app-container">
      <MainContent :courseId="courseId"/>
    </div>
  </template>

<script setup>
import { useRoute } from 'vue-router';
import MainContent from './components/MainContent.vue';
// 使用 useRoute 钩子获取当前路由信息
const route = useRoute();
const courseId = route.params.courseId; // 从路由参数中获取 courseId
 
// 由于 <script setup> 中定义的变量和函数默认暴露给模板，所以不需要显式 return
</script>
   
  <style>
  .app-container {
    display: flex;
    height: 100vh;
    margin: 0;
    padding: 0;
  }
  </style>