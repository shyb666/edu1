package com.sky.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Course {
    private Long courseId;
    //课程名
    private String courseName;
    //教师id
    private Long teacherId;
    //类型
    private String type;
    // 创建时间
    private LocalDateTime createTime;
    //作业数
    private int homeworkNum;
    //学生数
    private int studentNum;
    //课数
    private int classNum;
    //封面
    private String coverUrl;
}
