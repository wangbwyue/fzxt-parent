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
@ApiModel(value="科目分类对象",description="科目分类对象")
public class CourseType extends BaseModel{

	private Integer id;//主键

	@ApiModelProperty(value="分类名称",name="name")
	private String name;//分类名称

	@ApiModelProperty(value="排序",name="sort")
	private Integer sort;//

	@ApiModelProperty(value="父id",name="fid")
	private Integer fid;//父id

	@ApiModelProperty(value="等级",name="level")
	private Integer level;//等级

	@ApiModelProperty(value="是否显示",name="isshow")
	private Integer isshow;//是否显示


}
