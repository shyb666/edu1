<template>
    <div class="app-container">
      <el-row>
      <Sidebar :courseId="courseId"/>
      <MainContent />
    </el-row>
    </div>
  </template>
   
   
<script setup>
import { useRoute } from 'vue-router';
import Sidebar from './components/Sidebar.vue';
import MainContent from './components/MainContent.vue';

// 自动注册 Sidebar 和 MainContent 组件，因为它们是在 <script setup> 中导入的
 
// 使用 useRoute 钩子获取当前路由信息
const route = useRoute();
const courseId = route.params.courseId; // 从路由参数中获取 courseId
 
</script>
   
  <style>
  .app-container {
    display: flex;
    height: 80vh;
    margin: 0;
    padding: 0;
  }
  </style>