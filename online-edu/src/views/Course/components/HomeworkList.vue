<template>
  <div class="homework-container">
    <h1 class="page-title">作业列表</h1>
    
    <el-card shadow="never" class="list-card">
      <!-- 加载状态 -->
      <el-skeleton :loading="loading" animated>
        <template #default>
          <!-- 作业列表 -->
          <transition-group name="list" tag="div">
            <el-card 
              v-for="homework in homeworklist" 
              :key="homework.homeworkId"
              shadow="hover" 
              class="homework-item"
            >
              <div class="homework-header">
          
                <h3 class="homework-title">{{ homework.homeworkName }}</h3>
              </div>
              
              <div class="homework-content">
                <p>{{ homework.homeworkContent }}</p>
              </div>
              
              <div class="homework-footer">
                <el-button 
                  type="primary" 
                  size="small" 
                  @click="downloadHomework(homework.homeworkUrl)"
                >
                  <el-icon><Download /></el-icon>
                  下载作业
                </el-button>
                
                <el-button 
                  type="success" 
                  size="small"
                  @click="toggleSubmissionArea(homework.homeworkId)"
                >
                  <el-icon><View /></el-icon>
                  {{ activeSubmission === homework.homeworkId ? '取消提交' : '完成作业' }}
                </el-button>

                <el-button 
                  type="info" 
                  size="small" 
                  @click="getmyscore(homework.homeworkId)"
                >
                  <el-icon><ZoomIn /></el-icon>
                  完成情况
                </el-button>
              </div>

              <!-- 作业提交区域 -->
              <el-collapse-transition>
                <div v-show="activeSubmission === homework.homeworkId" class="submission-area">
                  <el-input
                    v-model="submissionText[homework.homeworkId]"
                    type="textarea"
                    :rows="3"
                    placeholder="请输入作业说明或内容"
                    class="submission-textarea"
                  />
                  
                  <div class="upload-section">
                    <el-upload
  class="upload-demo"
  action="https://jsonplaceholder.typicode.com/posts/"
  :on-change="handleFileChange"
  :auto-upload="false"
  :limit="1"
  :show-file-list="true"
>
  <el-button type="primary">
    <el-icon><Upload /></el-icon>
    选择文件
  </el-button>
  <template #tip>
    <div class="el-upload__tip">
      每次只能上传一个文件，不超过10MB
    </div>
  </template>
