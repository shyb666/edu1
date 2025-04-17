package com.sky.vo;

import lombok.Data;

@Data
public class SubmitHomeworkVO {
    private Long homeworkId;
    private Long studentId;
    private String homeworkContent;
    private String homeworkUrl;
}
