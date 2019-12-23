package com.grant.outsourcing.gs.api.controller;

import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.component.FileComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController extends BaseApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileComponent fileComponent;

    @PostMapping("/upload")
    public Map<String,Object> uploadFile (MultipartFile file) throws BaseException{
        LOGGER.debug("[uploadFile],file: {}",file);
        return buildResponse(fileComponent.uploadFile(file));
    }

    @GetMapping("/download/url")
    public Map<String,Object> getFileDownloadUrl (@RequestParam(value = "file_id") String fileId) throws BaseException {
        LOGGER.debug("[getFileDownloadUrl],file_id: {}",fileId);
        return buildResponse(fileComponent.getFileDownloadUrl(fileId));
    }
}
