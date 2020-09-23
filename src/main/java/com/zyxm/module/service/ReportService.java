package com.zyxm.module.service;

import com.zyxm.common.result.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Robert
 * @Create 2020/9/21
 * @Desc TODO
 **/
public interface ReportService {
    Result reportExcel();

    Result reportExcelAnnotation();

    Result reportPDF();

    Result reportWord();

    Result saveFile(MultipartFile file);
}
