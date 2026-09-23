package com.uestc.utils;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.PutObjectRequest;
import com.aliyun.sdk.service.oss2.models.PutObjectResult;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/*
* 阿里云OSS操作工具类
* */
@Slf4j
@Component
public class AliyunOSSOperator {

    // 对象名中的日期目录，形如 2026/09
    private static final DateTimeFormatter DATE_DIR_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM");

    // 后缀与 Content-Type 的对应关系，未命中的后缀一律按二进制流处理
    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private static final Map<String, String> CONTENT_TYPE_MAP = Map.ofEntries(
            Map.entry("png", "image/png"),
            Map.entry("jpg", "image/jpeg"),
            Map.entry("jpeg", "image/jpeg"),
            Map.entry("gif", "image/gif"),
            Map.entry("bmp", "image/bmp"),
            Map.entry("webp", "image/webp"),
            Map.entry("svg", "image/svg+xml"),
            Map.entry("pdf", "application/pdf"),
            Map.entry("txt", "text/plain"),
            Map.entry("mp4", "video/mp4")
    );

    // AccessKey 通过环境变量提供，不写在配置文件里
    @Value("${aliyun.oss.region:cn-chengdu}")
    private String region;
    @Value("${aliyun.oss.bucket-name:java-houfu}")
    private String bucketName;
    /*当配置项过多的时候，可以用 @ConfigurationProperties 注解来批量注入到bean对象中
    @ConfigurationProperties(prefix = "aliyun.oss")*/

    /**
     * 上传文件到OSS
     *
     * @param content          文件内容
     * @param originalFilename 原始文件名，只用来取后缀，不直接作为对象名
     * @return 文件上传后的公网访问地址
     */
    public String upload(byte[] content, String originalFilename) {
        // 对象名按 年/月/UUID.后缀 组织，例如 2026/09/0d1f2c3b4a5e4f6789ab.png
        String objectKey = buildObjectKey(originalFilename);
        String contentType = getContentType(originalFilename);

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        try (OSSClient client = clientBuilder.build()) {

            PutObjectRequest request = PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .contentType(contentType)
                    .body(BinaryData.fromBytes(content))
                    .build();

            PutObjectResult result = client.putObject(request);

            String url = buildUrl(objectKey);
            log.info("文件上传OSS成功，bucket: {}, key: {}, eTag: {}, url: {}",
                    bucketName, objectKey, result.eTag(), url);
            return url;

        } catch (Exception e) {
            ServiceException se = ServiceException.asCause(e);
            if (se != null) {
                log.error("文件上传OSS失败，requestId: {}, errorCode: {}", se.requestId(), se.errorCode());
            }
            log.error("文件上传OSS失败，原始文件名: {}", originalFilename, e);
            // 抛出让全局异常处理器统一返回错误结果，不能返回 null 当作上传成功
            throw new RuntimeException("文件上传失败：" + e.getMessage(), e);
        }
    }



    /**
     * 生成对象名（key）：年/月/UUID + 后缀
     */
    private String buildObjectKey(String originalFilename) {
        return LocalDate.now().format(DATE_DIR_FORMATTER)
                + "/" + UUID.randomUUID()
                + getSuffix(originalFilename);
    }

    /**
     * 取文件后缀，返回形如 ".png" 的字符串；没有后缀或后缀非法时返回空串
     */
    private String getSuffix(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }
        int index = originalFilename.lastIndexOf('.');
        if (index < 0 || index == originalFilename.length() - 1) {
            return "";
        }
        String suffix = originalFilename.substring(index + 1).toLowerCase(Locale.ROOT);
        // 后缀只允许字母数字，避免把路径分隔符等特殊字符拼进对象名
        return suffix.matches("[a-z0-9]{1,10}") ? "." + suffix : "";
    }

    /**
     * 根据后缀推断 Content-Type，未命中时按二进制流处理
     */
    private String getContentType(String originalFilename) {
        String suffix = getSuffix(originalFilename);
        if (suffix.isEmpty()) {
            return DEFAULT_CONTENT_TYPE;
        }
        return CONTENT_TYPE_MAP.getOrDefault(suffix.substring(1), DEFAULT_CONTENT_TYPE);
    }

    /**
     * 拼接公网访问地址
     */
    private String buildUrl(String objectKey) {
        return "https://" + bucketName + ".oss-" + region + ".aliyuncs.com/" + objectKey;
    }

}
