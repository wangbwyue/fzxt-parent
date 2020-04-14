package com.fzxt.controller;

import com.fzxt.model.Problem;
import com.fzxt.response.QueryResult;
import com.fzxt.response.Result;
import com.fzxt.service.ProblemService;
import com.fzxt.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

@Api(value="试题Scontroller",tags={"试题操作接口"})
@RestController
@RequestMapping("/problem")
@Slf4j
public class ProblemController {

    @Resource
    private ProblemService problemService;

    /**
     * 模糊分页查询
     * @param problem
     * @return
     */
    @ApiOperation(value="获取列表")
    @PostMapping("list")
    public QueryResult<Problem> list(@RequestBody  @ApiParam(name="试题对象",value="传入json格式",required=false) Problem problem){
        log.debug("--进入列表查询list方法--");
        PageHelper.startPage(problem.getRow(),problem.getSize());
        List<Problem> list = problemService.list(problem);
        PageInfo<Problem> pageInfo = new PageInfo<Problem>(list);
        return new QueryResult<Problem>(pageInfo.getList(),pageInfo.getTotal());
    }


    /**
     * 新增/修改
     * @param problem
     * @return
     */
    @ApiOperation(value="新增/修改")
    @PostMapping("save")
    public Result save(@RequestBody @ApiParam(name="试题对象",value="传入json格式",required=true) Problem problem){
        String id = problem.getId();
        if(StringUtils.isEmpty(id)){
            problemService.insert(problem);
        }  else{
            problemService.update(problem);
        }
        return Result.resultOk(problem);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value="根据id查询")
    @ApiImplicitParam(name="id",value="试题id",dataType="String", paramType = "query",required = true)
    @GetMapping("getById/{id}")
    public Problem getById(@PathVariable String id){
        return problemService.getById(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value="批量删除")
    @ApiImplicitParam(name="ids",value="试卷id数组",dataType="String[]", paramType = "query",required = true)
    @PostMapping("deletebth")
    public Result deletebth(@RequestBody String[] ids){
        int res = problemService.deletebth(ids);
        return Result.resultOk(res);
    }
}
