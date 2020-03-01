package com.grant.outsourcing.gs.db.mapper;

import com.grant.outsourcing.gs.db.model.SystemSetting;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SystemSettingMapper extends GeneralDao<SystemSetting> {

    @Select("select `value` from system_setting where `key` = #{key}")
    String findValueByKey (@Param("key") String key);
}
