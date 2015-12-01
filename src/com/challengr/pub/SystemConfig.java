package com.challengr.pub;

public class SystemConfig {
	// 文件类型
	public static final String PAGE_TYPE = ".html";
	public static final String TEXT_TYPE = ".txt";
	/* 存放图片的格式 */
	public static final String IMG_TYPE = ".jpg";
	
	// 转义字符前缀
	public static final String AMAZON_ESCAPE_PREFIX = "&#";
	public static final String UNICODE_PREFIX = "\\u";
	
	
	/* 亚马逊的字符集 */
	public static final String AMAZON_CHARSET = "utf-8";
	/**
	 * 存放图片
	 */
//	public static final String DOWNLOAD_PATH_ROOT = "\\\\SERVER\\BookCycle\\bookcindy\\";
	public static final String DOWNLOAD_PATH_ROOT = "D:\\Cindy\\Booklist\\SCU\\";
	/**
	 * 存放解析的小图片,相对位置
	 */
	public static final String DOWNLOAD_PATH_BOOK_IMG_S = "\\img\\s\\";
	/**
	 * 存放解析的大图片，相对位置
	 */
	public static final String DOWNLOAD_PATH_BOOK_IMG_L = "\\img\\l\\";
	
	/**
	 * 备份文件目录
	 */
	public static final String BACKUP_DUMP_DIR_FITNESS = "\\\\SERVER\\BookCycle\\amazonBookHTML\\books\\健身与保健goods\\";
	public static final String BACKUP_DUMP_DIR_SOCIETY = "\\\\SERVER\\BookCycle\\amazonBookHTML\\books\\goods_两个_心理学+社会科学\\";
	public static final String BACKUP_DUMP_DIR_ECONOMY = "\\\\SERVER\\BookCycle\\amazonBookHTML\\books\\经济管理2_goods\\";
	public static final String BACKUP_DUMP_DIR_INDUSTRY = "\\\\SERVER\\BookCycle\\amazonBookHTML\\books\\计算机与互联网goods\\";
	public static final String BACKUP_DUMP_DIR_NATURAL = "\\\\SERVER\\BookCycle\\amazonBookHTML\\books\\科学与自然goods\\";
	public static final String BACKUP_DUMP_DIR_EXAM = "\\\\SERVER\\BookCycle\\amazonBookHTML\\books\\考试goods\\";
	public static final String BACKUP_DUMP_DIR_MEDICINE = "\\\\SERVER\\BookCycle\\amazonBookHTML\\books\\医学goods\\";
	public static final String BACKUP_DUMP_DIR_TOOL = "\\\\Server\\BookCycle\\amazonBookHTML\\books\\辞典与工具书goods\\";
	public static final String BACKUP_DUMP_DIR_SUCCESS = "\\\\Server\\BookCycle\\amazonBookHTML\\books\\成功励志goods\\";
	public static final String BACKUP_DUMP_DIR_POLITICAL = "\\\\SERVER\\BookCycle\\amazonBookHTML\\books\\法律goods\\";
	/**
	 * 类别编号
	    1000	自然科学
		1001	人文社科
		1002	工业技术
		1003	经济管理
		1004	法律政治
		1005	农业科学
		1006	医药卫生
		1007	健身保健
		1008	成功励志
		1009	求职考试
		1010	工具书
		1011        教材教辅
	 */
	public static final String CATEGORY_NAME_NATURE = "自然科学";
	public static final int CATEGORY_ID_NATURE = 1000;
	public static final String CATEGORY_NAME_SOCIETY = "人文社科";
	public static final int CATEGORY_ID_SOCIETY = 1001;
	public static final String CATEGORY_NAME_INDUSTRY = "工业技术";
	public static final int CATEGORY_ID_INDUSTRY = 1002;
	public static final String CATEGORY_NAME_ECONOMY = "经济管理";
	public static final int CATEGORY_ID_ECONOMY = 1003;
	public static final String CATEGORY_NAME_POLITICAL = "法律政治";
	public static final int CATEGORY_ID_POLITICAL = 1004;
	public static final String CATEGORY_NAME_AGRICULTURAL = "农业科学";
	public static final int CATEGORY_ID_AGRICULTURAL = 1005;
	public static final String CATEGORY_NAME_MEDICINE = "医药卫生";
	public static final int CATEGORY_ID_MEDICINE = 1006;
	public static final String CATEGORY_NAME_FITNESS = "健身保健";
	public static final int CATEGORY_ID_FITNESS = 1007;
	public static final String CATEGORY_NAME_SUCCESS = "成功励志";
	public static final int CATEGORY_ID_SUCCESS = 1008;
	public static final String CATEGORY_NAME_EXAM = "求职考试";
	public static final int CATEGORY_ID_EXAM = 1009;
	public static final String CATEGORY_NAME_TOOL = "工具书";
	public static final int CATEGORY_ID_TOOL = 1010;
	public static final String CATEGORY_NAME_TEXTBOOK = "教材教辅";
	public static final int CATEGORY_ID_TEXTBOOK = 1011;
	
	
	/* 书单目录 */
	public static final String BOOKLIST_DIR = "D:\\Cindy\\Booklist\\SCU\\";
	/* 教材目录 */
	public static final String TEXTBOOKS_DIR = "D:\\Cindy\\Booklist\\Books\\";
}

