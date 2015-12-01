package com.challengr.dao;

import java.sql.SQLException;
import java.util.List;

import com.challengr.model.GoodsCategory;

public interface IGoodsCategoryDAO {
	List<GoodsCategory> getAllGoodsCategory() throws SQLException;
	int insertGoodsCategory(GoodsCategory goodsCategory) throws SQLException;
	GoodsCategory getGoodsCategoryByName(String name) throws SQLException;
	GoodsCategory getGoodsCategorySubTree(GoodsCategory category) throws SQLException;
	GoodsCategory getGoodsCategoryByNameAndParentId(GoodsCategory goodsCategory)
			throws SQLException;
}
