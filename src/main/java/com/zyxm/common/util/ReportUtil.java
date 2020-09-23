package com.zyxm.common.util;

import com.zyxm.common.constant.ReportConstant;
import com.zyxm.common.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @Author Robert
 * @Create 2020/9/11
 * @Desc TODO 报表工具类
 **/
@Slf4j
public class ReportUtil<T> {
    /**
     * 生成Word报表
     */
    public static File word(String templateName, Map<String, Object> paramMap, String reportType) {
        File wordFile = null;
        try {
            templateName = templateName + ReportConstant.FTL;

            wordFile = WordUtil.wordHandler(templateName, paramMap, reportType);
        } catch (Exception e) {
            log.debug(ResultEnum.REPORT_GENERATE_FAILURE.msg);
        }
        return wordFile;
    }

    /**
     * 生成pdf报表
     */
    public static File pdf(String templateName, Map<String, Object> paramMap) {
        File pdfFile = null;
        try {
            File htmlFile = FileUtil.createFile(templateName, ReportConstant.HTML);

            pdfFile = FileUtil.createFile(templateName, ReportConstant.PDF);

            templateName = templateName + ReportConstant.FTL;

            FreemarkerUtil.generateHtml(templateName, paramMap, htmlFile);

            PdfUtil.pdfHandler(htmlFile, pdfFile, true);
        } catch (Exception e) {
            log.debug(ResultEnum.REPORT_GENERATE_FAILURE.msg);
        }
        return pdfFile;
    }

    /**
     * 生成excel报表
     */
    public static File excel(String fileName, List<Object> title, List<List<Object>> paramList, String reportType) {
        File excelFile = null;
        try {
            excelFile = FileUtil.createFile(fileName, reportType);

            ExcelUtils.excelHandler(fileName, title, paramList, excelFile, reportType);
        } catch (Exception e) {
            log.debug(ResultEnum.REPORT_GENERATE_FAILURE.msg);
        }
        return excelFile;
    }

    public static <T> File excel(String fileName, Class<T> clazz, List<T> paramList, String reportType) {
        File excelFile = null;
        try {
            excelFile = FileUtil.createFile(fileName, reportType);

            ExcelUtils.excelHandler(fileName, clazz, paramList, reportType, excelFile);
        } catch (Exception e) {
            log.debug(ResultEnum.REPORT_GENERATE_FAILURE.msg);
        }
        return excelFile;
    }

    /**
     * 将文件压缩为zip格式
     */
    public static File zip(String fileName, List<File> fileList, Boolean isReFile) {
        File zipFlie = null;
        try {
            zipFlie = FileUtil.createFile(fileName, ReportConstant.ZIP);

            ZipUtil.compressFile(fileList, zipFlie, isReFile);
        } catch (Exception e) {
            log.debug(ResultEnum.FILE_COMPRESS_FAILURE.msg);
        }
        return zipFlie;
    }
}
