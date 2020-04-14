package com.fzxt.service.impl;

import com.fzxt.mapper.PapersMapper;
import com.fzxt.model.Papers;
import com.fzxt.service.PapersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PapersServiceImpl implements PapersService {

	@Resource
	private PapersMapper papersMapper;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(Papers papers) {
		return papersMapper.insert(papers);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(Papers papers) {
		return papersMapper.update(papers);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int deletebth(String[] ids) {
		return papersMapper.deletebth(ids);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public Papers getById(String id) {
		return papersMapper.getById(id);
	}
	
	/**
	 * 查询列表
	 */
	@Override
	public List<Papers> list(Papers papers) {
		return papersMapper.list(papers);
	}

	@Override
	public List<Papers> listhot(int size) {
		return papersMapper.listhot(size);
	}

	@Override
	public List<Papers> listrecommend(int size) {
		return papersMapper.listrecommend(size);
	}

}