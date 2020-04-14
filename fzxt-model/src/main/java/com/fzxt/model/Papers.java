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
@ApiModel(value="试卷对象",description="试卷对象")
public class Papers extends BaseModel{

	@ApiModelProperty(hidden=true)
	private String id;//主键id

	@ApiModelProperty(value="试卷名称",name="name")
	private String name;//试卷名称

	@ApiModelProperty(value="对应科目的id",name="courseType")
	private Integer courseType;//科目

	@ApiModelProperty(value="1热门0不热门",name="hot")
	private Integer hot;//是否热门

	@ApiModelProperty(value="是否推荐1推荐0不推荐",name="recommend")
	private Integer recommend;//是否推荐

	@ApiModelProperty(value="创建时间",hidden=true)
	private String createTime;//创建时间

	@ApiModelProperty(value="推荐时间",hidden=true)
	private String commendTime;//推荐时间

	@ApiModelProperty(value="最后修改时间",hidden=true)
	private String updateTime;//最后修改时间

	@ApiModelProperty(value="创建人",hidden=true)
	private String createUser;//创建人

	@ApiModelProperty(value="观看数",hidden=true)
	private Integer views;//观看数


}
