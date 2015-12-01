package com.challengr.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.challengr.dao.ibatis.CurriculumSqlMapDAO;
import com.challengr.model.Curriculum;
import com.challengr.parser.AmazonGoodsPageParser;
import com.challengr.parser.AmazonSearchResultsListPageParser;
import com.challengr.pub.SystemConfig;
import com.challengr.util.IOUtil;

/**
 * 对书单图书的操作
 * 1. 下载书单列表
 * 2. 下载图书信息
 * @author WangZhe
 *
 */
public class SearchResultsListService implements IBookListService {
	
	private final String SEARCHPREFIX = "http://www.amazon.cn/s/ref=nb_sb_noss_2?__mk_zh_CN=%E4%BA%9A%E9%A9%AC%E9%80%8A%E7%BD%91%E7%AB%99&url=search-alias%3Daps&field-keywords=";
	
	public SearchResultsListService() {
		super();
	}

	@Override
	public void updateBookList() {
		// 1. 根据搜索的uri下载same_name_list的html页面
//		downloadBookList();

		// 2. 解析same_name_list页面并将如下信息写入db_resultslist并下载图书的详细信息
//	    parseBookList();
		
		// 3. 解析教材的详细页面
//		parseBook();
		
	}
	
	/**
	 * 解析图书详细页面的内容
	 */
	/*
	private void parseBook() {
		final File booksDir = new File(SystemConfig.TEXTBOOKS_DIR);
		IBookListService bookListService = new BookListService();
		
		String content;
		File[] books = booksDir.listFiles();
		
		for (File book : books) {
			if (book.exists() && 
					book.isFile() && 
					book.canRead()&&
				    !book.isHidden()) {
					try {
						content = IOUtil.convertUTFFileToString(book.getAbsolutePath());
						System.out.println(content);
//						pageParser.parse(content, SystemConfig.CATEGORY_NAME_TEXTBOOK);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		}
		
		
	}
	*/
	/**
	 * 解析same_name_list页面并将如下信息写入db_resultslist
	 * a. title
	 * b. uriString
	 */
	private void parseBookList() {
		final File bookListDir = new File(SystemConfig.BOOKLIST_DIR);
		final AmazonSearchResultsListPageParser parser = new AmazonSearchResultsListPageParser();
		String content;
		
		for (File bookList : bookListDir.listFiles()) {
			if (bookList.exists() && 
				bookList.isFile() && 
				bookList.canRead()&&
			    !bookList.isHidden()) {
				try {
					content = IOUtil.convertFileToString(bookList.getAbsolutePath(), SystemConfig.AMAZON_CHARSET);
					parser.parse(content);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 下载搜索结果书单页面
	 */
	private void downloadBookList() {
		List<String> bookListUrls = new ArrayList<String>();  // 存放搜索结果的请求的url
		Set<String> uniqueNameSet = new HashSet<String>();    // 用于去重
		bookListUrls = getBookListUrls();
		String bookListName;
		String bookListFileName;
		for (String url : bookListUrls) {
			bookListName = url.substring(url.lastIndexOf("=") + 1);
			if (!uniqueNameSet.contains(bookListName)) {
				bookListFileName = SystemConfig.BOOKLIST_DIR + bookListName + SystemConfig.PAGE_TYPE;
				try {
					IOUtil.downloadFile(url, bookListFileName, 3);
					System.out.println(bookListFileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			uniqueNameSet.add(bookListName); 
		}
	}
	
	/**
	 * 获取搜索结果的同名书单
	 * 1. 从db_resultslist中的tab_book_list_SCU中获取组成查询url的信息
	 * 2. 拼接url信息放入list中并返回
	 * @return same_name_list的list信息
	 */
	public List<String> getBookListUrls() {
		
		List<Curriculum> resultsList = new ArrayList<Curriculum>();
		List<String> resultsListUrl = new ArrayList<String>();
		String title;
		String author;
		String publisher;
		String uri;
		try {
			CurriculumSqlMapDAO curriculumDAO = new CurriculumSqlMapDAO();
			resultsList = curriculumDAO.getAllCurriculums();
			for (Curriculum c : resultsList) {
//				title = c.getBookName().replaceAll("\\s", "");     // 去掉空格
				author = c.getBookAuthor();
//				publisher = c.getBookAuthor().trim().replace(" ", "");
				/*
				if (!isValidWord(title))
					continue;
				else if (isValidWord(author))
					uri = SEARCHPREFIX + title + "+" + author;
				else if (isValidWord(publisher))
					uri = SEARCHPREFIX + title + "+" + publisher;
				else
					uri = SEARCHPREFIX + title;
				*/
				if (!isValidWord(author))
					continue;
				uri = SEARCHPREFIX + author;
				resultsListUrl.add(uri);
			}
					
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		 
		return resultsListUrl;
	}
	
	// 过滤词判断
	private boolean isValidWord(String word) {
		boolean isValid = true;
		Set<String> invalidWords = new HashSet<String>();
		invalidWords.add("");   // 空
		invalidWords.add("不详");
		invalidWords.add("无");
		
		if (invalidWords.contains(word.trim())) 
			isValid = false;
		
		return isValid;
	}

}
