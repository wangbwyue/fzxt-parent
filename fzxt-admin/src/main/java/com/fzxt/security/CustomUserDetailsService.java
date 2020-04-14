package com.fzxt.security;

import com.fzxt.mapper.UserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登陆身份认证
 * Author: JoeTao
 * createAt: 2018/9/14
 */
@Component(value="CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;


    @Override
    public User loadUserByUsername(String name) throws UsernameNotFoundException {
     /*   User user = userMapper.findByUsername(name);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", name));
        }
        Role role = authMapper.findRoleByUserId(user.getId());
        user.setRole(role);*/
        return null;
    }
}