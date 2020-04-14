package com.fzxt.model;

import com.fzxt.model.BaseModel;
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
@ApiModel(value="课程对象",description="课程对象")
public class Course extends BaseModel{

	private String id;//主键

	@ApiModelProperty(value="名称",name="name")
	private String name;//名称

	@ApiModelProperty(value="类型ID",name="typeId")
	private String typeId;//类型

	@ApiModelProperty(value="课程描述",name="description")
	private String description;//课程描述

	@ApiModelProperty(value="所属机构",name="orgnId")
	private String orgnId;//所属机构

	@ApiModelProperty(value="创建时间",name="createTime",hidden=true)
	private String createTime;//创建时间

	@ApiModelProperty(value="最后修改时间",name="updateTime",hidden=true)
	private String updateTime;//最后修改时间
	

}
