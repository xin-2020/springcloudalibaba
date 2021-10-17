package com.tuilngxueyuan.feign;

import com.tuilngxueyuan.config.feignconfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @date 2021年10月06日13:49
 */
/**
*@Author
*@dat
*2.添加feign接口和方法
*name 指定要调用rest接口所对应的服务名称
*path 指定调用rest接口所在的storecontroller所指定的RequestMapping
 * 如果没有RequestMapping就不写
*/
//@FeignClient(name = "store-service",path = "/stock",configuration = feignconfig.class)
@FeignClient(name = "store-service",path = "/stock")
public interface storeFeignService {
    //声明需要调用的rest接口对应的方法--直接复制他
    @RequestMapping("/reduct")
    public String reduct();
    //声明feign接口就行啦，不需要写实现类

}
/*
store-nacos.store.java
@RequestMapping("/stock")
@RestController
public class sotre {

    @Value("${server.port}")
    String port;

    @RequestMapping("/reduct")
    public String reduct(){
        System.out.println("扣减成功");
        return "扣减库存"+port;
    }
}*/
