package com.lsx.consumer.client;

import com.lsx.consumer.pojo.User;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallBack implements UserClient {

    public User queryById(Integer id) {
        User user=new User();
        user.setName("未知用户！！！");
        return user;
    }
}
