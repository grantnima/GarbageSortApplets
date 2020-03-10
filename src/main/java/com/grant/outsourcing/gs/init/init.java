package com.grant.outsourcing.gs.init;

import cn.hutool.log.StaticLog;
import com.grant.outsourcing.gs.constant.CacheKey;
import com.grant.outsourcing.gs.db.mapper.GarbageMapper;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class init implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private GarbageMapper garbageMapper;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        StaticLog.info("【垃圾塞入redis】");
        List<Long> ids = garbageMapper.findAll();
        if(ids == null || ids.size() == 0){
            return;
        }
        //清除旧池
        RMap<Integer,Long> examPool = redissonClient.getMap(CacheKey.EXAM_POOL);
        examPool.clear();
        Integer count = 1;
        for(Long id : ids){
            examPool.put(count,id);
            count ++;
        }
        StaticLog.info("【垃圾加载完成】，size：{}",count);
    }
}
