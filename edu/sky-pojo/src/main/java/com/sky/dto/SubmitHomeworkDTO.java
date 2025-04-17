package com.sky.dto;

import lombok.Data;

@Data
public class SubmitHomeworkDTO {
    private Long homeworkId;
    private Long studentId;
    private String homeworkContent;
    private String homeworkUrl;
}
