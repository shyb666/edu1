<template>
  <div class="container">
    <div class="tabs-container">
      <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
        <el-tab-pane label="学生列表" name="students">
          <el-table :data="courseDetailInfo.students" style="width: 100%">
            <el-table-column prop="studentId" label="学生ID" width="180" />
            <el-table-column prop="studentName" label="学生姓名">
              <template #default="scope">
                {{ scope.row.studentName || '未设置姓名' }}
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <el-tab-pane label="课表信息" name="classes">
          <el-button type="success" @click="toAddClass"> 添加课程</el-button>
          <div class="class-list">
            <el-card 
              v-for="classItem in courseDetailInfo.classes" 
              :key="classItem.classId"
              class="class-card"
            >
              <template #header>
                <div class="class-header">
                  <span>{{ classItem.className }}</span>
                </div>
              </template>
              <div class="class-content">
                <p>{{ classItem.classContent }}</p>
                <div class="video-link">
                  <el-button 
                    type="primary" 
                    @click="handleViewClassDetail(classItem.classId)"
                  >
                    课程详情
                  </el-button>
                </div>
              </div>
            </el-card>
          </div>
        </el-tab-pane>

        <el-tab-pane label="课程作业" name="homeworks">
          <el-button type="success" @click="toAddHomework"> 添加作业</el-button>
          <h1>点击列表显示学生作业</h1>
          <el-table 
            :data="courseDetailInfo.homeworks" 
            style="width: 100%"
            @row-click="handleHomeworkClick"
          >
            <el-table-column prop="homeworkId" label="作业ID" width="100" />
            <el-table-column prop="homeworkName" label="作业名称" width="180" />
            <el-table-column prop="homeworkContent" label="作业说明" />
            <el-table-column label="作业文件">
              <template #default="scope">
                <el-link 
                  type="primary" 
                  :href="scope.row.homeworkUrl" 
                  target="_blank"
                >
                  下载作业
                </el-link>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 课程详情弹窗 -->
    <el-dialog 
      v-model="classDetailVisible" 
      :title="`课程详情 - ${currentClassName}`" 
      width="50%"
    >
      <el-form :model="currentClassDetail" label-width="100px">
        <el-form-item label="课程ID">
          <el-input v-model="currentClassDetail.classId" disabled />
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input v-model="currentClassDetail.className" />
        </el-form-item>
        <el-form-item label="课程内容">
          <el-input 
            v-model="currentClassDetail.classContent" 
            type="textarea" 
            :rows="4" 
          />
        </el-form-item>
        <el-form-item label="课程视频">
          <div class="video-container" v-if="currentClassDetail.videoUrl">
            <video 
              controls 
              width="100%"
              :src="currentClassDetail.videoUrl"
            >您的浏览器不支持 video 标签。</video>
          </div>
          <div v-else class="no-video">
            暂无视频
          </div>
          
          <div class="upload-container">
            <el-upload
              action="#"
              :auto-upload="false"
              :show-file-list="false"
              :on-change="handleVideoChange"
              accept="video/*"
            >
              <el-button type="primary">重新上传视频</el-button>
            </el-upload>
            <div v-if="uploading" class="uploading-text">上传中，请稍候...</div>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="classDetailVisible = false">取消</el-button>
        <el-button 
          type="primary" 
          @click="handleUpdateClass"
          :loading="updating"
        >
          保存修改
        </el-button>
      </template>
    </el-dialog>

    <!-- 作业学生表单弹窗 -->
    <el-dialog 
      v-model="homeworkDialogVisible" 
      :title="`作业详情 - ${currentHomeworkName}`" 
      width="70%"
    >
      <el-table :data="studentHomeworkData" style="width: 100%">
        <el-table-column prop="studentId" label="学生ID" width="180" />
        <el-table-column prop="studentName" label="学生姓名" width="180" />
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button 
              type="primary" 
              size="small"
              @click="handleViewHomework(scope.row)"
            >
              查看作业
            </el-button>
            
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!-- 作业详情弹窗 -->
    <el-dialog 
      v-model="homeworkDetailVisible" 
      :title="`作业详情 - 学生: ${currentStudentName}`" 
      width="50%"
    >
      <el-form label-width="100px">
        <el-form-item label="作业ID">
          <el-input v-model="currentHomeworkDetail.homeworkId" disabled />
        </el-form-item>
        <el-form-item label="学生ID">
          <el-input v-model="currentHomeworkDetail.studentId" disabled />
        </el-form-item>
        <el-form-item label="作业内容">
          <el-input 
            v-model="currentHomeworkDetail.homeworkContent" 
            type="textarea" 
            :rows="4" 
            disabled
          />
        </el-form-item>
        <el-form-item label="作业文件">
          <el-link 
            type="primary" 
            :href="currentHomeworkDetail.homeworkUrl" 
            target="_blank"
          >
            下载作业文件
          </el-link>
        </el-form-item>
        <el-form-item label="评分">
          <el-input-number 
            v-model="score" 
            :min="0" 
            :max="100" 
            placeholder="请输入分数(0-100)"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="homeworkDetailVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitScore">提交评分</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ElMessage } from 'element-plus'
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { getCourseDetailAPI } from '@/apis/user'
import { setHomeworkScoreAPI } from '@/apis/teacher'
import { getSubmitHomeworkAPI } from '@/apis/teacher'
import { updateClassAPI } from '@/apis/teacher'
import { uploadFile } from '@/apis/user'
import { useRouter } from 'vue-router'
const route = useRoute()
const courseId = route.params.courseId
const activeName = ref('students')
const router = useRouter()
const courseDetailInfo = ref({
  courseName: '',
  students: [],
  classes: [],
  homeworks: []
})

