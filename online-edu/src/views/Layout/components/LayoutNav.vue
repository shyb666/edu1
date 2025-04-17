<script setup>
import { useUserStore } from '@/stores/userStore'
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue' // 引入 ref 用于管理红点状态

const userStore = useUserStore()
const router = useRouter()

// 红点状态
const hasNewMessage = ref(true) // 假设有新消息

const confirm = () => {
  console.log('用户要退出登录了')
  // 退出登录业务逻辑实现
  // 1.清除用户信息 触发action
  userStore.clearUserInfo()
  // 2.跳转到登录页
  router.push('/login')
}

onMounted(()=>{
  userStore.getUserInfoById(userStore.userInfo.id)
  console.log(userStore.userDetailInfo)
})
</script>

<template>
  <nav class="app-topnav">
    <div class="container">
      <ul>
        <!-- 多模版渲染 区分登录状态和非登录状态 -->
        <template v-if="userStore.userInfo.token">
          
          
          <el-menu  
          class="menu1"
          mode="vertical"
          :collapse="true"
          :ellipsis="false"
          >
            <el-sub-menu class="mt" >
              <template #title class="mt"><h4>{{userStore.userInfo.name}}</h4></template>
              <el-menu-item class="mi" index="2-1">我的关注</el-menu-item>
              <el-menu-item class="mi" index="2-2">我的收藏</el-menu-item>
              <el-menu-item class="mi" index="2-3">设置</el-menu-item>
            </el-sub-menu>
          </el-menu>
          
          <li>
            <el-popconfirm @confirm="confirm" title="确认退出吗?" confirm-button-text="确认" cancel-button-text="取消">
              <template #reference>
                <a href="javascript:;">退出登录</a>
              </template>
            </el-popconfirm>
          </li>
          <!-- 教师端链接 - 仅当identity为1时显示 -->
          <li v-if="userStore.userDetailInfo.identity === 1" >
            <a href="javascript:;" @click="$router.push('/teacher')">教师端</a>
          </li>
          <li v-if="userStore.userDetailInfo.identity === 0">
            <a href="javascript:;" @click="$router.push('/mycourses')">我的课程</a></li>
          <li>
            <a href="javascript:;" @click="$router.push('/userinfo')">
              
            </a>
          </li>
        </template>
        <template v-else>
          <li><a href="javascript:;" @click="$router.push('/login')">请先登录</a></li>
          <li><a href="javascript:;" @click="$router.push('/project')">帮助中心</a></li>
          <li><a href="javascript:;">关于我们</a></li>
        </template>
      </ul>
    </div>
  </nav>
</template>

<style scoped>
/* 铃铛图标容器 */
.bell-icon {
  position: relative;
  display: inline-block;
}

/* 红点样式 */
.red-dot {
  position: absolute;
  top: 0;
  right: 0;
  width: 8px;
  height: 8px;
  background-color: red;
  border-radius: 50%;
  transform: translate(50%, -50%); /* 调整红点位置 */
}
</style>


<style scoped lang="scss">
.app-topnav {
  background: #333;

  ul {
    display: flex;
    height: 53px;
    justify-content: flex-end;
    align-items: center;

    li {
      a {
        padding: 0 15px;
        color: #cdcdcd;
        line-height: 1;
        display: inline-block;

        i {
          font-size: 14px;
          margin-right: 2px;
        }

        &:hover {
          color: $xtxColor;
        }
      }

      ~li {
        a {
          border-left: 2px solid #666;
        }
      }
    }
  }
}
.menu1{
  background: #333;
  color: rgb(255, 255, 255);
}
h4{
  color: rgb(255, 255, 255);
}
</style>