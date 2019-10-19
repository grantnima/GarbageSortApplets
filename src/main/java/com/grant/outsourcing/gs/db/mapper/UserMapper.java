package com.grant.outsourcing.gs.db.mapper;

import com.grant.outsourcing.gs.db.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends GeneralDao<User>
{
	@Select("select * from `user` where open_id = #{openId}")
	User findByOpenId (@Param("openId") String openId);

	@Select("select * from `user` where id = #{id}")
	User findById (@Param("id") String id);
}
