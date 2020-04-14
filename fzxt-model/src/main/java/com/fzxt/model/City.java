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
@ApiModel(value="城市对象",description="城市对象")
public class City extends BaseModel{

	private String id;//主键

	@ApiModelProperty(value="上级id",name="pid")
	private String pid;//上级id

	@ApiModelProperty(value="名称",name="name")
	private String name;//名称
	

}
