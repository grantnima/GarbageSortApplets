package com.grant.outsourcing.gs.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by simba on 2018/8/30 15:46.
 * Remarks：数据库忽视
 */
@Target(FIELD)//方法声明
@Retention(RUNTIME)//运行时注解，VM运行期间也会保留该注解，因此可以通过反射来获得该注解
@Documented//生成javadoc时会包含注解
public @interface DataBaseIgnore {
}