</el-upload>
                    
                    <el-button 
                      type="success" 
                      @click="submitHomework(homework.homeworkId)"
                      class="submit-btn"
                    >
                      <el-icon><Check /></el-icon>
                      提交作业
                    </el-button>
                  </div>
                </div>
              </el-collapse-transition>
            </el-card>
          </transition-group>
          
          <!-- 空状态 -->
          <el-empty v-if="!loading && homeworklist.length === 0" description="暂无作业" />
        </template>
      </el-skeleton>
    </el-card>
  </div>
  <el-dialog 
    v-model="dialogVisible" 
    title="作业完成情况" 
    width="50%"
  >
    <div class="homework-detail">
      <el-descriptions border :column="1">
        <el-descriptions-item label="作业ID">{{ singalHomework.homeworkId }}</el-descriptions-item>
        <el-descriptions-item label="提交内容">{{ singalHomework.homeworkContent }}</el-descriptions-item>
        <el-descriptions-item label="作业文件">
          <el-link 
            v-if="singalHomework.homeworkUrl" 
            :href="singalHomework.homeworkUrl" 
            target="_blank"
            type="primary"
          >
            下载作业文件
          </el-link>
          <span v-else>无</span>
        </el-descriptions-item>
        <el-descriptions-item label="分数">
          <el-tag :type="getScoreTagType(singalHomework.homeworkScore)">
            {{ singalHomework.homeworkScore || '未评分' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStateTagType(singalHomework.homeworkState)">
            {{ getStateText(singalHomework.homeworkState) }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>
    </div>
    
    <template #footer>
      <el-button @click="dialogVisible = false">关闭</el-button>
    </template>
  </el-dialog>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { Download, View, Upload, Check, ZoomIn } from '@element-plus/icons-vue'
import { getHomeworkAPI } from '@/apis/user'
import { uploadFile } from '@/apis/user'
import { submitHomeworkAPI } from '@/apis/student'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/userStore'
import { getHomeworkVOAPI } from '@/apis/student'
const dialogVisible = ref(false)
const route = useRoute()
const homeworklist = ref([])
const loading = ref(true)
const activeSubmission = ref(null)
const submissionText = ref({})
const selectedFiles = ref({})
const fileUploading = ref(false)
const submitting = ref(false)
const userStore = useUserStore()
const fileurl =ref()
const singalHomework=ref({})
// 获取作业列表
const getHomeworkList = async () => {
  try {
    loading.value = true
    const courseId = route.params.courseId
    const res = await getHomeworkAPI(courseId)
    homeworklist.value = res.data || []
  } catch (err) {
    console.error('获取作业失败:', err)
    ElMessage.error('加载作业列表失败')
  } finally {
    loading.value = false
  }
}

// 切换提交区域显示
const toggleSubmissionArea = (homeworkId) => {
  if (activeSubmission.value === homeworkId) {
    activeSubmission.value = null
  } else {
    activeSubmission.value = homeworkId
    // 初始化提交文本
    if (!submissionText.value[homeworkId]) {
      submissionText.value[homeworkId] = ''
    }
  }
}

// 处理文件选择 - 修改为只保留最新选择的单个文件
const handleFileChange = (file, fileList) => {
  if (fileList.length > 1) {
    // 如果选择了多个文件，只保留最后一个
    fileList = [fileList[fileList.length - 1]]
  }
  selectedFiles.value[activeSubmission.value] = fileList
}


const getmyscore = async (homeworkId) => {
  try {
    const res = await getHomeworkVOAPI(homeworkId, userStore.userInfo.id)
    singalHomework.value = res.data
    dialogVisible.value = true // 显示对话框
  } catch (error) {
    console.error('获取作业详情失败:', error)
    ElMessage.error('获取作业详情失败')
  }
}

// 添加辅助方法
const getScoreTagType = (score) => {
  if (score === null || score === undefined) return 'info'
  if (score >= 90) return 'success'
  if (score >= 60) return 'warning'
  return 'danger'
}

const getStateTagType = (state) => {
  switch (state) {
    case 1: return 'success' // 已提交
    case 2: return 'warning' // 已批改
    default: return 'info'   // 其他状态
  }
}

const getStateText = (state) => {
  switch (state) {
    case 0: return '未提交'
    case 1: return '已提交'
    case 2: return '已批改'
    default: return '未知状态'
  }
}
// 下载作业
const downloadHomework = (url) => {
  if (!url) {
    ElMessage.warning('作业文件不存在')
    return
  }
  window.open(url, '_blank')
}

// 上传单个文件fileurl.value =res.data
const uploadSingleFile = async (homeworkId) => {
  const files = selectedFiles.value[homeworkId] || []
  if (files.length === 0) return null

  try {
    fileUploading.value = true
    const formData = new FormData()
    // 只上传第一个文件
    formData.append('file', files[0].raw)

    const res = await uploadFile({ formData })
    fileurl.value=res.data
    console.log('fileurl',fileurl.value)
  } catch (error) {
    console.error('文件上传失败:', error)
    ElMessage.error('文件上传失败')
    return null
  } finally {
    fileUploading.value = false
  }
}

// 提交作业
const submitHomework = async (homeworkId) => {
  const text = submissionText.value[homeworkId] || ''
  const files = selectedFiles.value[homeworkId] || []
  
  if (!text.trim() && files.length === 0) {
    ElMessage.warning('请填写作业内容或上传文件')
    return
  }

  try {
    submitting.value = true
    
    if (files.length > 0) {
      await uploadSingleFile(homeworkId)
      
    }

    // 2. 准备提交数据
    const formData = {
      homeworkId: homeworkId,
      studentId: userStore.userInfo.id,
      homeworkContent: text,
      homeworkUrl: fileurl.value // 直接使用单个文件URL
    }

    // 3. 提交作业
    await submitHomeworkAPI(formData)
    
    ElMessage.success('作业提交成功')
    activeSubmission.value = null
    
    // 4. 刷新作业列表
    await getHomeworkList()
    
    // 5. 重置表单
    submissionText.value[homeworkId] = ''
    selectedFiles.value[homeworkId] = []
    
  } catch (error) {
    console.error('作业提交失败:', error)
    ElMessage.error('作业提交失败')
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  getHomeworkList()
})
</script>

<style scoped>
.homework-container {
  padding: 20px;
  width: 1300px;
  margin: 0 auto;
}

.page-title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}

.list-card {
  border-radius: 8px;
  background-color: #f8fafc;
}

.homework-item {
  margin-bottom: 20px;
  transition: all 0.3s ease;
  border-radius: 6px;
}

.homework-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.homework-header {
  display: flex;
  align-items: center;
  margin-bottom: 12px;
}

.homework-title {
  margin-left: 12px;
  margin-bottom: 0;
  font-size: 18px;
  color: #333;
}

.homework-content {
  margin: 12px 0;
  color: #666;
  line-height: 1.6;
}

.homework-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

/* 提交区域样式 */
.submission-area {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px dashed #eee;
}

.submission-textarea {
  margin-bottom: 15px;
}

.upload-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.submit-btn {
  margin-left: 15px;
}

/* 列表动画 */
.list-enter-active,
.list-leave-active {
  transition: all 0.5s ease;
}
.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
.homework-detail {
  padding: 20px;
}

.el-descriptions {
  margin-top: 20px;
}

.el-tag {
  margin-right: 10px;
}
</style>    