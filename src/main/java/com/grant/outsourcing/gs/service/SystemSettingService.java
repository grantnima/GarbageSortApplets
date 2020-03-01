package com.grant.outsourcing.gs.service;

import com.google.common.base.Strings;
import com.grant.outsourcing.gs.constant.CacheKey;
import com.grant.outsourcing.gs.db.mapper.SystemSettingMapper;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SystemSettingService {

    @Resource
    private SystemSettingMapper systemSettingMapper;

    @Resource
    private RedissonClient redissonClient;

    public String getValueByKey ( String key){
        RMap<String,String> settingMap = redissonClient.getMap(CacheKey.SYSTEM_SETTING);
        if(settingMap.containsKey(key)){
            return settingMap.get(key);
        }
        String value = systemSettingMapper.findValueByKey(key);
        if(!Strings.isNullOrEmpty(value)){
            settingMap.put(key,value);
        }
        return value;
    }

}
