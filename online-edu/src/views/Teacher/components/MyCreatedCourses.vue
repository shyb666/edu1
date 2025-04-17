<template>
    <div class="course-page">
      <h3 style="margin-bottom: 20px;">我创建的课程列表</h3>
      
      <!-- 课程列表展示 -->
      <el-row :gutter="20" v-if="courseList.length > 0">
        <el-col 
          v-for="course in paginatedCourses" 
          :key="course.courseId" 
          :xs="24" :sm="12" :md="8" :lg="6"
        >
          <el-card class="course-card" shadow="hover">
            <!-- 课程封面 -->
            <div class="course-cover">
              <img 
                :src="course.coverUrl || defaultCover"
                class="cover-image"
                @error="handleImageError"
              >
            </div>
            
            <!-- 课程信息 -->
            <div class="course-content">
              <div class="course-title">{{ course.courseName }}</div>
              <el-tag size="small" :type="getTagType(course.type)">
                {{ course.type || '未分类' }}
              </el-tag>
              
              <div class="course-meta">
                <span><i class="el-icon-user"></i> {{ course.studentNum || 0 }}人</span>
                <span><i class="el-icon-document"></i> {{ course.homeworkNum || 0 }}作业</span>
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
      
      <!-- 分页组件 -->
      <div class="pagination-container" v-if="courseList.length > 0">
        <el-pagination
          background
          layout="prev, pager, next, sizes, total"
          :total="courseList.length"
          :page-size="pageSize"
          :current-page="currentPage"
          :page-sizes="[16, 32, 64]"
          @size-change="handleSizeChange"
          @current-change="handlePageChange"
        />
      </div>
    </div>
  </template>
  
  <script setup>
  import { onMounted, ref, computed } from 'vue'
  import { getMyCreatedCoursesAPI } from '@/apis/teacher'
  import { useUserStore } from '@/stores/userStore'
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  const userStore = useUserStore()
  const courseList = ref([])
  const defaultCover = 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png'
  
  // 分页相关
  const currentPage = ref(1)
  const pageSize = ref(16) // 4行4列=16个课程/页
  
  // 获取课程列表
  const getMyCourses = async () => {
    try {
      const res = await getMyCreatedCoursesAPI(userStore.userInfo.id)
      courseList.value = res.data || []
    } catch (error) {
      console.error('获取课程列表失败:', error)
      courseList.value = []
    }
  }
  
  // 计算当前页显示的课程
  const paginatedCourses = computed(() => {
    const start = (currentPage.value - 1) * pageSize.value
    const end = start + pageSize.value
    return courseList.value.slice(start, end)
  })
  
  // 图片加载失败处理
  const handleImageError = (e) => {
    e.target.src = defaultCover
  }
  
  // 课程类型标签样式
  const getTagType = (type) => {
    const typeMap = {
      '计算机': 'success',
      '数学': 'warning',
      '英语': 'danger',
      '物理': 'info'
    }
    return typeMap[type] || 'info'
  }
  
  // 查看课程详情
  const viewCourseDetail = (courseId) => {
    router.push({    
      name: 'MyCreateCourseDetail',
      params: { courseId }
    })
  }
  
  // 分页大小改变
  const handleSizeChange = (size) => {
    pageSize.value = size
    currentPage.value = 1 // 重置到第一页
  }
  
  // 页码改变
  const handlePageChange = (page) => {
    currentPage.value = page
  }
  
  onMounted(() => {
    getMyCourses()
  })
  </script>
  
  <style scoped>
  .course-page {
    padding: 20px;
    max-width: 1400px;
    margin: 0 auto;
  }
  
  .course-card {
    margin-bottom: 20px;
    transition: all 0.3s;
    height: 100%;
    display: flex;
    flex-direction: column;
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
    flex: 1;
    display: flex;
    flex-direction: column;
  }
  
  .course-title {
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 10px;
    color: #333;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  
  .course-meta {
    margin: 10px 0;
    font-size: 12px;
    color: #909399;
    display: flex;
    justify-content: space-between;
  }
  
  .course-footer {
    margin-top: auto;
    text-align: right;
  }
  
  .pagination-container {
    margin-top: 30px;
    display: flex;
    justify-content: center;
  }
  
  /* 响应式调整 */
  @media (max-width: 768px) {
    .course-cover {
      height: 120px;
    }
  }
  </style>