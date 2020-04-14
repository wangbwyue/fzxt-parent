package com.fzxt.mapper;

import java.util.List;
import com.fzxt.model.City;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CityMapper {

	//新增
	public int insert(City city);

	//修改
	public int update(City city);

	//批量删除
	public int deletebth(String[] ids);

	//根据Id查询单条记录
	public City getById(String id);

	//查询列表
	public List<City> list(City city);
	
}