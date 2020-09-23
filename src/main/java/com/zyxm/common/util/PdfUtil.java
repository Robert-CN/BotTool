package com.zyxm.common.util;

import com.itextpdf.text.pdf.BaseFont;
import com.zyxm.common.constant.ReportConstant;
import org.springframework.util.ResourceUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author Robert
 * @Create 2020/9/11
 * @Desc TODO PDF工具类
 **/
public class PdfUtil {

    /**
     * 生成pdf报表
     */
    public static void pdfHandler(File htmlFile, File pdfFile, boolean isReFile) throws Exception {
        // 获取HTML文件路径
        String htmlPath = htmlFile.toPath().toUri().toURL().toString();
        // 获取输出流，输出文件
        FileOutputStream out = new FileOutputStream(pdfFile);
        // 创建itext输入流
        ITextRenderer renderer = new ITextRenderer();
        // 将HTML文件写入文档
        renderer.setDocument(htmlPath);
        // 解决字体问题
        ITextFontResolver font = renderer.getFontResolver();
        File arialuni = ResourceUtils.getFile(ReportConstant.ARIALUNI_TTF);
        File simsunTtc = ResourceUtils.getFile(ReportConstant.SIMSUN_TTC);
        File simsunTtf = ResourceUtils.getFile(ReportConstant.SIMSUN_TTF);
        font.addFont(arialuni.getCanonicalPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        font.addFont(simsunTtc.getCanonicalPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        font.addFont(simsunTtf.getCanonicalPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 布局
        renderer.layout();
        // 渲染报表
        renderer.createPDF(out);
        // 释放资源
        out.close();
        // 是否删除HTML文件
        if (isReFile) {
            FileUtil.delFile(htmlFile);
        }
    }
}
