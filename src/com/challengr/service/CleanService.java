/**
 * 
 */
package com.challengr.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.challengr.dao.IBookDAO;
import com.challengr.dao.ibatis.BookSqlMapDAO;
import com.challengr.model.Book;

/**
 * @author WangZhe
 * 2015年11月18日
 */
public class CleanService implements ICleanService {

	/* (non-Javadoc)
	 * @see com.challengr.service.ICleanService#clean()
	 */
	@Override
	public void clean() {
		IBookDAO bookDAO;
		List<Book> bookList = new ArrayList<Book>();
		int process = 0;       // 用于记录处理的进度
		try {
			bookDAO = new BookSqlMapDAO();
			bookList = bookDAO.getBookList(215000, 221528);
			// 对bookList中的书的title进行过滤
			for (Book book : bookList) {
				String title = book.getTitle();
				title = transformTitle(title);
				book.setTitle(title);      // 重新替换title
				bookDAO.updateBook(book);  // 更新这本书
				
				// 打印处理进度
				process++;
				if (process % 50 == 0) {
					System.out.printf("The clean process is : %.2f now!\n", process * 1.0 / bookList.size());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 对title进行转换
	 * 1. 处理转移字符
	 * 2. 删除多余内容
	 * @param title 原始title
	 * @return 新title
	 */
	private String transformTitle(String title) {
		// 处理转移字符
		Map<String, String> escapeStringMap = new HashMap<String, String>();  // 用来存放转义字符表
		escapeStringMap.put("&quot;", "\"");     // “
		for (String escapeString : escapeStringMap.keySet()) {
			if (title.contains(escapeString)) {
				title = title.replaceAll(escapeString, escapeStringMap.get(escapeString)); 
			}
		}
			
		// 处理多余字符
		List<String> dirtyStringList = new ArrayList<String>();
		final String replacedString = "";     // 用于替换所有的dirtyString
		dirtyStringList.add("<span title=");
		dirtyStringList.add("</span>");
		for (String dirtyString : dirtyStringList) {
			if (title.contains(dirtyString)) {
				title = title.replaceAll(dirtyString, replacedString);
			}
		}

		return title;
	}
	
	public static void main(String[] args) {
		CleanService clean = new CleanService();
		clean.clean();
	}

}
