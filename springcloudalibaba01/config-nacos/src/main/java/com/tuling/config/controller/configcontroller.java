package com.tuling.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @date 2021年10月07日23:02
 */
@RestController
@RequestMapping("/config")
public class configcontroller {
    @Value("${user.name}")
    public String username;
    @RequestMapping("/show")
    public String show(){
        return username;
    }
}
