package com.exbuilder.edu.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public @Data class TesterVO {

	private String column1;
	private String column2;
	
	private List<TesterVO> list;
}
