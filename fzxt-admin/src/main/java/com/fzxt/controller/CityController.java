package com.fzxt.controller;

import com.fzxt.model.City;
import com.fzxt.response.QueryResult;
import com.fzxt.response.Result;
import com.fzxt.service.CityService;
import com.fzxt.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value="CityScontroller",tags={"City操作接口"})
@RestController
@RequestMapping("/city")
public class CityController {

    @Resource
    private CityService cityService;

    /**
     * 模糊分页查询
     * @param city
     * @return
     */
    @ApiOperation(value="获取列表")
    @PostMapping("list")
    public QueryResult<City> list(@RequestBody(required = false) City city){
        PageHelper.startPage(city.getRow(),city.getSize());
        List<City> list = cityService.list(city);
        PageInfo<City> pageInfo = new PageInfo<City>(list);
        return new QueryResult<City>(pageInfo.getList(),pageInfo.getTotal());
    }


    /**
     * 新增/修改
     * @param city
     * @return
     */
    @ApiOperation(value="新增/修改")
    @PostMapping("save")
    public Result save(@RequestBody City city){
        String id = city.getId();
        if(StringUtils.isEmpty(id)){
            id = IDUtils.getPramaryId();
            city.setId(id);
            cityService.insert(city);
        }  else{
            cityService.update(city);
        }
        return Result.resultOk(city);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value="根据id查询")
    @GetMapping("getById/{id}")
    public City getById(@PathVariable String id){
        return cityService.getById(id);
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
          res = cityService.deletebth(id);
        }
        return Result.resultOk(res);
    }
}
