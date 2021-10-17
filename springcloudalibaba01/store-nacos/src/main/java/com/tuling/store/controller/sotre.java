package com.tuling.store.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @date 2021年10月04日13:42
 */
@RequestMapping("/stock")
@RestController
public class sotre {

    @Value("${server.port}")
    String port;

    @RequestMapping("/reduct")
    public String reduct() throws InterruptedException{
        System.out.println("扣减成功");
        return "扣减库存"+port;
    }
    @RequestMapping("/reduct2")
    public String reduct2() throws InterruptedException{
        int a=1/0;
        System.out.println("扣减成功");
        return "扣减库存"+port;
    }
}
