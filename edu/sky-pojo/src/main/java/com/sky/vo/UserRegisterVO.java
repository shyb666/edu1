package com.sky.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterVO {
    @ApiModelProperty("密码")
    private String username;

    @ApiModelProperty("邮箱")
    private String password;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("账号")
    private String account;
}
