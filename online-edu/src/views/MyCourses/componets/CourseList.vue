<template>
  <div class="course-page">
    <h3 style="margin-bottom: 20px;">我的课程列表</h3>
    
    <!-- 课程列表展示 -->
    <el-row :gutter="20" v-if="courseList.length > 0">
      <el-col 
        v-for="course in courseList" 
        :key="course.courseId" 
        :xs="24" :sm="12" :md="8" :lg="6"
      >
        <el-card class="course-card" shadow="hover">
          <!-- 课程封面 -->
          <div class="course-cover">
            <img 
              :src="course.coverUrl || 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png'"
              class="cover-image"
              @error="handleImageError"
            >
          </div>
          
          <!-- 课程信息 -->
          <div class="course-content">
            <div class="course-title">{{ course.courseName }}</div>
            <el-tag size="small" :type="course.type === '计算机' ? 'success' : 'info'">
              {{ course.type || '未分类' }}
            </el-tag>
            
            <div class="course-meta">
              <span><i class="el-icon-user"></i> {{ course.studentNum }}人</span>
              <span><i class="el-icon-document"></i> {{ course.homeworkNum }}作业</span>
            </div>
            
            <div class="course-footer">
              <el-button 
                type="primary" 
                size="small" 
                plain
                @click="viewCourseDetail(course.courseId)"
              >
                查看详情
              </el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <!-- 空状态提示 -->
    <el-empty v-else description="暂无课程数据" />
  </div>
</template>
<script setup>
import { onMounted, ref } from 'vue'
import { getMyCoursesAPI } from '@/apis/user'
import { useUserStore } from '@/stores/userStore'
import { useRouter } from 'vue-router'
const courseList = ref({})
const userStore = useUserStore() 
const router = useRouter()
const getMyCourses = async () => {
  const res = await getMyCoursesAPI(userStore.userInfo.id)
  courseList.value = res.data
}

// 图片加载失败处理
const handleImageError = (e) => {
  e.target.src = 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png'
}

// 查看课程详情
const viewCourseDetail = (courseId) => {
  console.log('查看课程:', courseId)
  router.push({
  name: 'CourseDetail',
  params: { courseId }
})
   
}




onMounted(() => {
  getMyCourses() // 确保调用函数
})
</script>

<style scoped>
.course-page {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.course-card {
  margin-bottom: 20px;
  transition: all 0.3s;
  height: 100%;
}

.course-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.course-cover {
  height: 160px;
  overflow: hidden;
  border-radius: 4px 4px 0 0;
}

.cover-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s;
}

.course-card:hover .cover-image {
  transform: scale(1.05);
}

.course-content {
  padding: 15px;
}

.course-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #333;
}

.course-meta {
  margin: 10px 0;
  font-size: 12px;
  color: #909399;
  display: flex;
  justify-content: space-between;
}

.course-footer {
  margin-top: 15px;
  text-align: right;
}
</style>