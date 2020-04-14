package com.fzxt.service;

import com.fzxt.model.Problem;

import java.util.List;

public interface ProblemService {

	//新增
	public int insert(Problem problem);

	//修改
	public int update(Problem problem);

//批量删除
	public int deletebth(String[] ids);

//根据Id查询单条记录
	public Problem getById(String id);

	//分页查询列表
	public List<Problem> list(Problem problem);

	//根据试卷id查找问题列表
	public List<Problem> listBypapersId(String papers_id);
}