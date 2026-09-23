package com.uestc;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;

import java.io.File;
import java.util.UUID;

public class OSSDemo {
    public static void main(String[] args) {
        String region = "cn-chengdu";
        String bucketName = "java-houfu";

        // 要上传的本地图片
        String localFilePath = "D:/projects/save/test.png";

        // 上传到 OSS 后的对象名（key），带 images/ 前缀方便归类
        String objectKey = "images/" + UUID.randomUUID() + ".png";

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        try (OSSClient client = clientBuilder.build()) {

            File file = new File(localFilePath);
            if (!file.exists()) {
                System.out.printf("本地文件不存在: %s\n", file.getAbsolutePath());
                return;
            }

            PutObjectRequest request = PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .contentType("image/png")
                    .build();

            // 传本地文件用 putObjectFromFile：SDK 自己读文件、自动带上 contentLength
            PutObjectResult result = client.putObjectFromFile(request, file);

            System.out.printf("上传成功: bucket=%s, key=%s, eTag=%s\n",
                    bucketName, objectKey, result.eTag());

            /*
            * 公网访问地址。仅当对象/bucket 是公共读时才能直接打开，
            * 无法通过url直接查看图片。
            * 1. 直接访问url时，浏览器手里只有一个 URL，它不知道这是什么：Content-Disposition: attachment 这个响应头会导致浏览器下载文件，而不是显示图片。
            * 2. <img src="URL" > 可以正常显示图片。 OSS 加的那两个头（Content-Disposition: attachment + x-oss-force-download: true）在子资源路径上等于装饰品
             */
            System.out.printf("URL: https://%s.oss-%s.aliyuncs.com/%s\n",
                    bucketName, region, objectKey);


        } catch (Exception e) {
            ServiceException se = ServiceException.asCause(e);
            if (se != null) {
                System.out.printf("ServiceException: requestId:%s, errorCode:%s\n", se.requestId(), se.errorCode());
            }
            System.out.printf("error:\n%s", e);
        }
    }
}
