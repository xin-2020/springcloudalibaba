package com.tuilngxueyuan.intercptor.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author
 * @date 2021年10月06日20:17
 */
public class CustomfeignInterceptor implements RequestInterceptor {
    Logger logger= LoggerFactory.getLogger(this.getClass());


    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("feign拦截器！");
    }
}
