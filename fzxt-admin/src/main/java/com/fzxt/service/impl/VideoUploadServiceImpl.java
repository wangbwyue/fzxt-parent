package com.fzxt.service.impl;

import com.fzxt.mapper.VideoMapper;
import com.fzxt.model.Video;
import com.fzxt.response.CheckChunkResult;
import com.fzxt.response.Result;
import com.fzxt.response.StatusCode;
import com.fzxt.service.VideoUploadService;
import com.fzxt.utils.FFmpegUtils;
import com.fzxt.utils.HlsVideoUtil;
import com.fzxt.utils.Mp4VideoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;


@Service
@Slf4j
public class VideoUploadServiceImpl implements VideoUploadService {

    @Resource
    private VideoMapper videoMapper;

    @Value("${fzxt-service-video.upload-location}")
    private String upload_location;
    @Value("${fzxt-service-video.ffmpeg-path}")
    private String ffmpegPath;

    /**
     * 获取文件目录路径
     */
    public String mediaFileFolderPath(String md5){

        String meidaPath  = upload_location + md5.substring(0,1)+"/"+md5.substring(1,2)+"/"+md5;
        return meidaPath;
    }
    /**
     * 获取文件存储路径
     */
    public String fmediaFilePath(String md5,String fileExt){
        String meidaPath  = upload_location + md5.substring(0,1)+"/"+md5.substring(1,2)+"/"+md5+"/"+md5+"."+fileExt;
        return meidaPath;
    }

    /**
     * 获取分块文件目录路径
     */
    public String mediaChunkFileFolderPath(String md5){

        String chunkPath  = upload_location + md5.substring(0,1)+"/"+md5.substring(1,2)+"/"+md5+"/chunk/";
        return chunkPath;
    }

    /**
     * 获取文件的相对路径
     * @param fileMd5
     * @param fileExt
     * @return
     */
    private String getFileFolderRelativePath(String fileMd5, String fileExt) {
        String fileFolderRelativePath = fileMd5.substring(0,1)+"/"+fileMd5.substring(1,2)+"/"+fileMd5+"/";

        return fileFolderRelativePath;
    }


    /**
     * 块文件存储目录结构 MD5第一个值+MD5第二个值+MD5为名的目录+MD5.后缀名
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt
     * @return
     */
    @Override
    public Result register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {

        //验证块文件目录是否存在
        String fileFolderPath = this.mediaFileFolderPath(fileMd5);
        //验证数据库中文件信息是否存在
        Video video = videoMapper.getById(fileMd5);
        //验证文件是否存在
        String filePath = this.fmediaFilePath(fileMd5, fileExt);
        File file = new File(filePath);

        //获取存在 返回信息到前端
        if(video == null){
            return Result.resultErr(StatusCode.UPLOAD_FILE_REGISTER_EXIST);
        }
        //不存在则创建目录
        File file1 = new File(fileFolderPath);
        boolean flag = true;
        if(!file1.exists()){
            flag = file1.mkdirs();
        }

        if(!flag){
            //上传文件目录创建失败
            return Result.resultErr(StatusCode.UPLOAD_FILE_REGISTER_CREATEFOLDER_FAIL);
        }
        return Result.resultOk(video);
    }

    /**
     * 分块校验
     * @param fileMd5
     * @param chunk 块文件下标
     * @param chunkSize
     * @return
     */
    @Override
    public Result checkchunk(String fileMd5, Integer chunk, Integer chunkSize) {

        String fileFolderPath = this.mediaChunkFileFolderPath(fileMd5);

        File file = new File(fileFolderPath + chunk);

        if(file.exists()){
            return new CheckChunkResult(StatusCode.SUCCESS,true);
        }else{

            return new CheckChunkResult(StatusCode.SUCCESS,false);
        }
    }

