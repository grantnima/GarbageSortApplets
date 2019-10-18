package com.grant.outsourcing.gs.utils;

import java.util.UUID;

public class StringUtils {

    public static String getSimpleUUID () {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
