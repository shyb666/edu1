package com.sky.entity;

import lombok.Data;

/**
 * @author 肖某
 */
@Data
public class HomeWork {
    private Long homeworkId;
    private String homeworkName;
    private String homeworkContent;
    private String homeworkUrl;
    private int state;
  }
