package com.wdt.minio.controller;

import com.wdt.common.log.Log;
import com.wdt.common.model.Result;
import com.wdt.minio.entity.ObjectItem;
import com.wdt.minio.utils.MinioManager;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/file")
public class MinioController {

    @Autowired
    private MinioManager minioManager;

    @GetMapping
    public String test() {
        return "Hello";
    }

    @Log("文件上传-上传单个文件")
    @PostMapping("/upload/single")
    public Result<String> upload(@RequestPart("file") MultipartFile multipartFile,
                                 @RequestParam("bucketName") String bucketName) {

        List<String> objectNames = minioManager.upload(multipartFile, bucketName);
        return Optional.ofNullable(objectNames)
                .filter(objects -> !objects.isEmpty())
                .map(objects -> Result.succeed(objects.get(0), 200, "文件上传成功"))
                .orElse(Result.failed(null, 500, "上传失败"));
    }

    @Log("文件上传-上传多个文件")
    @PostMapping("/upload/multiple")
    public Result<List<String>> uploads(@RequestParam("bucketName") String bucketName,
                                        @RequestPart("files") MultipartFile[] multipartFiles) {
        List<String> objectNames = minioManager.upload(multipartFiles, bucketName);
        return Optional.ofNullable(objectNames)
                .filter(objects -> !objects.isEmpty())
                .map(objects -> Result.succeed(objects, 200, "文件上传成功"))
                .orElse(Result.failed(null, 500, "文件上传失败"));
    }

    @Log("文件下载-下载多个文件，返回压缩包")
    @PostMapping("/download/multiple")
    public void downloads(@RequestBody ObjectItem[] objectItems,
                          HttpServletResponse response) {
        minioManager.download(objectItems, response);
    }

    @Log("文件下载-下载单个文件")
    @PostMapping("/download/single")
    public void download(@RequestBody ObjectItem objectItem,
                         HttpServletResponse response) {
        minioManager.download(objectItem, response);
    }

}
