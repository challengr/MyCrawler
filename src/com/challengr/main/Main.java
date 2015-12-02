package com.challengr.main;


import com.challengr.controller.MatchingBookController;
import com.challengr.model.CategoryInfoOfGoods;
import com.challengr.pub.SystemConfig;
import com.challengr.service.BookService;

public class Main {
	
	public static void main(String[] args) {

		CategoryInfoOfGoods category = new CategoryInfoOfGoods();
		category.setCategoryId(SystemConfig.CATEGORY_ID_TEXTBOOK);
		category.setCategoryName(SystemConfig.CATEGORY_NAME_TEXTBOOK);
		BookService bookService;
		bookService = new BookService();

		// 更新图书详情
		// 设置图书的分类
		// 健美健身(1-13063)
		// 人文社科（13064-21055）
		// 经济管理（21056-89928）_part1 (&quot; 没有去掉)
		// 经济管理（89929-95209）_part2 
		// 工业技术（95210-143271）计算机与互联网goods
		// 自然科学 
		// 医学
		// 辞典与工具书
		// 成功与励志
		// 法律
		// 教材教辅
		
		// 解析书目的详细页面
//		bookService.update(category, SystemConfig.TEXTBOOKS_DIR);
		
		// 下载并解析农业类的图书
//		BookListService.newInstance().updateBookList();
		
		/*
		// 下载有搜索结果的书单
		BookListController bookListController = new BookListController();	
		bookListController.control();	
		*/
		
		// 完成书单的书和book表中书的对应
		MatchingBookController matching = new MatchingBookController();
		matching.control();

	} 
	
	
}
