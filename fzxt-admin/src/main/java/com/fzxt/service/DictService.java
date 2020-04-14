package com.fzxt.service;

import com.fzxt.model.Dict;

import java.util.List;

public interface DictService {

	//新增
    int insert(Dict dict);

	//修改
    int update(Dict dict);

	//批量删除
    int deletebth(String[] ids);

	//根据Id查询单条记录
    Dict getById(String id);

	//分页查询列表
    List<Dict> list(Dict dict);

    //根据父id查询
    List<Dict> getByPid(String pid);

    //根据父code查询
    default List<Dict> getByCode(String code){return null;};
}