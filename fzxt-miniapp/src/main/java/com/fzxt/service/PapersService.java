package com.fzxt.service;

import com.fzxt.model.Papers;

import java.util.List;

public interface PapersService {

	//新增
	public int insert(Papers papers);

	//修改
	public int update(Papers papers);

//批量删除
	public int deletebth(String[] ids);

//根据Id查询单条记录
	public Papers getById(String id);

	//分页查询列表
	public List<Papers> list(Papers papers);

	//分页热门试卷
	public List<Papers> listhot(int size);

	//分页今日推荐列表
	public List<Papers> listrecommend(int size);
	
}