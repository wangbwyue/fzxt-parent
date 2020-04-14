package ${rootPath}.service;

import java.util.List;

import ${rootPath}.model.${objectName};

public interface ${objectName}Service {

	//新增
	public int insert(${objectName} ${objectNameLower});

	//修改
	public int update(${objectName} ${objectNameLower});

//批量删除
<#if keyType == 'Integer'>
	public int deletebth(Integer[] ids);
<#else>
	public int deletebth(String[] ids);
</#if>

//根据Id查询单条记录
<#if keyType == 'Integer'>
	public ${objectName} getById(Integer id);
<#else>
	public ${objectName} getById(String id);
</#if>

	//分页查询列表
	public List<${objectName}> list(${objectName} ${objectNameLower});
	
}