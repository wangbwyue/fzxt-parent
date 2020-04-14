package com.fzxt.controller;

import com.fzxt.model.Papers;
import com.fzxt.response.QueryResult;
import com.fzxt.response.Result;
import com.fzxt.service.PapersService;
import com.fzxt.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value="PapersScontroller",tags={"试卷操作接口"})
@RestController
@RequestMapping("/papers")
@Slf4j
public class PapersController {

    @Resource
    private PapersService papersService;

    /**
     * 模糊分页查询
     * @param papers
     * @return
     */
    @ApiOperation(value="获取列表")
    @PostMapping("list")
    public QueryResult<Papers> list(@RequestBody @ApiParam(name="试卷对象",value="传入json格式",required=false) Papers papers){
        log.debug("--进入列表查询list方法--");
        PageHelper.startPage(papers.getRow(),papers.getSize());
        List<Papers> list = papersService.list(papers);
        PageInfo<Papers> pageInfo = new PageInfo<Papers>(list);
        return new QueryResult<Papers>(pageInfo.getList(),pageInfo.getTotal());
    }


    /**
     * 新增/修改
     * @param papers
     * @return
     */
    @ApiOperation(value="新增/修改")
    @PostMapping("save")
    public Result save(@RequestBody  @ApiParam(name="试卷对象",value="传入json格式",required=true) Papers papers){
        String id = papers.getId();
        if(StringUtils.isEmpty(id)){
            papers.setId(IDUtils.getPramaryId());
            papersService.insert(papers);
        }  else{
            papersService.update(papers);
        }
        return Result.resultOk(papers);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value="根据id查询")
    @ApiImplicitParam(name="id",value="试卷id",dataType="String", paramType = "query",required = true)
    @GetMapping("getById/{id}")
    public Papers getById(@PathVariable String id){
        return papersService.getById(id);
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
        int res = papersService.deletebth(ids);
        return Result.resultOk(res);
    }
}
