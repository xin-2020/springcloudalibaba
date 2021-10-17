package com.tuilngxueyuan.feign.impl;

import com.tuilngxueyuan.feign.storeFeignService;
import org.springframework.stereotype.Component;

/**
 * @Author
 * @date 2021年10月09日21:18
 */
@Component
public class storefeignservicefallback implements storeFeignService {
    @Override
    public String reduct2() {
        return "降级了";
    }
}
