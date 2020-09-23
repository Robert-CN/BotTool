package com.zyxm.common.util;

import com.zyxm.common.constant.ReportConstant;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.util.ResourceUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @Author Robert
 * @Create 2020/9/11
 * @Desc TODO word工具类
 **/
public class WordUtil {
    /**
     * 生成Word报表
     */
    public static File wordHandler(String templateName, Map<String, Object> paramMap, String reportType) throws Exception {
        // 创建配置对象
        Configuration conf = new Configuration(Configuration.VERSION_2_3_30);
        // 设置字符集编码
        conf.setDefaultEncoding("UTF-8");
        // 获取模板文件
        File file = ResourceUtils.getFile(ReportConstant.FTL_LOCATION);
        // 设置模板文件保存位置
        conf.setDirectoryForTemplateLoading(file);
        // 加载模板文件
        Template template = conf.getTemplate(templateName);
        // 获取输出流，输出模板文件
        File wordFile = FileUtil.createFile(templateName, reportType);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wordFile), Charset.forName("UTF-8")));
        // 渲染数据
        template.process(paramMap, writer);
        // 释放资源
        writer.close();
        return wordFile;
    }
}
