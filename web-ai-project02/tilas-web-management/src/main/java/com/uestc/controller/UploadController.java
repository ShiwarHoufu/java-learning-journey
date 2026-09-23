package com.uestc.controller;

import com.uestc.pojo.Result;
import com.uestc.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/*
* 文件上传
* */
@Slf4j
@RestController
public class UploadController {

    // 文件本地存储目录
    private static final String SAVE_DIR = "D:/projects/save";

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;


    /*
    * 本地存储文件
    * */
    /*@PostMapping("/upload")
    public Result upload(String name, Integer age, @RequestParam("file") MultipartFile file) throws IOException {
        log.info("上传文件，name: {}, age: {}, 原始文件名: {}", name, age, file.getOriginalFilename());

        // 文件名用 UUID 生成，拼接原始文件名的后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = "";
        int index = originalFilename == null ? -1 : originalFilename.lastIndexOf(".");
        if (index >= 0) {
            suffix = originalFilename.substring(index);
        }
        String newFileName = UUID.randomUUID().toString() + suffix;

        // 目录不存在时先创建
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        file.transferTo(new File(dir, newFileName));
        log.info("文件保存成功，路径: {}", new File(dir, newFileName).getAbsolutePath());
        return Result.success(newFileName);
    }*/


    /*
    * 上传文件到OSS
    * */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        return Result.success(url);
    }
}
