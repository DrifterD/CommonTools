/*
 * Copyright (C), 2014-2017, 运策物流
 * FileName: Convert2Captial.java
 * Author:   yhx
 * Date:     2017年6月21日 下午4:40:00
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.yhx.otherUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.yhx.StringUtils.StringUtils;

/**
 * 〈一句话功能简述〉金额转换成中文大写<br>
 * 〈功能详细描述〉
 *
 * @author yanghanxiu
 * @see [相关类/方法]（可选）
 * @since
 */
public class Convert2Capital {

	private static final Map<Integer, String> CapitalStr = new HashMap<Integer, String>() {
		{
			put(0, "零");
			put(1, "壹");
			put(2, "贰");
			put(3, "叁");
			put(4, "肆");
			put(5, "伍");
			put(6, "陆");
			put(7, "柒");
			put(8, "捌");
			put(9, "玖");
		}
	};

	private static final Map<Integer, String> digitStr = new HashMap<Integer, String>() {
		{
			put(1, "");
			put(2, "拾");
			put(3, "佰");
			put(4, "仟");
		}
	};

	private static final Map<Integer, String> digit4 = new HashMap<Integer, String>() {
		{
			put(1, "元");
			put(2, "万");
			put(3, "亿");
		}
	};

	public static void main(String[] args) {

		long stat = System.currentTimeMillis();
		String tmp = convertToCapital("70100710.02");
		System.out.println("time=" + (System.currentTimeMillis() - stat));
		System.out.println(tmp);
	}

	public static final String convertToCapital(String num) {

		StringBuilder sBuilder = new StringBuilder();
		String suffix = "元";
		String prefix = "";
		String IntegerStr = "";
		String decimalStr = "";

		try {
			// 判断是否是数字
			double tmpDouble=Double.parseDouble(num);
			if(tmpDouble>1000000000000d)
				throw new RuntimeException("数据过于庞大");
		} catch (Exception exception) {
			throw exception;
		}

		BigDecimal bigDecimal = new BigDecimal(num);
		bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);

		if(bigDecimal.doubleValue()==0){
			return "零元整";
		}
		// 处理负数
		if (bigDecimal.doubleValue() < 0) {
			prefix = "负";
			bigDecimal = bigDecimal.abs();
		}

		String[] nums = bigDecimal.toString().split("\\.");
		boolean isDigital=Pattern.matches("[-+]?\\d+(.00)?", bigDecimal.toString());
		// 标记小数部分
		if (!isDigital) {

			// 处理小数四舍五入
			nums = bigDecimal.toString().split("\\.");
			suffix = "";
			decimalStr = nums[1];
		} else
			suffix = "整";

		IntegerStr = nums[0];
		int len = IntegerStr.length();

		for (int i = len, j = 1; i >= 0; i = i - 4) {
			String tmp = "";
			if(i==0) continue;
			if (i - 4 < 0)
				tmp = IntegerStr.substring(0, i);
			else
				tmp = IntegerStr.substring(i - 4, i);

//			System.out.println(integer2Capital(tmp,j) + digit4.get(j));
//			System.out.println(tmp);
			String num4Str=integer2Capital(tmp,j);
			sBuilder.insert(0, StringUtils.isEmpty(num4Str)?"":num4Str+ digit4.get(j));
			j++;
		}
		sBuilder.insert(0, prefix);

		//小于1的
		if(bigDecimal.abs().doubleValue()<1&&bigDecimal.abs().doubleValue()>0&&!isDigital){
			sBuilder=new StringBuilder();
			sBuilder.append(!isDigital ? decimal2Capital(decimalStr,true) : "");
		}else{
			sBuilder.append(!isDigital ? decimal2Capital(decimalStr,false) : "");
		}

		sBuilder.append(suffix);
		return sBuilder.toString();
	}

	// 处理整数部分
	private static String integer2Capital(String num,int digit4) {

		int length = num.toCharArray().length;
		StringBuilder sBuilder = new StringBuilder();

		for (int i = 0; i < length; i++) {

			int tmpNum = num.toCharArray()[i] - 48;

			if(i+1<length){
				int next=num.toCharArray()[i+1]-48;
				if(tmpNum==0&&next==0)
					continue;
			}

			if(i==length-1&&tmpNum==0)continue;

			sBuilder.append(tmpNum==0?CapitalStr.get(tmpNum):(CapitalStr.get(tmpNum) + digitStr.get(length - i)));
		}

		return sBuilder.toString();
	}

	// 处理小数部分
	private static String decimal2Capital(String num,boolean flag) {
		int num1=num.toCharArray()[0] - 48;
		int num2=num.toCharArray()[1] - 48;

		//绝对值在1以内，角单位是0不显示，绝对值大于1，则显示角
		String numStr="";
		if(flag){
			numStr=num1==0?"":(CapitalStr.get(num1) + "角");
		}else{
			numStr=num1==0?CapitalStr.get(num1):(CapitalStr.get(num1) + "角");
		}

		return numStr+(num2==0?"":CapitalStr.get(num2) + "分");
	}
}
