package com.challengr.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class BookListService implements IBookListService {
	private static final List<String> bookListUri = new LinkedList<String>();;
	
	private static BookListService INSTANCE;
	
	private BookListService() {
		bookListUri.add("http://www.amazon.cn/s/ref=sr_pg_1?fst=as%3Aoff&rh=n%3A658390051%2Cn%3A658399051%2Cn%3A658588051%2Cn%3A659723051%2Cn%3A663494051%2Ck%3A%E5%86%9C%E4%B8%9A&page=1");
		bookListUri.add("http://www.amazon.cn/s/ref=sr_pg_1?fst=as%3Aoff&rh=n%3A658390051%2Cn%3A663494051%2Ck%3A%E5%86%9C%E4%B8%9A&page=1");
		bookListUri.add("http://www.amazon.cn/s/ref=sr_pg_1?fst=as%3Aoff&rh=n%3A658390051%2Cn%3A658399051%2Cn%3A658588051%2Cn%3A659723051%2Cn%3A663492051%2Ck%3A%E5%86%9C%E4%B8%9A&page=1");
		bookListUri.add("http://www.amazon.cn/s/ref=sr_pg_1?fst=as%3Aoff&rh=n%3A658390051%2Cn%3A658431051%2Cn%3A659097051%2Cn%3A662229051%2Cn%3A664230051%2Ck%3A%E5%86%9C%E4%B8%9A&page=1");
		bookListUri.add("http://www.amazon.cn/s/ref=sr_pg_1?fst=as%3Aoff&rh=n%3A658390051%2Cn%3A658399051%2Cn%3A658588051%2Cn%3A659723051%2Cn%3A663493051%2Ck%3A%E5%86%9C%E4%B8%9A&page=1");
		bookListUri.add("http://www.amazon.cn/s/ref=sr_pg_1?fst=as%3Aoff&rh=n%3A658390051%2Cn%3A658399051%2Cn%3A658588051%2Cn%3A659723051%2Cn%3A663493051%2Ck%3A农业&page=1");
		bookListUri.add("http://www.amazon.cn/s/ref=sr_pg_1?fst=as%3Aoff&rh=n%3A658390051%2Cn%3A658399051%2Cn%3A658588051%2Cn%3A659723051%2Cn%3A663491051%2Ck%3A%E5%86%9C%E4%B8%9A&page=1");
		bookListUri.add("http://www.amazon.cn/s/ref=sr_pg_1?fst=as%3Aoff&rh=n%3A658390051%2Cn%3A658431051%2Cn%3A659097051%2Cn%3A662234051%2Cn%3A664255051%2Ck%3A农业&page=1");
		bookListUri.add("http://www.amazon.cn/s/ref=sr_pg_1?fst=as%3Aoff&rh=n%3A658390051%2Cn%3A658431051%2Cn%3A659097051%2Cn%3A662239051%2Cn%3A664270051%2Ck%3A农业&page=1");
		bookListUri.add("http://www.amazon.cn/s/ref=sr_pg_1?fst=as%3Aoff&rh=n%3A658390051%2Cn%3A658399051%2Cn%3A658588051%2Cn%3A659723051%2Cn%3A663488051%2Ck%3A农业&page=1");
	}
	
	public static BookListService newInstance() {
		if(INSTANCE == null) {
			INSTANCE = new BookListService(); 
		}
		return INSTANCE;
	}
	
	@Override
	public void updateBookList() {
		for(String uri : bookListUri) {
			try {
				new AmazonCollectServiceImpl().updateAgriculturalGoodsByCategory(uri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
