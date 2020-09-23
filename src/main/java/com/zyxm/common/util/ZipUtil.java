package com.zyxm.common.util;

import com.zyxm.common.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Author Robert
 * @Create 2020/9/16
 * @Desc TODO zip工具类
 **/
@Slf4j
public class ZipUtil {
    /**
     * 压缩文件
     */
    public static Boolean compressFile(List<File> fileList, File zipFlie, Boolean isReFile) {
        // 创建文件输入流，读取文件
        FileInputStream in = null;
        // 创建zip输出流，输出文件
        ZipArchiveOutputStream out = null;
        try {
            out = new ZipArchiveOutputStream(zipFlie);
            // 将需要扩展的条目，采用zip64扩展
            out.setUseZip64(Zip64Mode.AsNeeded);
            for (File file : fileList) {
                // 将读取的文件保存到ZipArchiveEntry中，再将ZipArchiveEntry放入压缩流进行压缩
                out.putArchiveEntry(new ZipArchiveEntry(file, file.getName()));
                // 创建文件输入流，读取文件
                in = new FileInputStream(file);
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                out.write(bytes);
            }
            // 刷新、关闭、删除
            out.closeArchiveEntry();
            out.flush();
            if (isReFile) {
                FileUtil.delFile(fileList);
            }
        } catch (Exception e) {
            log.debug(ResultEnum.FILE_COMPRESS_FAILURE.msg);
            return false;
        } finally {
            try {
                if (!Objects.isNull(in)) {
                    in.close();
                }
                if (!Objects.isNull(out)) {
                    out.close();
                }
            } catch (Exception e) {
                log.debug(ResultEnum.FILE_COMPRESS_FAILURE.msg);
            }
        }
        return true;
    }

    /**
     * 解压文件
     */
    public static List<File> unZip(String filePath) {
        List<File> fileList = new ArrayList<>();
        assert filePath != null;
        try (
                ArchiveInputStream in = new ArchiveStreamFactory("GBK")
                        .createArchiveInputStream("ZIP", new FileInputStream(filePath))
        ) {
            ArchiveEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                // 跳过无法读取的文件
                if (!in.canReadEntryData(entry)) {
                    continue;
                }
                // 创建数据源文件
                String targetPath = filePath.substring(0, filePath.lastIndexOf("."));
                File file = new File(targetPath, entry.getName());
                if (file.isDirectory()) {
                    // 创建文件夹
                    if (!file.isDirectory() && !file.mkdirs()) {
                        log.debug(ResultEnum.FILE_GENERATE_FAILURE.msg);
                    }
                } else {
                    File parentFile = file.getParentFile();
                    if (!parentFile.isDirectory() && !parentFile.mkdirs()) {
                        log.debug(ResultEnum.FILE_GENERATE_FAILURE.msg);
                    }
                    try (OutputStream out = Files.newOutputStream(file.toPath())) {
                        IOUtils.copy(in, out);
                    }
                    fileList.add(file);
                }
            }
        } catch (Exception e) {
            log.debug("unzip failure");
            return Collections.emptyList();
        }
        return fileList;
    }
}
