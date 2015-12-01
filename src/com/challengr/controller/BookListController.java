package com.challengr.controller;

import com.challengr.service.IBookListService;
import com.challengr.service.SearchResultsListService;

public class BookListController implements IController {

	private final IBookListService bookListService;
	
	public BookListController() {
		bookListService = new SearchResultsListService();
	}
	
	@Override
	public void before() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void after() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void control() {
		bookListService.updateBookList();
		
	}

}
