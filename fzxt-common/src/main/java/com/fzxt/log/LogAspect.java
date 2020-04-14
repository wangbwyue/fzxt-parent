package com.fzxt.log;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(Log.class);

    // 定义切点 @Pointcut。是面前注解类的地址。
    @Pointcut("@annotation(com.fzxt.log.Log)")
    public void serviceAspect() {
    }
        /**
         * @Description: 后置通知
         */
        @After("serviceAspect()")
        public void after(JoinPoint joinPoint) {
            logger.info("开始记录日志*************************");
//            要执行的操作
            logger.info("结束记录日志*************************");

        }
    }


