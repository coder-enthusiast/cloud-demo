package com.lsx.consumer.Controller;

import com.lsx.consumer.pojo.User;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired(required = false)
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User queryByid(@PathVariable("id") Integer id){

        //根据服务id获取实例user-service
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        //从实例中取出ip和端口
        ServiceInstance serviceInstance = instances.get(0);

        String url="http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/user/"+id;


        //String url="http://localhost:8081/user/"+id;
        User user=restTemplate.getForObject(url,User.class);
        return user;
    }


}
