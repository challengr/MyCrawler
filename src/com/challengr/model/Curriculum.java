package com.challengr.model;

/**
 * 书单
 * @author WangZhe
 *
 */
public class Curriculum {

	private int id;
	// 学院
	private String courseCollege;
	// 课程代号
	private String courseCode;
	// 课程名称
	private String courseName;
	// 教师姓名
	private String teacherName;
	// 教材名称
	private String bookName;
	// 教材出版社
	private String bookPublisher;
	// 教材作者
	private String bookAuthor;
	// 教材版本
	private String bookEdition;
	// 课程备注信息
	private String courseInfo;
	// 课程开设学校 
	private int courseUniversity;
	// 对应教材Id
	private int textbookId;
	// 教师联系信息
	private int teacherId;
	// 对应学院编号
	private int collegeId;
	
	
	// Getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseCollege() {
		return courseCollege;
	}
	public void setCourseCollege(String courseCollege) {
		this.courseCollege = courseCollege;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookPublisher() {
		return bookPublisher;
	}
	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public String getBookEdition() {
		return bookEdition;
	}
	public void setBookEdition(String bookEdition) {
		this.bookEdition = bookEdition;
	}
	public String getCourseInfo() {
		return courseInfo;
	}
	public void setCourseInfo(String courseInfo) {
		this.courseInfo = courseInfo;
	}
	public int getCourseUniversity() {
		return courseUniversity;
	}
	public void setCourseUniversity(int courseUniversity) {
		this.courseUniversity = courseUniversity;
	}
	public int getTextbookId() {
		return textbookId;
	}
	public void setTextbookId(int textbookId) {
		this.textbookId = textbookId;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public int getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}
	
	@Override
	public String toString() {
		return "Curriculum [id=" + id + ", courseCollege=" + courseCollege + ", courseCode=" + courseCode
				+ ", courseName=" + courseName + ", teacherName=" + teacherName + ", bookName=" + bookName
				+ ", bookPublisher=" + bookPublisher + ", bookAuthor=" + bookAuthor + ", bookEdition=" + bookEdition
				+ ", courseInfo=" + courseInfo + ", courseUniversity=" + courseUniversity + ", textbookId=" + textbookId
				+ ", teacherId=" + teacherId + ", collegeId=" + collegeId + "]";
	}
}
