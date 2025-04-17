<template>
  <div class="chat-panel">
    <!-- 消息列表 -->
    <div 
      v-for="message in messages" 
      :key="message.id" 
      class="message" 
      :class="{ 
        'message-right': message.isMe, 
        'message-left': !message.isMe 
      }"
    >
      <!-- 头像和昵称 -->
      <div class="message-avatar">
        <el-avatar 
          :size="40" 
          :src="message.isMe ? myAvatar : otherAvatar" 
          class="avatar" 
        />
        <span class="nickname">
          {{ message.isMe ? 'You' : message.senderName || 'Server' }}
        </span>
      </div>
      
      <!-- 消息内容 -->
      <div class="message-content">
        <div 
          class="message-bubble" 
          :class="{ 
            'my-bubble': message.isMe, 
            'other-bubble': !message.isMe 
          }"
        >
          <template v-if="message.type === 'text'">
            {{ message.text }}
          </template>
        </div>
        <span class="message-time">
          {{ formatTime(message.time) }}
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  messages: Array
});

const myAvatar = 'https://example.com/my-avatar.jpg';
const otherAvatar = 'https://example.com/default-avatar.jpg';

const formatTime = (time) => {
  if (!time) return '';
  const date = new Date(time);
  return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
};
</script>

<style scoped>
.chat-panel {
  margin-top: 0;
  background-color: white;
  height: 400px;
  overflow-y: auto;
  padding: 10px;
  display: flex;
  flex-direction: column;
}

.message {
  display: flex;
  margin-bottom: 15px;
  max-width: 80%;
}

.message-left {
  align-self: flex-start;
}

.message-right {
  align-self: flex-end;
  flex-direction: row-reverse;
}

.message-avatar {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 10px;
  flex-shrink: 0;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.nickname {
  font-size: 12px;
  color: #666;
  margin-top: 5px;
  white-space: nowrap;
}

.message-content {
  display: flex;
  flex-direction: column;
}

.message-bubble {
  padding: 10px 15px;
  border-radius: 18px;
  position: relative;
  max-width: 100%;
  word-wrap: break-word;
}

.my-bubble {
  background-color: #4CAF50; /* 绿色气泡 - 自己发送的消息 */
  color: white;
  border-top-right-radius: 2px;
}

.other-bubble {
  background-color: #f0f0f0; /* 灰色气泡 - 接收的消息 */
  color: #333;
  border-top-left-radius: 2px;
}

/* 消息气泡的小三角 */
.my-bubble::after {
  content: '';
  position: absolute;
  top: 10px;
  right: -9px;
  border-width: 10px 0 10px 10px;
  border-style: solid;
  border-color: transparent transparent transparent #4CAF50;
}

.other-bubble::after {
  content: '';
  position: absolute;
  top: 10px;
  left: -10px;
  border-width: 10px 10px 10px 0;
  border-style: solid;
  border-color: transparent #f0f0f0 transparent transparent;
}

.message-time {
  font-size: 11px;
  color: #999;
  margin-top: 4px;
}

.message-right .message-time {
  align-self: flex-end;
}

.message-left .message-time {
  align-self: flex-start;
}

/* 滚动条样式 */
.chat-panel::-webkit-scrollbar {
  width: 6px;
}

.chat-panel::-webkit-scrollbar-thumb {
  background-color: rgba(0, 0, 0, 0.2);
  border-radius: 3px;
}
</style>