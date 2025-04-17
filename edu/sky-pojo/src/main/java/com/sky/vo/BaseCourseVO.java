package com.sky.vo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BaseCourseVO {
    //课程id
    private Long courseId;
    //课程名
    private String courseName;
    //教师id
    private Long teacherId;
    //类型
    private String Type;
    // 创建时间
    private LocalDateTime createTime;
    //封面
    private String coverUrl;

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
