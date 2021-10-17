package com.tuling.store.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @date 2021年10月04日13:42
 */
@RequestMapping("/product")
@RestController
public class Productcontroller {

    @Value("${server.port}")
    String port;

    @RequestMapping("/{id}")
    public String get(@PathVariable Integer id) throws InterruptedException {
       // Thread.sleep(4000);
        System.out.println("查询商品 : "+id);
        return "查询商品 "+id+":"+port;
    }
}
