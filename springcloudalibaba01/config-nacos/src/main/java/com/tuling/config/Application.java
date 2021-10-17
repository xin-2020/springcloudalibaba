package com.tuling.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author
 * @date 2021年10月07日15:06
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
       while (true) {
           String userName = applicationContext.getEnvironment().getProperty("user.name");
           String userAge = applicationContext.getEnvironment().getProperty("user.age");
           System.err.println("user name : " + userName + "; age: " + userAge);
           System.out.println("当前时间：" +new Date());
           TimeUnit.SECONDS.sleep(1);
       }

       //nacos client 每 10ms 去配置中心进行判断，所以不够一秒钟就输出根据MD5进行判断 因为命名空间public的问题，可以注释掉或者改为dev

    }
}
