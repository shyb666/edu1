package com.sky.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//用户选择的课程列表VO
@Data
public class UserSelectCoursesVO extends UserVO {

    private List<BaseCourseVO> userCourses = new ArrayList<>();
}
