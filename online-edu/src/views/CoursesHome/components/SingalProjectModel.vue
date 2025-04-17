<script setup>
// 定义props
defineProps({
  // 主标题
  courseId: {
    type: Number
  },
  courseName: {
    type: String
  },
  teacherId: {
    type: Number
  },
  coverUrl:{
    type: String
  },
  Type:{
    type: String
  },
  createTime: {
    type: String
  },
  
})
</script>

<template>
  <div class="singal-item">
    <div class="container">
      <div class="head">
        <div class="image-container">
      <img :src="coverUrl"  class="image" style="width: 263px;height: 176px" />
    </div>
        <!-- 主体 -->
        <h3>
          {{ courseName }}
        </h3>
        <h4>
          {{ Type }}
        </h4>
        <h5>
          {{ createTime }}
        </h5>
      </div>
    </div>
  </div>
</template>

<style scoped>
.img{
    margin: 30px;
}
.image-container{
    margin-right: 30px;
}
</style>