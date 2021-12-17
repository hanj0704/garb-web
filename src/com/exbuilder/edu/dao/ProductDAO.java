package com.exbuilder.edu.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public ProductDAO() {
	}
	
	public List<Map<String, Object>> selectProductList() {
		return sqlSessionTemplate.selectList("product.selectProducList");
	}
	
	public List<Map<String, Object>> selectProductList(Map<String, Object> paramMap) {
		return sqlSessionTemplate.selectList("product.selectProductList", paramMap);
	}
	
	public int insertProduct(Map<String, String> paramMap) {
		return sqlSessionTemplate.insert("product.insertProduct", paramMap);
	}
	
	public int updateProduct(Map<String, String> paramMap) {
		return sqlSessionTemplate.update("product.updateProduct", paramMap);
	}

	public int deleteProduct(Map<String, String> paramMap) {
		return sqlSessionTemplate.update("product.deleteProduct", paramMap);
	}
}
