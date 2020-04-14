package com.fzxt.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@ApiModel(value = "返回说明")
public class Result {
    @ApiModelProperty(value = "成功标识；true：成功；false:失败")
    private boolean flag;//是否成功

    @ApiModelProperty(value = "返回状态码；20000:成功")
    private Integer code;// 返回码

    @ApiModelProperty(value = "描述信息")
    private String message;//描述信息

    @ApiModelProperty(value = "数据")
    private Object data;// 返回数据

    public Result(boolean flag,StatusCode statusCode,Object data){
        this.flag=flag;
        this.code=statusCode.code;
        this.message=statusCode.errmsg;
        this.data=data;
    }
    public Result(String message){
        this.flag=false;
        this.code=500;
        this.message=message;
    }

    //操作正确返回数据
    public static Result  resultOk(Object data) {
        return new Result(true,StatusCode.SUCCESS,data);
    }

    //操作错误返回
    public static Result resultErr(StatusCode statusCode) {
        return new Result(false,statusCode,null);
    }
    //操作错误返回
    public static Result resultErr(String message) {
        return new Result(message);
    }
}
