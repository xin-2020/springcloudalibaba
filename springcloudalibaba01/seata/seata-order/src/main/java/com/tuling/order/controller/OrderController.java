package com.tuling.order.controller;

import com.tuling.order.mapper.OrderService;
import com.tuling.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @date 2021年10月12日21:45
 */
@RequestMapping("/order")
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/add")
    public String add(){
        Order order=new Order();
        order.setProductId(200);
        order.setStatus(0);
        order.setTotalAmount(100);
        orderService.create(order);

        return "下单成功";
    }
}
