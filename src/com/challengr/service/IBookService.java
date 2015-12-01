package com.challengr.service;

import com.challengr.model.CategoryInfoOfGoods;


public interface IBookService {
	/**
	 * 下载html文件
	 */
	public void downLoadHTML();
	/**
	 * 解析网页，并保存信息
	 * @param content
	 */
	Object parse(String content, CategoryInfoOfGoods category);
	/**
	 * 更新信息
	 */
	void update(CategoryInfoOfGoods category, String dumpDir);
	
}
