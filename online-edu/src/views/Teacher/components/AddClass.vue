<template>
    <div class="course-upload-container">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>添加单节课程</span>
          </div>
        </template>
        <el-form 
          ref="formRef" 
          :model="formData" 
          :rules="rules" 
          label-width="120px"
          class="demo-ruleForm"
          style="display: flex; flex-direction: column; gap: 20px;"
        >
          <el-form-item label="课程名称" prop="className">
            <el-input v-model="formData.className" placeholder="请输入单节课程名称" />
          </el-form-item>
          <el-form-item label="课程内容" prop="classContent">
            <el-input v-model="formData.classContent" placeholder="请输入单节课程内容" />
          </el-form-item>
          <el-form-item label="课程视频" prop="videoUrl">
            <el-upload
              class="avatar-uploader"
              action="#"
              :show-file-list="false"
              :auto-upload="false"
              :on-change="handleFileChange"
              :before-upload="beforeAvatarUpload"
            >
              <el-button type="success">选择视频</el-button>
            </el-upload>
            <div class="el-upload__tip">请上传课程视频</div>
          </el-form-item>
          <el-form-item style="display: flex; gap: 10px;">
            <el-button type="primary" @click="submitForm" style="transition: all 0.3s ease;">提交</el-button>
            <el-button @click="resetForm" style="transition: all 0.3s ease;">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </template>
  
  <script setup>
  import { ref, reactive } from 'vue'
  import { ElMessage } from 'element-plus'
  import { uploadFile} from '@/apis/user'
  import { postClassAPI} from '@/apis/teacher'
  import { useUserStore } from '@/stores/userStore'
  import { useRoute } from 'vue-router'
  
  const formRef = ref(null)
  const file = ref(null)
  const route = useRoute()
  const userStore = useUserStore()
  
  const formData = reactive({
    className: '',
    classContent: '',
    courseId: route.params.courseId,
    videoUrl: ''
  })
  
  const rules = reactive({
    className: [
      { required: true, message: '请输入课程名称', trigger: 'blur' },
      { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
    ],
    classContent: [
      { required: true, message: '请输入课程内容', trigger: 'blur' }
    ]
  })
  
  // 文件选择处理
  const handleFileChange = (uploadFile) => {
    file.value = uploadFile.raw
  }
  
  // 上传文件
  const uploadCover = async () => {
    if (!file.value) {
      throw new Error('请选择视频文件')
    }
  
    const formDataToSend = new FormData()
    formDataToSend.append('file', file.value)
  
    try {
      const res = await uploadFile({ formData: formDataToSend })
      return res.data // 假设返回的就是videoUrl字符串
    } catch (error) {
      console.error('上传失败:', error)
      ElMessage.error('视频文件上传失败')
      throw error
    }
  }
  
  // 提交表单
  const submitForm = async () => {
    try {
      // 验证表单
      await formRef.value.validate()
      
      // 上传视频
      ElMessage.info('正在上传视频...')
      const videoUrl = await uploadCover()
      console.log(videoUrl)
      
      // 准备提交数据
      const submitData = {
        courseId: route.params.courseId,
        classContent: formData.classContent,
        className: formData.className,
        videoUrl: videoUrl
      }
      
      // 提交课程信息
      ElMessage.info('正在提交课程信息...')
      const res = await postClassAPI(submitData)
      
      ElMessage.success('课程提交成功')
      console.log('提交结果:', res)
      resetForm()
      
    } catch (error) {
      console.error('提交失败:', error)
      if (error.message !== '请选择视频文件') {
        ElMessage.error('提交失败: ' + (error.response?.data?.message || error.message))
      }
    }
  }
  
  // 重置表单
  const resetForm = () => {
    formRef.value.resetFields()
    file.value = null
    formData.videoUrl = ''
  }
  </script>
  
  <style scoped>
  .course-upload-container {
    padding: 20px;
    max-width: 800px;
    margin: 0 auto;
  }
  
  .box-card {
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    padding: 20px;
  }
  
  .card-header {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  .avatar-uploader {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 178px;
    height: auto;
    padding: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .avatar-uploader:hover {
    border-color: var(--el-color-primary);
  }
  
  .el-upload__tip {
    margin-top: 7px;
    color: var(--el-text-color-secondary);
    font-size: 12px;
  }
  
  .el-button {
    border-radius: 4px;
  }
  
  .el-button:hover {
    transform: scale(1.05);
  }
  </style>    