// 作业相关状态
const homeworkDetailVisible = ref(false)
const homeworkDialogVisible = ref(false)
const currentHomeworkId = ref(null)
const currentHomeworkName = ref('')
const studentHomeworkData = ref([])
const currentHomeworkDetail = ref({
  homeworkId: null,
  studentId: null,
  homeworkContent: '',
  homeworkUrl: ''
})
const currentStudentName = ref('')
const score = ref(null)

// 课程详情相关状态
const classDetailVisible = ref(false)
const currentClassDetail = ref({
  classId: null,
  className: '',
  classContent: '',
  videoUrl: ''
})
const currentClassName = ref('')
const uploading = ref(false)
const updating = ref(false)
const videoFile = ref(null)

const handleClick = (tab, event) => {
  console.log(tab, event)
}

// 获取课程详情
const getCourseDetail = async() => {
  try {
    const res = await getCourseDetailAPI(courseId)
    courseDetailInfo.value = res.data
    studentHomeworkData.value = courseDetailInfo.value.students
  } catch (error) {
    console.error("获取课程详情失败:", error)
    ElMessage.error('获取课程详情失败')
  }
}

// 查看课程详情
const handleViewClassDetail = async (classId) => {
  try {
    const res = await getCourseDetailAPI(courseId)
    const classItem = res.data.classes.find(item => item.classId === classId)
    if (classItem) {
      currentClassDetail.value = { ...classItem }
      currentClassName.value = classItem.className
      classDetailVisible.value = true
      videoFile.value = null // 重置上传文件状态
    } else {
      ElMessage.warning('未找到课程详情')
    }
  } catch (error) {
    console.error('获取课程详情失败:', error)
    ElMessage.error('获取课程详情失败')
  }
}

// 处理视频文件选择
const handleVideoChange = (file) => {
  if (!file) return
  
  // 检查文件类型
  const validTypes = ['video/mp4', 'video/webm', 'video/ogg']
  if (!validTypes.includes(file.raw.type)) {
    ElMessage.error('请上传有效的视频文件 (MP4, WebM, Ogg)')
    return
  }
  
  // 检查文件大小 (限制为100MB)
  const maxSize = 100 * 1024 * 1024
  if (file.raw.size > maxSize) {
    ElMessage.error('视频文件大小不能超过100MB')
    return
  }
  
  videoFile.value = file.raw
  
  // 预览视频
  const videoUrl = URL.createObjectURL(file.raw)
  currentClassDetail.value.videoUrl = videoUrl
}

// 上传视频文件
const uploadVideo = async () => {
  if (!videoFile.value) return currentClassDetail.value.videoUrl
  
  uploading.value = true
  try {
    const formData = new FormData()
    formData.append('file', videoFile.value)
    
    const res = await uploadFile({ formData })
    return res.data // 返回新视频的URL
  } catch (error) {
    console.error('视频上传失败:', error)
    ElMessage.error('视频上传失败')
    return null
  } finally {
    uploading.value = false
  }
}

