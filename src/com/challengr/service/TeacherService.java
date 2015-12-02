package com.challengr.service;

import com.challengr.dao.ICurriculumDAO;
import com.challengr.dao.ITeacherDAO;
import com.challengr.dao.ibatis.CurriculumSqlMapDAO;
import com.challengr.dao.ibatis.TeacherSqlMapDAO;
import com.challengr.model.Curriculum;
import com.challengr.model.Teacher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherService implements ITeacherService {

	private ICurriculumDAO curriculumDAO;
	private ITeacherDAO teacherDAO;
	
	@Override
	public void updateTeacher() {
		List<Curriculum> cList = new ArrayList<Curriculum>();
		
		try {
		curriculumDAO = new CurriculumSqlMapDAO();
		teacherDAO = new TeacherSqlMapDAO();
		cList = curriculumDAO.getAllCurriculums();
		
		String teacherName;
		Teacher teacher;
		for (Curriculum c : cList) {
			teacherName = c.getTeacherName();
			int starIndex;
			if (teacherName.contains("*")) {
				starIndex = teacherName.indexOf("*");
				teacherName = teacherName.substring(0, starIndex);
				
				teacher = teacherDAO.getTeacherByName(teacherName);
				if (teacher != null) {
					c.setTeacherId(teacher.getId());
					curriculumDAO.updateCurriculum(c);
				}	
					
			}
			
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		TeacherService teacherService = new TeacherService();
		teacherService.updateTeacher();
	}
	
}
