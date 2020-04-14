package com.fzxt.controller;

import com.fzxt.model.Dict;
import com.fzxt.redis.RedisUtil;
import com.fzxt.response.QueryResult;
import com.fzxt.response.Result;
import com.fzxt.service.DictService;
import com.fzxt.utils.CacheConstant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value="DictScontroller",tags={"Dict操作接口"})
@RestController
@RequestMapping("/dict")
@Slf4j
public class DictController {

    @Resource
    private DictService dictService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 初始化数据字典到缓存
     * @return
     */
    @ApiOperation(value="初始化数据字典到缓存")
    @PostMapping("init")
    // @Cacheable(value = CacheConstant.SYS_DICT_CACHE)
    public Result init(){
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
        return Result.resultOk("初始化成功");
    }
    /**
     * 初始化数据字典到缓存
     * @return
     */
    @ApiOperation(value="添加字典缓存")
    @PostMapping("addDictCache/{pid}")
     //@Cacheable(value = CacheConstant.SYS_DICT_CACHE)
    public Result addDictCache(@PathVariable String pid){
        Dict dict = dictService.getById(pid);
        List<Dict> plist = dictService.getByPid(pid);
        redisUtil.set(dict.getCode(),plist);
        return Result.resultOk("初始化成功");
    }

    /**
     * 模糊分页查询
     * @param dict
     * @return
     */
    @ApiOperation(value="获取列表")
    @PostMapping("list")
   // @Cacheable(value = CacheConstant.SYS_DICT_CACHE)
    public QueryResult<Dict> list(@RequestBody(required = false) Dict dict){
        log.debug("--进入列表查询list方法--");
        PageHelper.startPage(dict.getRow(),dict.getSize());
        List<Dict> list = dictService.list(dict);
        PageInfo<Dict> pageInfo = new PageInfo<Dict>(list);
        return new QueryResult<Dict>(pageInfo.getList(),pageInfo.getTotal());
    }


    /**
     * 新增/修改
     * @param dict
     * @return
     */
    @ApiOperation(value="新增/修改")
    @PostMapping("save")
    //@CacheEvict(value= CacheConstant.SYS_DICT_CACHE, allEntries=true)
    public Result save(@RequestBody Dict dict){
        String id = dict.getId();
        if(StringUtils.isEmpty(id)){
            dictService.insert(dict);
        }  else{
            dictService.update(dict);
        }
        return Result.resultOk(dict);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value="根据id查询")
    @GetMapping("getById/{id}")
   // @Cacheable(value = CacheConstant.SYS_DICT_CACHE, key="#id")
    public Dict getById(@PathVariable String id){
        return dictService.getById(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value="批量删除")
    @PostMapping("deletebth/{ids}")
    //@CacheEvict(value= CacheConstant.SYS_DICT_CACHE, allEntries=true)
    public Result deletebth(@PathVariable String ids){
        int res=0;
        if(!StringUtils.isEmpty(ids)){
          String[] id=ids.split(",");
          res = dictService.deletebth(id);
        }
        return Result.resultOk(res);
    }
}
