package com.tuilngxueyuan;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tuilngxueyuan.feign.storeFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @date 2021年10月04日13:40
 */
@RestController
@RequestMapping("/order")
public class ordercontroller {

    @Autowired
    storeFeignService storeFeignService;

    @RequestMapping("/add")
    public String add(){
        System.out.println("调用成功");
        String msg=storeFeignService.reduct2();

        return "hello  feign  "+msg;
    }


    @RequestMapping("/get/{id}")
    @SentinelResource(value = "getByid",blockHandler = "hotblockhandler")
    public String getByid(@PathVariable("id")Integer id) throws InterruptedException{
        System.out.println("zhengchang");
        return "zhengchang"+id;

    }

    public String hotblockhandler(@PathVariable("id")Integer id,BlockException e){
        return "yichang"+id;
    }

}
