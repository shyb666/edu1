import request from '@/utils/newhttp'
//加入课程
export const joinCourseAPI = ({ courseId, studentId }) => {
  return request({
    url: '/student/joincourse',
    method: 'POST',
    data: new URLSearchParams({ courseId, studentId }).toString(),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded' // 必须显式设置
    }
  })
}

//提交作业
export const submitHomeworkAPI = (formData) => {
  return request({
    url: 'student/submithomework',
    method: 'Post',
    data:formData
  })
}


  //获取单个作业完成情况
  export const getHomeworkVOAPI = (homeworkId,studentId) => {
    return request({
      url: '/student/gethomeworkdetail',
      method: 'Get',
      params: {
homeworkId,
studentId
      }
    })
  }

  