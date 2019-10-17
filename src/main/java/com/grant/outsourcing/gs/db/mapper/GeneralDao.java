package com.grant.outsourcing.gs.db.mapper;

import com.grant.outsourcing.gs.db.provider.GeneralProvider;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GeneralDao<T>
{
	@InsertProvider(type = GeneralProvider.class,method = "save")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void save(T project);

	@UpdateProvider(type = GeneralProvider.class,method = "update")
	void update(T project);
}
