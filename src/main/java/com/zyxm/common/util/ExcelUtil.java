package com.zyxm.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author Robert
 * @Create 2020/9/12
 * @Desc TODO excel工具类
 **/
@Slf4j
public class ExcelUtil {
    /**
     * 获取行/创建行
     */
    public static Row foundRow(Sheet sheet, Integer rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (Objects.isNull(row)) {
            return sheet.createRow(rowIndex);
        }
        return row;
    }

    /**
     * 获取列/创建列
     */
    public static Cell foundCell(Row row, Integer cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (Objects.isNull(cell)) {
            return row.createCell(cellIndex);
        }
        return cell;
    }

    /**
     * 获取某个工作表的值
     */
    public static List<Map<String, Object>> getSheetValue(Sheet sheet, String sheetName, Boolean isReTitle) {
        List<Map<String, Object>> mapResult = new ArrayList<>();
        if (sheet.getSheetName().equals(sheetName)) {
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (isReTitle) {
                    if (Objects.isNull(row) || row.getFirstCellNum() == i) {
                        continue;
                    }
                }
                Map<String, Object> map = new HashMap<>();
                short lastCellNum = row.getLastCellNum();
                for (int j = 0; j < lastCellNum; j++) {
                    map.put(getCellValue(sheet, 0, j), getCellValue(sheet, i, j));
                }
                mapResult.add(map);
            }
        }
        return mapResult;
    }

    /**
     * 获取某行所有列的值
     */
    public static List<String> getRowCell(Sheet sheet, Integer rowIndex) {
        Row row = sheet.getRow(rowIndex);
        short lastCellNum = row.getLastCellNum();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < lastCellNum; i++) {
            result.add(getCellValue(sheet, rowIndex, i));
        }
        return result;
    }

    /**
     * 获取某列所有行的值
     */
    public static List<String> getCellRow(Sheet sheet, Integer cellIndex) {
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        List<String> result = new ArrayList<>();
        for (int i = firstRowNum; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (Objects.isNull(row)) {
                continue;
            }
            result.add(getCellValue(sheet, i, cellIndex));
        }
        return result;
    }

    /**
     * 获取指定单元格的值
     */
    public static String getSheetCellValue(Workbook wb, String sheetName, Integer rowIndex, Integer cellIndex) {
        Sheet sheet = wb.getSheet(sheetName);
        if (Objects.isNull(sheet)) {
            log.debug("sheetName is not exists");
        }
        return getSheetCellValue(sheet, rowIndex, cellIndex);
    }

    public static String getSheetCellValue(Sheet sheet, Integer rowIndex, Integer cellIndex) {
        return getCellValue(sheet, rowIndex, cellIndex);
    }

    /**
     * 设置字体样式
     */
    public static Font setFontStyle(Workbook wb, String fontName, short fontSize, short fontColor, Boolean isBold) {
        Font font = wb.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontSize);
        font.setColor(fontColor);
        font.setBold(isBold);
        return font;
    }

    /**
     * 设置单元格样式
     */
    public static CellStyle setCellStyle(Workbook wb, Boolean isVertical, Boolean isAlign, Boolean isBorder) {
        CellStyle cellStyle = wb.createCellStyle();
        if (isVertical) {
            // 上下居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        if (isAlign) {
            // 左右居中
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        }
        if (isBorder) {
            // 边框
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
        }
        cellStyle.setLocked(true);
        cellStyle.setWrapText(false);
        cellStyle.setDataFormat(wb.createDataFormat().getFormat("@"));
        return cellStyle;
    }

    /**
     * 设置默认列宽
     */
    public static void setDefaultcellWidth(Sheet sheet, Integer length) {
        long width = 12;
        if (!Objects.isNull(length)) {
            if (length >= 4) {
                width = Math.round((10 * length) / (length / 2));
            } else {
                width = Math.round((10 * length) / 1.5);
            }
        }
        sheet.setDefaultColumnWidth(Integer.parseInt(String.valueOf(width)));
    }

    public static void setDefaultcellWidth(Sheet sheet) {
        setDefaultcellWidth(sheet, null);
    }

    /**
     * 设置工作表的值
     */
    public static void setSheetValue(Sheet sheet, List<List<Object>> cellValue) {
        for (int i = 0; i < cellValue.size(); i++) {
            Row row = foundRow(sheet, i);
            List<Object> list = cellValue.get(i);
            for (int j = 0; j < list.size(); j++) {
                Cell cell = foundCell(row, j);
                setCellValue(cell, list.get(j));
            }
        }
    }

    public static void setSheetValue(Sheet sheet, List<List<Object>> cellValue, CellStyle cellStyle) {
        for (int i = 0; i < cellValue.size(); i++) {
            Row row = foundRow(sheet, i);
            List<Object> list = cellValue.get(i);
            for (int j = 0; j < list.size(); j++) {
                Cell cell = foundCell(row, j);
                setCellValue(cell, list.get(j));
                cell.setCellStyle(cellStyle);
            }
        }
    }

    /**
     * 设置某行所有列的值
     */
    public static void setRowCell(Sheet sheet, Integer rowIndex, List<Object> cellValue) {
        Row row = foundRow(sheet, rowIndex);
        for (int i = 0; i < cellValue.size(); i++) {
            Cell cell = foundCell(row, i);
            setCellValue(cell, cellValue.get(i));
        }
    }

    public static void setRowCell(Sheet sheet, Integer rowIndex, List<Object> cellValue, CellStyle cellStyle) {
        Row row = foundRow(sheet, rowIndex);
        for (int i = 0; i < cellValue.size(); i++) {
            Cell cell = foundCell(row, i);
            setCellValue(cell, cellValue.get(i));
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * 设置某列所有行的值
     */
    public static void setCellRow(Sheet sheet, Integer cellIndex, List<Object> rowValue) {
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        for (int i = firstRowNum; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            Cell cell = foundCell(row, cellIndex);
            setCellValue(cell, rowValue.get(i));
        }
    }

    /**
     * 设置指定单元格的值
     */
    public static void setSheetCellValue(Sheet sheet, Integer rowIndex, Integer cellIndex, Object cellValue) {
        Row row = foundRow(sheet, rowIndex);
        Cell cell = foundCell(row, cellIndex);
        setCellValue(cell, cellValue);
    }

    /**
     * 设置单元格的值
     */
    public static void setCellValue(Cell cell, Object obj) {
        if (Objects.isNull(cell) || Objects.isNull(obj)) {
            return;
        }
        if (obj instanceof Byte) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(((Byte) obj).doubleValue());
        } else if (obj instanceof Short) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(((Short) obj).doubleValue());
        } else if (obj instanceof Integer) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(((Integer) obj).doubleValue());
        } else if (obj instanceof Long) {
            Long value = (Long) obj;
            try {
                if (value.toString().length() == 13) {
                    Date date = new Date(value);
                    cell.setCellValue(date);
                } else if (value.toString().length() == 10) {
                    Date date = new Date(value * 1000);
                    cell.setCellValue(date);
                }
            } catch (Exception e) {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(value.doubleValue());
            }
        } else if (obj instanceof Double) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue((Double) obj);
        } else if (obj instanceof Float) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(((Float) obj).doubleValue());
        } else if (obj instanceof Boolean) {
            cell.setCellType(CellType.BOOLEAN);
            cell.setCellValue((Boolean) obj);
        } else if (obj instanceof BigDecimal) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(((BigDecimal) obj).doubleValue());
        } else if (obj instanceof Date) {
            cell.setCellValue((Date) obj);
        } else {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(obj.toString());
        }
    }

    /**
     * 获取单元格的值
     */
    public static String getCellValue(Sheet sheet, Integer rowIndex, Integer cellIndex) {
        Row row = foundRow(sheet, rowIndex);
        Cell cell = foundCell(row, cellIndex);
        CellType cellType = cell.getCellTypeEnum();
        String result;
        switch (cellType) {
            // 数字
            case NUMERIC:
                // 是否是日期
                if (DateUtil.isCellDateFormatted(cell)) {
                    result = DateUtils.dateFormatYMDHMS(cell.getDateCellValue());
                } else {
                    result = String.valueOf(cell.getNumericCellValue());
                }
                break;
            // 字符串
            case STRING:
                result = cell.getStringCellValue();
                break;
            // 布尔
            case BOOLEAN:
                result = String.valueOf(cell.getBooleanCellValue());
                break;
            // 公式
            case FORMULA:
                cell.setCellType(CellType.STRING);
                result = cell.getStringCellValue();
                break;
            default:
                result = "";
                break;
        }
        return result;
    }
}
