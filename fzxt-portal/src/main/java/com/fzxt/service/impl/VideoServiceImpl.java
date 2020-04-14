package com.fzxt.service.impl;

import com.fzxt.mapper.VideoMapper;
import com.fzxt.model.Video;
import com.fzxt.service.VideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

	@Resource
	private VideoMapper videoMapper;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(Video video) {
		return videoMapper.insert(video);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(Video video) {
		return videoMapper.update(video);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int deletebth(String[] ids) {
		return videoMapper.deletebth(ids);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public Video getById(String id) {
		return videoMapper.getById(id);
	}
	
	/**
	 * 查询列表
	 */
	@Override
	public List<Video> list(Video video) {
		return videoMapper.list(video);
	}

}