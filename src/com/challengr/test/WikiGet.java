package com.challengr.test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.challengr.util.IOUtil;

public class WikiGet {

	public static void main(String[] args) {
		try {
			IOUtil.downloadFile(
					"http://www.amazon.cn/s/ref=nb_sb_noss_2?__mk_zh_CN=%E4%BA%9A%E9%A9%AC%E9%80%8A%E7%BD%91%E7%AB%99&url=search-alias%3Daps&field-keywords=Business+Analysis",
					"D:\\Cindy\\Booklist\\SCU\\a.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ThreadPoolExecutor exec = new ThreadPoolExecutor(0, 0, 0, null, null);

	}

}
