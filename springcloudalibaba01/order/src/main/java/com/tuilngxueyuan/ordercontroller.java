package com.tuilngxueyuan;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author
 * @date 2021年10月04日13:40
 */
@RestController
@RequestMapping("/order")
public class ordercontroller {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/add")
    public String add(){
        System.out.println("调用成功");
        String msg = restTemplate.getForObject("http://localhost:8011/stock/reduct", String.class);
        return "helloworld"+msg;
    }
}
