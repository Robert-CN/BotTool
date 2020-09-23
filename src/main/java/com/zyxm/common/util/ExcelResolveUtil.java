package com.zyxm.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyxm.common.constant.ReportConstant;
import com.zyxm.common.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Robert
 * @Create 2020/9/15
 * @Desc TODO excel解析工具类
 **/
@Slf4j
public class ExcelResolveUtil {

    public static Workbook excelResolve(File dirFile) {
        Workbook workbook = null;
        try {
            // 解析文件类型
            Integer resolve = fileResolve(dirFile);
            if (resolve == 0) {
                log.debug(ResultEnum.FILE_NOT_FOUND.msg);
            } else if (resolve == 1) {
                workbook = readXLS(dirFile);
            } else if (resolve == 2) {
                workbook = readXLSX(dirFile);
            }
        } catch (Exception e) {
            log.debug(ResultEnum.FILE_READ_FAILURE.msg);
        }
        return workbook;
    }

    private static Workbook readXLSX(File dirFile) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(dirFile);
        return workbook;
    }

    private static Workbook readXLS(File dirFile) throws Exception {
        POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(dirFile));
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        return workbook;
    }

    public static JSONArray read(Sheet sheet) {
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        // 判断该工作表是否有数据
        if (firstRowNum == lastRowNum) {
            return new JSONArray();
        }
        // 获取第一行
        Row row = ExcelUtil.foundRow(sheet, firstRowNum);
        short lastCellNum = row.getLastCellNum();
        Map<Integer, String> mapKey = new HashMap<>();
        for (int j = 0; j < lastCellNum; j++) {
            mapKey.put(j, ExcelUtil.getCellValue(sheet, firstRowNum, j));
        }
        // 获取每一行
        JSONArray array = new JSONArray();
        for (int k = firstRowNum + 1; k <= lastRowNum; k++) {
            JSONObject obj = new JSONObject();
            StringBuilder sb = new StringBuilder();
            for (int h = 0; h < lastCellNum; h++) {
                String cellValue = ExcelUtil.getCellValue(sheet, k, h);
                // 用于判断该行是否有数据
                sb.append(cellValue);
                obj.put(mapKey.get(h), cellValue);
            }
            if (sb.toString().length() > 0) {
                array.add(obj);
            }
        }
        return array;
    }

    private static Integer fileResolve(File dirFile) {
        if (Objects.isNull(dirFile) || !dirFile.isFile() || !dirFile.exists()) {
            return 0;
        }
        String name = dirFile.getName();
        String suffix = name.substring(name.lastIndexOf("."));
        if (suffix.equals(ReportConstant.XLS)) {
            return 1;
        } else if (suffix.equals(ReportConstant.XLSX)) {
            return 2;
        }
        return 3;
    }
}
