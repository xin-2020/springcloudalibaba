package com.tuilngxueyuan;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tuilngxueyuan.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/add")
    public String add(){
        System.out.println("调用成功");

        return "helloworld";
    }
    @RequestMapping("/flow")
 //   @SentinelResource(value = "flow",blockHandler = "flowblockHandler")
    public String flow(){
        return "正常";
    }

    public String flowblockHandler(BlockException e){
        return "流控!!";
    }

    @RequestMapping("/flowthre")
    @SentinelResource(value = "flow",blockHandler = "flowblockHandler")
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

    @Autowired
    IOrderService iOrderService;
    @RequestMapping("/test1")
    public String test1(){
        return  iOrderService.getUser();
    }
    @RequestMapping("/test2")
    public String test2(){
        return  iOrderService.getUser();
    }


    @RequestMapping("/flowthre2")
    public String flowthre2() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("正常访问！");
        return "正常";
    }
}
