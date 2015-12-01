package com.challengr.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Goods {
	//�?大goods id号，表达无穷大即数据库中id无上限制
	public static int MAX_GOODS_ID=999999999;
	private int id;
	private String uriString; //商品页面地址链接
	private String name; //商品名称

	private List<CategoryInfoOfGoods> categoryInfoOfGoodsList = new ArrayList<CategoryInfoOfGoods>();
	
	public void setCategoryInfo(int categoryId, int rankingInCategory) {
		int i=0;
		for (;i<categoryInfoOfGoodsList.size();i++) {
			CategoryInfoOfGoods categoryInfoOfGoods = categoryInfoOfGoodsList.get(i);
			if (categoryInfoOfGoods.getCategoryId()==categoryId){
				break;
			}
		}
		if (i==categoryInfoOfGoodsList.size()){
			categoryInfoOfGoodsList.add(new CategoryInfoOfGoods(categoryId,rankingInCategory));
		}else{
			categoryInfoOfGoodsList.get(i).setRankingInCategory(rankingInCategory);
		}
	}
	
	public List<CategoryInfoOfGoods> getCategoryInfoOfGoods() {
		return categoryInfoOfGoodsList;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUriString() {
		return uriString;
	}
	public void setUriString(String uriString) {
		this.uriString = uriString;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
