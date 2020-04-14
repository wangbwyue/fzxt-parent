package com.fzxt.config;

import com.fzxt.model.Dict;
import com.fzxt.redis.RedisUtil;
import com.fzxt.service.DictService;
import com.fzxt.utils.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class StartupLoading implements CommandLineRunner {

    @Resource
    private DictService dictService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void run(String... args) throws Exception {
        log.debug(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
        List<Dict> plist = dictService.getByPid("0");
        if(plist !=null && plist.size()>0){
            for (int i=0;i<plist.size();i++){
                Dict dict = plist.get(i);
                List<Dict> slist = dictService.getByPid(dict.getId());
                if(slist !=null && slist.size()>0){
                    redisUtil.set(CacheConstant.SYS_DICT_CACHE+"::"+dict.getCode(),slist);
                } else {
                    redisUtil.set(CacheConstant.SYS_DICT_CACHE+"::"+dict.getCode(),dict.getName());
                }
            }
        }
        log.debug(">>>>>>>>>>>>>>>数据加载成功<<<<<<<<<<<<<");
    }
}