    /**
     * 分块上传
     * @param file
     * @param chunk
     * @param fileMd5
     * @return
     */
    @Override
    public Result uploadchunk(MultipartFile file, Integer chunk, String fileMd5) {

        //获取块文件目录路径
        String folderPath = this.mediaChunkFileFolderPath(fileMd5);

        //检查块文件目录是否存在,不存在则创建
        File chunkFile = new File(folderPath);
        boolean exists = chunkFile.exists();
        if(!exists){
            chunkFile.mkdirs();
        }
        //获取输入输出流进行拷贝
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            //capy流进行存储
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(new File(folderPath+chunk));
            IOUtils.copy(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //返回块文件路径
        return Result.resultOk(folderPath);
    }

    /**
     * 文件合并
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt
     * @return
     */
    @Override
    public Result mergechunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {

        //获取块文件路径,如果不存在则创建
        String fileFolderPath = this.mediaChunkFileFolderPath(fileMd5);
        File chunkFolder = new File(fileFolderPath);

        if(!chunkFolder.exists()){
            chunkFolder.mkdir();
        }

        //判断合并文件是否存在 如果存在则删除
        String fmediaFilePath = this.fmediaFilePath(fileMd5, fileExt);
        File mergec = new File(fmediaFilePath);
        if(mergec.exists()){
            mergec.delete();
        }

        boolean newFile = false;
        try {
            //创建合并文件
            newFile = mergec.createNewFile();
        } catch (IOException e) {
            return Result.resultErr(StatusCode.MERGE_FILE_FAIL);
        }

        if(!newFile){
            return Result.resultErr(StatusCode.MERGE_FILE_CREATEFAIL);
        }

        //将块文件合并
        mergec = chunkFile(fileMd5,fmediaFilePath);
        if(mergec == null){
            return Result.resultErr(StatusCode.UPLOAD_FILE_REGISTER_FAIL);
        }

        File file = new File(fmediaFilePath);
        //校验MD5是否正确
        boolean check = checkFileMd5(file,fileMd5);

        if(!check){
            return Result.resultErr(StatusCode.MERGE_FILE_CHECKFAIL);
        }
        //获取视频时长
        String loca = upload_location + getFileFolderRelativePath(fileMd5,fileExt);
        String videoTime = FFmpegUtils.getVideoTime(loca);
        //将文件信息保存到数据库
        Video video = new Video();
        //MD5值做为ID
        video.setId(fileMd5);
        video.setCreateTime(new Date().toString());
        video.setLongTime(videoTime);
        video.setCourseId("");
        //.m3u8存放路径  + "hls" + fileMd5 + ".m3u8"
        String m3u8Path = getFileFolderRelativePath(fileMd5,fileExt);
        video.setUrl(m3u8Path);
        video.setName(fileName);

        videoMapper.insert(video);

        Result result = this.videoProcessing(video.getId());

        return Result.resultOk(result.getData());
    }

    /**
     * 视频处理
     */
    public Result videoProcessing(String videoId){

        //查询数据库视频信息是否存在
        Video video = videoMapper.getById(videoId);
        if(video == null){
            return Result.resultErr(StatusCode.VIDEO_FILE_PROCESSING);
        }

        //将avi文件转为MP4
        String ffmpeg_path = ffmpegPath;
        String video_path = upload_location + video.getUrl() + video.getName();
        String mp4_name = video.getId() + ".mp4";
        String mp4folder_path = upload_location + video.getUrl() ;
        Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpeg_path,video_path,mp4_name,mp4folder_path);
        String result = mp4VideoUtil.generateMp4();

        if(result == null || !result.equals("success")){

            return Result.resultErr(StatusCode.VIDEO_FILE_PROCESSING);
        }

        //将MP4转为m3u8,ts文件
        String mp4_video_path = upload_location + video.getUrl() + mp4_name;
        String m3u8_name = video.getId() + ".m3u8";
        String m3u8folder_path = upload_location + video.getUrl() + "hls/";
        HlsVideoUtil hlsVideoUtil = new HlsVideoUtil(ffmpeg_path,mp4_video_path,m3u8_name,m3u8folder_path);

        String m3u8 = hlsVideoUtil.generateM3u8();
        if(m3u8 == null || !m3u8.equals("success")){

            return Result.resultErr(StatusCode.ERROR);
        }



        //存入m3u8文件路径
        video.setUrl(video.getUrl() + "hls/" + m3u8_name);

        videoMapper.update(video);
        return Result.resultOk(video);
    }

    /**
     * 校验md5
     * @param mediaFile
     * @param fileMd5
     * @return
     */
    private boolean checkFileMd5(File mediaFile, String fileMd5) {

        if(mediaFile == null || StringUtils.isEmpty(fileMd5)){
            return false;
        }

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(mediaFile);
            //获取文件的MD5值
            String md2Hex = DigestUtils.md5Hex(fileInputStream);

            if(fileMd5.equalsIgnoreCase(md2Hex)){
                return true;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 合并块文件
     * @param fileMd5
     * @return
     */
    private File chunkFile(String fileMd5,String mediaFilePath) {

        //找到块文件存储路径
        String fileFolderPath = this.mediaChunkFileFolderPath(fileMd5);

        File file = new File(fileFolderPath);

        File[] files = file.listFiles();

        List<File> chunkFiles = Arrays.asList(files);
        //排序
        chunkFiles.sort(new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if(Integer.parseInt(o1.getName()) > Integer.parseInt(o2.getName())){
                    return 1;
                }
                return -1;
            }
        });

        try {
            //创建写流
            RandomAccessFile write = new RandomAccessFile(new File(mediaFilePath),"rw");
            //创建文件缓冲区
            byte[] bytes = new byte[1024];
            //遍历块文件目录
            for (File chunkFile : chunkFiles) {

                int let = - 1;
                RandomAccessFile read = new RandomAccessFile(chunkFile,"r");
                while((let = read.read(bytes)) != -1){
                    write.write(bytes,0,let);
                }
                read.close();
            }

            write.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
