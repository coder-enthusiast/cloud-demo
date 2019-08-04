package com.lsx.user.service;

import com.lsx.user.mapper.UserMapper;
import com.lsx.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
/***
 * 测试Hystrix,线程睡眠2秒.*/
    public User queryId(Integer id){
      /*  try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return userMapper.selectByPrimaryKey(id);
    }
}
