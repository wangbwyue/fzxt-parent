package com.fzxt.service.impl;

import com.fzxt.mapper.CourseTypeMapper;
import com.fzxt.model.CourseType;
import com.fzxt.service.CourseTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseTypeServiceImpl implements CourseTypeService {

	@Resource
	private CourseTypeMapper courseTypeMapper;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(CourseType courseType) {
		return courseTypeMapper.insert(courseType);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(CourseType courseType) {
		return courseTypeMapper.update(courseType);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int deletebth(Integer[] ids) {
		return courseTypeMapper.deletebth(ids);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public CourseType getById(Integer id) {
		return courseTypeMapper.getById(id);
	}
	
	/**
	 * 查询列表
	 */
	@Override
	public List<CourseType> list(CourseType courseType) {
		return courseTypeMapper.list(courseType);
	}

	@Override
	public List<CourseType> getByPid(Integer fid) {
		return courseTypeMapper.getByPid(fid);
	}

}