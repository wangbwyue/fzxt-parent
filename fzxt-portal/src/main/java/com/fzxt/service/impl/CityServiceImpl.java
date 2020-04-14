package com.fzxt.service.impl;

import com.fzxt.mapper.CityMapper;
import com.fzxt.model.City;
import com.fzxt.service.CityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

	@Resource
	private CityMapper cityMapper;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(City city) {
		return cityMapper.insert(city);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(City city) {
		return cityMapper.update(city);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int deletebth(String[] ids) {
		return cityMapper.deletebth(ids);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public City getById(String id) {
		return cityMapper.getById(id);
	}
	
	/**
	 * 查询列表
	 */
	@Override
	public List<City> list(City city) {
		return cityMapper.list(city);
	}

}