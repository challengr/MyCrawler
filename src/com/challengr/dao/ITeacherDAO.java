package com.challengr.dao;

import java.sql.SQLException;
import java.util.List;

import com.challengr.model.Teacher;

public interface ITeacherDAO {

	public void insertTeacher(Teacher Teacher) throws SQLException;
	public void deleteTeacherById(int id) throws SQLException;
	public void updateTeacher(Teacher Teacher) throws SQLException;
	public List<Teacher> getAllTeachers() throws SQLException;
	public List<Teacher> getTeacherList(int formId, int toId) throws SQLException;
	public Teacher getTeacherByName(String name) throws SQLException;
	
}
