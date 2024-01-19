package com.wdt.minio.utils;

import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.exception.BusinessException;
import com.wdt.minio.entity.ObjectItem;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@Slf4j
public class MinioUtil {
    /**
     * minio 操作
     * <p>
     * 基础概念：device  表示存储磁盘路径     set ：device组的意思
     * 对象：api 操作的文件   桶 ： 相当于文件存储父文件夹
     * <p>
     * ec:纠错码 例如8个盘  4个元数据 ，4个编码盘  编码盘为单数
     * <p>
     * java  操作 minio  主要 使用  创建桶 ，查看桶是否存在  ，删除桶 ，获取桶，  上传对象（文件） ，下载对象
     */

    @Resource
    private MinioClient minioClient;

    /**
     * 创建桶
     */
    public boolean makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new BusinessException(CodeEnum.MAKE_BUCKET_ERR);
        }
        return true;
    }


    /**
     * 查看桶是否存在
     */

    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new BusinessException(CodeEnum.BUCKET_EXISTS_ERR);
        }
    }


    /**
     * 删除桶
     */
    public boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new BusinessException(CodeEnum.BUCKET_EXISTS_ERR);
        }
        return true;
    }

    /**
     * 获取所有桶
     */
    public List<Bucket> listBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new BusinessException(CodeEnum.LIST_BUCKET_ERR);
        }
    }

    /**
     * 查看某个桶的所所有文件对象
     */
    public List<ObjectItem> listObjects(String bucketName) {
        List<ObjectItem> objectItems = new ArrayList<>();
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        //  Iterable 表示这个 对象 可以 被迭代  ，里边会有一个返回迭代器的方法, Iterator：迭代器
        Iterator<Result<Item>> iterator = results.iterator();
        while (iterator.hasNext()) {
            Result<Item> result = iterator.next();
            try {
                Item item = result.get();
                objectItems.add(new ObjectItem(item.objectName(), item.size(), item.isDir(),bucketName));
            } catch (Exception e) {
                throw new BusinessException(CodeEnum.GET_OBJECT_ERR);
            }

        }
        return objectItems;
    }

    /**
     * 文件上传 上传多个文件
     * @param multipartFiles
     * @param bucketName
     * @return
     */
    public List<String> upload(MultipartFile[] multipartFiles, String bucketName) {
        List<String> objectNames = new ArrayList<>();
        Arrays.stream(multipartFiles).forEach(multipartFile -> {
            // 生成唯一文件名
            String originalFilename = multipartFile.getOriginalFilename() + "-" + UUID.randomUUID();
            // 文件大小
            long size = multipartFile.getSize();
            // 文件类型
            String contentType = multipartFile.getContentType();
            // try-with-resources 语法糖  要求将需要关闭的资源对象写在 try 块的括号内。这样，在代码块结束时，这些资源对象会自动关闭
            try (InputStream in = multipartFile.getInputStream()) {
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(originalFilename)
                        .stream(in, size, -1)
                        .contentType(contentType)
                        .build());
            } catch (Exception e) {
                throw new BusinessException(CodeEnum.UPLOAD_FIFE_ERR);
            }
            objectNames.add(originalFilename);
        });
        return objectNames;
    }

    /**
     * 上传单个文件
     */
    public List<String> upload(MultipartFile multipartFile, String bucketName) {
        return upload(new MultipartFile[]{multipartFile}, bucketName);
    }

    /**
     * 下载单个文件
     *
     * @param objectItem 要下载的文件对象
     * @param response   HTTP响应对象
     */
    public void download(ObjectItem objectItem, HttpServletResponse response) {
        try {
            // 下载文件
            downloadObject(objectItem, response.getOutputStream());
            // 设置响应头信息
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + objectItem.getObjectName() + "\"");
        } catch (IOException | MinioException e) {
            throw new BusinessException(CodeEnum.UPLOAD_FIFE_ERR);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下载多个文件并打包压缩
     *
     * @param objectItems 要下载的文件对象数组
     * @param response    HTTP响应对象
     */
    public void download(ObjectItem[] objectItems, HttpServletResponse response) {
        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            // 遍历每个文件对象并下载压缩
            for (ObjectItem objectItem : objectItems) {
                // 下载文件并添加到压缩包中
                downloadAndAddToZip(objectItem, zipOut);
            }
            // 完成压缩并设置响应头信息
            zipOut.finish();
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=\"download.zip\"");
        } catch (IOException | MinioException e) {
            throw new BusinessException(CodeEnum.UPLOAD_FIFE_ERR);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下载文件并添加到压缩包中
     *
     * @param objectItem 文件对象
     * @param zipOut     压缩包输出流
     */
    private void downloadAndAddToZip(ObjectItem objectItem, ZipOutputStream zipOut) throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {
        // 下载文件
        downloadObject(objectItem, zipOut);
        ZipEntry zipEntry = new ZipEntry(objectItem.getObjectName());
        zipOut.putNextEntry(zipEntry);
    }

    /**
     * 下载文件内容到输出流
     *
     * @param objectItem   文件对象
     * @param outputStream 输出流
     */
    private void downloadObject(ObjectItem objectItem, OutputStream outputStream) throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {
        try (InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(objectItem.getBucketName())
                .object(objectItem.getObjectName())
                .build())) {
            IOUtils.copy(inputStream, outputStream);
        }
    }

    /**
     * 批量删除文件对象
     * @param bucketName 存储bucket名称
     * @param objects 对象名称集合
     */
    public Iterable<Result<DeleteError>> removeObjects(String bucketName, List<String> objects) {
        List<DeleteObject> dos = objects.stream().map(DeleteObject::new).collect(Collectors.toList());
        return minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());
    }
}
