package com.grant.outsourcing.gs.db.mapper;

import com.grant.outsourcing.gs.db.model.RewardedDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RewardedDetailMapper extends GeneralDao<RewardedDetail> {

    @Select("select * from reward_detail where user_id = #{user_id}")
    RewardedDetail findOneByUserId (@Param("userId") String userId);

    @Select("select * from reward_detail")
    List<RewardedDetail> findAll ();
}
