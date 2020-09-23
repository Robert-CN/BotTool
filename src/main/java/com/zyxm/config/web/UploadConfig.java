package com.zyxm.config.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Robert
 * @Create 2020/9/21
 * @Desc TODO
 **/
@Data
@Component
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {
    private String uploadPathPrefix;
    private String accessPathPrefix;
}
