package com.fzxt.model;

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
@ApiModel(value="数据字典对象",description="数据字典对象")
public class Dict extends BaseModel{

	private String id;//主键

	@ApiModelProperty(value="姓名",name="name")
	private String name;//姓名

	@ApiModelProperty(value="编码",name="code")
	private String code;//编码

	@ApiModelProperty(value="是否显示父id",name="pid")
	private String pid;//父id

	@ApiModelProperty(value="排序",name="sort")
	private String sort;//排序
	

}
