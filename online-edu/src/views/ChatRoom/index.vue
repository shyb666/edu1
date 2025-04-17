<template>
  <el-container class="chat_room_container">
    <el-main>
      <!-- 使用el-row和el-col实现响应式布局 -->
      <el-row :gutter="20">
        <!-- 左侧聊天区域 -->
        <el-col 
          :span="identity === 1 ? 16 : 24" 
          class="chat-section"
        >
          <ChatPanel :messages="messages" />
          <MessageInput 
            :ws="ws" 
            :receiverId="receiverId"
            :messages="messages" 
            :courseId="courseId"
            @send-message="handleSendMessage" 
            @message-toshow="handleMessageToShow"
          />
        </el-col>

        <!-- 右侧学生列表（仅教师可见） -->
        <el-col 
          v-if="identity === 1" 
          :span="8" 
          class="student-list-section"
        >
          <StudentList :courseId="courseId" @row-clicked="handleStudentSelected" />
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue';
import MessageInput from './components/MessageInput.vue'; // 发送消息的子页面
import ChatPanel from './components/ChatPanel.vue'; // 接收消息的子页面
import { useUserStore } from '@/stores/userStore';
import { useRoute } from 'vue-router';
import StudentList from './components/StudentList.vue';

// 消息列表
const messages = ref([]);
const localmessages = ref([]);
const userStore = useUserStore();
const route = useRoute();
const receiverId = ref();
const courseId = computed(() => {
  const id = route.params.courseId;
  return id ? Number(id) : null; // 确保转换为Number类型
});

const identity = userStore.userDetailInfo.identity;
// WebSocket 实例
let userId = userStore.userInfo.id;
let ws = null;

// 初始化 WebSocket
const initWebSocket = () => {
  ws = new WebSocket(`ws://localhost:8080/myWs1?userId=${userId}`);
  console.log("userId", userId);
  ws.onopen = () => {
    console.log('WebSocket connected');
    messages.value.push({
      id: messages.value.length + 1,
      type: 'text',
      text: 'Connected to WebSocket server',
      isMe: false,
    });
  };

  ws.onmessage = (event) => {
    console.log('Received message:', event.data);
    messages.value.push({
      id: messages.value.length + 1,
      type: 'text',
      text: event.data,
      isMe: false,
    });
  };

  ws.onerror = (error) => {
    console.error('WebSocket error:', error);
    messages.value.push({
      id: messages.value.length + 1,
      type: 'text',
      text: 'WebSocket error',
      isMe: false,
    });
  };

  ws.onclose = () => {
    console.log('WebSocket disconnected');
    messages.value.push({
      id: messages.value.length + 1,
      type: 'text',
      text: 'Disconnected from WebSocket server',
      isMe: false,
    });
  };
};

// 选择消息接收者
const handleStudentSelected = (studentId) => {
  receiverId.value = studentId;
  console.log('父组件接收到 studentId:', receiverId.value);
};

// 发送消息
const handleSendMessage = (message) => {
  if (ws && ws.readyState === WebSocket.OPEN) {
    ws.send(JSON.stringify(message));
    // 添加 isMe 标识
    const messageToShow = {
      ...message,
      id: messages.value.length + 1,
      type: 'text',
      text: message.content,
      isMe: true,
    };
    messages.value.push(messageToShow);
  } else {
    console.error('WebSocket is not connected');
  }
};

// 组件挂载时初始化 WebSocket
onMounted(() => {
  initWebSocket();
});

// 组件卸载时关闭 WebSocket
onUnmounted(() => {
  if (ws) {
    ws.close();
  }
});
</script>
   
   <style scoped>
   .chat_room_container {
     height: 100vh;
     width: 1200px;
   }
   
   /* 聊天区域样式 */
   .chat-section {
     display: flex;
     flex-direction: column;
     height: calc(100vh - 60px); /* 减去可能的header高度 */
   }
   
   /* 学生列表区域样式 */
   .student-list-section {
     border-left: 1px solid #ebeef5;
     padding-left: 20px;
     height: calc(100vh - 60px);
     overflow-y: auto;
   }
   
   /* 响应式调整 - 在小屏幕上学生列表全宽显示 */
   @media (max-width: 768px) {
     .el-col {
       width: 100% !important;
     }
     .student-list-section {
       border-left: none;
       border-top: 1px solid #ebeef5;
       padding-left: 0;
       padding-top: 20px;
     }
   }
   </style>