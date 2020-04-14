package com.fzxt.mapper;

import java.util.List;
import com.fzxt.model.Papers;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PapersMapper {

	//新增
	public int insert(Papers papers);

	//修改
	public int update(Papers papers);

	//批量删除
	public int deletebth(String[] ids);


	//根据Id查询单条记录
	public Papers getById(String id);


	//查询列表
	public List<Papers> list(Papers papers);

	//热门试卷
	public List<Papers> listhot(int size);

	//今日推荐列表
	public List<Papers> listrecommend(int size);
	
}