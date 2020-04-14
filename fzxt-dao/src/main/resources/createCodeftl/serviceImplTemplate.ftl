package ${rootPath}.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import ${rootPath}.model.${objectName};
import ${rootPath}.mapper.${objectName}Mapper;
import ${rootPath}.service.${objectName}Service;

@Service
public class ${objectName}ServiceImpl implements ${objectName}Service {

	@Resource
	private ${objectName}Mapper ${objectNameLower}Mapper;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(${objectName} ${objectNameLower}) {
		return ${objectNameLower}Mapper.insert(${objectNameLower});
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(${objectName} ${objectNameLower}) {
		return ${objectNameLower}Mapper.update(${objectNameLower});
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
<#if keyType == 'Integer'>
	public int deletebth(Integer[] ids) {
<#else>
	public int deletebth(String[] ids) {
</#if>
		return ${objectNameLower}Mapper.deletebth(ids);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
<#if keyType == 'Integer'>
	public ${objectName} getById(Integer id) {
<#else>
	public ${objectName} getById(String id) {
</#if>
		return ${objectNameLower}Mapper.getById(id);
	}
	
	/**
	 * 查询列表
	 */
	@Override
	public List<${objectName}> list(${objectName} ${objectNameLower}) {
		return ${objectNameLower}Mapper.list(${objectNameLower});
	}

}