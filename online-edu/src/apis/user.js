import request from '@/utils/newhttp'
import httpInstance from "@/utils/newhttp"
//获取所有课程
export const getAllCoursesAPI = () => {
    return request({
      url: '/user/courselist',
      method: 'Get',
    })
  }

//获取学生所有课程
export const getMyCoursesAPI = (studentId) => {
  return request({
    url: `/student/getmycourses/${studentId}`,
    method: 'Get',
  })
}

  //根据名称获取全部课程基本信息
export const getCoursesByNameAPI = (courseName) => {
  return request({
    url: `/user/courselist/${courseName}`,
    method: 'Get',
    data:
      courseName   
  })
}

//根据类型获取全部课程基本信息
export const getCoursesByTypeAPI = (type) => {
  return request({
    url: `/user/courselistbytype/${type}`,
    method: 'Get',
    data:
      type
    
  })
}

//登录
export const loginAPI = ({phone,password}) => {
  return request({
    url: '/user/login',
    method: 'Post',
    data: {
      phone,
      password}
  })
}

  //注册
  export const registerAPI = ({phone,password,identity,email,name}) => {
    return request({
      url: '/user/register',
      method: 'Post',
      data: {phone,password,identity,email,name}
    })
  }

  //获取课程粗略信息
  export const getCourseInfoAPI = (courseId) => {
    return request({
      url: `/user/courseinfo/${courseId}`,
      method: 'Get',
      data: courseId
    })
  }

  //获取课程详细信息
  export const getCourseDetailAPI = (courseId) => {
    return request({
      url: `/student/getdetail/${courseId}`,
      method: 'Get',
      data: courseId
    })
  }

  //获取布置的作业
  export const getHomeworkAPI = (courseId) => {
    return request({
      url: `/user/gethomework/${courseId}`,
      method: 'Get',
      data: courseId
    })
  }



  //获取该课程作业学生的完成情况
  export const getHomeworksVOsAPI = ({courseId,studentId}) => {
    return request({
      url: '/user/gethomeworkscore',
      method: 'Get',
      data: {
        courseId,
        studentId
      }
    }
  )
  }

//上传文件
  export function uploadFile({formData}) {
    return httpInstance({
      url: 'user/upload', 
      method: 'POST', 
       headers: {
         'Content-Type': 'multipart/form-data'
      },
      data: formData 
    });
  }

  //根据用户id获取数据
  export const getUserInfoByIdAPI = (id) => {
    return request({
      url: `/user/${id}`,
      method: 'Get',
    })
  }