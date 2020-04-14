package com.fzxt.controller;

import com.fzxt.response.Result;
import com.fzxt.service.VideoUploadService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Api(value="videoScontroller",tags={"视频上传接口"})
@RestController
@RequestMapping("/video/upload")
public class VideoUploadController {


    @Autowired
    private VideoUploadService uploadService;

    /**
     * 文件上传前进行文件信息验证
     * @param fileMd5 文件生成的唯一的MD5码
     * @param fileName 文件的名称
     * @param fileSize 文件的大小
     * @param mimetype 文件的类型
     * @param fileExt   文件的扩展名
     * @return
     */
    @PostMapping("/register")
    public Result register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        return uploadService.register(fileMd5,fileName,fileSize,mimetype,fileExt);
    }

    /**
     * 验证分块是否存在
     * @param fileMd5 文件的MD5码
     * @param chunk 块的下标
     * @param chunkSize 块的大小
     * @return
     */
    @PostMapping("/checkchunk")
    public Result checkchunk(String fileMd5, Integer chunk, Integer chunkSize) {
        return uploadService.checkchunk(fileMd5,chunk,chunkSize);
    }

    /**
     * 进行文件上传
     * @param file
     * @param chunk
     * @param fileMd5
     * @return
     */
    @PostMapping("/uploadchunk")
    public Result uploadchunk(MultipartFile file, Integer chunk, String fileMd5) {
        return uploadService.uploadchunk(file,chunk,fileMd5);
    }

    /**
     * 合并分块成大文件并删除分块
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt
     * @return
     */
    @PostMapping("/mergechunks")
    public Result mergechunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        return uploadService.mergechunks(fileMd5,fileName,fileSize,mimetype,fileExt);
    }
}
