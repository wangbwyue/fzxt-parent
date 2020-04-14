package com.fzxt.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="基础分页对象",description="基础分页对象")
public class BaseModel implements Serializable {

    @ApiModelProperty(value="页面大小默认10",name="size")
    public int size=10;//每页大小，默认10

    @ApiModelProperty(value="当前页默认1",name="row")
    public int row=1;//当前页，默认1

}
