<template>
    <el-container class="container">
        <div class="main_content">
      <el-header class="header-title">名称模糊查询</el-header>
      <el-main>
        <el-row :gutter="20">
          <el-col v-for="(courseInfo, index) in courseInfos" :key="index" :span="6">
            <el-card class="project_item" @click="toDetail(courseInfo.courseId)">
                <SingalItem :courseId="courseInfo.courseId" :courseName="courseInfo.courseName" :Type="courseInfo.Type" 
                :coverUrl="courseInfo.coverUrl" :teacherId="courseInfo.teacherName"
                :createTime="courseInfo.createTime"/>
            </el-card>
          </el-col>
        </el-row>
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[20]"
          :page-size="20"
          layout="total, prev, pager, next, jumper"
          :total="totalProducts"
        />
      </el-main>
    </div>
    </el-container>
  </template>
  
  <script setup>
import SingalItem from "./SingalProjectModel.vue"
import { onMounted, ref, computed, watch } from 'vue'
import { getCoursesByNameAPI } from "@/apis/user"
import { useRouter, useRoute, onBeforeRouteUpdate } from 'vue-router'

const router = useRouter()
const route = useRoute()
const courseName = ref(route.params.courseName)
const courseInfos = ref([])
const currentPage = ref(1)

const getCourseByName = async() => {
  try {
    const res = await getCoursesByNameAPI(courseName.value)
    courseInfos.value = res.data
    console.log('获取课程数据:', res.data)
  } catch (error) {
    console.error('获取课程失败:', error)
  }
}

// 方案1：使用watch监听路由参数
watch(
  () => route.params.courseName,
  (newVal) => {
    if (newVal) {
      courseName.value = newVal
      getCourseByName()
    }
  },
  { immediate: true }
)


const toDetail = (courseId) => {          
  router.push({ name: 'CourseIntro', params: { courseId } })
}

const totalProducts = computed(() => courseInfos.value.length)

const paginatedProducts = computed(() => {
  const start = (currentPage.value - 1) * 20
  const end = start + 20
  return courseInfos.value.slice(start, end)
})

const handleCurrentChange = (page) => {
  currentPage.value = page
}
</script>
  
  <style scoped>
  .header-title{
        
        font-size: 40px;
          font-weight: normal;
          line-height: 60px;
  }
  .main_content{
     width: 100%;
     margin-left:95px ;
     margin-right:95px ;

  }
  .container{
        background-color: white;
        width: 100%;
    margin-bottom: 30px;
    margin-top: 15px;
  }
  
  .el-card.project_item {
  transition: none; /* 移除可能的过渡效果 */
  box-shadow: 0 ;
}
 
.el-card.project_item:hover {
    transform: translate3d(0, -6px, 0);
    box-shadow: 0 3px 8px rgb(0 0 0 / 20%);
}

  .product-image {
    width: 100%;
    height: auto;
  }
  .el-col {
    margin-bottom: 20px;
  }
  </style>