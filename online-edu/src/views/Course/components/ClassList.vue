<template>
    <div class="course-container">
      <h1>课程列表</h1>
      
      <el-row :gutter="20" v-loading="loading">
        <el-col :span="16" v-if="selectedClass">
          <div class="content-area">
            <h2>{{ selectedClass.className }}</h2>
            <div class="class-content">
              {{ selectedClass.classContent }}
            </div>
            
            <div class="video-player" v-if="selectedClass.videoUrl">
              <h3>视频课程</h3>
              <video 
                controls 
                :src="selectedClass.videoUrl" 
                style="width: 100%; max-height: 500px;"
              ></video>
            </div>
          </div>
        </el-col>
        
        <el-col :span="selectedClass ? 8 : 24">
          <div class="class-list">
            <el-card shadow="hover">
              <template #header>
                <div class="card-header">
                  <span>课程列表</span>
                </div>
              </template>
              
              <el-scrollbar height="600px">
                <el-menu 
                  :default-active="activeIndex"
                  @select="handleSelect"
                >
                  <el-menu-item 
                    v-for="item in classlist" 
                    :key="item.classId" 
                    :index="item.classId.toString()"
                  >
                    <span>{{ item.className }}</span>
                  </el-menu-item>
                </el-menu>
              </el-scrollbar>
            </el-card>
          </div>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import { getCourseInfoAPI } from '@/apis/user'
  import { ElMessage } from 'element-plus'
  
  const route = useRoute()
  const classlist = ref([])
  const loading = ref(true)
  const selectedClass = ref(null)
  const activeIndex = ref('')
  
  // 获取课程列表
  const getClassList = async () => {
    try {
      const courseId = route.params.courseId
      const res = await getCourseInfoAPI(courseId)
      classlist.value = res.data.classes
      // 默认选中第一个课程
      if (classlist.value.length > 0) {
        handleSelect(classlist.value[0].classId.toString())
      }
    } catch (error) {
      console.error('获取课程信息失败:', error)
      ElMessage.error('获取课程信息失败')
    } finally {
      loading.value = false
    }
  }
  
  // 选择课程
  const handleSelect = (index) => {
    activeIndex.value = index
    selectedClass.value = classlist.value.find(item => item.classId.toString() === index)
  }
  
  onMounted(() => {
    getClassList()
  })
  </script>
  
  <style scoped>
  .course-container {
    padding: 20px;
    width: 1500px;
  }
  
  .content-area {
    background: #fff;
    padding: 20px;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
  }
  
  .class-content {
    padding: 15px 0;
    line-height: 1.6;
    border-bottom: 1px solid #eee;
    margin-bottom: 20px;
  }
  
  .video-player {
    margin-top: 20px;
  }
  
  .class-list {
    margin-bottom: 20px;
  }
  
  .card-header {
    font-weight: bold;
    font-size: 16px;
  }
  </style>