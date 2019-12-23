package com.grant.outsourcing.gs.db.mapper;

import com.grant.outsourcing.gs.db.model.File;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface FileMapper extends GeneralDao<File> {

    @Select("select * from file where id = #{id}")
    File findOneById (@Param("id") String id);
}
