<template>
  <div class="sidebar">
    <el-scrollbar style="height: 100vh;">
      <ul class="custom-list">
        <li v-for="item in navItems" :key="item.name" class="list-item">
          <router-link
            :to="item.route"
            active-class="active-link"
            exact-active-class="exact-active-link"
          >
            <h4 class="item_label">{{ item.label }}</h4>
          </router-link>
        </li>
      </ul>
    </el-scrollbar>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { ElScrollbar } from 'element-plus';
import 'element-plus/theme-chalk/el-scrollbar.css';


const props = defineProps({
  courseId: Number,
});

// 直接定义导航项，使用 currentProjectId.value 来访问 ref 的值
const navItems = [
  { label: '创建课程', route: '/teacher/createcourse' },
  { label: '我的课程', route: '/teacher/mycourses' },
  { label: '在线答疑', route: '/teacher/chatroom' },
];
</script>

<style scoped>
/* 现有样式保持不变 */
.item_label {
  color: #494949;
}

.sidebar {
  border-radius: 6px;
  background: #dbdbdb;
  width: 200px;
  height: 650px;
  overflow: hidden;
  font-weight: 2px;
  box-sizing: border-box;
}

.list-item {
  border-radius: 6px;
  width: 100%;
  padding: 10px;
  cursor: pointer;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.3s ease;
}

/* 修改 hover 样式 */
.list-item:hover {
  background-color: #929292;
}

/* 修改选中项的样式 */
.list-item.active-link {
  background-color: #929292;
}

.list-item.exact-active-link {
  background-color: #929292;
}
</style>