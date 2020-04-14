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
@ApiModel(value="视频对象",description="视频对象")
public class Video extends BaseModel{

	private String id;//主键

	@ApiModelProperty(value="视频名称",name="name")
	private String name;//视频名称

	@ApiModelProperty(value="路径",name="url")
	private String url;//路径

	@ApiModelProperty(value="所属课程",name="courseId")
	private String courseId;//所属课程

	@ApiModelProperty(value="上传时间",name="createTime")
	private String createTime;//上传时间

	@ApiModelProperty(value="时长",name="longTime")
	private String longTime;//时长
	

}
