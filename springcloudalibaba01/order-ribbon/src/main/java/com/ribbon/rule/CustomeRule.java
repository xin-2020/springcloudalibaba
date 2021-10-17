package com.ribbon.rule;

import com.alibaba.nacos.client.naming.utils.ThreadLocalRandom;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * @Author
 * @date 2021年10月06日0:46
 */
public class CustomeRule extends AbstractLoadBalancerRule {
    @Override//初始化配置的
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {

        ILoadBalancer loadBalancer = this.getLoadBalancer();
        //获得当前请求的实例
        List<Server> reachableServers = loadBalancer.getReachableServers();
        //使用线程随机数，传一个范围进去-->服务的数量，目前是两个（0/1）
        int random = ThreadLocalRandom.current().nextInt(reachableServers.size());
        //获取随机数下标
        Server server = reachableServers.get(random);
        /*if (server.isAlive()){
            return server;//是否存活，存活才返回
        }*/
        return server;
    }
}
