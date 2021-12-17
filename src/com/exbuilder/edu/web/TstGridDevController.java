package com.exbuilder.edu.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.cleopatra.spring.TSVDataView;


/**
 * <pre>
 * 시  스  템  : exam
 * 단위시스템  : 응용
 * 프로그램명  : 그리드(응용)
 * 설      명    : 응용 샘플(CMN_TMP_REG) web controller
 * </pre>
 * 
 * 이력사항
 * 
 */
@Controller
@RequestMapping("/TstGrid")
public class TstGridDevController {
	
	
	@RequestMapping("/onLoad.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		
		SampleDefaultVO sv = new SampleDefaultVO();
		
		List list  = new ArrayList();
		list.add(sv);
		list.add(sv);
		list.add(sv);
		list.add(sv);
		list.add(sv);
		
		
		dataRequest.setResponse("sv", list);
				
		return new JSONDataView();
	}
	
}
