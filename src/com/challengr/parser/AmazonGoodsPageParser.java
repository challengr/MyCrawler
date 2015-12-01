package com.challengr.parser;

import com.challengr.model.Book;
import com.challengr.model.CategoryInfoOfGoods;
import com.challengr.model.Goods;
import com.challengr.model.GoodsCategory;

public class AmazonGoodsPageParser extends PageContentParser {

	@Override
	public Object parse(String content, CategoryInfoOfGoods category) {
		// 用来做定位指针
		int pos1;
		int pos2;
		int pos3;
		int pos4;
		Book book = new Book();
		//获取书名
		final String PF_Title_prefix = "<div class=\"ma-title\"><p class=\"wraptext goto-top\">";
		final String PF_Title_suffix = " <";
		String title;
		
		title = getItem(content,PF_Title_prefix,PF_Title_suffix);
		
		// 对title经行转码
		if (title == null) {
			title = getItem(content, "<span id=\"productTitle\" class=\"a-size-large\">", "</span>");
			if (title != null)
				title = revocerAmazonES(title);
		}
		
		if(null != title) {
			book.setTitle(title);
		} 
					
		//获取装订类型
		String typeString;
		final String PF_Type_prefix = "丛书名:";
		final String PF_Type_suffix = ":</b>";
		pos1 = content.indexOf(PF_Type_prefix, 0);
		pos2 = content.indexOf("<li><b>", pos1) + "<li><b>".length();
		pos3 = content.indexOf(PF_Type_suffix, pos2);
		if ((pos3 - pos2) < 0) {
			typeString = null;
		} else {
			typeString = content.substring(pos2, pos3);
		} 
		book.setType(formatHTMLString(typeString));
		
		//获取作者
		final String PF_Author_start = "<span class=\"author notFaded\" data-width=\"\">";
		final String PF_Author_prefixs = "search-alias=books\">";
		final String PF_Author_suffix = "</a>";
		book.setAuthor(formatHTMLString(Parser.between(content, PF_Author_start, PF_Author_prefixs, PF_Author_suffix)));
		
		//获取原价
		final String PF_ListPrice_prefix = "<span class=\"a-color-secondary\">市场价:</span> <span class=\"a-color-secondary a-text-strike\">";
		final String PF_ListPrice_suffix = "</span>";
		book.setListPrice(formatHTMLString(getItem(content,PF_ListPrice_prefix,PF_ListPrice_suffix)));
		
		//获取实际销售价格
		final String PF_ActualPrice_prefix = "<span class=\"a-size-medium a-color-price inlineBlock-display offer-price a-text-normal price3P\">";
		final String PF_ActualPrice_suffix = "</span>";
		book.setActualPrice(formatHTMLString(getItem(content,PF_ActualPrice_prefix,PF_ActualPrice_suffix)));
		
		//获取出版社信息
		final String PF_Publisher_prefix = "<li><b>出版社:</b>";
		final String PF_Publisher_suffix = "</li>";
		book.setPublisher(formatHTMLString(getItem(content, PF_Publisher_prefix, PF_Publisher_suffix)));		
		
		//获取丛书名
		String bookSetName;
		final String PF_BookSetName_prefix = "<li><b>丛书名:</b>";
		final String PF_BookSetName_suffix = "</a></li>";
		pos1 = content.indexOf(PF_BookSetName_prefix, 0);
		pos2 = content.indexOf("<a href=\"", pos1);
		pos3 = content.indexOf("\">", pos2) + "\">".length();
		pos4 = content.indexOf(PF_BookSetName_suffix, pos3);
		if ((pos4 - pos3) <= 0 || (pos4 - pos3) >= 100) {
			bookSetName = null;
		} else {
			bookSetName = content.subSequence(pos3, pos4).toString();
		}
		book.setBookSetName(formatHTMLString(bookSetName));

		//获取页数
		//final String PF_Pages_prefix = "<li><b>" + book.getType() + ":</b>";
		final String PF_Pages_prefix = "装:</b>";
		final String PF_Pages_suffix = "页</li>";
		book.setPages(formatHTMLString(getItem(content, PF_Pages_prefix, PF_Pages_suffix)));
		
		//获取语种
		final String PF_Language_prefix = "<li><b>语种：</b>";
		final String PF_Language_suffix = "</li>";
		book.setLanguage(formatHTMLString(getItem(content,PF_Language_prefix,PF_Language_suffix)));
		
		//获取开本
		final String PF_Size_prefix = "<li><b>开本:</b>";
		final String PF_Size_suffix = "</li>";
		book.setSize(formatHTMLString(getItem(content,PF_Size_prefix,PF_Size_suffix)));

		//获取ISBN
		final String PF_ISBN_prefix = "<li><b>ISBN:</b>";
		final String PF_ISBN_suffix = "</li>";
		book.setISBN(formatHTMLString(getItem(content,PF_ISBN_prefix,PF_ISBN_suffix)));
		
		//获取条形码
		final String PF_BarCode_prefix = "<li><b>条形码:</b>";
		final String PF_BarCode_suffix = "</li>";
		book.setBarCode(formatHTMLString(getItem(content,PF_BarCode_prefix,PF_BarCode_suffix)));
		
		//获取商品尺寸
		final String PF_Dimension_prefix = "<li><b>\n    商品尺寸: \n    </b>\n";
		final String PF_Dimension_suffix = "\n    </li>";
		book.setDimensions(formatHTMLString(getItem(content,PF_Dimension_prefix,PF_Dimension_suffix)));
		
		//获取商品重量
		final String PF_Weight_prefix = "<li><b>\n    商品重量: \n    </b>\n";
		final String PF_Weight_suffix = "\n    </li>";
		book.setWeight(formatHTMLString(getItem(content,PF_Weight_prefix,PF_Weight_suffix)));
		
		//获取品牌
		final String PF_Brand_prefix = "<li><b>品牌:</b>";
		final String PF_Brand_suffix = "</li>";
		book.setBrand(formatHTMLString(getItem(content,PF_Brand_prefix,PF_Brand_suffix)));
		
		//获取ASIN
		final String PF_ASIN_prefix = "<li><b>ASIN: </b>";
		final String PF_ASIN_suffix = "</li>";
		book.setASIN(formatHTMLString(getItem(content,PF_ASIN_prefix,PF_ASIN_suffix)));

		//获取在分类中的排名
		final String PF_RANKING_prefix = "图书商品里排第";
		final String PF_RANKING_suffix = "名";
		String rankingStr = getItem(content,PF_RANKING_prefix,PF_RANKING_suffix);
		if (rankingStr!=null){
			rankingStr = rankingStr.replaceAll(",", "");
			book.setCategoryInfo(category.getCategoryId(), Integer.parseInt(rankingStr));
		}
		
		return book;
	}
	
	public Object parseRanking(String content, Goods goods, GoodsCategory cateogry) {
		
		//获取在分类中的排名
		final String PF_RANKING_prefix = "图书商品里排第";
		final String PF_RANKING_suffix = "名";
		String rankingStr = getItem(content,PF_RANKING_prefix,PF_RANKING_suffix);
		if (rankingStr!=null){
			rankingStr = rankingStr.replaceAll(",", "");
			goods.setCategoryInfo(cateogry.getId(), Integer.parseInt(rankingStr));
		}
				
		return goods;
	}
	
}
