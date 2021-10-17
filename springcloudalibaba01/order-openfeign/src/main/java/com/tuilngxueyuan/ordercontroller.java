package com.tuilngxueyuan;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.tuilngxueyuan.feign.ProductFeignService;
import com.tuilngxueyuan.feign.storeFeignService;
import org.checkerframework.checker.units.qual.A;
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
    storeFeignService storeFeignService;
    @Autowired
    ProductFeignService productFeignService;
    @RequestMapping("/add")
    public String add(){
        System.out.println("调用成功");
        String msg=storeFeignService.reduct();
        String id=productFeignService.get(1);
        return "hello  feign  "+msg+"id:"+id;
    }
}
