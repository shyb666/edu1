package com.sky.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CourseVO extends BaseCourseVO {

    //作业数
    private int homeworkNum;
    //学生数
    private int studentNum;
    //课数
    private int classNum;
    //课表
    private List<com.sky.entity.Class> classes = new ArrayList<>();
}

