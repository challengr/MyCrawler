package com.challengr.dao.ibatis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.challengr.dao.IBookDAO;
import com.challengr.dao.MyDAO;
import com.challengr.model.Book;

public class BookSqlMapDAO extends MyDAO implements IBookDAO {

	private static int count = 0;
	
	public BookSqlMapDAO() throws IOException {
		super();
	}
	
	@Override
	public Book getBookByUriString(Book book) throws SQLException {
		return (Book) sqlMapClient.queryForObject("getBookByUriString", book);
	}
	
	@Override
	public void insertBook(Book newBook) throws SQLException {
		Book shadowBook = (Book) sqlMapClient.queryForObject("getBookByISBNAndTitle", newBook);
		/*当该书不存在时，下载*/
		if (null == shadowBook){
			sqlMapClient.insert("insertBook", newBook);
		} 
		/*
		if (getBookByUriString(book)==null){
			sqlMapClient.insert("insertBook", book);
		}
		*/
	}

	@Override
	public void insertBookList(List<Book> bookList) throws SQLException {
		for(Book book : bookList){
			insertBook(book);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getAllBook() throws SQLException {
		return sqlMapClient.queryForList("getAllBook");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBookList(int fromId, int toId) throws SQLException {
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("fromId", fromId);
		params.put("toId", toId);		
		return sqlMapClient.queryForList("getBookList",params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getAllBookbyRanking() throws SQLException {
		return sqlMapClient.queryForList("getAllBookByRanking");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getAllBookWithCategoryInfo() throws SQLException {
		return sqlMapClient.queryForList("getAllBookWithCategoryInfo");
	}
	
	@Override
	public void updateBook(Book book) throws SQLException {
		Book shadowBook = (Book) sqlMapClient.queryForObject("getBookByISBNAndTitle", book);
		if (shadowBook==null){
			sqlMapClient.update("updateBook",book);
		}
		else{
			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("bookId", book.getId());
			params.put("shadowBookId", shadowBook.getId());
			sqlMapClient.insert("insertBookShadowRelation", params);
			sqlMapClient.update("updateBookShadowFlag", book);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Book> searchString() throws SQLException {
		List<Book> list =  sqlMapClient.queryForList("getBookListBySearchString", "工业");
		return list;
	}

	@Override
	public void updateGoodsCaegoryRelation(Book book) throws SQLException {
		sqlMapClient.update("updateGoodsCaegoryRelation", book);
	}
	
	@Override
	public void insertGoodsCaegoryRelation(Book book) throws SQLException {
		List<com.challengr.model.CategoryInfoOfGoods> cinfoList = book.getCategoryInfoOfGoods();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(com.challengr.model.CategoryInfoOfGoods cinfo : cinfoList){
			map.put("goodsId", book.getId());
			map.put("categoryId", cinfo.getCategoryId());
//			map.put("rankingInCategory", cinfo.getRankingInCategory());
			map.put("rankingInAll", cinfo.getRankingInCategory());
			sqlMapClient.insert("insertGoodsCaegoryRelation", map);			
		}
	}

	@Override
	public int updateBookByTitleAndAuthor(String title, String author) throws SQLException {
		List<Book> bookList = new ArrayList<Book>();
		int bookId = 0;
		
		bookList = sqlMapClient.queryForList("getBookByTitle", title);
		if (bookList.size() != 0) {
			for (Book b : bookList) {
//				System.out.println("In : " + b);
				if (author != null && b.getAuthor() != null && b.getAuthor().contains(author)) {
					count++;
					bookId = b.getId();
					System.out.println("SUCCESS : " + b.getId());
					break;
				} 
			}
		}
		return bookId;
	}

	/* (non-Javadoc)
	 * @see com.challengr.dao.IBookDAO#getBookListByAuthor()
	 */
	@Override
	public List<Book> getBookListByAuthor(String author) throws SQLException {
		return sqlMapClient.queryForList("getBookListByAuthor", author);
	}
	

}
