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
@ApiModel(value="试题对象",description="试题对象")
public class Problem extends BaseModel{

	@ApiModelProperty(value="主键id",name="id")
	private String id;//主键id

	@ApiModelProperty(value="题目名称",name="name")
	private String name;//题目名称

	@ApiModelProperty(value="知识点标签",name="knowledge")
	private String knowledge;//知识点标签

	@ApiModelProperty(value="正确答案",name="rightanswer")
	private String rightanswer;//正确答案

	@ApiModelProperty(value="答案解析",name="analysis")
	private String analysis;//答案解析

	@ApiModelProperty(value="知识讲义",name="handouts")
	private String handouts;//知识讲义

	@ApiModelProperty(value="创建人",name="createUser",hidden = true)
	private String createUser;//创建人

	@ApiModelProperty(value="创建时间",name="createTime",hidden = true)
	private String createTime;//创建时间

	@ApiModelProperty(value="最后修改人",name="updateUer",hidden = true)
	private String updateUer;//最后修改人

	@ApiModelProperty(value="最后修改时间",name="updateTime",hidden = true)
	private String updateTime;//最后修改时间

	@ApiModelProperty(value="练习人数",name="views",hidden = true)
	private Integer views;//练习人数


}
