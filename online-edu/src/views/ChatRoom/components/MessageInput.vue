<template>
  <div class="message-input">
    <!-- 工具栏 -->
    <div class="toolbar">
      <!-- 上传图片 -->
      <el-upload
        action="https://your-upload-endpoint.com/upload"
        :before-upload="handleBeforeUploadImage"
        :show-file-list="false"
      >
        <el-button type="text" title="上传图片">
          <el-icon><Picture /></el-icon>
        </el-button>
      </el-upload>

      <!-- 上传文件 -->
      <el-upload
        action="https://your-upload-endpoint.com/upload"
        :before-upload="handleBeforeUploadFile"
        :show-file-list="false"
      >
        <el-button type="text" title="上传文件">
          <el-icon><Document /></el-icon>
        </el-button>
      </el-upload>

      <!-- 选择表情 -->
      <el-button type="text" title="选择表情" @click="openEmojiPicker">
        <el-icon><ChatDotRound /></el-icon>
      </el-button>
    </div>

    <!-- 输入框 -->
    <el-input
      v-model="inputText"
      placeholder="Type a message"
      type="textarea"
      @keyup.enter="sendMessage"
      :rows="3"
    />

    <!-- 发送按钮 -->
    <el-button type="primary" @click="sendMessage">Send</el-button>
  </div>
</template>
  
<script setup>
import { ref, onMounted } from 'vue';
import { getCourseInfoAPI } from '@/apis/user';
import { useUserStore } from '@/stores/userStore';

const props = defineProps({
  ws: Object,
  messages: Array,
  courseId: Number,
  receiverId: Number,
});

const emit = defineEmits(['send-message']);
const userStore = useUserStore()
const inputText = ref('')
const receiverId = ref(null) // 改为响应式ref

// 发送消息
const sendMessage = () => {
  if (inputText.value.trim()) {
    if(userStore.userDetailInfo.identity==0){
    const message = {
      senderId: userStore.userInfo.id,
      receiverId: receiverId.value, 
      content: inputText.value,
    };
    emit('send-message', message);
    emit('message-toshow', message);
    inputText.value = '';
  }
  if(userStore.userDetailInfo.identity==1){
    const message = {
      senderId: userStore.userInfo.id,
      receiverId: props.receiverId, // 直接使用receiverId.value
      content: inputText.value,
    };
    emit('send-message', message);
    emit('message-toshow', message);
    inputText.value = '';
  }
}
};

// 获取接收者ID
const getReceiverId = async () => {
  try {
    const res = await getCourseInfoAPI(props.courseId)
    if (res.data) {
      receiverId.value = res.data.teacherId
      console.log('接收者ID:', receiverId.value)
    }
  } catch (error) {
    console.error('获取教师ID失败:', error)
    // 可以添加用户提示，如：
    // ElMessage.error('获取教师信息失败')
  }
}

onMounted(() => {
  if(userStore.userDetailInfo.identity==0){
    getReceiverId()
  }
  
})
</script>
  
<style scoped>
.message-input {
  padding: 5px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: #f9f9f9;
}

.toolbar {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
}

.toolbar .el-button {
  margin-right: 10px;
  padding: 5px;
}

.toolbar .el-button:hover {
  background-color: #f0f0f0;
  border-radius: 4px;
}

.el-textarea {
  margin-bottom: 10px;
}

.el-button--primary {
  width: 100%;
}
</style>