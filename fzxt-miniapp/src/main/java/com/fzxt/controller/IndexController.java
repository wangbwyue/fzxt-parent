package com.fzxt.controller;

import com.fzxt.model.IndexResult;
import com.fzxt.model.Papers;
import com.fzxt.response.QueryResult;
import com.fzxt.service.PapersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="CourseTypeScontroller",tags={"首页接口"})
@RestController
@RequestMapping("/index")
@Slf4j
public class IndexController {

    @Resource
    private PapersService papersService;
    /**
     * 获取首页数据
     * @return
     */
    @ApiOperation(value="获取首页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="hotsize",value="热门数据显示条数",dataType="int", paramType = "query"),
            @ApiImplicitParam(name="recommendsize",value="推荐数据显示条数",dataType="int", paramType = "query")
    })
    @GetMapping("list")
    public IndexResult list(@RequestParam(defaultValue = "3") int hotsize,
                            @RequestParam(defaultValue = "4") int recommendsize){
        List<Papers> listhot = papersService.listhot(hotsize);
        List<Papers> listrecommend = papersService.listrecommend(recommendsize);
        return new IndexResult(listhot,listrecommend);
    }
}
