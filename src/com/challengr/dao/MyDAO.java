package com.challengr.dao;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyDAO {
	protected SqlMapClient sqlMapClient;
	protected MyDAO() throws IOException{
		String resource = "SqlMapConfig.xml";
		Reader reader = Resources.getResourceAsReader (resource);
		sqlMapClient =  SqlMapClientBuilder.buildSqlMapClient(reader);
	}

}
