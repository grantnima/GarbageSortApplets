package com.grant.outsourcing.gs.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.constant.Constant;
import com.grant.outsourcing.gs.db.mapper.SystemSettingMapper;
import com.grant.outsourcing.gs.service.SystemSettingService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;

public class OSSUtils {

    private static SystemSettingService systemSettingService;

    private static SystemSettingService getSystemSettingService () {
        if (systemSettingService == null){
            systemSettingService = SpringUtils.getBeanByClass(SystemSettingService.class);
        }
        return systemSettingService;
    }

    // 生成OSSClient
//    private static OSS ossClient = new OSSClientBuilder().build(Constant.OSS_ENDPOINT, Constant.OSS_ACCESSKEY_ID, Constant.OSS_ACCESSKEY_SECRET);

    private static OSS ossClient = new OSSClientBuilder().build(getSystemSettingService().getValueByKey("OSS_ENDPOINT"), getSystemSettingService().getValueByKey("OSS_ACCESSKEY_ID"), getSystemSettingService().getValueByKey("OSS_ACCESSKEY_SECRET"));

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
//        getOssClient().putObject(Constant.OSS_BUCKET_NAME, fileKey, inputStream);
        getOssClient().putObject(getSystemSettingService().getValueByKey("OSS_BUCKET_NAME"), fileKey, inputStream);
    }

    /**
     * 获取下载文件url
     * @param url
     * @return
     * @throws BaseException
     */
    public static String getFileDownloadLink (String url) throws BaseException {
//        return Constant.OSS_BUCKET_DOMAIN + "/" + url;
        return getSystemSettingService().getValueByKey("OSS_BUCKET_DOMAIN") + "/" + url;
    }

}
