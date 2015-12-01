package com.challengr.service;


public interface ICollectService {
	public final static int SITE_AMAZON = 0;
	public final static int SITE_DANGDANG = 1;
	void updateGoodsSummaryByRootCategory() throws Exception;
	void updateGoodsSummaryByCategoryName(String rootCategoryName, int parentId)
			throws Exception;
	void updateGoodsDetailByGoodsUri(int fromGoodsId, int toGoodsId)
			throws Exception;
	public void updateGoodsDetailByGoodsUri() throws Exception;
	void updateGoodsRankingInCategorySubTree(
			String nameOfRootOfCategorySubtree, int parentId) throws Exception;
	void updateGoodsOnFly(String nameOfCategory, int parentId) throws Exception;
	
}