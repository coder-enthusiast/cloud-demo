package com.lsx.consumer.Controller;

import com.lsx.consumer.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/consumer")
@DefaultProperties(defaultFallback = "DefaultFallback")
public class ConsumerController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired(required = false)
    private RibbonLoadBalancerClient ribbonLoadBalancerClient;

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

    @RequestMapping(value = "/ribbon/{id}",method = RequestMethod.GET)
    public User ribbonqueryByid(@PathVariable("id") Integer id){
        //用ribbon来选择使用哪个服务端
        ServiceInstance instance = ribbonLoadBalancerClient.choose("user-service");
        //拼装访问服务端的URL地址
        String url="http://"+instance.getHost()+":"+instance.getPort()+"/user/"+id;
        User user=restTemplate.getForObject(url,User.class);
        return user;
    }

    @RequestMapping(value = "/LoadBalanced/{id}",method = RequestMethod.GET)
    public User LoadBalancedqueryByid(@PathVariable("id") Integer id){

        //@LoadBalanced对RestTemplate进行注解，添加拦截器，对service-id拦截，返回service对应的地址。
        String url="http://user-service/user/"+id;
        User user=restTemplate.getForObject(url,User.class);
        return user;
    }
    /**
     * @HystrixCommand  对当前方法超时时间进行设置
     * **/
    @RequestMapping(value = "/Hystrix/{id}",method = RequestMethod.GET)
    //@HystrixCommand(fallbackMethod = "HystrixQueryByIdFallback")
  /*  @HystrixCommand(commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })*/
    public String Hystrix_queryById(@PathVariable("id") Integer id){
        //@LoadBalanced对RestTemplate进行注解，添加拦截器，对service-id拦截，返回service对应的地址。
        String url="http://user-service/user/"+id;
        String user=restTemplate.getForObject(url,String.class);
        return user;
    }
    public String HystrixQueryByIdFallback(Integer id){
        return "请求超时，服务器拥挤。";
    }
    /**
     * 超时提示**/
    public String DefaultFallback(){
        return "请求超时，服务器拥挤。";
    }



}
