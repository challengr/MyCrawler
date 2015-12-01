package com.challengr.dao.ibatis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.challengr.dao.ITeacherDAO;
import com.challengr.dao.MyDAO;
import com.challengr.model.Teacher;

public class TeacherSqlMapDAO extends MyDAO implements ITeacherDAO {

	public TeacherSqlMapDAO() throws IOException {
		super();
	}

	@Override
	public void insertTeacher(Teacher teacher) throws SQLException {
		if (getTeacherByName(teacher.getName()) == null)
			sqlMapClient.insert("insertTeacher", teacher);
	}

	@Override
	public void deleteTeacherById(int id) throws SQLException {
		sqlMapClient.delete("deleteTeacherById", id);
	}

	@Override
	public void updateTeacher(Teacher teacher) throws SQLException {
		sqlMapClient.update("updateTeacher", teacher);
	}

	@Override
	public Teacher getTeacherByName(String name) throws SQLException {
		Teacher t = new Teacher();
		try {
			t = (Teacher) sqlMapClient.queryForObject("getTeacherByName", name);	
		} catch (Exception e) {
			System.out.println(name);
		}
		return t;
	}

	@Override
	public List<Teacher> getAllTeachers() throws SQLException {
		return sqlMapClient.queryForList("getAllTeachers");
	}

	@Override
	public List<Teacher> getTeacherList(int fromId, int toId) throws SQLException {
		Map<String, Integer> rangeMap = new HashMap<String, Integer>();
		rangeMap.put("from", fromId);
		rangeMap.put("to", toId);
		return sqlMapClient.queryForList("getTeacherList", rangeMap);
	}

}
