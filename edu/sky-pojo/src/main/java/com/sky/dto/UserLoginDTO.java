package com.sky.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * C端用户登录
 */
@Data
public class UserLoginDTO implements Serializable {

    @ApiModelProperty("账号，即手机号")
    private String phone;

    @ApiModelProperty("密码")
    private String password;

}
