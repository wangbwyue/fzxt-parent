package com.fzxt.service.impl;

import com.fzxt.mapper.UserMapper;
import com.fzxt.model.User;
import com.fzxt.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl  implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> list(User user) {
        return userMapper.list(user);
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public User getById(String id) {
        return userMapper.getById(id);
    }

    @Override
    public int deletebth(String[] ids) {
        return userMapper.deletebth(ids);
    }

}
