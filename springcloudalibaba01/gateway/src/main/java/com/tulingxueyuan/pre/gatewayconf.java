package com.tulingxueyuan.pre;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @Author
 * @date 2021年10月14日20:54
 */
@Configuration
public class gatewayconf {
    @PostConstruct
    public void Unit(){
        BlockRequestHandler blockRequestHandler  =new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                //自定义的匿名类----异常处理
                System.out.println(throwable);
                HashMap<String,String> map=new HashMap<>();
                map.put("code", HttpStatus.TOO_MANY_REQUESTS.toString());
                map.put("msg", "限流了");


                return ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(map))
                        ;
            }

        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }
}
