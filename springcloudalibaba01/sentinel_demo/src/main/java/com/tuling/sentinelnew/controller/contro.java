package com.tuling.sentinelnew.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.tuling.sentinelnew.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @date 2021年10月08日14:08
 */
@Slf4j
@RestController
public class contro {
    private static final String RESOURCE_NAME="hello";
    private static final String USER_RESOURCE_NAME="user";
    private static final String DEGRAD_RESOURCE_NAME="degrad";

    //资源名称一般跟地址名称保持一致
    @RequestMapping(value = "/hello")
    public String hello()   {
        Entry entry=null;
        // 务必保证 finally 会被执行
        try {
            //针对资源进行限制
            entry= SphU.entry(RESOURCE_NAME);
            //被保护的业务逻辑
            String str="hello world ";
            log.info("====="+str+"=====");
            return str;
        } catch (BlockException e) {
            e.printStackTrace();
            //资源访问阻止，被限流或者北降级
            //进行相应的处理操作
            log.info("block!");
            return "被流控了";
        }catch (Exception ex) {
            //若需要配置降级规则，需要通过这种方式李璐业务程序
            Tracer.traceEntry(ex, entry);
        }finally {
            // 务必保证 exit，务必保证每个 entry 与 exit 配对
            if (entry != null) {
                entry.exit();
            }
        }

        return null;
    }


    //流控规则
    @PostConstruct
    private void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        //为那个资源进行流控
        FlowRule rule = new FlowRule();
        rule.setRefResource(RESOURCE_NAME);
        // set limit qps to 20
        rule.setCount(1);//阈值为1,1s之内智能访问一次
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);

        rules.add(rule);


        //为那个资源进行流控
        FlowRule rule2 = new FlowRule();
        rule2.setRefResource(USER_RESOURCE_NAME);

        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // set limit qps to 20
        rule2.setCount(1);//阈值为1,1s之内智能访问一次
        rules.add(rule2);
        //加载进去
        FlowRuleManager.loadRules(rules);
    }
    /***
    *@Author
    *@date
       @SentinelResource 改善接口中资源定义和被流控降级后的处理方法
       配置bean-SentinelResourceAspect
     通过value属性去定义资源
     blockHandler 设置流控降级后的处理方法

       --默认该方法需要声明在接口同一类中
    */
    @RequestMapping("/user")
    @SentinelResource(value = USER_RESOURCE_NAME,blockHandler = "blockHandlerForGetUser")
    public User getUser(String id){
        return new User("==========xixi");
    }

    /****
    *流控处理的方法
    *
    *
    *一定要public
     * 返回值要跟原方法保持一致---->blockHandler设置的方法一致--上面一个方法
     * 参数也要包含原方法参数，顺序要一致，
     * 可以在参数最后添加这个BlockException异常类，可以区分是什么规则的异常
     *
     * 如果不想写在同一个类里面，原方法中的注解去指定在哪里
     * @RequestMapping("user")
     *     @SentinelResource(value = USER_RESOURCE_NAME,blockHandlerClass = User.class,blockHandler = "blockHandlerForGetUser")
     *     public User getUser(String id){
     *         return new User("xixi");
     *     }
     *
     *     且本方法需要声明为static 方法
     *
     *
    */
    public User blockHandlerForGetUser(String id,BlockException ex){
        ex.printStackTrace();
        return new User("+++++++++++++++++++++++++++++++++++++++++++++++++++++++流控!!!!!");

    }


    /*降级规则*/
    @PostConstruct
    private void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(DEGRAD_RESOURCE_NAME);
        // set threshold RT, 10 ms
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        rule.setCount(2);
        rule.setMinRequestAmount(2);

        rule.setStatIntervalMs(60*1000);
        //熔断市场10s 一旦促发熔断，10s之内会调用处理方法
        //10s后后处于半开状态 恢复接口的请求，第一次请求就失败会再次熔断，
        rule.setTimeWindow(10);
        rules.add(rule);

        DegradeRuleManager.loadRules(rules);
    }

    @RequestMapping("/degrade")
    @SentinelResource(value = DEGRAD_RESOURCE_NAME,entryType = EntryType.IN,
    blockHandler = "blockHandlerForFb"
    )
    public User degrade(String id){
        throw  new RuntimeException("异常");
    }
    public User blockHandlerForFb(String id,BlockException ex){
        return new User("熔断降级");
    }



}
