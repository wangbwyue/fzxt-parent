package ${rootPath}.model;

import ${rootPath}.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="${remark}对象",description="${remark}对象")
public class ${objectName} extends BaseModel{

<#list fieldList as var>
	<#if var[1] == 'Integer'>
	private Integer ${var[5]};//${var[2]}
	<#else>
	@ApiModelProperty(value="${var[2]}",name="${var[5]}")
	private String ${var[5]};//${var[2]}
	</#if>

</#list>

}
