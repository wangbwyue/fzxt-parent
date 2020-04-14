package com.fzxt.service;


import com.fzxt.response.Result;
import org.springframework.web.multipart.MultipartFile;

public interface VideoUploadService {

    /**
     * 文件上传前进行文件信息验证
     * @param fileMd5 文件生成的唯一的MD5码
     * @param fileName 文件的名称
     * @param fileSize 文件的大小
     * @param mimetype 文件的类型
     * @param fileExt   文件的扩展名
     * @return
     */
    public Result register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt);

    /**
     * 验证分块是否存在
     * @param fileMd5 文件的MD5码
     * @param chunk 块的下标
     * @param chunkSize 块的大小
     * @return
     */
    public Result checkchunk(String fileMd5, Integer chunk, Integer chunkSize);

    /**
     * 进行文件上传
     * @param file
     * @param chunk
     * @param fileMd5
     * @return
     */
    public Result uploadchunk(MultipartFile file, Integer chunk, String fileMd5);

    /**
     * 合并分块成大文件并删除分块
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt
     * @return
     */
    public Result mergechunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt);
}
