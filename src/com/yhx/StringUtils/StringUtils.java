/*
 * Copyright (C), 2014-2017, 运策物流
 * FileName: StringUtils.java
 * Author:   yhx
 * Date:     2017年6月21日 下午4:37:38
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.yhx.StringUtils;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author yanghanxiu
 * @see [相关类/方法]（可选）
 * @since
 */
public class StringUtils {

	  /**
   * 转换字符串第一个为大写
   *
   * @param str
   * @return
   */
  public static String passFirstUpper(String str) {
	if (null!=str&&""==str.trim()) {
	    return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	return str;
  }
}
