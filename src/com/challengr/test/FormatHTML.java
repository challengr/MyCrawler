package com.challengr.test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.challengr.dao.ibatis.BookSqlMapDAO;
import com.challengr.model.Book;
import com.challengr.parser.PageContentParser;

public class FormatHTML {
	static int count = 0;
	
	public static void formatTitle() {
		
		List<Book> bookList = new ArrayList<Book>();
		String title;
		try {
			BookSqlMapDAO bookDAO = new BookSqlMapDAO();
			
			bookList = bookDAO.getBookList(216613, 220703);
			
			for (Book book : bookList) {
				title = book.getTitle();
				title = PageContentParser.formatHTMLString(title);
				book.setTitle(title);
				book.setBookSetName(PageContentParser.formatHTMLString(book.getBookSetName()));
//				System.out.println(book);
				bookDAO.updateBook(book);
				
				// 打印更新进度
				count++;
				if (count % 5000 == 0)
					System.out.println(count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		formatTitle();
		System.out.println(count);
	}
	
}
