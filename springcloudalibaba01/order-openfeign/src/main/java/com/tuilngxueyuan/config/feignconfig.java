package com.tuilngxueyuan.config;

import feign.Contract;
import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author
 * @date 2021年10月06日14:28
 * 全局配置 当使用configuration会将配置作用在所有的服务提供方
 * 局部配置 如果只想针对某一个或者某几个服务进行配置就不要加这个注解
 */
@Configuration
public class feignconfig {
    //配置日志输出级别
    @Bean
    public Logger.Level feignLonggerLevel(){
       return Logger.Level.FULL;
    }

    /***
    *@Author
    *@date
     * 修改契约配置，支持feign原生的注解
    */
/*    @Bean
    public Contract feignController(){
        return new Contract.Default();
    }*/

    /***
    *@Author
    *@date
     * 超时时间配置1
     *
    */
 /*   @Bean
    public Request.Options options(){
        return new Request.Options(5000,10000);
    }*/
/**
*@Author
*@date
*自定义拦截器
*/
/*     @Bean
    public  FeignAuthRequestInterceptor feignAuthRequestInterception(){
         return  new FeignAuthRequestInterceptor();
     }*/

}
