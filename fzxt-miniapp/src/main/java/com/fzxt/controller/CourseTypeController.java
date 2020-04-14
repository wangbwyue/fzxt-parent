package com.fzxt.controller;

import com.fzxt.model.CourseType;
import com.fzxt.response.QueryResult;
import com.fzxt.response.Result;
import com.fzxt.service.CourseTypeService;
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

@Api(value="科目分类Scontroller",tags={"科目分类操作接口"})
@RestController
@RequestMapping("/courseType")
@Slf4j
public class CourseTypeController {

    @Resource
    private CourseTypeService courseTypeService;

    /**
     * 模糊分页查询
     * @param courseType
     * @return
     */
    @ApiOperation(value="获取列表")
    @PostMapping("list")
    public QueryResult<CourseType> list(@RequestBody  @ApiParam(name="科目分类对象",value="传入json格式",required=false) CourseType courseType){
        log.debug("--进入列表查询list方法--");
        PageHelper.startPage(courseType.getRow(),courseType.getSize());
        List<CourseType> list = courseTypeService.list(courseType);
        PageInfo<CourseType> pageInfo = new PageInfo<CourseType>(list);
        return new QueryResult<CourseType>(pageInfo.getList(),pageInfo.getTotal());
    }


    /**
     * 新增/修改
     * @param courseType
     * @return
     */
    @ApiOperation(value="新增/修改")
    @PostMapping("save")
    public Result save(@RequestBody @ApiParam(name="科目分类对象",value="传入json格式",required=true) CourseType courseType){
        Integer id = courseType.getId();
        if(id == null || id == 0){
            courseTypeService.insert(courseType);
        }  else{
            courseTypeService.update(courseType);
        }
        return Result.resultOk(courseType);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value="根据id查询")
    @GetMapping("getById/{id}")
    public CourseType getById(@ApiParam(name = "appid", value = "要查询的科目分类id", required = true) @PathVariable Integer id){
        return courseTypeService.getById(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value="批量删除")
    @ApiImplicitParam(name="ids",value="科目分类id数组",dataType="Integer[]", paramType = "query",required = true)
    @PostMapping("deletebth")
    public Result deletebth(@RequestBody Integer[] ids){
        int res = courseTypeService.deletebth(ids);
        return Result.resultOk(res);
    }

    /**
     * 根据父id查询
     * @param fid
     * @return
     */
    @ApiOperation(value="根据父id查询")
    @GetMapping("getByPid/{pid}")
    public List<CourseType> getByPid(@ApiParam(name = "pid", value = "科目分类父id", required = true) @PathVariable Integer fid){
        return courseTypeService.getByPid(fid);
    }

}
