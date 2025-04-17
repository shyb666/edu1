package com.sky.service;

import com.sky.dto.CourseDTO;
import com.sky.dto.SubmitHomeworkDTO;
import com.sky.entity.Course;
import com.sky.entity.HomeWork;
import com.sky.entity.UserHomework;
import com.sky.vo.BaseCourseVO;
import com.sky.vo.CourseDetailVO;
import com.sky.vo.UserHomeworkVO;

import java.util.List;

public interface StudentService {

    /**
     * 获取课程详细信息
     */
    CourseDetailVO getCoursesDetailInfo(Long courseId);

    /**
     * 根据分类获取课程
     */
    List<BaseCourseVO> getCoursesByType(String type);

    /**
     * 根据名字模糊查询课程
     */
    List<BaseCourseVO> getCoursesByName(String courseName);

    /**
     * 获取全部课程基本信息
     */
    List<BaseCourseVO> getAllBaseCourses();

    /**
     * 获取学生加入的课程基本信息
     */
    List<Course> getMyBaseCourses(Long studentId);

    /**
     * 加入课程
     * courseId
     */
    void addCourseStudent(Long id,Long courseId);

    /**
     * 查看课程作业详情（包括成绩）
     * courseId，studentId
     */
    List<UserHomeworkVO> getHomework(Long courseId,Long studentId);

    /**
     * 查看单条课程作业详情（包括成绩）
     * homeworkId，studentId
     */
    UserHomework getAHomework(Long homeworkId, Long studentId);

    /**
     * 退出课程
     * courseId
     */
    void deleteCourseStudent(Long id,Long courseId);

    /**
     * 提交作业
     * @param submitHomeworkDTO
     */
    void submitHomework(SubmitHomeworkDTO submitHomeworkDTO);
}
