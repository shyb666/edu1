<template>
    <div class="course-container">
      <!-- 顶部课程基本信息卡片 -->
      <el-card class="course-card">
        <div class="course-header">
          <el-image 
            class="course-cover"
            :src="courseInfo.coverUrl" 
            fit="cover"
            :preview-src-list="[courseInfo.coverUrl]"
          >
            <template #error>
              <div class="image-error">
                <el-icon><Picture /></el-icon>
              </div>
            </template>
          </el-image>
          
          <div class="course-meta">
            <h2>{{ courseInfo.courseName }}</h2>
            <div class="meta-item">
              <el-icon><User /></el-icon>
              <span>教师ID: {{ courseInfo.teacherId }}</span>
            </div>
            <div class="meta-item">
              <el-icon><Calendar /></el-icon>
              <span>创建时间: {{ courseInfo.createTime }}</span>
            </div>
            <div class="meta-item">
              <el-icon><Collection /></el-icon>
              <span>类型: {{ courseInfo.type }}</span>
            </div>
            <div class="stats">
              <el-tag type="info">作业数: {{ courseInfo.homeworkNum }}</el-tag>
              <el-tag type="info">学生数: {{ courseInfo.studentNum }}</el-tag>
              <el-tag type="info">课程数: {{ courseInfo.classNum }}</el-tag>
            </div>
            <div>
              <el-button type="success" class="addbt" @click="joinCourse">
                加入课程
              </el-button>
            </div>
          </div>
        </div>
      </el-card>
  
      <!-- 课时列表 -->
      <el-card class="classes-card">
        <template #header>
          <div class="card-header">
            <span>课时列表</span>
          </div>
        </template>
        
        <el-table :data="courseInfo.classes" stripe style="width: 100%">
          <el-table-column prop="classId" label="课时ID" width="100" />
          <el-table-column prop="className" label="课时名称" width="180" />
          <el-table-column prop="classContent" label="课时说明" />
        </el-table>
      </el-card>
    </div>
  </template>
  
  <script setup>
  import { onMounted, ref } from 'vue'
  import { getCourseInfoAPI } from '@/apis/user'
  import { joinCourseAPI } from '@/apis/student'
  import { useUserStore } from '@/stores/userStore'
  import { 
    User, 
    Calendar, 
    Collection, 
    Picture 
  } from '@element-plus/icons-vue'
  const userStore=useUserStore()
  const props = defineProps({
    courseId: {
      type: Number,
      required: true
    }
  })
  
  const courseInfo = ref({
    classes: [] // 确保classes有默认值避免undefined错误
  })
  
  const getCourseInfo = async (courseId) => {
    try {
      const res = await getCourseInfoAPI(courseId)
      courseInfo.value = res.data
      console.log(courseInfo.value)
    } catch (error) {
      console.error('获取课程信息失败:', error)
      ElMessage.error('获取课程信息失败')
    }
  }
  
  const joinCourse=async()=>{
    const studentId=userStore.userInfo.id
    const courseId=props.courseId
   await joinCourseAPI({courseId,studentId}) 
   console.log('加入课程')
  }
  
  onMounted(() => {
    getCourseInfo(props.courseId)
  })
  </script>
  
  <style scoped>
  .addbt{
    margin-top: 10px;
  }
  .course-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
  }
  
  .course-card {
    margin-bottom: 20px;
  }
  
  .course-header {
    display: flex;
    gap: 30px;
  }
  
  .course-cover {
    width: 300px;
    height: 200px;
    border-radius: 4px;
    flex-shrink: 0;
  }
  
  .image-error {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background: #f5f7fa;
    color: #909399;
  }
  
  .course-meta {
    flex: 1;
  }
  
  .course-meta h2 {
    margin-top: 0;
    margin-bottom: 15px;
    color: #303133;
  }
  
  .meta-item {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
    color: #606266;
  }
  
  .meta-item .el-icon {
    margin-right: 8px;
  }
  
  .stats {
    margin-top: 15px;
    display: flex;
    gap: 10px;
  }
  
  .classes-card {
    margin-top: 20px;
  }
  
  .card-header {
    font-size: 18px;
    font-weight: bold;
  }
  </style>