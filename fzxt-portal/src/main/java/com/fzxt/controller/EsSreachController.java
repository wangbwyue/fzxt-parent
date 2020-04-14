package com.fzxt.controller;

import com.fzxt.model.Course;
import com.fzxt.response.QueryResult;
import com.fzxt.service.EsSreachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;


@Api(value="SearchScontroller",tags={"es查询操作接口"})
@RestController
@RequestMapping("/portal/search")
public class EsSreachController {

    @Resource
    private EsSreachService esSreachService;

    @ApiOperation(value="es查询课程")
    @PostMapping(value="/list")
    public QueryResult<Course> searchCourseList(@RequestBody(required = false) Course course) throws IOException {
        return esSreachService.searchCourseList(course);
    }
}
