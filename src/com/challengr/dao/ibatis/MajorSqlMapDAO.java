package com.challengr.dao.ibatis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.challengr.dao.IMajorDAO;
import com.challengr.dao.ITeacherDAO;
import com.challengr.dao.MyDAO;
import com.challengr.model.Major;
import com.challengr.model.Teacher;

public class MajorSqlMapDAO extends MyDAO implements IMajorDAO {

	public MajorSqlMapDAO() throws IOException {
		super();
	}

	@Override
	public void insertMajor(Major major) throws SQLException {
		if (getMajorByName(major.getName()) == null)
			sqlMapClient.insert("insertMajor", major);
	}

	@Override
	public void updateMajor(Major major) throws SQLException {
		sqlMapClient.update("updateMajor", major);
	}

	@Override
	public List<Major> getAllMajors() throws SQLException {
		return sqlMapClient.queryForList("getAllMajors");
	}

	@Override
	public Major getMajorByName(String name) throws SQLException {
		return (Major) sqlMapClient.queryForObject("getMajorByName", name);
	}


}
