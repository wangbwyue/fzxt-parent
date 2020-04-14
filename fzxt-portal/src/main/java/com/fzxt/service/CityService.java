package com.fzxt.service;

import java.util.List;

import com.fzxt.model.City;

public interface CityService {

	//新增
	public int insert(City city);

	//修改
	public int update(City city);

	//批量删除
	public int deletebth(String[] ids);

	//根据Id查询单条记录
	public City getById(String id);

	//分页查询列表
	public List<City> list(City city);
	
}