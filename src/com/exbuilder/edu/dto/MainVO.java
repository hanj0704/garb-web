package com.exbuilder.edu.dto;

import java.util.List;

public class MainVO {

private static final long serialVersionUID = 1L;
	
	
	/** 아이디 */
	private String column1;
	
	
	private List<HanVO> hanVOList;
	
	public List<HanVO> getHanVoList(){
		return hanVOList;
	}
	
	public void setHanVOList(List<HanVO> hanVOList) {
		this.hanVOList = hanVOList;
	}
	
	public String getColumn1(){
		return column1;
	}
	
	public void setColumn1(String column1) {
		this.column1 = column1;
	}
}
