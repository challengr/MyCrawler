/**
 * 
 */
package com.challengr.controller;

import com.challengr.service.CurriculumService;
import com.challengr.service.ICurriculumService;

/**
 * @author WangZhe
 * 2015年11月16日
 */
public class MatchingBookController implements IController {
	
	private ICurriculumService curriculumService;

	public MatchingBookController() {
		this.curriculumService = new CurriculumService();
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.challengr.controller.IController#before()
	 */
	@Override
	public void before() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.challengr.controller.IController#after()
	 */
	@Override
	public void after() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.challengr.controller.IController#control()
	 */
	@Override
	public void control() {
		
		curriculumService.mappingBook();
	}

}
