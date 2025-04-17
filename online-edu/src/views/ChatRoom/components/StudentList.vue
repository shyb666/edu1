<template>
  <div class="container1">
    <!-- 列表和成员信息的容器 -->
     <h1>点击切换答疑对象</h1>
    <div class="content_container">
      <!-- 成员列表 -->
      <div 
        class="member_list" 
        :style="{ 
          marginRight: memberInfo.studentId !== 0 ? '380px' : '0', 
          transition: 'margin-right 0.3s ease' 
        }"
      >
        <el-table 
          :data="mlist" 
          style="width: 100%" 
          class="m_tab" 
          @row-click="handleRowClick"
          :row-style="rowStyle"
        >
        <el-table-column class="col_item" prop="id" label="学生Id" width="170">
            <template #default="{ row }">
              <div style="display: flex; align-items: center;">
                <span>{{ row.studentId }}</span>
              </div>
            </template>
          </el-table-column>
          <!-- Name 列 -->
          <el-table-column class="col_item" prop="name" label="学生姓名" width="170">
            <template #default="{ row }">
              <div style="display: flex; align-items: center;">
                <span>{{ row.studentName }}</span>
              </div>
            </template>
          </el-table-column>
          
        </el-table>
      </div>  
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, defineEmits } from 'vue'
import { getMyStudentsAPI } from '@/apis/teacher'
import { useUserStore } from '@/stores/userStore'

const emit = defineEmits(['row-clicked']) // 定义要触发的事件

const userStore = useUserStore()
const mlist = ref([])
const receiverId = ref(null)

const handleRowClick = async (row) => {
if (row.studentId) { // 确保点击的是有数据的行，不是空白行
  receiverId.value = row.studentId
  console.log("接收者id设置", receiverId.value)
  emit('row-clicked', row.studentId) // 触发事件并传递 studentId
}
}

const getmemberlist = async () => {
try {
  console.log('开始获取学生列表')
  const res = await getMyStudentsAPI(userStore.userInfo.id)
  console.log('获取到的学生数据:', res.data)
  
  // 预留3个空白行
  const blankRows = 3
  const blankData = Array(blankRows).fill({ 
      studentId: '',
      studentName: '',   
  })
  
  // 合并实际数据与空白行
  mlist.value = [...res.data, ...blankData]
  console.log('最终列表数据:', mlist.value)
} catch (error) {
  console.error('获取学生列表失败:', error)
}
}

const rowStyle = () => {
return {
  height: '50px',
  lineHeight: '50px'
}
}

const memberInfo = ref({
studentName: '',
studentId: 0,
})

onMounted(() => {
  if(userStore.userDetailInfo.identity==1)
 { getmemberlist()
}

})
</script>
  
  <style>
  .container1 {
    max-width: 300px;
    display: flex;
    flex-direction: column;
    background-color: rgb(255, 255, 255);
    margin-top: 40px;
    padding-top: 10px;
    border-radius: 6px;
    border: 1.8px solid rgb(206, 206, 206);
    box-shadow: 2px 2px 5px rgba(138, 138, 138, 0.5);
  }
  
  .table_title {
    text-align: center; /* 标题居中 */
    margin-bottom: 20px; /* 与表格的间距 */
  }
  
  .content_container {
    display: flex;
    position: relative;
    overflow: hidden; /* 隐藏溢出的内容 */
  }
  
  .member_list {width: 100%;
    max-height: 400px;
    flex: 1;
  
    padding-bottom: 30px;
    transition: margin-right 0.3s ease; /* 添加过渡效果 */
  }
  
  
  
  .avatar {
    margin-left: 58px;
  }
  
  
  .card_item {
    line-height: 34px;
  }
  
  /* 设置表格行高 */
  .m_tab .el-table__row {
    height: 50px; /* 每行高度设置为50px */
    line-height: 50px; /* 确保内容垂直居中 */
  }
  
  /* 设置表头高度（可选） */
  .m_tab .el-table__header th {
    height: 50px; /* 表头高度设置为50px */
    line-height: 50px; /* 确保内容垂直居中 */
  }
  </style>