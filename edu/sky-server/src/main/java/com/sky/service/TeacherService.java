package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.Course;
import com.sky.entity.User;
import com.sky.vo.SubmitHomeworkVO;
import com.sky.vo.UserVO;

import java.util.List;

public interface TeacherService {
    /**
     * 创建课程（不带课表）
     * @param courseDTO
     * @return
     */
    void createCourse(CourseDTO courseDTO);

    /**
     * 获取老师创建的课程基本信息
     */
    List<Course> getMyCreatedBaseCourses(Long teacherId);

    /**
     * 获取课程学生列表
     * courseId
     * @return
     */
    List<UserVO> getStudents(Long courseId);

    /**
     * 获取我的全部学生列表
     * courseId
     * @return
     */
    List<UserVO> getMyStudents(Long teacherId);

    /**
     * 插入课表
     * @param classDTO
     * @return
     */
    void insertClass(ClassDTO classDTO);

    /**
     * 修改课表
     * @param updateClassDTO
     * @return
     */
    void updateClass(UpdateClassDTO updateClassDTO);

    /**
     * 添加作业
     * @param addHomeworkDTO
     * @return
     */
    void addHomework(AddHomeworkDTO addHomeworkDTO);

    /**
     * 取消作业
     *  homeworkId
     * @return
     */
    void deleteHomework(Long homeworkId);

    /**
     * 获取学生作业
     * @return
     */
    SubmitHomeworkVO getSubmitHomework(Long homeworkId, Long studentId);

    /**
     * 学生作业评分
     * @return
     */
    void setHomeworkScore(Long homeworkId,Long studentId,int score);
}
