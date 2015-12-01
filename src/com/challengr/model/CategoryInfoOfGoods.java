package com.challengr.model;


//商品的分类属性：商品在分类中的排名�?�（这里不是商品分类，�?�是商品在其分类中表现出的属性）
public class CategoryInfoOfGoods {
	private int categoryId; //商品分类
	private int rankingInCategory;
	private String categoryName; // 分类名称
	
	public CategoryInfoOfGoods(){}
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public CategoryInfoOfGoods(int categoryId,int rankingInCategory){
		this.categoryId = categoryId;
		this.setRankingInCategory(rankingInCategory);
	}
	public int getCategoryId(){
		return categoryId;
	}
	public int getRankingInCategory(){
		return rankingInCategory;
	}
	public void setRankingInCategory(int rankingInCategory) {
		this.rankingInCategory = rankingInCategory;
	}
}
