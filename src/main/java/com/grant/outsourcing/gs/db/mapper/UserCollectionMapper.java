package com.grant.outsourcing.gs.db.mapper;

import com.grant.outsourcing.gs.db.model.UserCollection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserCollectionMapper extends GeneralDao<UserCollection>
{
	@Select("select * from user_collection where user_id = #{userId}")
	List<UserCollection> findByUserId (@Param("userId") String userId);

	@Delete("delete from user_collection where id = #{id}")
	void deleteById (@Param("id") String id);

	@Select("select * from user_collection where user_id = #{userId} and garbage_id = #{garbageId}")
	UserCollection findByUserIdAndGarbageId (@Param("userId") String userId, @Param("garbageId") String garbageId);
}
