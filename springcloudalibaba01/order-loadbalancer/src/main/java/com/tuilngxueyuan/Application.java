package com.tuilngxueyuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author
 * @date 2021年10月04日13:44
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);

    }
    @Bean
    @LoadBalanced//添加负载均衡注解
    public RestTemplate restTemplate(RestTemplateBuilder Builder){
        RestTemplate build = Builder.build();
        return build;

    }
}
