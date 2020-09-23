package com.zyxm.common.util;

import com.zyxm.common.enums.ResultEnum;
import com.zyxm.common.result.Results;
import com.zyxm.config.web.UploadConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;

/**
 * @Author Robert
 * @Create 2020/9/21
 * @Desc TODO 文件工具类
 **/
@Slf4j
@Component
public class FileUtils {
    private static UploadConfig config;

    @Autowired
    public void setConfig(UploadConfig config) {
        this.config = config;
    }

    public static String save(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        // 绝对路径
        String aboultePath = config.getUploadPathPrefix() + config.getAccessPathPrefix();
        String dataToString = DateUtils.dataToString();
        String fileKey = fileName + DateUtils.getTimestamp() + suffix;
        File aboulteFilePath = new File(aboultePath + File.separator + dataToString);
        // 相对路径
        String filePath = FileUtil.decollatorFormat(config.getAccessPathPrefix() + File.separator + dataToString + File.separator + fileKey);
        try {
            // 保存文件
            if (!aboulteFilePath.exists()) {
                aboulteFilePath.mkdirs();
            }
            file.transferTo(new File(aboulteFilePath, fileKey));
        } catch (Exception e) {
            log.debug(ResultEnum.FILE_GENERATE_FAILURE.msg);
        }
        return filePath;
    }

    public static ResponseEntity download(String fileName, String filePath) {
        HttpHeaders headers = new HttpHeaders();
        byte[] bytes = null;
        try {
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
            FileInputStream in = new FileInputStream(filePath);
            bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(Results.failure(ResultEnum.FILE_DOWNLOAD_FAILURE.msg));
        }
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    public static File copy(File sourceFile) {
        String fileName = sourceFile.getName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = fileName.contains("-") ? fileName.substring(0, fileName.lastIndexOf("-")) + suffix : fileName;
        String pathPrefix = config.getUploadPathPrefix();
        File absoultePath = new File(pathPrefix);
        if (!absoultePath.isDirectory() && !absoultePath.mkdirs()) {
            log.debug(ResultEnum.FILE_GENERATE_FAILURE.msg);
        }
        File targetFile = new File(absoultePath, fileName);
        try (
                InputStream in = Files.newInputStream(sourceFile.toPath());
                OutputStream out = Files.newOutputStream(targetFile.toPath())
        ) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            log.debug("file copy failure");
        }
        FileUtil.delFile(sourceFile);
        return targetFile;
    }
}
