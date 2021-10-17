package com.tuilngxueyuan.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tuilngxueyuan.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * @Author
 * @date 2021年10月09日13:31
 */
@Service
public class orderServiceImpl implements IOrderService {
    @Override
    @SentinelResource(value = "getUser",blockHandler = "blockHandlergetUser")
    public String getUser() {
        return "查询用户!";
    }

    public String blockHandlergetUser(BlockException e) {
        return "流控户!";
    }
}
