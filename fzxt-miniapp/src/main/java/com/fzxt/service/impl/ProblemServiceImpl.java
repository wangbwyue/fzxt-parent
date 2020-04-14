package com.fzxt.service.impl;

import com.fzxt.mapper.ProblemMapper;
import com.fzxt.model.Problem;
import com.fzxt.service.ProblemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

	@Resource
	private ProblemMapper problemMapper;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(Problem problem) {
		return problemMapper.insert(problem);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(Problem problem) {
		return problemMapper.update(problem);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int deletebth(String[] ids) {
		return problemMapper.deletebth(ids);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public Problem getById(String id) {
		return problemMapper.getById(id);
	}
	
	/**
	 * 查询列表
	 */
	@Override
	public List<Problem> list(Problem problem) {
		return problemMapper.list(problem);
	}

	@Override
	public List<Problem> listBypapersId(String papers_id) {
		return problemMapper.listBypapersId(papers_id);
	}

}