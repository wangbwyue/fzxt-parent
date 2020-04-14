package com.fzxt.mapper;

import java.util.List;
import com.fzxt.model.Video;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoMapper {

	//新增
	public int insert(Video video);

	//修改
	public int update(Video video);

	//批量删除
	public int deletebth(String[] ids);

	//根据Id查询单条记录
	public Video getById(String id);

	//查询列表
	public List<Video> list(Video video);
	
}