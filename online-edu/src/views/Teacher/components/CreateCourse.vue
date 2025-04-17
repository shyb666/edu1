<template>
    <div class="course-upload-container">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>课程信息提交</span>
          </div>
        </template>
  
        <el-form 
          ref="formRef" 
          :model="formData" 
          :rules="rules" 
          label-width="120px"
          class="demo-ruleForm"
        >
          <el-form-item label="课程名称" prop="courseName">
            <el-input v-model="formData.courseName" placeholder="请输入课程名称" />
          </el-form-item>
  
  
          <el-form-item label="课程类型" prop="type">
            <el-select v-model="formData.type" placeholder="请选择课程类型">
              <el-option label="计算机" value="计算机" />
              <el-option label="数学" value="数学" />
              <el-option label="英语" value="英语" />
              <el-option label="美术" value="美术" />
              <el-option label="政治" value="政治" />
              <el-option label="法律" value="法律" />
              <el-option label="其他" value="其他" />
            </el-select>
          </el-form-item>
  
          <el-form-item label="课程封面" prop="coverUrl">
            <el-upload
              class="avatar-uploader"
              action="#"
              :show-file-list="false"
              :auto-upload="false"
              :on-change="handleFileChange"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="formData.coverUrl" :src="formData.coverUrl" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
            <div class="el-upload__tip">请上传课程封面图片（支持JPG/PNG格式，不超过2MB）</div>
          </el-form-item>
  
          <el-form-item>
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </template>
  
  <script setup>
  import { ref, reactive } from 'vue'
  import { Plus } from '@element-plus/icons-vue'
  import { ElMessage } from 'element-plus'
  import { uploadFile} from '@/apis/user'
  import { postCourseAPI} from '@/apis/teacher'
  import { useUserStore } from '@/stores/userStore'
  const formRef = ref(null)
  const file = ref(null)
  const userStore=useUserStore()
  const formData = reactive({
    courseName: '',
    teacherId: null,
    type: '',
    coverUrl: ''
  })
  
  const rules = reactive({
    courseName: [
      { required: true, message: '请输入课程名称', trigger: 'blur' },
      { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
    ],
    teacherId: [
      { required: true, message: '请输入教师ID', trigger: 'blur' },
      { type: 'number', message: '教师ID必须为数字', trigger: 'blur' }
    ],
    type: [
      { required: true, message: '请选择课程类型', trigger: 'change' }
    ],
    coverUrl: [
      { required: true, message: '请上传课程封面', trigger: 'blur' }
    ]
  })
  
  // 文件选择处理
  const handleFileChange = (uploadFile) => {
    file.value = uploadFile.raw
    previewImage(uploadFile.raw)
  }
  
  // 图片预览
  const previewImage = (file) => {
    const reader = new FileReader()
    reader.onload = (e) => {
      formData.coverUrl = e.target.result
    }
    reader.readAsDataURL(file)
  }
  
  // 上传前校验
  const beforeAvatarUpload = (rawFile) => {
    const isJpgOrPng = ['image/jpeg', 'image/png'].includes(rawFile.type)
    const isLt2M = rawFile.size / 1024 / 1024 < 2
  
    if (!isJpgOrPng) {
      ElMessage.error('封面图片只能是 JPG/PNG 格式!')
      return false
    }
    if (!isLt2M) {
      ElMessage.error('封面图片大小不能超过 2MB!')
      return false
    }
    
    return true
  }
  
  

  // 上传文件
  const uploadCover = async () => {
    if (!file.value) {
      throw new Error('请选择封面文件')
    }
  
    const formData = new FormData()
    formData.append('file', file.value)
  
    try {
      const res = await uploadFile({ formData })
      return res.data // 假设返回的就是coverUrl字符串
    } catch (error) {
      console.error('上传失败:', error)
      ElMessage.error('封面图片上传失败')
      throw error
    }
  }
  
  // 提交表单
  const submitForm = async () => {
    try {
      // 验证表单
      await formRef.value.validate()
      
      // 上传封面
      ElMessage.info('正在上传封面...')
      const coverUrl = ref({})
      coverUrl.value= await uploadCover()
      console.log(coverUrl.value)
      
      // 准备提交数据
      const submitData = {
        courseName: formData.courseName,
        teacherId: userStore.userInfo.id,
        type: formData.type,
        coverUrl: coverUrl.value
      }
      
      // 提交课程信息
      ElMessage.info('正在提交课程信息...')
      const res = await postCourseAPI(submitData)
      
      ElMessage.success('课程提交成功')
      console.log('提交结果:', res)
      resetForm()
      
    } catch (error) {
      console.error('提交失败:', error)
      if (error.message !== '请选择封面文件') {
        ElMessage.error('提交失败: ' + (error.response?.data?.message || error.message))
      }
    }
  }
  
  // 重置表单
  const resetForm = () => {
    formRef.value.resetFields()
    file.value = null
    formData.coverUrl = ''
  }
  </script>
  
  <style scoped>
  .course-upload-container {
    padding: 20px;
    max-width: 800px;
    margin: 0 auto;
  }
  
  .avatar-uploader {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 178px;
    height: 178px;
  }
  
  .avatar-uploader:hover {
    border-color: var(--el-color-primary);
  }
  
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
    object-fit: cover;
  }
  
  .el-upload__tip {
    margin-top: 7px;
    color: var(--el-text-color-secondary);
    font-size: 12px;
  }
  </style>