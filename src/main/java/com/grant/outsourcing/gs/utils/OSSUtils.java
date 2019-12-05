package com.grant.outsourcing.gs.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.constant.Constant;

import java.io.InputStream;

public class OSSUtils {

    // 生成OSSClient
    private static OSS ossClient = new OSSClientBuilder().build(Constant.OSS_ENDPOINT, Constant.OSS_ACCESSKEY_ID, Constant.OSS_ACCESSKEY_SECRET);

    private static OSS getOssClient(){
        return ossClient;
    }

    /**
     * 上传文件
     * @param inputStream
     * @param fileKey
     * @throws BaseException
     */
    public static void uploadFile (InputStream inputStream, String fileKey) throws BaseException {
        getOssClient().putObject(Constant.OSS_BUCKET_NAME, fileKey, inputStream);
    }

    /**
     * 获取下载文件url
     * @param url
     * @return
     * @throws BaseException
     */
    public static String getFileDownloadLink (String url) throws BaseException {
        return Constant.OSS_BUCKET_DOMAIN + "/" + url;
    }

}
