package com.grant.outsourcing.gs.db.mapper;

import com.grant.outsourcing.gs.db.model.Garbage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

public interface GarbageMapper extends GeneralDao<Garbage>
{
	@Select("select * from garbage where name = #{name}")
	Garbage findByName (@Param("name") String name);

	@Select("select * from garbage where sort = #{sort} order by capital_letter asc")
	List<Garbage> findBySort (@Param("sort") Integer sort);
}
