package com.grant.outsourcing.gs.service;

import com.grant.outsourcing.gs.db.mapper.FileMapper;
import com.grant.outsourcing.gs.db.model.File;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FileService {

    @Resource
    private FileMapper fileMapper;

    public void save (File file){
        fileMapper.save(file);
    }

    public File findOne (Long id){
        return fileMapper.findOneById(id);
    }
}
