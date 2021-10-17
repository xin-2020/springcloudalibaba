package com.tuling.order.service;

import com.tuling.order.mapper.OrderService;
import com.tuling.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author
 * @date 2021年10月12日22:01
 */
public class OrderServiceImpl implements OrderService {
    /*@Autowired
    OrderMapper orderMapper;
    */
    @Autowired
    RestTemplate restTemplate;

    @Transactional//本地方法事务
    @Override
    public Order create(Order order) {
        /***
        *c插入能否成功
        */
        MultiValueMap<String,Object> paramMap=new LinkedMultiValueMap<>();
        paramMap.add("productId",order.getProductId());
        String msg=restTemplate.postForObject("http://localhost:8071/store/reduct",paramMap,String.class);
        //异常
        int a=1/0;

        return order;
    }
}
