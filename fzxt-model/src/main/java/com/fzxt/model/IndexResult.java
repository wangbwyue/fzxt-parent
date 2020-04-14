package com.fzxt.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "首页接口返回说明")
public class IndexResult {

    @ApiModelProperty(value = "热门列表")
    private List<Papers> hotlist;

    @ApiModelProperty(value = "推荐列表")
    private List<Papers> listrecommend;


}
