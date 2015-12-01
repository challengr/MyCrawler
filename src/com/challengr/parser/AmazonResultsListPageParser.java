package com.challengr.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.challengr.model.Book;
import com.challengr.model.GoodsCategory;

public class AmazonResultsListPageParser extends PageContentParser {
	private GoodsCategory goodsCategory;
	
	public AmazonResultsListPageParser(GoodsCategory goodsCategory){
		this.goodsCategory = goodsCategory;
	}
	
	public AmazonResultsListPageParser() {}
	
	@Override
	public Object parse(String content) {
		try {
			Map<String, Object> parseResult = new HashMap<String, Object>();
			
			//第一步：查找当前页中商品列表
			ArrayList<Book> bookList = new ArrayList<Book>();            // 图书列表中的书
//			ArrayList<String> thumbnailList = new ArrayList<String>();   // 图书列表中的图书缩略图 
			//获取列表中每本书的result编号
			final String PF_RecordRangeStart = "<h2 id=\"s-result-count\" class=\"a-size-base a-spacing-small a-spacing-top-small a-text-normal\">显示： ";
			final String PF_RecordRangeHyphen = "-";
			final String PF_RecordRangeEnd = "条";
			int pos1 = content.indexOf(PF_RecordRangeStart);
			int pos2,pos3;
			int startResultIndex,endResultIndex;
			
//			String thumbnailImgUrl;
			//若当前分类的列表总数量小于16条，列表页中不会出现"显示：xxx-yyy"的标示，而是“显示所有xxx个结果”
			if (pos1==-1){
				final String[] PF_RecordRangeStart_SinglePage = {"<h2 id=\"s-result-count\" class=\"a-size-base a-spacing-small a-spacing-top-small a-text-normal\">显示：",
						" "};
				final String PF_RecordRangeStart_SinglePage_suffix = " 个结果";
				endResultIndex = Integer.parseInt(getItem(content, PF_RecordRangeStart_SinglePage, PF_RecordRangeStart_SinglePage_suffix));
				startResultIndex = 1;				
//				final String PF_RecordRangeStart_SinglePage = "<h2 class=\"resultCount\" id=\"resultCount\"><span>显示所有 ";
//				final String PF_RecordRangeEnd_SinglePage = " 个结果";
//				pos1 = content.indexOf(PF_RecordRangeStart_SinglePage);
//				pos1 += PF_RecordRangeStart_SinglePage.length();
//				pos2 = content.indexOf(PF_RecordRangeEnd_SinglePage, pos1);
//				startResultIndex = 1;
//				endResultIndex = Integer.parseInt(content.substring(pos1, pos2));			
			}else{
				pos1 += PF_RecordRangeStart.length();
				pos2 = content.indexOf(PF_RecordRangeHyphen, pos1);
				pos3 = content.indexOf(PF_RecordRangeEnd, pos2);				
				startResultIndex = Integer.parseInt(content.substring(pos1, pos2));
				endResultIndex = Integer.parseInt(content.substring(pos2+1, pos3));
			}
			//根据result编号查找每本书的连接信息
			final String PF_ResultIndex = "<li id=\"result_";
			final String PF_ResultIndex_suffix = "\"";
			final String PF_ResultHref = "<a class=\"a-link-normal a-text-normal\" target=\"_blank\" href=\"";
			final String PF_ResultHrefSuffix = "\">";
			
			// 解析pageList中图书的缩略图
//			String PF_ThumbnailPrefix = "src=\"";
//			String PF_ThumbnailSuffix = "\"";
			
			for(int i=startResultIndex-1;i<=endResultIndex-1;i++){
				Book book = new Book();
				pos1 = content.indexOf(PF_ResultIndex + i + PF_ResultIndex_suffix);
				pos2 = content.indexOf(PF_ResultHref, pos1) + PF_ResultHref.length();
				pos3 = content.indexOf(PF_ResultHrefSuffix, pos2);
				book.setUriString(content.substring(pos2, pos3));
				
//				book.setCategoryInfo(goodsCategory.getId(), i);
if (book.getUriString().length()>1024){
System.out.println("UriString超长！");
}
//				System.out.println(book.getUriString());
				bookList.add(book);
				
//				pos2 = content.indexOf(PF_ThumbnailPrefix, pos1) + PF_ThumbnailPrefix.length();
//				pos3 = content.indexOf(PF_ThumbnailSuffix, pos2);
//				thumbnailImgUrl = content.substring(pos2, pos3);
//System.out.println(thumbnailImgUrl);				
//				thumbnailList.add(thumbnailImgUrl);
			}
			parseResult.put("bookListInPage", bookList);
//			parseResult.put("thumbnailList", thumbnailList);   // 将缩略图的Url打包放入parseResult中
			//第二步：查找下一页地址
			final String[] PF_NextPage_prefixes = {"<span class=\"pagnCur\">",
					" <span class=\"pagnLink\">",
					"<a href=\""};
			final String PF_NextPage_suffix = "\" >";
			String nextPageUri = getItem(content, PF_NextPage_prefixes, PF_NextPage_suffix);
//System.out.println("nextPageUri : " + nextPageUri);			
			if (nextPageUri!=null)
				parseResult.put("nextPageUriString", nextPageUri);
			
			//第三步：查找下一页编号
			final String[] PF_NextPageNo_prefixes = {"<span class=\"pagnCur\">",
					" <span class=\"pagnLink\">",
					"<a href=\"",
					"/ref=sr_pg_"};
			final String PF_NextPageNo_suffix = "/";
			String nextPageNo = getItem(content, PF_NextPageNo_prefixes, PF_NextPageNo_suffix);
System.out.println("nextPageNo. : " + nextPageNo);			
			if (nextPageNo!=null)
				parseResult.put("nextPageNo", nextPageNo);
			
			//第四步：查找分页数量
			final String[] PF_PageCount_prefixes = {"<span class=\"pagnDisabled\">"};
			final String PF_PageCount_suffix = "</span>";
			String pageCount = getItem(content, PF_PageCount_prefixes, PF_PageCount_suffix);
System.out.println("pageCount :　" + pageCount);			
			if (pageCount!=null)
				parseResult.put("pageCount", Integer.valueOf(pageCount));
			
			return parseResult;
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
}
