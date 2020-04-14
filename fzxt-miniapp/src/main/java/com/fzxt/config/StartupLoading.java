package com.fzxt.config;

import com.fzxt.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
@Order(value=1)
public class StartupLoading implements CommandLineRunner {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void run(String... args) throws Exception {
        log.debug(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
        log.debug(">>>>>>>>>>>>>>>数据加载成功<<<<<<<<<<<<<");
    }
}

