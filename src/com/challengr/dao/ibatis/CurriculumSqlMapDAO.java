package com.challengr.dao.ibatis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.challengr.dao.ICurriculumDAO;
import com.challengr.dao.MyDAO;
import com.challengr.model.Curriculum;

public class CurriculumSqlMapDAO extends MyDAO implements ICurriculumDAO {

	public CurriculumSqlMapDAO() throws IOException {
		super();
	}

	@Override
	public void insertCurriculum(Curriculum curriculum) throws SQLException {
		sqlMapClient.insert("insertCurriculum", curriculum);
	}

	@Override
	public void deleteCurriculumById(int id) throws SQLException {
		sqlMapClient.delete("deleteCurriculumById", id);
	}

	@Override
	public void updateCurriculum(Curriculum curriculum) throws SQLException {
		// 当textbook中没有id时候，则插入id
		if ((getCurriculumById(curriculum.getId()).getTextbookId()) == 0) {
			System.out.println(curriculum.getTextbookId() + "is updated!");
			sqlMapClient.update("updateCurriculum", curriculum);
		}
	}

	@Override
	public Curriculum getCurriculumById(int curriculumId) throws SQLException {
		return (Curriculum) sqlMapClient.queryForObject("getCurriculumById", curriculumId);
	}
	
	@Override
	public List<Curriculum> selectCurriculumByTitleAndAuthor(String title, String author) throws SQLException {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("title", title);
		paramsMap.put("author", author);
		return sqlMapClient.queryForList("selectCurriculumByTitleAndAuthor", paramsMap);
	}

	@Override
	public List<Curriculum> getCurriculumByTitleAndPublisher(String title, String publisher) throws SQLException {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("title", title);
		paramsMap.put("publisher", publisher);
		return sqlMapClient.queryForList("getCurriculumByTitleAndPublisher", paramsMap);
	}

	@Override
	public List<Curriculum> getCurriculumListByUniversity(int universityId) throws SQLException {
		return sqlMapClient.queryForList("getCurriculumListByUniversity", universityId);
	}

	@Override
	public List<Curriculum> getCurriculumListByCollege(int collegeId) throws SQLException {
		return sqlMapClient.queryForList("getCurriculumListByCollege", collegeId);
	}

	@Override
	public List<Curriculum> getCurriculumListByUniversityAndCollege(int universityId, int collegeId) throws SQLException {
		Map<String, Integer> paramsMap = new HashMap<String, Integer>();
		paramsMap.put("universityId", universityId);
		paramsMap.put("collegeId", collegeId);
		return sqlMapClient.queryForList("getCurriculumListByUniversityAndCollege");
	}

	@Override
	public List<Curriculum> getCurriculumList(int fromId, int toId) throws SQLException {
		Map<String, Integer> paramsMap = new HashMap<String, Integer>();
		paramsMap.put("fromId", fromId);
		paramsMap.put("toId", toId);
		return sqlMapClient.queryForList("getCurriculumList", paramsMap);
	}

	@Override
	public List<Curriculum> getAllCurriculums() throws SQLException {
		return sqlMapClient.queryForList("getAllCurriculums");
	}


}
