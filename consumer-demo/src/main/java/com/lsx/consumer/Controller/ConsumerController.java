package com.lsx.consumer.Controller;

import com.lsx.consumer.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User queryByid(@PathVariable("id") Integer id){
        String url="http://localhost:8081/user/"+id;
        User user=restTemplate.getForObject(url,User.class);
        return user;
    }


}
