package com.exbuilder.edu.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.exbuilder.edu.dao.HanwhaDAO;

@Controller
@RequestMapping("/hanwha")
public class HanwhaTestController {

	@Autowired
	private HanwhaDAO hanwha;
	
	public HanwhaTestController(){
		
	}
	
	
	@RequestMapping("/comList.do")
	public View comList(HttpServletRequest request, HttpServletResponse response,DataRequest req)throws IOException {
		
		ParameterGroup paramGrp = req.getParameterGroup("dmParam");
		String parameter = req.getParameter("VALS");
		Map<String,Object>  paramMap = new HashMap<>();
//		if(paramGrp != null) {
//			
//			String values = paramGrp.getValue("value");
//			
//			if(values != null && !"".equals(values)) {
//				
//				paramMap.put("VALS", values);
//			} else {
//				paramMap.put("VALS", "");
//			}
//		
//		}
		if(parameter != null && !"".equals(parameter)) {
			
			paramMap.put("VALS", parameter);
		} else {
			paramMap.put("VALS", "");
		}
		List<Map<String,Object>> lists = hanwha.selectComList(paramMap);
		
		req.setResponse("ds1", lists);
		
		return new JSONDataView();
	}
}
