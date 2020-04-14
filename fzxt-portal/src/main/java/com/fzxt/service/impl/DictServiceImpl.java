package com.fzxt.service.impl;

import com.fzxt.mapper.DictMapper;
import com.fzxt.model.Dict;
import com.fzxt.service.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictServiceImpl implements DictService {

	@Resource
	private DictMapper dictMapper;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(Dict dict) {
		return dictMapper.insert(dict);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(Dict dict) {
		return dictMapper.update(dict);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int deletebth(String[] ids) {
		return dictMapper.deletebth(ids);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public Dict getById(String id) {
		return dictMapper.getById(id);
	}
	
	/**
	 * 查询列表
	 */
	@Override
	public List<Dict> list(Dict dict) {
		return dictMapper.list(dict);
	}

	@Override
	public List<Dict> getByPid(String pid) {
		return dictMapper.getByPid(pid);
	}

}