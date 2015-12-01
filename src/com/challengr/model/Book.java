package com.challengr.model;

public class Book extends Goods{
	private String title; //书名
	private String author; //作者
	private String listPrice; //市场价格
	private String actualPrice; //实际售价
	private String publisher; //出版社
	private String bookSetName; //丛书
	private String type; //平装
	private String language; //语种
	private String size; //纸张大小
	private String ISBN; //ISBN
	private String barCode; //条形码
	private String dimensions; //商品尺寸: 25.4 x 18.2 x 1.8 cm
	private String weight; //商品重量: 540 g
	private String brand; //品牌: 中国人民大学出版社
	private String ASIN; //ASIN: B004OQHOR8
	private String pages; //页码
	private String imgUrl1;   // s
	private String imgUrl2; // l
	
	public String getImgUrl1() {
		return imgUrl1;
	}
	public void setImgUrl1(String imgUrl1) {
		this.imgUrl1 = imgUrl1;
	}
	public String getImgUrl2() {
		return imgUrl2;
	}
	public void setImgUrl2(String imgUrl2) {
		this.imgUrl2 = imgUrl2;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getListPrice() {
		return listPrice;
	}
	public void setListPrice(String listPrice) {
		this.listPrice = listPrice;
	}
	public String getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(String actualPrice) {
		this.actualPrice = actualPrice;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getBookSetName() {
		return bookSetName;
	}
	public void setBookSetName(String bookSetName) {
		this.bookSetName = bookSetName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getASIN() {
		return ASIN;
	}
	public void setASIN(String aSIN) {
		ASIN = aSIN;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public String getPages() {
		return pages;
	}
	
	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", listPrice="
				+ listPrice + ", actualPrice=" + actualPrice + ", publisher="
				+ publisher + ", bookSetName=" + bookSetName + ", type=" + type
				+ ", language=" + language + ", size=" + size + ", ISBN="
				+ ISBN + ", barCode=" + barCode + ", dimensions=" + dimensions
				+ ", weight=" + weight + ", brand=" + brand + ", ASIN=" + ASIN
				+ ", pages=" + pages + ", imgUrl1=" + imgUrl1 + ", imgUrl2="
				+ imgUrl2 + "]";
	}	
	
	/*
	@Override
	public String toString() {
		return "Book [title=" + title + ", uriString=" + getUriString() + "]";
	}
	*/
	
}
