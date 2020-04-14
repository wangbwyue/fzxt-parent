package com.fzxt.service;

import com.fzxt.model.CourseType;

import java.util.List;

public interface CourseTypeService {

	//新增
	public int insert(CourseType courseType);

	//修改
	public int update(CourseType courseType);

//批量删除
	public int deletebth(Integer[] ids);

//根据Id查询单条记录
	public CourseType getById(Integer id);

	//分页查询列表
	public List<CourseType> list(CourseType courseType);

	//根据父id查询
	public List<CourseType> getByPid(Integer fid);
}