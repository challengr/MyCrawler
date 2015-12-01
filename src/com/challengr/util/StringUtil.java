package com.challengr.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.challengr.pub.SystemConfig;

public class StringUtil {
	/*public static String trimAllWhiteSpace(String s){
		StringTokenizer st = new StringTokenizer(s," ",false);
		String t="";
		while (st.hasMoreElements()) 
			t += st.nextElement();
		return t;
	}*/
	// 去掉空格的时候把中间的"，"页去掉
	public static String trimAllWhiteSpace(String s){
		StringTokenizer st = new StringTokenizer(s," ",false);
		String comma = ",";
		String t="";
		while (st.hasMoreElements()) {
			t += st.nextElement();
		}
		if (t.contains(comma))
			t = t.replace(comma, "");
		return t;
	}
	
	/**
	 * 还原转义字符(escaping sequence)
	 * input :   &#22269;&#38469;&#36152;&#26131;&#23398;
	 * output : 
	 * @param str 转义字符
	 * @return 原字符
	 * @throws UnsupportedEncodingException 
	 */
	public static String recoverEscapingSequence(String escapedSequence) {
		String result;
		// 替换所有amazon的转义前缀
		escapedSequence = 
				StringUtil.transformingAmazonEscapingWords(escapedSequence);
		/**
		 * 以AMAZON_ESCAPE_PREFIX为分隔符切分字符串，以每个字符串为单位逐个判断。
		 * 1. 若为>=4位则进行转换操作
		 *    a. 包含纯数字
		 *    b. 不包含纯数字
		 * 2. 否则原样拼接
		 */
		String[] words = escapedSequence.split(SystemConfig.AMAZON_ESCAPE_PREFIX);
		StringBuilder sBuilder = new StringBuilder();
		
		for (String word : words) {
			if (word.length() < 4) {
				sBuilder.append(word);
			} else {
				boolean isDigital = true;  // 标记这个word是否为纯数字
				char[] chars = word.toCharArray();
				int commaIndex = word.indexOf(";");           // 分号分隔符的索引 
				int n = word.length();
				for (int i = 0; i < commaIndex; i++) {
					if (!Character.isDigit(chars[i]))
						isDigital = false;
				}
				if (isDigital && commaIndex <= n && commaIndex >= 0) {           // 存在纯数字则分成两部分处理 
					try {
						String digits = 
								Integer.toHexString(
										Integer.valueOf(word.substring(0, commaIndex))); // 纯数字部分   
						String escapeDigits = SystemConfig.UNICODE_PREFIX + digits;
						sBuilder.append(escapeDigits);
						sBuilder.append(word.substring(commaIndex + 1));                 // 其他部分，同时要去掉";"
					} catch (StringIndexOutOfBoundsException e) {
						System.out.println("word=" + word + " ,commaIndex=" + commaIndex);
					}
					
				} else
					sBuilder.append(word);
			}
		}
		
		result = sBuilder.toString();
		// 过滤非法unicode字符
		result = discardIllegalUnicode(result);
		
//		System.out.println(sBuilder.toString());
		return result;    
	}
	
	/**
	 * 去除特殊字符
	 * @param string 原始字符串
	 * @return 过滤后的字符串
	 */
	private static String discardIllegalUnicode(String string) {
		String result = string;
		int start;
		int end;

		// 去除ub7
		while (result.contains("\\ub7")) {
			start = result.indexOf("\\ub7");
			end = start + 4;
			result = result.substring(0, start) + result.substring(end);
		}
		
		return result;
	}

	/**
	 * 将Amazon中出现的转义字符经行转换
	 * @param content 原始字符串
	 * @return 转以后的字符串
	 */
	public static String transformingAmazonEscapingWords(String content) {
		String result = content;
		result = result.replaceAll("&quot;", "\"");
//		result = result.replaceAll("\\ub7", "");
		
		return result;
	}
	
	/**
	 * 将unicode转化为中文
	 * @param ori unicode字符串
	 * @return 转换好的中文
	 */
	public static String convertUnicode(String ori){
        char aChar;
        int len = ori.length();
        StringBuilder sBuilder = new StringBuilder(len);
        for (int x = 0; x < len;) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    sBuilder.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    sBuilder.append(aChar);
                }
            } else
                sBuilder.append(aChar);
 
        }
        return sBuilder.toString();
	}
	
	/**
	 * 根据筛选规则找出在字符串中所有符合规则的条目并返回
	 * @param content 字符串文档
	 * @param pattern 正则表达式形式的筛选规则
	 * @return 所有符合筛选条件的条目列表
	 */
	public static List<String> getResultList(String content, String pattern) {
		List<String> list = new ArrayList<String>();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);
		
		while(m.find()) {
			list.add(m.group(0));
//			System.out.println(m.start());    // Start index
//			System.out.println(m.end());      // End index
		}
		return list;
	}

	//转义url中的特殊字符
	public static String formatUriString(String url){
		String newUrl = url;
		newUrl = newUrl.replaceAll("[^\\d,，]", ""); //去掉所有非数字和非中英文逗号的字符
		newUrl = newUrl.replaceAll(",","%2C");
		newUrl = newUrl.replaceAll("，","%2C");
		newUrl = newUrl.replaceAll(" ","%20");
		
		return newUrl;
	}
	
	/**
	 * 去掉多余的系统(Windows)文件名字符,不能以空格为开头和结束,不能用/\:*?"<>|作为文件名称,文件名称为1-255位
	 * @param fileName 原始字符串
	 * @return 过滤后的字符串
	 */
	public static String formatFileNameString(String fileName) {
		String newName = fileName;
		newName = newName.trim();
		newName = newName.replace("/", " ");
		newName = newName.replace("\\", " ");
		newName = newName.replace(":", "");
		newName = newName.replace("*", "");
		newName = newName.replace("?", "");
		newName = newName.replace("<", "");
		newName = newName.replace("\"", "");
		newName = newName.replace(">", "");
		newName = newName.replace("|", "");
		
		return newName;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
//		String str = "3S&#25216;fsfs&#26415;&#22312;aaaa&#27700;hfs&#22303;&#20445;&#25345;&#20013;&#30340;&#24212;&#29992;";
//		System.out.println(revocerAmazonES(str));
		
		String input = "水文统计学/黄振平.html";
		System.out.println(formatFileNameString(input));
		/*
		Pattern p = Pattern.compile("<li id=\"result_(.*?)</li>");
		String input = "dfdsfsf<li id=\"result_1\" data-asin=\"B00DZ240YM</li>llkl<li id=\"result_3\" data-asin=\"B00DZ240YM</li>dfdfd<li id=\"result_7\" data-asin=\"B00DZ240YM</li>f";
		Matcher m = p.matcher(input);
		
		while (m.find()) {
			System.out.println(m.group());
			System.out.println(m.start());
			System.out.println(m.end());
		}
		*/
	}
	
}