// 更新课程信息
const handleUpdateClass = async () => {
  updating.value = true
  
  try {
    // 如果有新视频，先上传视频
    if (videoFile.value) {
      const newVideoUrl = await uploadVideo()
      if (newVideoUrl) {
        currentClassDetail.value.videoUrl = newVideoUrl
      }
    }
    
    // 更新课程信息
    await updateClassAPI(currentClassDetail.value)
    ElMessage.success('课程信息更新成功')
    classDetailVisible.value = false
    getCourseDetail() // 刷新课程数据
  } catch (error) {
    console.error('更新课程失败:', error)
    ElMessage.error('课程信息更新失败')
  } finally {
    updating.value = false
  }
}

// 点击作业行
const handleHomeworkClick = async (row) => {
  currentHomeworkId.value = row.homeworkId
  currentHomeworkName.value = row.homeworkName
  try {
    homeworkDialogVisible.value = true
  } catch (error) {
    console.error("获取作业学生信息失败:", error)
    ElMessage.error('获取作业学生信息失败')
  }
}

// 查看作业操作
const handleViewHomework = async (row) => {
  try {
    const res = await getSubmitHomeworkAPI(currentHomeworkId.value, row.studentId)
    if (!res.data || !res.data.homeworkId) {
      ElMessage.warning('该学生尚未提交作业')
    } else {
      currentHomeworkDetail.value = res.data
      currentStudentName.value = row.studentName
      score.value = res.data.score || null
      homeworkDetailVisible.value = true
    }
  } catch (error) {
    console.error('查看作业失败:', error)
    ElMessage.error('获取作业详情失败')
  }
}

// 批改作业操作
const handleGradeHomework = async (row) => {
  await handleViewHomework(row)
}

// 提交评分
const handleSubmitScore = async () => {
  if (score.value === null || score.value < 0 || score.value > 100) {
    ElMessage.warning('请输入有效的分数(0-100)')
    return
  }
  
  try {
    await setHomeworkScoreAPI(
      currentHomeworkDetail.value.homeworkId,
      score.value,
      currentHomeworkDetail.value.studentId,
    )
    ElMessage.success('评分提交成功')
    homeworkDetailVisible.value = false
  } catch (error) {
    console.error('提交评分失败:', error)
    ElMessage.error('评分提交失败')
  }
}
//带参路由
const toAddClass=()=>{
  router.push({ name: 'AddClass', params: { courseId: courseId } });
}

const toAddHomework=()=>{
  router.push({ name: 'AddHomework', params: { courseId: courseId } });
}
onMounted(() => {
  getCourseDetail()
})
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.tabs-container {
  width: 100%;
  max-width: 1200px;
  display: flex;
  justify-content: center;
}

.demo-tabs {
  width: 100%;
}

.class-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  padding: 10px;
}

.class-card {
  margin-bottom: 20px;
}

.class-header {
  font-weight: bold;
}

.class-content {
  min-height: 100px;
}

.video-link {
  margin-top: 15px;
  text-align: right;
}

/* 视频相关样式 */
.video-container {
  margin-bottom: 15px;
  border: 1px solid #eee;
  border-radius: 4px;
  overflow: hidden;
}

.no-video {
  padding: 20px;
  text-align: center;
  color: #999;
  background-color: #f5f5f5;
  border-radius: 4px;
  margin-bottom: 15px;
}

.upload-container {
  display: flex;
  align-items: center;
  gap: 10px;
}

.uploading-text {
  color: #666;
  font-size: 14px;
}

/* 使选项卡从中间向两侧排列 */
:deep(.el-tabs__nav) {
  display: flex;
  justify-content: center;
}

:deep(.el-tabs__nav-wrap) {
  display: flex;
  justify-content: center;
}

:deep(.el-tabs__active-bar) {
  transition: all 0.3s;
}

:deep(.el-tabs__item) {
  padding: 0 20px;
  font-size: 16px;
}

/* 表格行可点击样式 */
:deep(.el-table__row) {
  cursor: pointer;
}

:deep(.el-table__row:hover) {
  background-color: #f5f7fa;
}

/* 按钮间距 */
.el-button + .el-button {
  margin-left: 8px;
}
</style>