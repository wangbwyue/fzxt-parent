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
@ApiModel(value="AccessToken对象",description="AccessToken对象")
public class AccessToken {

    @ApiModelProperty(value="获取到的凭证")
    private String access_token;

    @ApiModelProperty(value="凭证有效时间，单位：秒。目前是7200秒之内的值。")
    private int expires_in;

    @ApiModelProperty(value="错误码")
    private int errcode;

    @ApiModelProperty(value="错误信息")
    private String errmsg;

}
