package com.zyxm.common.enums;

/**
 * @Author Robert
 * @Create 2020/9/11
 * @Desc TODO
 **/
public enum ResultEnum {
    SUCCESS(200, "成功"),
    FAILURE(400, "失败"),
    FILE_GENERATE_FAILURE("文件生成失败"),
    REPORT_GENERATE_FAILURE("报表生成失败"),
    FILE_READ_FAILURE("文件读取失败"),
    FILE_COMPRESS_FAILURE("文件压缩失败"),
    FILE_DOWNLOAD_FAILURE("文件下载失败"),
    FILE_UPLOAD_FAILURE("文件上传失败"),
    FILE_NOT_FOUND("文件不存在");
    public Integer code;
    public String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ResultEnum(String msg) {
        this.code = code;
        this.msg = msg;
    }
}
