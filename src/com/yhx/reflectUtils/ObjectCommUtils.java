/*
 * Copyright (C), 2014-2017, 运策物流
 * FileName: ObjectCommUtils.java
 * Author:   yhx
 * Date:     2017年6月21日 下午4:34:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.yhx.reflectUtils;

import java.lang.reflect.Method;

/**
 * 〈一句话功能简述〉<br>
 * 通过反射复制对象
 *
 * @author yanghanxiu
 * @see [相关类/方法]（可选）
 * @since
 */
public class ObjectCommUtils {


	/**
	 * 拷贝源实例中的所有属性到目标实例中
	 *
	 * @param resourceObj 源实例
	 * @param targetObj 目标实例
	 * @throws Exception
	 */
	public static void copyProperty(Object resourceObj, Object targetObj)
			throws Exception {
		if (!resourceObj.getClass().getName().equals(
				targetObj.getClass().getName())) {
			throw new Exception(
					"resourceObj and targetObj is not the same class instance");
		}

		Method[] resMethods = resourceObj.getClass().getDeclaredMethods();
		Method[] targetMethods = targetObj.getClass().getDeclaredMethods();
		String fieldName = "";
		for (Method targetMethod : targetMethods) {
			fieldName = targetMethod.getName();
			if (fieldName.indexOf("set") > -1) {
				fieldName = fieldName.substring(fieldName.indexOf("set") + 3,
						fieldName.length());
				for (Method resMethod : resMethods) {
					if (("get" + fieldName).equalsIgnoreCase(resMethod
							.getName())) {
						Object value = resMethod.invoke(resourceObj, null);
						Object[] params = { value };
						targetMethod.invoke(targetObj, params);
						break;
					}
				}
			}
		}
	}
}
