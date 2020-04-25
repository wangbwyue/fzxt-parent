package com.fzxt.miniapp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @描述
 * @创建人 wangyue
 * @创建时间2020/4/1613:43
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UserInfoParam对象",description="UserInfoParam对象")
public class UserInfoParam {

    @ApiModelProperty(value="获取到的凭证")
    private String token;

    @ApiModelProperty(value="加密数据字符串")
    private String encryptedData;

    @ApiModelProperty(value="初始向量")
    private String iv;
}


