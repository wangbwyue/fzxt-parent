package com.fzxt.mapper;

import java.util.List;
import com.fzxt.model.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper {

	//新增
	public int insert(Course course);

	//修改
	public int update(Course course);

	//批量删除
	public int deletebth(String[] ids);

	//根据Id查询单条记录
	public Course getById(String id);

	//查询列表
	public List<Course> list(Course course);
	
}