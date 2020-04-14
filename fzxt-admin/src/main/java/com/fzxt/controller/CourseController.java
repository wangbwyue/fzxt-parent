package com.fzxt.controller;

import com.fzxt.model.Course;
import com.fzxt.response.QueryResult;
import com.fzxt.response.Result;
import com.fzxt.service.CourseService;
import com.fzxt.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

@Api(value="CourseScontroller",tags={"Course操作接口"})
@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {

    @Resource
    private CourseService courseService;

    /**
     * 模糊分页查询
     * @param course
     * @return
     */
    @ApiOperation(value="获取列表")
    @PostMapping("list")
    public QueryResult<Course> list(@RequestBody(required = false) Course course){
        log.debug("--进入列表查询list方法--");
        PageHelper.startPage(course.getRow(),course.getSize());
        List<Course> list = courseService.list(course);
        PageInfo<Course> pageInfo = new PageInfo<Course>(list);
        return new QueryResult<Course>(pageInfo.getList(),pageInfo.getTotal());
    }


    /**
     * 新增/修改
     * @param course
     * @return
     */
    @ApiOperation(value="新增/修改")
    @PostMapping("save")
    public Result save(@RequestBody Course course){
        String id = course.getId();
        if(StringUtils.isEmpty(id)){
            id = IDUtils.getPramaryId();
            course.setId(id);
            courseService.insert(course);
        }  else{
            courseService.update(course);
        }
        return Result.resultOk(course);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value="根据id查询")
    @GetMapping("getById/{id}")
    public Course getById(@PathVariable String id){
        return courseService.getById(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value="批量删除")
    @PostMapping("deletebth/{ids}")
    public Result deletebth(@PathVariable String ids){
        int res=0;
        if(!StringUtils.isEmpty(ids)){
          String[] id=ids.split(",");
          res = courseService.deletebth(id);
        }
        return Result.resultOk(res);
    }
}
