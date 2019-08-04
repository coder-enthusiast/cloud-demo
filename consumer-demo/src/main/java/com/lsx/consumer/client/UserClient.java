package com.lsx.consumer.client;

import com.lsx.Config.FeignConfig;
import com.lsx.consumer.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service",fallback = UserClientFallBack.class,configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("user/{id}")
    User queryById(@PathVariable("id")Integer id);
}
