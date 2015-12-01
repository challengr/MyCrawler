package com.challengr.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.challengr.dao.ibatis.BookSqlMapDAO;
import com.challengr.model.Book;
import com.challengr.parser.AmazonResultsListPageParser;
import com.challengr.pub.SystemConfig;
import com.challengr.util.IOUtil;

public class AmazonCollectServiceImpl implements ICollectService {
	private static int count = 1000;
	
	public AmazonCollectServiceImpl() throws IOException {
	
	}
	
	/**
	 * 访问分类的商品列表页，并更新分类中商品的基本信息：商品页链接，商品名称，出版社，价格
	 */
	@SuppressWarnings("unchecked") 
	public void updateAgriculturalGoodsByCategory(String bookUri){
		try {
			
			String nextPageUriString = bookUri;
			String nextPageNo = "1";
			String listPage = null;
			ArrayList<Book> bookList = null;
			Map<String, Object> map = null;
			
			while(nextPageNo!=null){
				listPage = "H:\\goods_list" + "\\" + SystemConfig.CATEGORY_ID_AGRICULTURAL + 
						"_" + SystemConfig.CATEGORY_NAME_AGRICULTURAL + "_" + nextPageNo + ".html";

				//如果商品列表页面还未下载，则下载；否则直接读取本地缓存文件
				File file=new File(listPage);
				if(!file.exists()){
//					System.out.println("下载远端商品列表页：" + nextPageUriString);
//					System.out.println("保存至本地商品列表页："+ listPage);
//					System.out.println();				
					//下载服务器端页面
					IOUtil.saveURIToFile(nextPageUriString, listPage); 
				}
				
				//解析商品列表页面
				map = (Map<String, Object>) (new AmazonResultsListPageParser()).parse(IOUtil.convertUTFFileToString(listPage));
				bookList = (ArrayList<Book>) map.get("bookListInPage");
				nextPageNo = (String)map.get("nextPageNo");
				nextPageUriString = createStandaloneUriOfListPage(nextPageNo, bookUri);
				
System.out.println("nextPageUriString " + nextPageUriString);				
				//保存商品（此时的商品对象仅包含分类中的商品基本信息）
				for(Book book : bookList) {
					Thread.sleep(300);
					IOUtil.saveURIToFile(book.getUriString(), "H:\\goods" + "\\" + count++ + ".html");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	private String createStandaloneUriOfListPage(String pageNo, String baseUri) {
		String delimit1 = "ref=sr_pg_";
		String delimit2 = "?";
		int pos1 = baseUri.indexOf(delimit1);
		int pos2 = baseUri.indexOf(delimit2, pos1+delimit1.length());
		String prefix = baseUri.substring(0,pos2);
		int pos3 = prefix.lastIndexOf("_");
		int pos4 = baseUri.lastIndexOf("page=");

		return baseUri.substring(0,pos3+1) 
			 + pageNo 
			 + baseUri.substring(pos2, pos4) 
			 + "page="
			 + pageNo;
		
	}
	
	@Override
	public void updateGoodsSummaryByRootCategory() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGoodsSummaryByCategoryName(String rootCategoryName,
			int parentId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGoodsDetailByGoodsUri(int fromGoodsId, int toGoodsId)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGoodsDetailByGoodsUri() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGoodsRankingInCategorySubTree(
			String nameOfRootOfCategorySubtree, int parentId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGoodsOnFly(String nameOfCategory, int parentId)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
