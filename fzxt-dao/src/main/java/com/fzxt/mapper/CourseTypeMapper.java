package com.fzxt.mapper;

import java.util.List;
import com.fzxt.model.CourseType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseTypeMapper {

	//新增
	public int insert(CourseType courseType);

	//修改
	public int update(CourseType courseType);

	//批量删除
	public int deletebth(Integer[] ids);


	//根据Id查询单条记录
	public CourseType getById(Integer id);


	//查询列表
	public List<CourseType> list(CourseType courseType);

	//根据父id查询
	public List<CourseType> getByPid(Integer fid);
	
}