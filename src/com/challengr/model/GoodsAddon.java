package com.challengr.model;

public class GoodsAddon {
	private int id;
	private int goodsId;
	private String title;	//可能和对应goods的title不同，因为来自不同源
	private int fromSite;
	private String uriString; //可能和对应goods的title不同，因为来自不同源
	private String imgUriString1; //商品图标uri1
	private String imgUriString2; //商品图标uri2
	private String imgUriString3; //商品图标uri3
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getFromSite() {
		return fromSite;
	}
	public void setFromSite(int fromSite) {
		this.fromSite = fromSite;
	}
	public String getUriString() {
		return uriString;
	}
	public void setUriString(String uriString) {
		this.uriString = uriString;
	}
	public String getImgUriString1() {
		return imgUriString1;
	}
	public void setImgUriString1(String imgUriString1) {
		this.imgUriString1 = imgUriString1;
	}
	public String getImgUriString2() {
		return imgUriString2;
	}
	public void setImgUriString2(String imgUriString2) {
		this.imgUriString2 = imgUriString2;
	}
	public String getImgUriString3() {
		return imgUriString3;
	}
	public void setImgUriString3(String imgUriString3) {
		this.imgUriString3 = imgUriString3;
	}
	
}
