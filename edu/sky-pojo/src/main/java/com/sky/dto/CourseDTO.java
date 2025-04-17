package com.sky.dto;

import lombok.Data;

@Data
public class CourseDTO {
    //课程名
    private String courseName;
    //教师id
    private Long teacherId;
    //类型
    private String type;
    //封面
    private String coverUrl;
}
