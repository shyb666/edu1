package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.HomeWork;
import com.sky.entity.User;
import com.sky.vo.BaseCourseVO;
import com.sky.vo.CourseVO;

import java.util.List;

public interface UserService {
    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);


    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    void save(UserDTO userDTO);


    /**
     * 获取教师布置的作业

     * courseId
     */
    List<HomeWork>  getHomeworkList(Long courseId);

    /**
     * 根据id查找用户

     * @param id
     */
    User getById( Long id);


    /**
     * 获取课程部分信息
     */
    CourseVO getCoursesInfo(Long courseId);

    /**
     * 获取全部课程基本部分信息
     */
    List<BaseCourseVO> getAllBaseCourses();

    /**
     * 根据名字获取课程基本部分信息
     */
    List<BaseCourseVO> getAllBaseCoursesByName(String courseName);

    /**
     * 根据类型获取课程基本部分信息
     */
    List<BaseCourseVO> getAllBaseCoursesByType(String type);
}
