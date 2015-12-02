package com.challengr.model;

import java.util.ArrayList;

/**
 * 商品分类
 */
public class GoodsCategory {
	public static final int PARENT_ID_OF_ROOT_NODE = 0;
	public static final int INVALID_ID = -2;
	public static final String NAME_OF_ROOT_NODE = "图书";
	private int id;
	private int parentId;
	private String name;
	private String uriString;
	private int goodsCount;
	
	//以下两个成员变量不从数据库中直接读取，�?�是在dao层�?�过递归计算来构�?
	//减少数据库计�?
	private GoodsCategory parent;
	private ArrayList<GoodsCategory> children;	
	
	public GoodsCategory() {
		id = INVALID_ID;
		children = new ArrayList<GoodsCategory>();
	};
	
	public boolean isLeaf() {
		return children.size()==0;
	}

	public boolean isRoot() {
		return parentId==PARENT_ID_OF_ROOT_NODE;
	}
	
	public void addChild(GoodsCategory newGoodsCategory) {
		for(int i=0;i<children.size();i++){
			if (children.get(i).getName().equals(newGoodsCategory.getName())){
				return;
			}
		}
		children.add(newGoodsCategory);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUriString() {
		return uriString;
	}

	public void setUriString(String uriString) {
		this.uriString = uriString;
	}

	public GoodsCategory getParent() {
		return parent;
	}

	public void setParent(GoodsCategory parent) {
		this.parent = parent;
	}

	public ArrayList<GoodsCategory> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<GoodsCategory> children) {
		this.children = children;
	}

	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}
	
	
}
