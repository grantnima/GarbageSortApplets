package com.grant.outsourcing.gs.db.mapper;

import com.grant.outsourcing.gs.db.model.GarbageSubmit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface GarbageSubmitMapper extends GeneralDao<GarbageSubmit>
{
	@Select("select id from garbage_submit where user_id = #{userId} and garbage_name = #{garbageName}")
	GarbageSubmit findByUserIdAndGarbageName (@Param("userId") String userId, @Param("garbageName") String garbageName );
}
