package com.fzxt.response;

public enum StatusCode {

    SUCCESS(20000,"操作成功！"),
    ERROR(20001,"操作失败！"),
    LOGINERROR(20002,"用户名或密码错误！"),
    ACCESSERROR(20003,"权限不足！"),
    REMOTEERROR(20004,"远程调用失败！"),
    REPERROR(20005,"重复操作！"),
    UNAUTHENTICATED(20006,"此操作需要登陆系统！"),
    LOGINNULL(20007,"用户名不能为空！"),
    LOGINREPEAT(20008,"用户名已经存在！"),
    //第三方登录
    ACCESSTOKENERR(21001,"AccessToken获取失败！"),
    OPENTIDERR(21002,"OpenId获取失败！"),
    JWTTOKENEXPIRE(21004,"token过期！"),
    CODEISNULL(21005,"code不能为空"),
    ERRORDECRYPT(21005,"code不能为空"),

    UPLOAD_FILE_REGISTER_FAIL(22001,"上传文件在系统注册失败，请刷新页面重试！"),
    UPLOAD_FILE_REGISTER_EXIST(22002,"上传文件在系统已存在！"),
    CHUNK_FILE_EXIST_CHECK(22003,"分块文件在系统已存在！"),
    MERGE_FILE_FAIL(22004,"合并文件失败，文件在系统已存在！"),
    MERGE_FILE_CREATEFAIL(22005,"创建文件失败"),
    UPLOAD_FILE_REGISTER_CREATEFOLDER_FAIL(22006,"上传文件目录创建失败"),
    MERGE_FILE_CHECKFAIL(22005,"合并文件校验失败！"),
    VIDEO_FILE_PROCESSING(22006,"视频处理失败"),
    SERVER_ERROR(99999,"抱歉，系统繁忙，请稍后重试！");
    //操作代码
    public int code;

    //操作是否成功
    public String errmsg;

    private StatusCode(int code, String errmsg){
        this.code = code;
        this.errmsg = errmsg;
    }
}
