package com.fzxt.controller;


import com.fzxt.model.User;
import com.fzxt.redis.RedisUtil;
import com.fzxt.response.Result;
import com.fzxt.service.DictService;
import com.fzxt.utils.CacheConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value="门户controller",tags={"门户操作接口"})
@RestController
@RequestMapping("/portal")
@Slf4j
public class PortalController {

    @Resource
    private RedisUtil redisUtil;


    @ApiOperation(value="首页导航接口")
    @GetMapping("banner")
    public Result banner(){
        Object o = redisUtil.get(CacheConstant.SYS_DICT_CACHE + "::sydh");
        return Result.resultOk(o);
    }


    @ApiOperation(value="首页轮播接口")
    @GetMapping("lunbo")
    public Result lunbo(){
        return Result.resultOk(null);
    }

    @ApiOperation(value="推荐课程接口")
    @GetMapping("kecheng")
    public Result kecheng(){
        return Result.resultOk(null);
    }

    @ApiOperation(value="年级数据接口")
    @GetMapping("nianji")
    public Result nianji(){
        return Result.resultOk(null);
    }

    @ApiOperation(value="教材数据接口")
    @GetMapping("jiaocai")
    public Result jiaocai(){
        return Result.resultOk(null);
    }

}
