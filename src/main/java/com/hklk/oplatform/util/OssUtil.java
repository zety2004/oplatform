package com.hklk.oplatform.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class OssUtil {
    private static Logger logger = LoggerFactory.getLogger(OssUtil.class); // 日志记录
    private static String endpoint = PropUtil.getProperty("ossEndpoint");
    private static String accessKeyId = PropUtil.getProperty("ossAccessKeyId");
    private static String accessKeySecret = PropUtil.getProperty("ossAccessKeySecret");
    private static String bucketName = PropUtil.getProperty("ossBucketName");

    public static boolean initOss() {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        if (ossClient.doesBucketExist(bucketName)) {
        } else {
            ossClient.createBucket(bucketName);
        }
        ossClient.shutdown();
        return true;
    }

    public static void uploadString(String ossKey, String out) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        InputStream is = new ByteArrayInputStream(out.getBytes());
        ossClient.putObject(bucketName, ossKey, is);
        logger.debug("Object：" + ossKey + "存入OSS成功。");
        ossClient.shutdown();
    }

    public static void getOssString(String ossKey) throws IOException {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, ossKey);
        InputStream inputStream = ossObject.getObjectContent();
        StringBuilder objectContent = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;
            objectContent.append(line);
        }
        inputStream.close();
        ossClient.shutdown();
        logger.debug("Object：" + ossKey + "的内容是：" + objectContent);
    }

    public static void uploadFile(String fileKey, File file) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileKey, file);
        ossClient.shutdown();
        logger.debug("Object：" + fileKey + "存入OSS成功。");
    }

    public static void deleteFile(String fileKey) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, fileKey);
        ossClient.shutdown();
    }
}
