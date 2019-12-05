package com.grant.outsourcing.gs.db.mapper;

import com.grant.outsourcing.gs.db.model.Garbage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public interface GarbageMapper extends GeneralDao<Garbage>
{
	@Select("select * from garbage where name = #{name}")
	Garbage findByName (@Param("name") String name);

	@Select("select * from garbage where sort = #{sort} order by capital_letter asc")
	List<Map<String,Object>> findBySort (@Param("sort") Integer sort);

	@Select("select name from garbage where id = #{id}")
	String findNameById (@Param("id") Long id);

	@Select("select sort from garbage where id = #{id}")
	Integer findSortById (@Param("id") Long id);

	@Select("select * from garbage where name like #{regx}")
	List<Garbage> findByRegx (@Param("regx") String regx);

	@Select("select * from garbage where id = #{id}")
	Garbage findById (@Param("id") Long id);

	@Select("SELECT t1.id FROM garbage AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `garbage`)-(SELECT MIN(id) FROM garbage))+(SELECT MIN(id) FROM garbage)) AS id) AS t2 WHERE t1.id >= t2.id ORDER BY t1.id LIMIT 1")
	Long findIdByRandom ();
}
