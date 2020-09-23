package com.zyxm.common.util;

import com.zyxm.common.constant.ReportConstant;
import com.zyxm.config.annotation.Excel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author Robert
 * @Create 2020/9/12
 * @Desc TODO excel工具类
 **/
@Slf4j
public class ExcelUtils<T> {
    /**
     * 生成excel报表
     */
    public static void excelHandler(String sheetName, List<Object> title, List<List<Object>> paramList, File excelFile, String reportType) throws Exception {
        if (reportType.equals(ReportConstant.XLS)) {
            xlsHandler(sheetName, title, paramList, excelFile);
        } else if (reportType.equals(ReportConstant.XLSX)) {
            xlsxHandler(sheetName, title, paramList, excelFile);
        }
    }

    public static <T> void excelHandler(String sheetName, Class<T> clazz, List<T> paramList, String reportType, File excelFile) throws Exception {
        Workbook workbook = null;
        if (reportType.equals(ReportConstant.XLS)) {
            workbook = new HSSFWorkbook();
        } else if (reportType.equals(ReportConstant.XLSX)) {
            workbook = new XSSFWorkbook();
        }
        export(workbook, sheetName, clazz, paramList);
        workbook.write(new FileOutputStream(excelFile));
        workbook.close();
    }

    private static void xlsHandler(String sheetName, List<Object> title, List<List<Object>> paramList, File excelFile) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        export(sheet, title, paramList);
        workbook.write(excelFile);
        workbook.close();
    }

    private static void xlsxHandler(String sheetName, List<Object> title, List<List<Object>> paramList, File excelFile) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        export(sheet, title, paramList);
        workbook.write(new FileOutputStream(excelFile));
        workbook.close();
    }

    private static void export(Sheet sheet, List<Object> title, List<List<Object>> paramList) {
        ExcelUtil.setRowCell(sheet, 0, title);
        for (int i = 0; i < paramList.size(); i++) {
            ExcelUtil.setRowCell(sheet, (i + 1), paramList.get(i));
        }
    }

    private static <T> void export(Workbook workbook, String sheetName, Class<T> clazz, List<T> paramList) throws Exception {
        // 允许导出的字段
        List<Field> fieldList = new ArrayList<>();
        // 获取此字节码对象允许导出的字段
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Excel.class)) {
                fieldList.add(field);
            }
        }
        Sheet sheet = workbook.createSheet(sheetName);
        int firstRow = 0;
        Row row = sheet.createRow(firstRow);
        // 设置默认列宽
        Optional<Field> first = fieldList.stream()
                .sorted(Comparator.comparingLong((Field f) -> f.getAnnotation(Excel.class).value().length())
                        .reversed()).findFirst();
        ExcelUtil.setDefaultcellWidth(sheet,first.get().getAnnotation(Excel.class).value().length());
        //  设置上下水平居中无边框
        CellStyle cellStyle = ExcelUtil.setCellStyle(workbook, true, true, false);
        // 表头数据
        for (int j = 0; j < fieldList.size(); j++) {
            Excel report = fieldList.get(j).getAnnotation(Excel.class);
            Cell cell = row.createCell(j);
            cell.setCellStyle(cellStyle);
            ExcelUtil.setCellValue(cell, report.value());
        }
        // 列表数据
        for (int i = 0; i < paramList.size(); i++) {
            T obj = paramList.get(i);
            Row cellRow = sheet.createRow(firstRow + i + 1);
            for (int j = 0; j < fieldList.size(); j++) {
                Field field = fieldList.get(j);
                // 设置可访问私有属性
                field.setAccessible(true);
                String val = Objects.isNull(field.get(obj)) ? "" : String.valueOf(field.get(obj));
                Cell cell = cellRow.createCell(j);
                cell.setCellStyle(cellStyle);
                ExcelUtil.setCellValue(cell, val);
            }
        }
    }
}
