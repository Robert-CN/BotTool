package com.zyxm;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @Author Robert
 * @Create 2020/9/12
 * @Desc TODO
 **/
public class ExcelTest {

    public static void main(String[] args) {
        List<Map<String, Object>> mapList = importXLSX();
        mapList.stream().forEach(System.out::println);
    }

    private static List<Map<String, Object>> importXLSX() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        try {
            // 加载文件
            FileInputStream in = new FileInputStream(new File("E:\\upload\\56b6a45384ff49ab82985ee956b79ab4.xlsx"));
            // 创建工作簿
            HSSFWorkbook wb = new HSSFWorkbook(in);
            // 获取工作表总数
            int num = wb.getNumberOfSheets();
            for (int i = 0; i < num; i++) {
                // 获取工作表
                HSSFSheet sheet = wb.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                if (sheetName.equals("Sheet1")) {
                    // 获取最后一行单元格的下标
                    int lastRowNum = sheet.getLastRowNum();
                    for (int j = 0; j <= lastRowNum; j++) {
                        HSSFRow row = sheet.getRow(j);
                        // 忽略标题
                        if (Objects.isNull(row) || row.getFirstCellNum() == j){
                            continue;
                        }
                        // 获取每行的列的个数
                        short lastCellNum = row.getLastCellNum();
                        Map<String, Object> map = new HashMap<>();
                        for (int k = 0; k < lastCellNum; k++) {
                            map.put(sheet.getRow(0).getCell(k).getStringCellValue(), row.getCell(k).getStringCellValue());
                        }
                        mapList.add(map);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapList;
    }

    private static void exportXLSX() {
        // 创建工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建工作表
        HSSFSheet sheet = wb.createSheet("Sheet1");
        //设置列宽
        sheet.setDefaultColumnWidth(12);
        //表头字体
        Font headerFont = wb.createFont();
        headerFont.setFontName("微软雅黑");
        headerFont.setFontHeightInPoints((short) 18);
        headerFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        headerFont.setColor(HSSFColor.BLACK.index);
        //正文字体
        Font contextFont = wb.createFont();
        contextFont.setFontName("微软雅黑");
        contextFont.setFontHeightInPoints((short) 12);
        contextFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        contextFont.setColor(HSSFColor.BLACK.index);
        //表头样式，左右上下居中
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);// 左右居中
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中
        headerStyle.setLocked(true);
        headerStyle.setWrapText(false);// 自动换行
        //单元格样式，左右上下居中 边框
        CellStyle commonStyle = wb.createCellStyle();
        commonStyle.setFont(contextFont);
        commonStyle.setAlignment(CellStyle.ALIGN_CENTER);// 左右居中
        commonStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中
        commonStyle.setLocked(true);
        commonStyle.setWrapText(false);// 自动换行
        commonStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        commonStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        commonStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        commonStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        // 创建行
        HSSFRow row = sheet.createRow(0);
        // 创建列
        HSSFCell cell = null;
        // 添加标题
        for (int i = 0; i < 5; i++) {
            // 设置行高
            row.setHeight((short) 500);
            cell = row.createCell(i);
            cell.setCellValue("标题" + (i + 1));
            cell.setCellStyle(headerStyle);
        }
        // 添加正文
        for (int i = 0; i < 5; i++) {
            HSSFRow row1 = sheet.createRow((i + 1));
            row1.setHeight((short) 500);
            for (int j = 0; j < 5; j++) {
                HSSFCell cell1 = row1.createCell(j);
                cell1.setCellValue("值" + (j + 1));
                cell1.setCellStyle(commonStyle);
            }
        }
        try {
            FileOutputStream stream = new FileOutputStream(new File("E:\\upload\\" + UUID.randomUUID().toString().replace("-", "") + ".xlsx"));
            wb.write(stream);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
