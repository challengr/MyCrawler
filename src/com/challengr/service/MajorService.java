package com.challengr.service;

import com.challengr.dao.ICurriculumDAO;
import com.challengr.dao.IMajorDAO;
import com.challengr.dao.ibatis.CurriculumSqlMapDAO;
import com.challengr.dao.ibatis.MajorSqlMapDAO;
import com.challengr.model.Curriculum;
import com.challengr.model.Major;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MajorService implements IMajorService {

	private IMajorDAO majorDAO;
	private ICurriculumDAO curriculumDAO;
	
	@Override
	public void updateMajor() {
//		insertMajor();
		
		List<Curriculum> cList = new ArrayList<Curriculum>();
		try {
			majorDAO = new MajorSqlMapDAO();
			curriculumDAO = new CurriculumSqlMapDAO();
			cList = curriculumDAO.getAllCurriculums();
			
			int collegeId;
			for (Curriculum c : cList) {
				collegeId = majorDAO.getMajorByName(c.getCourseCollege()).getId();
				if (collegeId != 0) {
					c.setCollegeId(collegeId);
					curriculumDAO.updateCurriculum(c);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertMajor() {
		List<Curriculum> cList = new ArrayList<Curriculum>();
		try {
			curriculumDAO = new CurriculumSqlMapDAO();
			majorDAO = new MajorSqlMapDAO();
			cList = curriculumDAO.getAllCurriculums();
			
			Major major = new Major();
			for (Curriculum c : cList) {
				major.setName(c.getCourseCollege());
				majorDAO.insertMajor(major);
			}
			
			int collegeId;
			for (Curriculum c : cList) {
				collegeId = majorDAO.getMajorByName(c.getCourseCollege()).getId();
				if (collegeId != 0) {
					c.setCollegeId(collegeId);
					curriculumDAO.updateCurriculum(c);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MajorService majorService = new MajorService();
		majorService.updateMajor();
	}

}
