package com.exbuilder.edu.web;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.cleopatra.spring.UIView;
import com.exbuilder.edu.dto.HanVO;
import com.exbuilder.edu.dto.MainVO;
import com.exbuilder.edu.dto.SampleVO;
import com.exbuilder.edu.dto.TestVO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	DefaultListableBeanFactory df ;
	
	public TestController(){
		
	}
	@RequestMapping("hanKey.do")
	public View hanLLists(HttpServletRequest request, HttpServletResponse response, DataRequest dataReq) throws IOException {
		
		
		
		return new UIView("/202102/tester.clx");
	}
	@RequestMapping("/test.do")
	public void testlist(HttpServletRequest request, HttpServletResponse response, DataRequest datareq)throws IOException {
		
		
		ParameterGroup pag = datareq.getParameterGroup("dm1");
		System.out.println(pag.getValue("column1"));
		System.out.println(pag.getValue("column2"));
	}
	
	
	@RequestMapping("/gaehang.do")
	public View gaehangList(HttpServletRequest request, HttpServletResponse response, DataRequest datareq) throws IOException {
		
		HashMap<String, Object> hashmap = new HashMap<>();
		hashmap.put("column1", "abc\ndef");
		
		datareq.setResponse("dm1", hashmap);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/testVo.do")
	public View testVo(@ModelAttribute HanVO vo,  HttpServletResponse response, DataRequest datareq) throws Exception {
				
		System.out.println(vo);

		List<Map<String, Object>> dsList = new ArrayList<Map<String, Object>>();		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column1", "1");
		map.put("column2", "test1");
		map.put("column3", "1111");
		map.put("column4", "010-1111-1111");
		map.put("column5", "학동");
		dsList.add(map);
//		
//		model.addAttribute("paginationInfo",paginationInfo);
//		model.addAttribute("dsList", dsList);
		datareq.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	@RequestMapping("/testHan.do")
	public View testHan(@ModelAttribute MainVO vo,HttpServletResponse response, DataRequest datareq) throws Exception {
		
		ParameterGroup[] paramgrp = datareq.getParameterGroups();
		System.out.println(paramgrp);
		ParameterGroup param1 = datareq.getParameterGroup("dm1");
		System.out.println(param1);
		Iterator ab = param1.getAllRows();
		System.out.println(param1.getAllRows());
		while(ab.hasNext()) {
			System.out.println(ab.next());
		}
		System.out.println(vo);
		return new JSONDataView();
		
	}
//	@RequestBody Map<String,Object> param
	@ResponseBody
	@RequestMapping(value="/tes.do",method=RequestMethod.POST)
	public View testTes(@RequestBody List<TestVO> param,HttpServletRequest request, HttpServletResponse response) throws Exception{
		for(String name : df.getBeanDefinitionNames()){
			
			System.out.println(name +"\t"+df.getBean(name).getClass().getName());
		}
		System.out.println(param);
		
		param.forEach(new Consumer<TestVO>(){
			@Override
			public void accept(TestVO t) {
				// TODO Auto-generated method stub
				System.out.println(t.getColumn1());
			}
		});
//		Cookie cook = new Cookie("name",URLEncoder.encode("테스트","UTF-8"));
//		cook.setPath("/test");
//		response.addCookie(cook);
//		
//		final Cookie cookiebongji[] = request.getCookies();
//		
//		if(cookiebongji != null && cookiebongji.length>0){
//			
//			for(Cookie cookss : cookiebongji){
//				System.out.println(cookss);
//			}
//		}
//		System.out.println(param.getColumn1());
//		Set<String> keySet = param.keySet();
//		keySet.forEach(new Consumer<String>() {
//		@Override
//		public void accept(String t) {
//			// TODO Auto-generated method stub
//			System.out.println(t);
//			System.out.println(param.get(t));
//		}
//		});
		return new JSONDataView();
	}
	
}
