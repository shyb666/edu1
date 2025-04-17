import request from '@/utils/newhttp'
//创建课程
export const postCourseAPI = (formData) => {
    return request({
      url: '/teacher/postcourse',
      method: 'Post',
      data:formData
    })
  }

  //修改课表
export const updateClassAPI = (formData) => {
  return request({
    url: '/teacher/updateclass',
    method: 'put',
    data:formData
  })
}

  //课表添加课程
export const postClassAPI = (formData) => {
    return request({
      url: '/teacher/postclass',
      method: 'Post',
      data:formData
    })
  }

  //课程添加作业
export const postHomeworkAPI = (formData) => {
    return request({
      url: '/teacher/posthomework',
      method: 'Post',
      data:formData
    })
  }

  //获取学生提交的作业
  export const getSubmitHomeworkAPI = (homeworkId,studentId) => {
    return request({
      url: '/teacher/getsubmithomework',
      method: 'Get',
      params: { homeworkId,studentId }
    })
  }

  //作业评分
  export const setHomeworkScoreAPI = (homeworkId,score,studentId) => {
    return request({
      url: '/teacher/setscore',
      method: 'Put',
      params: {homeworkId,score,studentId}
    })
  }

  //获取该课程学生列表
  export const getCourseStudentsAPI = (courseId) => {
    return request({
      url:  `/teacher/getstudents/${courseId} `,
      method: 'Get',
      
    })
  }

  //获取该课程学生列表
  export const getMyStudentsAPI = (teacherId) => {
    return request({
      url:  `/teacher/getmystudents/${teacherId} `,
      method: 'Get',
      
    })
  }

  //获取我创建的课程
  export const getMyCreatedCoursesAPI = (teacherId) => {
    return request({
      url: '/teacher/getmycourses',
      method: 'GET',
      params: { teacherId }  // axios 会自动将其转换为查询参数
    })
  }