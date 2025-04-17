<template>
    <el-container class="container">
        <div class="main_content">
      <el-header class="header-title">畅销课程</el-header>
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
  
  <script>
  import SingalItem from "./SingalProjectModel.vue"
  import { onMounted, ref ,computed } from 'vue';
  import { getAllCoursesAPI } from "@/apis/user";
  import { useRouter } from 'vue-router'
  export default {
    setup(){
      const router = useRouter()
 const courseInfos=ref([])
  const getAllCourse= async()=>{
   const res= await getAllCoursesAPI()
   courseInfos.value=res.data
   console.log('获取所有课程基本信息res:',res)
   console.log('获取所有课程基本信息courseinfos:',courseInfos)
}
        onMounted(() => getAllCourse())

        const toDetail = (courseId) => {          
          router.push({ name: 'CourseIntro', params: { courseId: courseId } });
          console.log('Project ID is',courseId);
}

return{
    toDetail,
    courseInfos
}
    },
    data() {
      return {
        currentPage: 1,
      };
    },
    computed: {
      totalProducts() {
        return this.courseInfos.length;
      },
      paginatedProducts() {
        const start = (this.currentPage - 1) * 20;
        const end = start + 20;
        return this.courseInfos.slice(start, end);
      },
    },
    methods: {
      handleCurrentChange(page) {
        this.currentPage = page;
      },
    },
    components: {
        SingalItem,
  }
  };
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