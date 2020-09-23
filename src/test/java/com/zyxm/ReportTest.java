package com.zyxm;

import com.alibaba.fastjson.JSONArray;
import com.zyxm.common.constant.ReportConstant;
import com.zyxm.common.util.ExcelResolveUtil;
import com.zyxm.common.util.ReportUtil;
import com.zyxm.common.util.ZipUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.*;

@SpringBootTest
class ReportTest {

    @Test
    public void exportDOC() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", "新亮点·新纪录");
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("高一工序在2020年9月5日中班丁班以投料714.132吨，班产698.085吨刷新Ф6.5规格2020年7月8日丁班班产691.718吨纪录！！");
        }
        map.put("dataList", dataList);
        ReportUtil.word("喜报记录", map, ReportConstant.DOC);
    }

    @Test
    public void exportPDF() {
        Map<String, Object> param = new HashMap<>();
        param.put("number", "0101001");
        param.put("schoolName", "xxx大学");
        param.put("xz", "张三");
        param.put("jlsj", "1997-10-12");
        param.put("description", "精益求精 天天向上 乐于助人");
        param.put("zdmj", "370");
//        JSONObject json = JSONObject.parseObject(JSON.toJSONString(export));
//        File file = ExportUtil.pdf("学校简介", json.getInnerMap());
        ReportUtil.pdf("学校简介", param);
    }

    @Test
    public void exportXLSX() {
        String[] titleArray = {"姓名", "年龄", "性别", "出身日期"};
        List<Object> title = Arrays.asList(titleArray);
        List<List<Object>> param = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Object> list = new ArrayList<>();
            list.add("test" + i);
            list.add(String.valueOf(Integer.parseInt("10") + i));
            if (i % 2 == 0) {
                list.add("女");
            } else {
                list.add("男");
            }

            list.add("2020-9-" + String.valueOf(Integer.parseInt("10") + i));
            param.add(list);
        }
        ReportUtil.excel("poi", title, param, ReportConstant.XLS);
    }

    @Test
    public void importXls() {
        File file = new File("E:\\upload\\excel测试annotation.xlsx");
        Workbook workbook = ExcelResolveUtil.excelResolve(file);
        Sheet sheet = workbook.getSheetAt(0);
        JSONArray read = ExcelResolveUtil.read(sheet);
        System.out.println("read = " + read);
    }

    @Test
    public void compressZIP() {
        File file = new File("E:\\upload\\excel测试annotation.xlsx");
        File file1 = new File("E:\\upload\\excel测试.xls");
        ReportUtil.zip("压缩测试", Arrays.asList(file, file1), true);
    }

    @Test
    public void unzip() {
        File file = new File("C:\\Users\\Robert\\AppData\\Local\\Temp\\压缩测试-5817179574128915697.zip");
        List<File> fileList = ZipUtil.unZip(file.getPath());
        System.out.println("fileList = " + fileList);
    }

}
