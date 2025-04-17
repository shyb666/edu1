package com.sky.vo;

import lombok.Data;

@Data
public class UserHomeworkVO {

        private int hsId;
    //作业名
    private String homeworkName;
        //学生名
        private String name;
        //作业id
        private Long id;
        //得分
        private int score;
//教师布置作业的文本
        private String homeworkContent;
    //教师布置作业的url
        private String homeworkUrl;
  //学生提交作业的文本
    private String submitContent;
    //学生提交作业的url
    private String submitUrl;
        //状态
        private int state;
}
