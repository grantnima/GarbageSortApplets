package com.grant.outsourcing.gs.component;

import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.constant.ERespCode;
import com.grant.outsourcing.gs.db.model.File;
import com.grant.outsourcing.gs.service.FileService;
import com.grant.outsourcing.gs.utils.IdUtil;
import com.grant.outsourcing.gs.utils.OSSUtils;
import com.grant.outsourcing.gs.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class FileComponent {

    @Autowired
    private FileService fileService;

    public Map<String,Object> uploadFile (MultipartFile multipartFile) throws BaseException {
        if (multipartFile == null){
            throw new BaseException(ERespCode.RESOURCE_NOT_FOUND,"接受不到文件");
        }
        File file = new File();
        file.setId(StringUtils.getSimpleUUID());
        file.setCreateTime(System.currentTimeMillis());
        file.setFileName(multipartFile.getOriginalFilename());
        //生成url
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String[] array = multipartFile.getContentType().split("/");
        file.setFileUrl(uuid + "." + array[array.length - 1]);

        try {
            OSSUtils.uploadFile(multipartFile.getInputStream(),file.getFileUrl());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(ERespCode.INTERNAL_ERROR,"文件上传失败");
        }
        fileService.save(file);
        Map<String,Object> response = new HashMap<>();
        response.put("file_id",file.getId());
        return response;
    }

    public Map<String,Object> getFileDownloadUrl (String fileId) throws BaseException {
        Map<String,Object> response = new HashMap<>();

        File file = fileService.findOne(fileId);
        if(file == null){
            throw new BaseException(ERespCode.RESOURCE_NOT_FOUND,"找不到文件");
        }
        String url;
        try {
            url = OSSUtils.getFileDownloadLink(file.getFileUrl());
        } catch (BaseException e) {
            e.printStackTrace();
            throw new BaseException(ERespCode.INTERNAL_ERROR,"获取文件下载url失败");
        }
        response.put("url",url);
        return response;
    }
}
