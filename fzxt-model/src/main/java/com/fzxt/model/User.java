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
@ApiModel(value="用户对象",description="用户对象")
public class User extends BaseModel{

	private String id;//主键

	@ApiModelProperty(value="姓名",name="name")
	private String name;//姓名

	@ApiModelProperty(value="昵称",name="nickname")
	private String nickname;//昵称

	@ApiModelProperty(value="城市",name="city")
	private String city;//城市

	@ApiModelProperty(value="用户头像",name="avatarurl")
	private String avatarurl;//用户头像

	@ApiModelProperty(value="用户头像",name="avatarurl")
	private String province;//省

	@ApiModelProperty(value="用户头像",name="avatarurl")
	private String country;//国家

	@ApiModelProperty(value="用户头像",name="avatarurl")
	private String school;//学校

	@ApiModelProperty(value="性别",name="gender")
	private String gender;//性别

	@ApiModelProperty(value="电话",name="phone")
	private String phone;//电话

	@ApiModelProperty(value="密码",name="password")
	private String password;//密码

	@ApiModelProperty(value="微信",name="wxopenId")
	private String wxopenId;//微信

	@ApiModelProperty(value="微信",name="miniopenId")
	private String miniopenId;//微信

	@ApiModelProperty(value="微博",name="wbopenId")
	private String wbopenId;//微博

	@ApiModelProperty(value="QQ",name="qqopenId")
	private String qqopenId;//QQ

	@ApiModelProperty(value="创建时间",name="createTime",hidden = true)
	private String createTime;//创建时间


}
