package com.fzxt.miniapp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="Code2Session对象",description="Code2Session对象")
public class Code2Session {


    @ApiModelProperty(value="用户唯一标识")
    private String openid;

    @ApiModelProperty(value="会话密钥")
    private String session_key;

    @ApiModelProperty(value="用户在开放平台的唯一标识符")
    private String unionid;

    @ApiModelProperty(value="错误码")
    private int errcode;

    @ApiModelProperty(value="错误信息")
    private String errmsg;
}