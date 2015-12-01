package com.challengr.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.challengr.dao.IBookDAO;
import com.challengr.dao.ICurriculumDAO;
import com.challengr.dao.ITeacherDAO;
import com.challengr.dao.ibatis.BookSqlMapDAO;
import com.challengr.dao.ibatis.CurriculumSqlMapDAO;
import com.challengr.dao.ibatis.TeacherSqlMapDAO;
import com.challengr.model.Book;
import com.challengr.model.Curriculum;
import com.challengr.model.Teacher;

public class CurriculumService implements ICurriculumService {

	private ICurriculumDAO curriculumDAO;
	private ITeacherDAO teacherDAO;
	private IBookDAO bookDAO;

	public CurriculumService() {
		try {
			this.curriculumDAO = new CurriculumSqlMapDAO();
			this.bookDAO = new BookSqlMapDAO();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateCurriculum() {
		// 导入教师信息
//		insertTeacherList();
		
		mappingBook();

	}

	@Override
	public void mappingBook() {
		
		List<Curriculum> curriculumList = new ArrayList<Curriculum>();
		try {
			String title;       // 书名
			String author;      // 作者
			String publisher;   // 出版社
			int textbookId;     // 与book表中对应书的id
			curriculumList = curriculumDAO.getAllCurriculums();
			for (Curriculum c : curriculumList) {
				// 从书单中查询有关书的定位信息
				title = c.getBookName();
				author = c.getBookAuthor();
				publisher = c.getBookPublisher();
				
				/**
				 * 算法规则：
				 * 1. 先通过作者的姓名来从书库中搜索出该作者写作的所有的书放入sameAuthorList
				 * 2. 再将书单中title中的前两个字符拿出来与sameAuthorList中所有书的书名进行比对，首次匹配则认为
				 *    匹配成功
				 */
				List<Book> sameAuthorList = new ArrayList<Book>();
				
				// 对书单当中书的作者进行处理
				author = cleanAuthor(author);
				// 对书单当中的书名进行处理
				title = cleanTitle(title);
				if (!author.equals("")) {
					sameAuthorList = bookDAO.getBookListByAuthor(author);
					for (Book book : sameAuthorList) {
						// 找到第一本对应的书的id，并更新到书单表中
						if (book.getTitle().contains(title)) {
							c.setTextbookId(book.getId());
							curriculumDAO.updateCurriculum(c);
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对title进行处理
	 * @param title 书单当中的书的title
	 * @return 书名当中的前两个字
	 */
	private String cleanTitle(String title) {
		if (title.length() > 3)
			title = title.substring(0, 2);
		return title;
	}

	/**
	 * 对author进行过滤处理
	 * @param author 作者姓名
	 * @return 如果输入不为null，则返回第一个作者的名字；否则，返回“”
	 */
	private String cleanAuthor(String author) {
		if (author == null) {
			return ""; 
		}
		
		List<String> splitorList = new ArrayList<String>();
		// 添加分隔符
		splitorList.add(",");       // ,
		splitorList.add("，");       // ，
		splitorList.add("、");       // 、

		for (String splitor : splitorList) {
			if (author.contains(splitor))
				author = author.replace(splitor, " ");   // 统一分隔符为空格
		}
		// 分割出第一个作者的姓名并返回
		String[] authors;
		authors = author.split(" ");
		author = authors[0];         
			
		return author;
	}
	
	private void insertTeacherList() {
		List<Curriculum> cList = new ArrayList<Curriculum>();
		List<Teacher> tList = new ArrayList<Teacher>();
		try {
			curriculumDAO = new CurriculumSqlMapDAO();
			teacherDAO = new TeacherSqlMapDAO();
			cList = curriculumDAO.getAllCurriculums();
			tList = teacherDAO.getAllTeachers();

			Teacher t;
			String name;
			String info;
			int starIndex = 0;
			for (Curriculum c : cList) {
				t = new Teacher();
				name = c.getTeacherName();
				info = c.getCourseInfo();
				if (name.contains("*")) {
					starIndex = name.indexOf("*");
					name = name.substring(0, starIndex);
				}

				if (info.contains(name) || info.trim().length() == 11) {
					t.setName(name);
					t.setContactInfo(containsPhoneNumber(info));
					teacherDAO.insertTeacher(t);
				}

				int indexOfOne;
				if (info.length() != 0 || info != null) {
					indexOfOne = info.indexOf("1");
					name = formatName(name);
					if (indexOfOne != -1) { // 找到手机号码
						t.setName(name);
						t.setContactInfo(containsPhoneNumber(info));
						teacherDAO.insertTeacher(t);
					}
				}
				
				// 删除重复信息
				removeSameItem();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void removeSameItem() {
		List<Teacher> tList = new ArrayList<Teacher>();
		Set<Teacher> tSet = new HashSet<Teacher>();
		List<Teacher> deleteList = new ArrayList<Teacher>();

		try {
			teacherDAO = new TeacherSqlMapDAO();

			tList = teacherDAO.getAllTeachers();

			for (Teacher t : tList) {
				tSet.add(t);
			}

			for (Teacher tc : tSet) {
				tList.remove(tc);
			}

			for (Teacher tcer : tList)
				teacherDAO.deleteTeacherById(tcer.getId());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static String formatName(String name) {
		name = name.trim();
		name = name.replace(":", "");
		name = name.replace(",", "");
		name = name.replace("，", "");

		return name;
	}

	private static String containsPhoneNumber(String info) {
		Pattern p = Pattern.compile("[0-9]{11}");
		Matcher m = p.matcher(info);

		if (m.find()) {
			info = m.group(0);
		}
		return info;
	}

	public static void main(String[] args) {
		
		CurriculumService cService = new CurriculumService();
		/*
		cService.updateCurriculum();
		*/
		
		// 测试cleanAuthor（）函数
// 		System.out.println(cService.cleanAuthor("戴枝荣、张明远"));
	    System.out.println(cService.cleanTitle("电子"));	
	}

}
