package com.exbuilder.edu.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HanwhaDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public HanwhaDAO() {
	}
	
	public List<Map<String, Object>> selectComList(Map<String,Object> paramMap) {
		return sqlSessionTemplate.selectList("hanwha.selectComList",paramMap);
	}
	
}
