package com.grant.outsourcing.gs.service;

import com.grant.outsourcing.gs.db.mapper.RewardedDetailMapper;
import com.grant.outsourcing.gs.db.model.RewardedDetail;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RewardDetailService {

    @Resource private RewardedDetailMapper rewardedDetailMapper;

    public void save (RewardedDetail rewardedDetail) {
        rewardedDetailMapper.save(rewardedDetail);
    }

    public void update (RewardedDetail rewardedDetail) {
        rewardedDetailMapper.update(rewardedDetail);
    }

    public RewardedDetail findOneByUserId (String userId){
        return rewardedDetailMapper.findOneByUserId(userId);
    }

    public List<RewardedDetail> findAll () {
        return rewardedDetailMapper.findAll();
    }
}
