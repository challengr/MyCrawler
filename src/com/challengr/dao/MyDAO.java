package com.challengr.dao;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class MyDAO {
	protected SqlMapClient sqlMapClient;
	protected MyDAO() throws IOException{
		String resource = "com/challengr/dao/ibatis/SqlMapConfig.xml";
		Reader reader = Resources.getResourceAsReader (resource);
		sqlMapClient =  SqlMapClientBuilder.buildSqlMapClient(reader);
	}

}
