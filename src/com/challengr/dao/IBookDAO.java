package com.challengr.dao;

import java.sql.SQLException;
import java.util.List;

import com.challengr.model.Book;


public interface IBookDAO {
	public final static String FLAG_SHAOW = "FLAG_SHADOW"; //已存在uri不同但是title和isbn都相同的book
	public final static String FLAG_PAGE_EXPIRED = "FLAG_PAGE_EXPIRED";  //uri失效的book
	public final static int FIELD_TITLE_MAX_LIMIT = 200;  //书名长度上限
	public final static int FIELD_TYPE_MAX_LIMIT = 12; // 书装订类型的字数上线
	public void insertBook(Book book) throws SQLException;
	public void insertBookList(List<Book> bookList) throws SQLException;
	public List<Book> getAllBook() throws SQLException;
	public void updateBook(Book book) throws SQLException;
	public List<Book> searchString() throws SQLException;
	public void updateGoodsCaegoryRelation(Book book) throws SQLException;
	public void insertGoodsCaegoryRelation(Book book) throws SQLException;
	public Book getBookByUriString(Book book) throws SQLException;
	public List<Book> getAllBookbyRanking() throws SQLException;
	public List<Book> getAllBookWithCategoryInfo() throws SQLException;
	/**
	 * 查询指定范围的所有book，并以List形式返回
	 * @param fromId 开始id
	 * @param toId 结束id
	 * @return 以List形式返回查询结果
	 * @throws SQLException
	 */
	public List<Book> getBookList(int fromId, int toId) throws SQLException;
	public int updateBookByTitleAndAuthor(String title, String author) throws SQLException;
	/**
	 * 通过作者的姓名查询出书库中的所有该作者的出版物
	 * @return 返回同一个作者出版的所有书
	 * @throws SQLException 
	 */
	public List<Book> getBookListByAuthor(String author) throws SQLException;
}
