package com.sky.dto;

import lombok.Data;

@Data
public class UpdateClassDTO {
    private Long classId;
    private String className;
    private String classContent;
    private String videoUrl;
}
