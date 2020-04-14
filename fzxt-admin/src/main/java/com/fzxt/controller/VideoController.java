package com.fzxt.controller;

import com.fzxt.model.Video;
import com.fzxt.response.QueryResult;
import com.fzxt.response.Result;
import com.fzxt.response.StatusCode;
import com.fzxt.service.VideoService;
import com.fzxt.utils.HlsVideoUtil;
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

@Api(value="VideoScontroller",tags={"Video操作接口"})
@RestController
@RequestMapping("/video")
@Slf4j
public class VideoController {

    @Resource
    private VideoService videoService;

    /**
     * 模糊分页查询
     * @param video
     * @return
     */
    @ApiOperation(value="获取列表")
    @PostMapping("list")
    public QueryResult<Video> list(@RequestBody(required = false) Video video){
        log.debug("--进入列表查询list方法--");
        PageHelper.startPage(video.getRow(),video.getSize());
        List<Video> list = videoService.list(video);
        PageInfo<Video> pageInfo = new PageInfo<Video>(list);
        return new QueryResult<Video>(pageInfo.getList(),pageInfo.getTotal());
    }


    /**
     * 新增/修改
     * @param video
     * @return
     */
    @ApiOperation(value="新增/修改")
    @PostMapping("save")
    public Result save(@RequestBody Video video){
        String id = video.getId();
        if(StringUtils.isEmpty(id)){
            id = IDUtils.getPramaryId();
            video.setId(id);
            videoService.insert(video);
        }  else{
            videoService.update(video);
        }
        return Result.resultOk(video);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value="根据id查询")
    @GetMapping("getById/{id}")
    public Video getById(@PathVariable String id){
        return videoService.getById(id);
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
          res = videoService.deletebth(id);
        }
        return Result.resultOk(res);
    }
    /**
     * 视频处理测试
     * @return
     */
    @ApiOperation(value="视频处理测试")
    @PostMapping("ffmpeg")
    public Result ffmpeg(){
        String mp4_video_path ="/opt/ffmpeg/input.mp4";
        String m3u8_name = "input.m3u8";
        String m3u8folder_path = "/opt/ffmpeg/hls/";
        HlsVideoUtil hlsVideoUtil = new HlsVideoUtil("ffmpeg",mp4_video_path,m3u8_name,m3u8folder_path);
        String m3u8 = hlsVideoUtil.generateM3u8();
        if(m3u8 == null || !m3u8.equals("success")){
            return Result.resultErr(StatusCode.ERROR);
        }
        return Result.resultOk(m3u8);
    }
}
