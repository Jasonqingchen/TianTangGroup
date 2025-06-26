package com.ruoyi.archives.util;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.ruoyi.archives.config.MinioConfig.bucketName;

/**
 * Minio 文件存储工具类
 *
 * @author liiuqingchen
 */
public class MinioUtil {

    @Autowired
    public MinioClient minioClient;

    /**
     * 获取kkfile 需要的 url 预览路径
     * @param objectName 文件名字
     * @return
     */
    public  String getPreviewUrl(String objectName) {
        try {
            GetPresignedObjectUrlArgs urlArgs = GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).method(Method.GET).build();
            return minioClient.getPresignedObjectUrl(urlArgs);
        } catch (Exception e) {
            throw new RuntimeException("获取预览链接失败" + e.getMessage());
        }
    }
    /**
     * 上传文件
     *
     * @param bucketName 桶名称
     * @param fileName
     *
     * @throws IOException
     */
    public static String uploadFile(String bucketName,
                                    String fileName,
                                    MultipartFile multipartFile) throws IOException {
        String url;
        MinioClient minioClient = SpringUtils.getBean(MinioClient.class);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, multipartFile.getSize(), -1)
                    .contentType(multipartFile.getContentType())
                    .build());
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .method(Method.GET)
                    .build());
            url = url.substring(0, url.indexOf('?'));
            return ServletUtils.urlDecode(url);
        }
        catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}