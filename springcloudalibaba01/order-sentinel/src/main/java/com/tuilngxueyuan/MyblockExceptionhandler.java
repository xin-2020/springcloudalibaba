package com.tuilngxueyuan;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuilngxueyuan.domain.result;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.logging.Logger;


/**
 * @Author
 * @date 2021年10月08日21:07
 */
@Component

public class MyblockExceptionhandler implements BlockExceptionHandler {
 //  Logger log= (Logger) LoggerFactory.getLogger(this.getClass());
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
    //e.getRule() 包含资源，规则的详细信息
       // log.info("BlockException========="+e.getRule());
        result r=null;//该接口有不同的子类，判断就完了
    if(e instanceof FlowException){
        r=result.error(100,"接口限流了");
    }else if(e instanceof DegradeException){
        r=result.error(101,"服务降级了");
    }else if(e instanceof ParamFlowException){
        r=result.error(102,"热点参数被限流了");
    }else if(e instanceof FlowException){
        r=result.error(103,"触发系统保护规则");
    }else if(e instanceof FlowException){
        r=result.error(104,"授权规则不通过");
    }

    //返回json数据
    httpServletResponse.setStatus(500);
    httpServletResponse.setCharacterEncoding("utf-8");
    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(httpServletResponse.getWriter(),r);
    }
}
