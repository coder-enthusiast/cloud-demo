package com.lsx.user.service;

import com.lsx.user.mapper.UserMapper;
import com.lsx.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryId(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }
}
