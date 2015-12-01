package com.challengr.parser;

import java.util.ArrayList;
import java.util.List;

import com.challengr.model.Book;
import com.challengr.pub.SystemConfig;
import com.challengr.util.IOUtil;
import com.challengr.util.StringUtil;

/**
 * Amazon图书搜索页面解析
 * URL:http://www.amazon.cn/s/ref=nb_sb_noss_2?__mk_zh_CN=亚马逊网站&url=search-alias%3Daps&field-keywords=
 * 
 * 
 * Test Cases:
 * 1. There is no result
 * 2. Pages of results
 * 3. Only some items in one page
 * @author WangZhe
 *
 */
public class AmazonSearchResultsListPageParser extends PageContentParser {
	
	@Override
	public Object parse(String content) {
		List<String> resultList = new ArrayList<String>();
		String prefix = "<ul id=\"s-results-list-atf\"";     // 过滤"<ul ... </ul>"
		String suffix = "</ul>";
		String pattern = "result_(.*)(/>|</i>|)";  // 过滤"<li id="result_  ...  </li>"
		
		content = getItem(content, prefix, suffix);
		if (content != null) {
			resultList = StringUtil.getResultList(content, pattern);
		}

		/**
		 * 将list中所有的条目（result_）逐个解析，将数据存入数据库
		 * 1. 图书的下载地址
		 * 2. 书名
		 */
		Book book;
		for (String item : resultList) {
			book = new Book();
			// 解析uriString
			String uriString;
			String uriPrefix = "href=\"";
			String uriSuffix = "\"><img";
			uriString = getItem(item, uriPrefix, uriSuffix);
			book.setUriString(uriString);
			
			// 解析书名
			String title;
			String titlePrefix = "title=\"";
			String titleSuffix = "\"";
			title = getItem(item, titlePrefix, titleSuffix);
			if (title != null) 
				title = revocerAmazonES(title);
				book.setTitle(title);
			
			// 下载教材的详细页面
			try {
				// 过滤title中的不符合命名规范的字符
				title = StringUtil.formatFileNameString(title);
				IOUtil.downloadFile(uriString, SystemConfig.TEXTBOOKS_DIR + title + SystemConfig.PAGE_TYPE, 3);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(book);	
			}
			
		}
			
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
	}
	
}
