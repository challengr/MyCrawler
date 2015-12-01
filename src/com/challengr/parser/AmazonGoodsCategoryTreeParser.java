package com.challengr.parser;

import com.challengr.model.GoodsCategory;
import com.challengr.util.StringUtil;

public class AmazonGoodsCategoryTreeParser extends PageContentParser {
	final String HOST_URI = "http://www.amazon.cn";
	@Override
	public GoodsCategory parse(String content, Object params) {
		
		GoodsCategory parentCategory = (GoodsCategory)params;
		String pageFlag = parentCategory.getName();
		System.out.println("**开始收集：" + pageFlag + " 的子分类**");
		//筛选出从当前父分类到分类树结束之间的内容，即已当前父分类的整个子树内容
		/*String PF_Range_suffix = "</ul>";
		String targetContent;
		if (!pageFlag.equals("图书")){
			String[] PF_Range_prefixes = {"<h2>类别</h2>",
					"<span class=\"expand\">图书</span>",
					"<strong>"+pageFlag+"</strong>"};
			targetContent = getItem(content, PF_Range_prefixes, PF_Range_suffix);
		}
		else{
			String[] PF_Range_prefixes = {"<h2>类别</h2>",
									"<strong>"+pageFlag+"</strong>"};
			targetContent = getItem(content, PF_Range_prefixes, PF_Range_suffix);
		}
*/
		//筛选出从当前父分类到分类树结束之间的内容，即已当前父分类的整个子树内容
		String PF_Range_suffix = "</ul>";
		String targetContent;
		if (!pageFlag.equals("图书")){
			String[] PF_Range_prefixes = {"<strong>"+pageFlag+"</strong>"};
			targetContent = getItem(content, PF_Range_prefixes, PF_Range_suffix);
		}
		else{
			String[] PF_Range_prefixes = {"<strong>"+pageFlag+"</strong>"};
			targetContent = getItem(content, PF_Range_prefixes, PF_Range_suffix);
		}
System.out.println("targetContent : " + targetContent);
		//获取一个子分类的href
		final String[] PF_SubCategoryUri_prefixes = {
				"<li ",
				"<a href=\""};
		final String PF_SubCategoryUri_suffix = "\">";
		final String PF_SubCategoryName_prefixes = "<span class=\"refinementLink\">";	
		final String PF_SubCategoryName_suffix = "</span>";
		final String PF_SubCategoryGoodsCount_prefixes = "<span class=\"narrowValue\">&nbsp;(";	
		final String PF_SubCategoryGoodsCount_suffix = ")</span>";		
		final String PF_SubCategory_suffix = "</li>";
		//循环获取每一个子分类
//		int i = 1;
		while(targetContent.indexOf(PF_SubCategoryUri_prefixes[0]) != -1){
//System.out.println("category index : " + i++);		
			GoodsCategory goodsCategory = new GoodsCategory();
			// 对分类页的uri进行处理
			String oldURI = HOST_URI + getItem(targetContent,PF_SubCategoryUri_prefixes,PF_SubCategoryUri_suffix);
			String newURI = oldURI.replace("&amp;", "&");	
			String latestURI = newURI.replace("475-3787078-1516405?", "?");			
			goodsCategory.setUriString(latestURI);

//System.out.println("targetURI : " + latestURI);
			goodsCategory.setName(getItem(targetContent,PF_SubCategoryName_prefixes,PF_SubCategoryName_suffix));
			goodsCategory.setGoodsCount(Integer.parseInt(StringUtil.trimAllWhiteSpace(getItem(targetContent,PF_SubCategoryGoodsCount_prefixes,PF_SubCategoryGoodsCount_suffix))));
			goodsCategory.setParent(parentCategory);
			//这句奇怪的语句用于保证跳到下一个li
			targetContent = targetContent.substring(targetContent.indexOf(PF_SubCategoryName_prefixes));
					targetContent=	targetContent.substring(targetContent.indexOf(PF_SubCategory_suffix)+PF_SubCategory_suffix.length());
			System.out.println(goodsCategory.getName() + " " + goodsCategory.getGoodsCount() + " " + goodsCategory.getUriString());
			parentCategory.addChild(goodsCategory);
		}
		System.out.println("**结束收集：" + pageFlag + " 的子分类 **");
		System.out.println();
		return parentCategory;
	}
}