<template>
    <div class="main-content">
      <!-- 二级路由出口组件 -->
      <RouterView/>
    </div>
  </template>
  
  <script setup>
import { RouterView } from 'vue-router';
  </script>
  
  <style scoped>
  .main-content {
    flex: 1;
    overflow: auto;
    
    max-height: 700px;
    box-sizing: border-box;
    width: auto;
  }
  
  
  </style>