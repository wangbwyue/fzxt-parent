package com.fzxt.mapper;


import com.fzxt.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 伟伟爱撒娇
 * @data 2020/2/18 - 23:11
 */
@Mapper
public interface UserMapper {

    /**
     * 模拟查询
     * @return
     */
   public List<User> list(User user);

    /**
     * 新增
     * @return
     */
    public int insert(User user);

    /**
     *  修改
     * @return
     */
    public int update(User user);

    /**
     * 根据主键查询
     * @return
     */
    public User getById(String id);

    /**
     * 根据动态字段查询单条
     * @return
     */
    public User getOneByField(@Param("field") String field,@Param("fieldvalue") String fieldvalue);

    /**
     *  批量删除
     * @return
     */
    public int deletebth(String[] ids);

}
