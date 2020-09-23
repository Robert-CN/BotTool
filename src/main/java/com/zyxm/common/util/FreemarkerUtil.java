package com.zyxm.common.util;

import com.zyxm.common.constant.ReportConstant;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @Author Robert
 * @Create 2020/9/12
 * @Desc TODO 静态化页面技术
 **/
public class FreemarkerUtil {

    public static void generateHtml(String templateName, Map<String, Object> paramMap, File dirFile) throws Exception {
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
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dirFile), Charset.forName("UTF-8")));
        // 渲染数据
        template.process(paramMap, writer);
        // 释放资源
        writer.close();
    }

    public static String generateHtml(String templateName, Map<String, Object> paramMap) throws Exception {
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
        // 获取输出流，输出模板字符串
        StringWriter writer = new StringWriter();
        // 渲染数据
        template.process(paramMap, writer);
        // 刷新流
        writer.flush();
        String html = writer.toString();
        // 释放资源
        writer.close();
        return html;
    }
}
