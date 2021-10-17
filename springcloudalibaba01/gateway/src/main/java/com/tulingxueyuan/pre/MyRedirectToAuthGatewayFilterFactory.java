package com.tulingxueyuan.pre;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @Author
 * @date 2021年10月13日23:33
 */
@Component
public class MyRedirectToAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<MyRedirectToAuthGatewayFilterFactory.Config> {

    public MyRedirectToAuthGatewayFilterFactory() {
        super(MyRedirectToAuthGatewayFilterFactory.Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("value");
    }

    public GatewayFilter apply(MyRedirectToAuthGatewayFilterFactory.Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                String city=exchange.getRequest().getQueryParams().getFirst("city");

                //获取city参数，如果不等于value则失败
                if(StringUtils.isNotBlank(city)){
                    if(config.getValue().equals(city)){
                       return chain.filter(exchange);
                    }else {
                        exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
                      return   exchange.getResponse().setComplete();
                    }
                }
                //正常请求
                return chain.filter(exchange);
            }
        };
    }


    public static class Config {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
