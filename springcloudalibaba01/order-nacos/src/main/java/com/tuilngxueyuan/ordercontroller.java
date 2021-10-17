package com.tuilngxueyuan;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

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
        String msg = restTemplate.getForObject("http://store-service/stock/reduct", String.class);
        return "helloworld"+msg;
    }

    @RequestMapping("/header")
    public String header(@RequestHeader("X-Request-color") String color){
        return "helloworld"+color;
    }

/***************************************************************************/


    @RequestMapping("/flow")
    //   @SentinelResource(value = "flow",blockHandler = "flowblockHandler")
    public String flow(){
        return "正常";
    }



    @RequestMapping("/flowthre")
    public String flowthre() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return "正常";
    }

    @RequestMapping("/add2")
    public String add2(){
        System.out.println("下单成功");
        return "生成订单";
    }

    @RequestMapping("/get")
    public String get(){
        return "查询订单";
    }



    @RequestMapping("/flowthre2")
    public String flowthre2() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("正常访问！");
        return "正常";
    }
}
