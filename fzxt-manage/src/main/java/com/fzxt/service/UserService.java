package com.fzxt.service;

import com.fzxt.mapper.UserMapper;
import com.fzxt.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    /**
     * 模拟查询
     * @return
     */
    List<User> list(User user);

    /**
     * 新增
     * @return
     */
    int insert(User user);

    /**
     *  修改
     * @return
     */
    int update(User user);

    /**
     * 根据主键查询
     * @return
     */
    User getById(String id);

    /**
     *  批量删除
     * @return
     */
    int deletebth(String[] ids);

    /**
     * 根据动态字段查询单条
     * @return
     */
    default User getOneByField(String field,String fieldvalue){
        return null;
    };
}
