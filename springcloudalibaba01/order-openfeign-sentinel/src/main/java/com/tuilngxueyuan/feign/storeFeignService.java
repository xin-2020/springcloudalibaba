package com.tuilngxueyuan.feign;

import com.tuilngxueyuan.config.feignconfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tuilngxueyuan.feign.impl.storefeignservicefallback;
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

@FeignClient(name = "store-nacos",path = "/stock",fallback = storefeignservicefallback.class)
public interface storeFeignService {
    //声明需要调用的rest接口对应的方法--直接复制他
    @RequestMapping("/reduct")
    public String reduct2();
    //声明feign接口就行啦，不需要写实现类

}
