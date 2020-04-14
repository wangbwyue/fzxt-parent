package ${rootPath}.controller;

import ${rootPath}.model.${objectName};
import ${rootPath}.response.QueryResult;
import ${rootPath}.response.Result;
import ${rootPath}.service.${objectName}Service;
import ${rootPath}.utils.IDUtils;
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

@Api(value="${remark}Scontroller",tags={"${remark}操作接口"})
@RestController
@RequestMapping("/${objectNameLower}")
@Slf4j
public class ${objectName}Controller {

    @Resource
    private ${objectName}Service ${objectNameLower}Service;

    /**
     * 模糊分页查询
     * @param ${objectNameLower}
     * @return
     */
    @ApiOperation(value="获取列表")
    @PostMapping("list")
    public QueryResult<${objectName}> list(@RequestBody  @ApiParam(name="${remark}对象",value="传入json格式",required=false) ${objectName} ${objectNameLower}){
        log.debug("--进入列表查询list方法--");
        PageHelper.startPage(${objectNameLower}.getRow(),${objectNameLower}.getSize());
        List<${objectName}> list = ${objectNameLower}Service.list(${objectNameLower});
        PageInfo<${objectName}> pageInfo = new PageInfo<${objectName}>(list);
        return new QueryResult<${objectName}>(pageInfo.getList(),pageInfo.getTotal());
    }


    /**
     * 新增/修改
     * @param ${objectNameLower}
     * @return
     */
    @ApiOperation(value="新增/修改")
    @PostMapping("save")
    public Result save(@RequestBody @ApiParam(name="${remark}对象",value="传入json格式",required=true) ${objectName} ${objectNameLower}){
    <#if keyType == 'Integer'>
        Integer id = ${objectNameLower}.getId();
        if(id == null || id == 0){
    <#else>
        String id = ${objectNameLower}.getId();
        if(StringUtils.isEmpty(id)){
    </#if>
            ${objectNameLower}Service.insert(${objectNameLower});
        }  else{
            ${objectNameLower}Service.update(${objectNameLower});
        }
        return Result.resultOk(${objectNameLower});
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value="根据id查询")
<#if keyType == 'Integer'>
    @ApiImplicitParam(name="id",value="${remark}id",dataType="Integer", paramType = "query",required = true)
    @GetMapping("getById/{id}")
    public ${objectName} getById(@PathVariable Integer id){
<#else>
    @ApiImplicitParam(name="id",value="${remark}id",dataType="String", paramType = "query",required = true)
    @GetMapping("getById/{id}")
    public ${objectName} getById(@PathVariable String id){
</#if>
        return ${objectNameLower}Service.getById(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value="批量删除")
<#if keyType == 'Integer'>
    @ApiImplicitParam(name="ids",value="试卷id数组",dataType="Integer[]", paramType = "query",required = true)
    @PostMapping("deletebth")
    public Result deletebth(@RequestBody Integer[] ids){
<#else>
    @ApiImplicitParam(name="ids",value="试卷id数组",dataType="String[]", paramType = "query",required = true)
    @PostMapping("deletebth")
    public Result deletebth(@RequestBody String[] ids){
</#if>
        int res = ${objectNameLower}Service.deletebth(ids);
        return Result.resultOk(res);
    }
}
