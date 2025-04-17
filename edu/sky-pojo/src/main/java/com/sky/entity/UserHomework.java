package com.sky.entity;

import lombok.Data;

//学生提交的作业
@Data
public class UserHomework {
    private Long hsId;
    //学生id
    private Long studentId;
    //作业id
    private Long homeworkId;
    //得分
    private int homeworkScore;
    //文本
    private String homeworkContent;
    //作业url
    private String homeworkUrl;
    //状态
    private int homeworkState;
}
