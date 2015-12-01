package com.challengr.dao;

import java.sql.SQLException;
import java.util.List;

import com.challengr.model.Major;

public interface IMajorDAO {
	public void insertMajor(Major major) throws SQLException;
	public void updateMajor(Major major) throws SQLException;
	public List<Major> getAllMajors() throws SQLException;
	public Major getMajorByName(String name) throws SQLException;
}
