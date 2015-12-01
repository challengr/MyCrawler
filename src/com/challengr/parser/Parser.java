package com.challengr.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.challengr.util.ArrayUtil;

public class Parser {
	/**
	 * 从一个文件中解析一个属性的值
	 * @param content 输入的文本信息
	 * @param start 开始的查找位置
	 * @param prefix 该属性的前缀字符串
	 * @param suffix 该属性的后缀字符串
	 * @return 如果解析出则返回，否则返回null
	 */
	public static String between(String content, int start, String prefix, String suffix) {
		String property = null;
		int propertyStart;
		int propertyEnd;
		try {
			propertyStart = content.indexOf(prefix, start) + prefix.length();
			propertyEnd = content.indexOf(suffix, propertyStart);
			if (propertyStart <= propertyEnd && propertyEnd >= 0)
				property = content.substring(propertyStart, propertyEnd);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
System.out.println("属性解析失败！ " + content);			
		}
		return property;
	}
	
	/**
	 * 从一个文件中解析一个属性的值(适用于文件中的属性开始位置的字符串有唯一标识)
	 * @param content 输入的文本信息
	 * @param start 开始的查找位置所在的字符串
	 * @param prefix 该属性的前缀字符串
	 * @param suffix 该属性的后缀字符串
	 * @return
	 */
	public static String between(String content, String start, String prefix, String suffix) {
		String property = null;
		// 所需获得属性的开始位置
		int propertyStart;
		// 所需获得属性的结束坐标
		int propertyEnd;
		
		// 如果查找的开始位置不存在则返回
		int startIndex = content.indexOf(start);
		if(-1 != startIndex) {
			try {
				propertyStart = content.indexOf(prefix, startIndex) + prefix.length();
				propertyEnd = content.indexOf(suffix, propertyStart);
				property = content.substring(propertyStart, propertyEnd);
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
	System.out.println("属性解析失败！");			
			}
		}
		
		return property;
	}
	
	/**
	 * 从一个文件中解析一个属性的值(适用于文件中的属性开始位置的字符串有唯一标识)
	 * 前缀和后缀不是严格意义上的前后缀
	 * @param content 所要解析关键字所在的文件
	 * @param start 需要开始解析的位置
	 * @param prefix 解析属性的前缀字符串
	 * @param prefixOffset 前缀字符串最后一位距离关键词的长度
	 * @param suffix 解析属性的后缀字符串
	 * @param suffixOffset 后缀字符串最开始一位距离关键字的长度
	 * @return
	 */
	public static String between(String content, String start, String prefix, int prefixOffset, String suffix, int suffixOffset) {
		String property = null;
		// 所需获得属性的开始位置
		int propertyStart;
		// 所需获得属性的结束坐标
		int propertyEnd;
		
		// 如果查找的开始位置不存在则返回
		int startIndex = content.indexOf(start);
		if(-1 != startIndex) {
			try {
				propertyStart = content.indexOf(prefix, startIndex) + prefix.length() + prefixOffset;
				propertyEnd = content.indexOf(suffix, propertyStart) - suffixOffset;
				property = content.substring(propertyStart, propertyEnd);
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
	System.out.println("属性解析失败！");			
			}
		}
		
		return property;
	}
	
	public static String[] toStringArray(String content, String regex) {
		Pattern pt = Pattern.compile(regex);
		Matcher mc = pt.matcher(content);
		String[] strings = new String[8];
		int index = 0;
		while(mc.find()) {
			strings[index++] = mc.group();
			if(index>=strings.length) {
				strings = (String[]) ArrayUtil.arrayExpand(strings);
			}
		}
		return strings;
	}
	
	public static String toString(String content, String regex) {
		Pattern pt = Pattern.compile(regex);
		Matcher mc = pt.matcher(content);
		if(mc.find()) {
			return mc.group();
		}
		return null;
	}
	
}
