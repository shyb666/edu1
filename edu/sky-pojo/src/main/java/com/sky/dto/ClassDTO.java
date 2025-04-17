package com.sky.dto;

import com.sky.entity.Class;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ClassDTO {
    private Long courseId;

    private String className;
    private String classContent;
    private String videoUrl;
}
