package com.grant.outsourcing.gs.db.provider;

import com.google.common.base.CaseFormat;
import com.grant.outsourcing.gs.utils.DateUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class GeneralProvider
{
	/**
	 * 对象保存操作
	 */
	public String save(Object parameter) throws Exception {
		List<Object> data = operationObjectConversion(parameter);
		//读取第一个对象
		if (data.size() == 0){
			throw new Exception("插入数据库时发现对象为空");
		}
		Object object = data.get(0);
		Map<String,Object> attributes = beanConversionMap(object);
		if (attributes.get("id") == null){
			attributes.remove("id");
		}
		//要存入key值
		Set<String> keys = attributes.keySet();
		//解析表名
		String tableName = object.getClass().getSimpleName();
		//类名做驼峰转下划线处理
		tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,tableName);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("insert into `").append(tableName).append("` (");
		for (String key:keys){
			//key做驼峰转下划线处理
			key = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,key);
			stringBuilder.append("`").append(key).append("`");
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length()-1);
		stringBuilder.append(") VALUES ");
		Object tempObject;
		for (int i=0;i<data.size();i++){
			if (i != 0){
				//重新解析
				attributes = beanConversionMap(data.get(i));
			}
			stringBuilder.append("(");
			//遍历Map集合
			for (String key : keys){
				tempObject = attributes.get(key);
				if (tempObject == null){
					stringBuilder.append("null");
				}else if(tempObject instanceof Enum){
					//这是一个枚举类型
					stringBuilder.append(((Enum)tempObject).ordinal());
				}else if(tempObject instanceof Date){
					//这是一个时间类型
					stringBuilder.append("'").append(DateUtils.formatDate((Date)tempObject)).append("'");
				}else if(tempObject instanceof String){
					//这是一个字符串类型
					stringBuilder.append("\"").append(tempObject.toString().replaceAll("\\\"","'")).append("\"");
				}else{
					stringBuilder.append(tempObject.toString());
				}
				stringBuilder.append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length()-1);
			stringBuilder.append("),");
		}
		stringBuilder.deleteCharAt(stringBuilder.length()-1);
		return stringBuilder.toString();
	}

	/**
	 * 对象更新操作
	 */
	public String update(Object parameter) throws Exception {
		List<Object> data = operationObjectConversion(parameter);
		//读取第一个对象
		if (data.size() == 0){
			throw new Exception("空对象不能自行更新操作");
		}
		//分析需要修改的值
		Object object = data.get(0);
		Map<String,Object> attributes = beanConversionMap(object);
		if (attributes.get("id") == null){
			throw new Exception("必须传入识别id");
		}
		String id = attributes.get("id").toString();
		attributes.remove("id");
		if (data.size() > 1){
			throw new Exception("仅支持单对象更新");
		}
		//要存入key值
		Set<String> keys = attributes.keySet();
		//解析表名
		String tableName = object.getClass().getSimpleName();
		//类名做驼峰转下划线处理
		tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,tableName);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("UPDATE `").append(tableName).append("` SET ");
		Object tempObject;
		for (String key : keys){
			tempObject = attributes.get(key);
			//key做驼峰转下划线处理
			key = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,key);
			if (tempObject == null){
				continue;
			}else if(tempObject instanceof Enum){
				//这是一个枚举类型
				stringBuilder.append("`").append(key).append("`").append(" = ").append(((Enum)tempObject).ordinal());
			}else if(tempObject instanceof Date){
				//这是一个时间类型
				stringBuilder.append("`").append(key).append("`").append(" = ").append("'").append(DateUtils.formatDate((Date)tempObject)).append("'");
			}else if(tempObject instanceof String){
				//这是一个字符串类型
				stringBuilder.append("`").append(key).append("`").append(" = ").append("\"").append(tempObject.toString().replaceAll("\\\"","'")).append("\"");
			} else{
				stringBuilder.append("`").append(key).append("`").append(" = ").append(tempObject.toString());
			}
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length()-1);
		stringBuilder.append(" WHERE id = ").append(id);
		return stringBuilder.toString();
	}


	/**
	 * bean转Map
	 */
	private Map<String,Object> beanConversionMap(Object bean) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
		Map<String, Object> fieldMap =new HashMap<>();
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
		//获取所有的属性描述器
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor pd:pds){
			String key = pd.getName();
			Method getter = pd.getReadMethod();
			Object value = getter.invoke(bean);
			fieldMap.put(key, value);
		}
		return fieldMap;
	}

	private List<Object> operationObjectConversion(Object parameter){
		List<Object> data;
		//判断传入类型
		if (parameter instanceof Map){
			Map<String,Object> para = (Map<String, Object>) parameter;
			data = (List<Object>) para.get("list");
			//传入的是集合
		}else{
			//传入的是对象
			data = new ArrayList<>();
			data.add(parameter);
		}
		return data;
	}
}
