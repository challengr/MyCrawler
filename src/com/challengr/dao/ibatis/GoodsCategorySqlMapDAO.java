package com.challengr.dao.ibatis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.challengr.dao.IGoodsCategoryDAO;
import com.challengr.dao.MyDAO;
import com.challengr.model.GoodsCategory;

public class GoodsCategorySqlMapDAO extends MyDAO implements IGoodsCategoryDAO {

	public GoodsCategorySqlMapDAO() throws IOException {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsCategory> getAllGoodsCategory() throws SQLException {
		return sqlMapClient.queryForList("getAllGoodsCategory");
	}
	
	@Override
	public GoodsCategory getGoodsCategorySubTree(GoodsCategory category) throws SQLException {
		List<GoodsCategory> goodsCategoryList = getAllGoodsCategory();
		return buildTreeFromList(category, goodsCategoryList);
	}
	
	private GoodsCategory buildTreeFromList(GoodsCategory parent, List<GoodsCategory> goodsCategoryList) {
		for (GoodsCategory goodsCategory : goodsCategoryList){
			if (goodsCategory.getParentId() == parent.getId()){
				parent.addChild(buildTreeFromList(goodsCategory, goodsCategoryList));
				goodsCategory.setParent(parent);
			}
		}
		return parent;
	}

	@Override
	public int insertGoodsCategory(GoodsCategory goodsCategory) throws SQLException {
		return (Integer)sqlMapClient.insert("insertGoodsCategory", goodsCategory);
	}

	@Override
	public GoodsCategory getGoodsCategoryByName(String name) throws SQLException {
		return (GoodsCategory)sqlMapClient.queryForObject("getGoodsCategoryByName", name);
	}
	
	@Override
	public GoodsCategory getGoodsCategoryByNameAndParentId(GoodsCategory goodsCategory) throws SQLException {
		return (GoodsCategory)sqlMapClient.queryForObject("getGoodsCategoryByNameAndParentId", goodsCategory);
	}
}
