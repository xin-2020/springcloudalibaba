package com.tuling.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @date 2021年10月04日13:42
 */
@RequestMapping("/stock")
@RestController
public class sotre {
    @RequestMapping("/reduct")
    public String reduct(){
        System.out.println("扣减成功");
        return "springcloudalibaba";
    }
}
