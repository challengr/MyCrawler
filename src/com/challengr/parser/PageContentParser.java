package com.challengr.parser;

import java.util.ArrayList;
import java.util.List;

import com.challengr.model.CategoryInfoOfGoods;
import com.challengr.model.GoodsCategory;
import com.challengr.pub.SystemConfig;
import com.challengr.util.StringUtil;

public abstract class PageContentParser {

	protected Object parse(String content)
	{
		return null;
	}
	
	protected Object parse(String content, Object params[])
	{
		return null;
	}
	
	protected Object parse(String content, CategoryInfoOfGoods category) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 拼接字符串
	public static String getItem(String content, String[] PF_prefixes, String PF_suffix)
	{
		int pos1 = 0;
		for(int i=0;i<PF_prefixes.length;i++){
			pos1 = content.indexOf(PF_prefixes[i],pos1);
			if (pos1 == -1)
				return null;
			pos1 += PF_prefixes[i].length();
		}
		
		int pos2 = content.indexOf(PF_suffix, pos1);
		if (pos2 == -1)
			return null;
		
		return content.substring(pos1, pos2).trim();		
	}
	
	// 字符串数组的长度
	public static int lengthOfStrings(String[] strings) {
		int length = 0;
		for (String string : strings) {
			length += string.length();
		}
		return length;
		
	}
	
	public static String getItem(String content, String PF_prefix, String PF_suffix)
	{
		String[] PF_prefixes = new String[1];
		PF_prefixes[0] = PF_prefix;
		return getItem(content, PF_prefixes, PF_suffix);
	}
	
	public static String getItem(String content, String PF_prefix1, String PF_prefix2, String PF_suffix)
	{
		String[] PF_prefixes = new String[2];
		PF_prefixes[0] = PF_prefix1;
		PF_prefixes[1] = PF_prefix2;
		return getItem(content, PF_prefixes, PF_suffix);
	}
	
	public static List<String> getItems(String content, String[] PF_prefixes, String PF_suffix)
	{
		List<String> items = new ArrayList<String>();
		int pos1 = 0;
		while(true){
			for(int i=0;i<PF_prefixes.length;i++){
				pos1 = content.indexOf(PF_prefixes[i],pos1);
				if (pos1 == -1)
					break;
				pos1 += PF_prefixes[i].length();
			}
			if (pos1 == -1)
				break;
			
			int pos2 = content.indexOf(PF_suffix, pos1);
			if (pos2 == -1)
				break;
			
			items.add(content.substring(pos1, pos2).trim());
			
			pos1 = pos2 + PF_suffix.length();
		}
		
		return items;
	}
	
	/**
	 * 对HTML网页中的特殊字符集特定格式经行格式化
	 * @param s 原始HTML字符串
	 * @return 转义、替换后的字符串
	 */
	public static String formatHTMLString(String s) {
		String str = s;
		if (str != null) {
			str = str.trim();                       // 去掉字符串前后的空白符
			str = str.replaceAll("&#39;", "'");     // 替换为‘
			str = str.replaceAll("&amp;", "&");     // 替换为&符号
			str = str.replaceAll("&gt;", ">");      // 替换为>
			str = str.replaceAll("&lt;", "<");      // 替换为<
			str = str.replaceAll("&quot;", "\"");   // 替换为"
			str = str.replaceAll("<span title=\"", "");   
													// 去掉<span title="
			str = str.replaceAll("</span>", "");    // 去掉</span>
			str = str.replaceAll("\">", "");        // 去掉">
			str = str.replaceAll("\\\\", " ");      // 去除转义字符\
		}
		
		return str;
	}

	/**
	 * 将Amazon中的html转义字符转换成中文
	 * @param str 待转义的字符串
	 * @return 转换成功则返回中文字符串， 否则返回null
	 */
	public static String revocerAmazonES(String str) {
		String result = str;
		
		try {
			// 对HTML中String的特殊字符以及特定格式经行处理
			result = formatHTMLString(str);
			
			// 包含转义字符
			if (str.contains(SystemConfig.AMAZON_ESCAPE_PREFIX)) {
				result = StringUtil.recoverEscapingSequence(str);
				result = StringUtil.convertUnicode(result);
			}
		} catch (IllegalArgumentException e) {
			System.out.println("FAILLOG : " + result);
		}
		 
		return result;
	}
	
	public static void main(String[] args) {
		
		String input = "圣才考研专业课高鸿业西方经济学&lt;宏观部分第5版&gt;笔记和课后习题详解(含考研真题第2版)/国内外经典教材辅导系列\">圣才考研专业课高鸿业西方经济学<宏观部分第5版>笔记和课后习题详解(含考研真题第2版) / 国内外经典教材辅导系列";

		System.out.println(formatHTMLString(input));
		
	}

	/**
	 * @param content
	 * @param params
	 * @return
	 */
	public GoodsCategory parse(String content, Object params) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
