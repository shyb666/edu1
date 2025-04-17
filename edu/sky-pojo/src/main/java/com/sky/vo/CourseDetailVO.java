package com.sky.vo;

import com.sky.entity.HomeWork;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourseDetailVO extends CourseVO  {

    //学生列表
    private List<UserVO> students = new ArrayList<>();
    //作业表
    private List<HomeWork> homeworks = new ArrayList<>();
}
