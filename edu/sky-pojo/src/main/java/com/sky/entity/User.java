package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
        private Long id;
        //姓名
        private String name;
        //手机号
        private String phone;
        //密码
        private String password;
        //身份
        private int identity;
        //邮箱
        private String email;
        //课程数量
        private int courseNum;

}
