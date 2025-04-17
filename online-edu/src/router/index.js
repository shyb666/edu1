// createRouter：创建router实例对象
// createWebHistory：创建history模式的路由

import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login/index.vue'
import Layout from '@/views/Layout/index.vue'
import CoursesHome from '@/views/CoursesHome/index.vue'
import Register from '@/views/Register/index.vue'
import CourseIntro from '@/views/CourseIntro/index.vue'
import Mycourses from '@/views/MyCourses/index.vue'
import CourseDetail from '@/views/Course/index.vue'
import HomeworkList from '@/views/Course/components/HomeworkList.vue'
import ClassList from '@/views/Course/components/ClassList.vue'
import ChatRoom from '@/views/ChatRoom/index.vue'
import Teacher from '@/views/Teacher/index.vue'

//教师端
import TeacherChatroom from '@/views/ChatRoom/index.vue'
import CreateCourse from '@/views/Teacher/components/CreateCourse.vue'
import AddClass from '@/views/Teacher/components/AddClass.vue'
import AddHomework from '@/views/Teacher/components/AddHomework.vue'
import MyCreateCourseDetail from '@/views/Teacher/components/MyCourseDetail.vue'
import MyCreatedCourses from '@/views/Teacher/components/MyCreatedCourses.vue'

//检索
import SeaechByName from '@/views/CoursesHome/components/SeaechByName.vue'
import SeaechByType from '@/views/CoursesHome/components/SeaechByType.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  // path和component对应关系的位置
  routes: [
    {
      path: '/',
      component: Layout,
      children: [
        {
          path: '',
          component: CoursesHome
        },
        {
          path: '/teacher',
          component: Teacher,
          children:[
            {
                  path: 'createcourse',
                  component: CreateCourse
            },
            {
              path: 'mycoursedetail/:courseId',
              name: 'MyCreateCourseDetail',
              component: MyCreateCourseDetail,
            },
            {
              path: 'addclass/:courseId',
              name: 'AddClass',
              component: AddClass
            },
            {
              path: 'addhomework/:courseId',
              name: 'AddHomework',
              component: AddHomework
            },
            {
      path: 'mycourses',
      component: MyCreatedCourses
    },
{
  path: 'chatroom',
  component: TeacherChatroom
}
          ]
        },
        {
          path: 'intro/:courseId',
          name: 'CourseIntro',
          component: CourseIntro
        },
        {
          path: 'searchbyname/:courseName',
          name: 'SeaechByName',
          component: SeaechByName
        },
        {
          path: 'searchbytype/:type',
          name: 'SeaechByType',
          component: SeaechByType
        },
        {
          path: 'mycourses',
          component: Mycourses         
        },
        {
          path: 'coursedetail/:courseId',
          name: 'CourseDetail',
          component: CourseDetail,
          children:[
            {
                  path: 'chatroom',
                  component: ChatRoom
            },
            {
              path: 'homeworklist',
              component: HomeworkList
        },
        {
          path: 'classlist',
          component: ClassList
    }
          ]
        },
     
      ]
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/register',
      component: Register
    }
  ],
  // 路由滚动行为定制
  scrollBehavior () {
    return {
      top: 0
    }
  }
})

export default router
