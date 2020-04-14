package com.fzxt.service.impl;

import com.fzxt.mapper.CourseMapper;
import com.fzxt.model.Course;
import com.fzxt.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

	@Resource
	private CourseMapper courseMapper;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(Course course) {
		return courseMapper.insert(course);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(Course course) {
		return courseMapper.update(course);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int deletebth(String[] ids) {
		return courseMapper.deletebth(ids);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public Course getById(String id) {
		return courseMapper.getById(id);
	}
	
	/**
	 * 查询列表
	 */
	@Override
	public List<Course> list(Course course) {
		return courseMapper.list(course);
	}

}