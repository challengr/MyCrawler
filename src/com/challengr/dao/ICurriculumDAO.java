package com.challengr.dao;

import java.sql.SQLException;
import java.util.List;

import com.challengr.model.Curriculum;

public interface ICurriculumDAO {

	public void insertCurriculum(Curriculum curriculum) throws SQLException;
	public void deleteCurriculumById(int id) throws SQLException;
	/**
	 * 当书单中不存在该书与book表中书的对应时，更新书单信息
	 * @param curriculum 新的书单
	 * @throws SQLException
	 */
	public void updateCurriculum(Curriculum curriculum) throws SQLException;
	public Curriculum getCurriculumById(int curriculumId) throws SQLException;
	public List<Curriculum> selectCurriculumByTitleAndAuthor(String title, String author) throws SQLException;
	public List<Curriculum> getCurriculumByTitleAndPublisher(String title, String publisher) throws SQLException;
	public List<Curriculum> getCurriculumListByUniversity(int universityId) throws SQLException;
	public List<Curriculum> getCurriculumListByCollege(int collegeId) throws SQLException;
	public List<Curriculum> getCurriculumListByUniversityAndCollege(int universityId, int collegeId) throws SQLException;
	public List<Curriculum> getCurriculumList(int fromId, int toId) throws SQLException;
	public List<Curriculum> getAllCurriculums() throws SQLException;
	
}
