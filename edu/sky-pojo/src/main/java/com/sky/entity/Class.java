package com.sky.entity;

import lombok.Data;

@Data
public class Class {
    private Long courseId;
    private Long classId;
    private String className;
    private String classContent;
    private String videoUrl;
}
