package com.challengr.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.challengr.dao.IBookDAO;
import com.challengr.dao.ibatis.BookSqlMapDAO;
import com.challengr.model.Book;
import com.challengr.model.CategoryInfoOfGoods;
import com.challengr.parser.AmazonGoodsPageParser;
import com.challengr.parser.PageContentParser;
import com.challengr.pub.SystemConfig;
import com.challengr.util.IOUtil;
import com.challengr.util.ImageUtil;

public class BookService implements IBookService {

	@Override
	public Object parse(String content, CategoryInfoOfGoods category) {
		// 解析图书页面
		return new AmazonGoodsPageParser().parse(content, category);
	}

	@Override
	public void update(CategoryInfoOfGoods category, String srcDir) {
		File fileDir;
		File[] fileList;
		int count;
		Book book;
		BookSqlMapDAO bookSqlMapDao = null;

		fileDir = new File(srcDir);
		fileList = fileDir.listFiles();
		try {
			bookSqlMapDao = new BookSqlMapDAO();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		count = 0;
		
		// 创建图片保存的目录
		createImageDir(SystemConfig.DOWNLOAD_PATH_ROOT + category.getCategoryName());
		for (File file : fileList) {
			// 打印进度
			if (count % 100 == 0) {
			System.out.println("图书信息解析已完成" + 
							String.format("%.2f", count * 100.0 / fileList.length * 1.0) + "%");
			}
			count++;

			try {
				book = (Book) parse(IOUtil.convertFileToString(
						file.getAbsolutePath(), SystemConfig.AMAZON_CHARSET), category);
				if (formatBookData(book)) {
					
					continue;
				}
				// 更新书目的图片信息
				book = (Book) updateImage(IOUtil.convertFileToString(file.getAbsolutePath(), 
													   SystemConfig.AMAZON_CHARSET), book, category);
//				Thread.sleep(new Random().nextInt(150) + 200);
				// 将book插入tab_book表中
				bookSqlMapDao.insertBook(book);
				if(book.getId()!=0) {
					// 更新book和category的关系
					bookSqlMapDao.insertGoodsCaegoryRelation(book);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}

	}
	
	private void createImageDir(String imgDumpDir) {
		File dir = new File(imgDumpDir);
		// 创建大图保存目录
		File img_l_dir = new File(imgDumpDir + SystemConfig.DOWNLOAD_PATH_BOOK_IMG_L.replace("l\\", "l"));
		// 创建小图保存目录
		File img_s_dir = new File(imgDumpDir + SystemConfig.DOWNLOAD_PATH_BOOK_IMG_S.replace("s\\", "s"));
		if(!dir.exists())
			dir.mkdirs();
		if(!img_l_dir.exists())
			img_l_dir.mkdirs();
		if(!img_s_dir.exists()) 	
			img_s_dir.mkdir();
	}

	/**
	 * 判定该book是否需要过滤掉,并对某些字段内容进行格式化处理
	 * 
	 * @param book
	 * @return true 成功格式化，false 格式化失败
	 */
	private boolean formatBookData(Book book) {
		// 过滤掉在线课程访问码的书
		if (book.getType() != null
				&& book.getType().contains("Printed Access Code")) {
//			System.out.println("该book为访问码，忽略");
			return true;
		}
		// 过滤掉Kindle的书
		if (book.getType() != null && book.getType().contains("Kindle")) {
//			System.out.println("该book为Kindle，忽略");
			return true;
		}
		// 过滤掉无书名的书
		if (book.getTitle() == null) {
//			System.out.println("该book无书名，忽略");
			return true;
		}
		// 过滤掉type异常的书：FIELD_TYPE_MAX_LIMIT
		if (book.getTitle() != null && book.getType().length() > IBookDAO.FIELD_TYPE_MAX_LIMIT) {
			return true;
		}
		// 对书名中的&quot进行替换(解析时已经做了处理)
//		book.setTitle(book.getTitle().replaceAll("&quot;", "\""));
		// 书名只保留DB字段上限字节数：FIELD_TITLE_MAX_LIMIT
		if (book.getTitle() != null && book.getTitle().length() > 200) {
			book.setTitle(book.getTitle().substring(0,
					IBookDAO.FIELD_TITLE_MAX_LIMIT));
		}
		// size字段格式化，去掉pages_per_sheet
		if (book.getSize() != null
				&& book.getSize().contains("pages_per_sheet")) {
			book.setSize(book.getSize().replaceAll("pages_per_sheet", ""));
		}
		// size字段格式化，若包含"mm"关键字，则将size内容移至dimensions
		if (book.getSize() != null && book.getSize().contains("mm")) {
			book.setDimensions(book.getSize());
			book.setSize(null);
		}
		return false;
	}

	@Override
	public void downLoadHTML() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 解析网页或下载图片
	 * @param content 图书的HTML文件
	 * @param book 已经解析了书名类型等信息的book
	 * @param category 该书所属类别
	 * @return 更新了图片信息的book
	 */
	public Object updateImage(String content, Book book, CategoryInfoOfGoods category) {
		StringBuilder imgNameBuilder;
		String barcode;
		String imgPath = "";
		String imgUrl = "";
		
//		CRC32 crc32 = new CRC32();
//		crc32.update(book.getTitle().getBytes());
		// 生成图片保存名（categoryId_titleCRC32_ISBN_l(s)）
		barcode = book.getBarCode();
		if(null == barcode) {
			imgNameBuilder = new StringBuilder(category.getCategoryId() + "_" + barcode + "_" + "0" + "_");
		} else {
			imgNameBuilder = new StringBuilder(category.getCategoryId() + "_" + barcode + "_");
		}
		// 解析或下载大图片
		imgPath = SystemConfig.BOOKLIST_DIR +      // 保存图片的根目录
				category.getCategoryName() +             // 所属类别
				SystemConfig.DOWNLOAD_PATH_BOOK_IMG_L +  // 图片类型
				imgNameBuilder.toString() + "l" +        // 图片名称
				SystemConfig.IMG_TYPE;                   // 图片类型说明
//System.out.println(book.getTitle());		
		if (content.contains("data:image/jpeg;base64,")) {
			// 获得页面上base64编码图片的内容并解析大图并保存
			imgUrl = PageContentParser.getItem(content, 
					"data:image/jpeg;base64,", 
					"\"");
//System.out.println("title " + book.getTitle());
			ImageUtil.GenerateImage(imgUrl, imgPath);
		} else {
			try {
				// 图片保存地址：D:\\dump\\Bomb\\ + categoryName + img\\l\\
				imgUrl = PageContentParser.getItem(content, 
						"<img id=\"imgBlkFront\" src=\"",
						"\"");
				if (imgUrl == null)
					imgUrl = PageContentParser.getItem(content, 
							"<img alt=\"\" src=\"",
							"\"");
				if (imgUrl != null) {
					IOUtil.saveImgToFile(imgUrl, imgPath);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 将大图压缩成小图并且保存(w: h:)
		try {
			ImageUtil.resize(SystemConfig.DOWNLOAD_PATH_ROOT +            // 原图的决定对路径
							 category.getCategoryName() +
							 SystemConfig.DOWNLOAD_PATH_BOOK_IMG_L +
							 imgNameBuilder.toString() + "l" +
							 SystemConfig.IMG_TYPE, 
									   SystemConfig.DOWNLOAD_PATH_ROOT +  // 压缩后的图片的绝对路径
									   category.getCategoryName() +
									   SystemConfig.DOWNLOAD_PATH_BOOK_IMG_S + 
									   imgNameBuilder.toString() + "s" +
									   SystemConfig.IMG_TYPE, 
									   			160,         // 压缩后的宽度
									   			 	0.7f);   // 压缩质量
		} catch (IOException e) {
			e.printStackTrace();
		}    

		// 将文件名的相对路径写入数据库
		book.setImgUrl1(imgNameBuilder.toString() + "s" + SystemConfig.IMG_TYPE);
		book.setImgUrl2(imgNameBuilder.toString() + "l" + SystemConfig.IMG_TYPE);
		
		return book;
	}

}
