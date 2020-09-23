package com.zyxm.module.controller;

import com.zyxm.common.enums.ResultEnum;
import com.zyxm.common.result.Result;
import com.zyxm.common.util.FileUtils;
import com.zyxm.module.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @Author Robert
 * @Create 2020/9/15
 * @Desc TODO
 **/
@Api(tags = "01-01 报表管理")
@RestController
@RequestMapping("/report")
public class ReprotController {
    @Autowired
    private ReportService reportService;

    @GetMapping("excel")
    @ApiOperation(value = "导出excel报表")
    public ResponseEntity reportExcel() {
        Result result = reportService.reportExcel();
        if (!result.getCode().equals(ResultEnum.SUCCESS.code)) {
            return ResponseEntity.unprocessableEntity().body(result);
        }
        File file = (File) result.getData();
        return FileUtils.download(file.getName(), file.getPath());
    }

    @GetMapping("excel_annotation")
    @ApiOperation(value = "导出excel报表注解版")
    public ResponseEntity reportExcelAnnotation() {
        Result result = reportService.reportExcelAnnotation();
        if (!result.getCode().equals(ResultEnum.SUCCESS.code)) {
            return ResponseEntity.unprocessableEntity().body(result);
        }
        File file = (File) result.getData();
        return FileUtils.download(file.getName(), file.getPath());
    }

    @GetMapping("pdf")
    @ApiOperation(value = "导出pdf报表")
    public ResponseEntity reportPDF() {
        Result result = reportService.reportPDF();
        if (!result.getCode().equals(ResultEnum.SUCCESS.code)) {
            return ResponseEntity.unprocessableEntity().body(result);
        }
        File file = (File) result.getData();
        return FileUtils.download(file.getName(), file.getPath());
    }

    @GetMapping("word")
    @ApiOperation(value = "导出word报表")
    public ResponseEntity reportWord() {
        Result result = reportService.reportWord();
        if (!result.getCode().equals(ResultEnum.SUCCESS.code)) {
            return ResponseEntity.unprocessableEntity().body(result);
        }
        File file = (File) result.getData();
        return FileUtils.download(file.getName(), file.getPath());
    }

    @PostMapping("save_file")
    @ApiOperation(value = "保存文件")
    public Result saveFile(@ApiParam(required = true) @RequestParam("file") MultipartFile file) {
        return reportService.saveFile(file);
    }
}
