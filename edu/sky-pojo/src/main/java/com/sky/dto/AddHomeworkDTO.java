package com.sky.dto;

import lombok.Data;

@Data
public class AddHomeworkDTO {
    private Long courseId;
    private String homeworkName;
    private String homeworkContent;
    private String homeworkUrl;
}
