package com.swpym.blog.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-05-09 15:08
 */
@Component
public class AliyunOssUtil {

    private static final Logger LOG = LoggerFactory.getLogger(AliyunOssUtil.class);

    @Autowired
    private OSS ossClient;

    /**
     * @param bucketName
     * @description: 删除存储空间buckName
     * @author: shaowei
     * @date: 2020-05-09 16:21:08
     * @return: void
     */
    public void deleteBucket(String bucketName) {
        ossClient.deleteBucket(bucketName);
        LOG.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * @param bucketName
     * @description: 获取存储空间基本信息
     * @author: shaowei
     * @date: 2020-05-11 09:23:37
     * @return: com.aliyun.oss.model.BucketInfo
     */
    public BucketInfo getBucketInfo(String bucketName) {
        return ossClient.getBucketInfo(bucketName);
    }

    /**
     * 根据key删除OSS服务器上的文件
     * <p>
     * param ossClient oss连接
     * param bucketName 存储空间
     * param folder 模拟文件夹名 如"qj_nanjing/"
     * param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public void deleteFile(String bucketName, String folder, String key) {
        ossClient.deleteObject(bucketName, folder + key);
        LOG.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }

    /**
     * @param cannedACL  CannedAccessControlList.PublicRead 权限
     * @param bucketName
     * @description: 创建存储空间
     * @author: shaowei
     * @date: 2020-05-09 16:13:09
     * @return: java.lang.String
     */
    public String createBucketName(String bucketName, CannedAccessControlList cannedACL) {
        // 存储空间
        final String bucketNames = bucketName;
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            createBucketRequest.setCannedACL(cannedACL);
            // 如果创建存储空间的同时需要指定存储类型以及数据容灾类型, 可以参考以下代码。
            // 此处以设置存储空间的存储类型为标准存储为例。
            createBucketRequest.setStorageClass(StorageClass.Standard);
            Bucket bucket = ossClient.createBucket(createBucketRequest);
            LOG.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * @param bucketName
     * @param folder     模拟文件夹名如"qj_nanjing/"
     * @description: 创建模拟文件夹
     * @author: shaowei
     * @date: 2020-05-09 16:22:13
     * @return: java.lang.String
     */
    public String createFolder(String bucketName, String folder) {
        // 文件夹名
        final String keySuffixWithSlash = folder;
        // 判断文件夹是否存在，不存在则创建
        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
            // 创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            LOG.info("创建文件夹成功");
            // 得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 上传
     * param ossClient oss连接
     * param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     * param bucketName 存储空间
     * param storePath 模拟文件夹名 如"qj_nanjing/"
     * return String 返回的唯一MD5数字签名
     */
    public String uploadObjectOSS(String bucketName, String storePath, MultipartFile file) {
        String resultStr = null;
        this.createFolder(bucketName, storePath);
        try {
            // 文件名
            String fileName = file.getOriginalFilename();
            String fileType = StringUtils.substringAfterLast(fileName, ".");
            // 文件大小
            Long fileSize = file.getSize();
            InputStream is = file.getInputStream();
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            // 上传的文件的长度
            metadata.setContentLength(is.available());
            // 指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            // 指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            // 指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            // 如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            // 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            // 上传文件 (上传文件流的形式)
            String saveFileName = UUID.randomUUID().toString().replace("-", "").toUpperCase() + "." + fileType;
            String path = storePath + saveFileName;
            PutObjectResult putResult = ossClient.putObject(bucketName, path, is, metadata);
            // 解析结果
            Date expiration = new Date(System.currentTimeMillis() + 3600 * 10000);
            // 生成URL
            URL url = ossClient.generatePresignedUrl(bucketName, path, expiration);
            resultStr = url.toString();
            LOG.info("putResult.getETag():" + putResult.getETag());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            LOG.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultStr;
    }

    /**
     * @param fileName
     * @description: 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @author: shaowei
     * @date: 2020-05-09 16:28:36
     * @return: java.lang.String
     */
    public String getContentType(String fileName) {
        // 文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)
                || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".png".equalsIgnoreCase(fileExtension)) {
            return "image/png";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        // 默认返回类型
        return "";
    }

    public void exportOssFile(OutputStream os, String objectName) throws IOException {
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(objectName, "images/962AD55914FD4E6287D750752EB45BE1.png");
        // 读取文件内容。
        BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
        BufferedOutputStream out = new BufferedOutputStream(os);
        byte[] buffer = new byte[1024];
        int lenght = 0;
        while ((lenght = in.read(buffer)) != -1) {
            out.write(buffer, 0, lenght);
        }
        if (out != null) {
            out.flush();
            out.close();
        }
        if (in != null) {
            in.close();
        }
    }
}
