package com.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author
 * @date 2021年10月06日0:06
 *随机的负载均衡策略
 */
@Configuration
public class RibbinRandomRule {

    /*方法名一定要叫iRule*/
    @Bean
    public IRule iRule(){
        return new RandomRule();
    }
}
