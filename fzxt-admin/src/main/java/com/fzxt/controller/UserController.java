package com.fzxt.controller;

import com.fzxt.log.Log;
import com.fzxt.model.User;
import com.fzxt.response.QueryResult;
import com.fzxt.response.Result;
import com.fzxt.service.UserService;
import com.fzxt.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value="用户controller",tags={"用户操作接口"})
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 模糊分页查询
     * @param user
     * @return
     */
    @ApiOperation(value="获取用户列表")
    @PostMapping("list")
    @Log
    public QueryResult<User> list(@RequestBody(required = false) User user){
        log.debug("--进入用户列表--");
        PageHelper.startPage(user.getRow(),user.getSize());
        List<User> list = userService.list(user);
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return new QueryResult<User>(pageInfo.getList(),pageInfo.getTotal());
    }


    /**
     * 新增/修改
     * @param user
     * @return
     */
    @ApiOperation(value="新增/修改用户")
    @PostMapping("save")
    public Result save(@RequestBody User user){
        String id = user.getId();
        if(StringUtils.isEmpty(id)){
            id = IDUtils.getPramaryId();
            user.setId(id);
            userService.insert(user);
        }  else{
            userService.update(user);
        }
        return Result.resultOk(user);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value="根据id查询用户")
    @GetMapping("getById/{id}")
    public User getById(@PathVariable String id){
        return userService.getById(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value="批量删除用户")
    @PostMapping("deletebth/{ids}")
    public Result deletebth(@PathVariable String ids){
        int res=0;
        if(!StringUtils.isEmpty(ids)){
          String[] id=ids.split(",");
          res = userService.deletebth(id);
        }
        return Result.resultOk(res);
    }
}
