package com.zyxm.module.service.impl;

import com.zyxm.common.constant.ReportConstant;
import com.zyxm.common.enums.ResultEnum;
import com.zyxm.common.result.Result;
import com.zyxm.common.result.Results;
import com.zyxm.common.util.FileUtils;
import com.zyxm.common.util.ReportUtil;
import com.zyxm.module.entity.pojo.ReportTest;
import com.zyxm.module.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

/**
 * @Author Robert
 * @Create 2020/9/21
 * @Desc TODO
 **/
@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public Result reportExcel() {
        String[] titleArray = {"姓名", "年龄", "性别", "出身日期"};
        List<Object> title = Arrays.asList(titleArray);
        List<List<Object>> param = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Object> list = new ArrayList<>();
            list.add("test" + (i + 1));
            list.add(String.valueOf(Integer.parseInt("10") + (i + 1)));
            if (i % 2 == 0) {
                list.add("女");
            } else {
                list.add("男");
            }

            list.add("2020-9-" + String.valueOf(Integer.parseInt("10") + (i + 1)));
            param.add(list);
        }
        File excelFile = ReportUtil.excel("excel测试", title, param, ReportConstant.XLS);
        File file = FileUtils.copy(excelFile);
        return Results.success(file);
    }

    @Override
    public Result reportExcelAnnotation() {
        List<ReportTest> param = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ReportTest reportTest = ReportTest.builder()
                    .stuNo(00 + (i + 1))
                    .name("name" + (i + 1))
                    .sex("sex" + (i + 1))
                    .age(10 + (i + 1))
                    .birthday(LocalDate.now())
                    .build();
            param.add(reportTest);
        }
        File excelFile = ReportUtil.excel("excel测试annotation", ReportTest.class, param, ReportConstant.XLSX);
        File file = FileUtils.copy(excelFile);
        return Results.success(file);
    }

    @Override
    public Result reportPDF() {
        Map<String, Object> param = new HashMap<>();
        param.put("number", "0101001");
        param.put("schoolName", "xxx大学");
        param.put("xz", "张三");
        param.put("jlsj", "1997-10-12");
        param.put("description", "精益求精 天天向上 乐于助人");
        param.put("zdmj", "370");
        File pdfFile = ReportUtil.pdf("学校简介", param);
        File file = FileUtils.copy(pdfFile);
        return Results.success(file);
    }

    @Override
    public Result reportWord() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", "新亮点·新纪录");
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("高一工序在2020年9月5日中班丁班以投料714.132吨，班产698.085吨刷新Ф6.5规格2020年7月8日丁班班产691.718吨纪录！！");
        }
        map.put("dataList", dataList);
        File docxFile = ReportUtil.word("喜报记录", map, ReportConstant.DOCX);
        File file = FileUtils.copy(docxFile);
        return Results.success(file);
    }

    @Override
    public Result saveFile(MultipartFile file) {
        String path = FileUtils.save(file);
        if (StringUtils.isEmpty(path)) {
            return Results.failure(ResultEnum.FILE_UPLOAD_FAILURE.msg);
        }
        return Results.success(path);
    }
}
