package com.tulingxueyuan.pre;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.cloud.gateway.handler.predicate.QueryRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.prefs.AbstractPreferences;

/**
 * @Author
 * @date 2021年10月13日21:59
 */
@Component
public class CheakAuthRoutePredicateFactory extends AbstractRoutePredicateFactory<CheakAuthRoutePredicateFactory.Config> {


    public CheakAuthRoutePredicateFactory() {
        super(CheakAuthRoutePredicateFactory.Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("name");//Config类中定义的值
    }

    public Predicate<ServerWebExchange> apply(CheakAuthRoutePredicateFactory.Config config) {
        return new GatewayPredicate() {
            public boolean test(ServerWebExchange exchange) {
                //进行逻辑的判断
                if(config.getName().equals("nanning")){
                    return true;
                }else {
                    return false;
                }
            }
        };
    }

    //用于接收配置文件中断言信息的值
    @Validated
    public static class Config {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
