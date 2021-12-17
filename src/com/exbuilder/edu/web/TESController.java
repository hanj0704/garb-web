package com.exbuilder.edu.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.json.JSONArray;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

@Controller
@RequestMapping("/tes")
public class TESController {

	public TESController(){
		
	}
	

	@RequestMapping("/list.do")
	public void getList(HttpServletRequest request, HttpServletResponse response, DataRequest datareq)throws IOException, ParseException {
		
		ParameterGroup paramGrp = datareq.getParameterGroup("");
		JSONParser parser= new JSONParser();
		
		JSONObject jsonob = new JSONObject();
		String strs = "{\"LIST\": {\"CL\": [{\"SLOT_NO\":\"1\",\"WAFER_NO\":\"21\",\"WAFER_LOADED\":\"Y\"},{\"SLOT_NO\":\"2\",\"WAFER_NO\":\"22\",\"WAFER_LOADED\":\"Y\"},{\"SLOT_NO\":\"3\",\"WAFER_NO\":\"23\",\"WAFER_LOADED\":\"Y\"}],\"LP\" :[{\"LP_ID\":\"1\",\"LP_EXIST\":\"Y\",\"MAX_WAFER_CNT\":25,\"FOUP_LOADED\":\"Y\",\"LP_DOOR_STATUS\":\"N\"},{\"LP_ID\":\"2\",\"LP_EXIST\":\"Y\",\"MAX_WAFER_CNT\":25,\"FOUP_LOADED\":\"Y\",\"LP_DOOR_STATUS\":\"N\"},{\"LP_ID\":\"3\",\"LP_EXIST\":\"Y\",\"MAX_WAFER_CNT\":25,\"FOUP_LOADED\":\"Y\",\"LP_DOOR_STATUS\":\"N\"},{\"LP_ID\":\"4\",\"LP_EXIST\":\"Y\",\"MAX_WAFER_CNT\":25,\"FOUP_LOADED\":\"Y\",\"LP_DOOR_STATUS\":\"N\"}]},\"MAP\": {\"TM\": {\"TM_ROTATE\":90,\"A_ARM_EXIST\":\"TRUE\",\"B_ARM_EXIST\":\"FALSE\"},\"AL\" :{\"WAFER_LOADED\": \"Y\",\"WAFER_NO\" : \"13\"}}}";
//		String strs = "{\"books\":[{\"genre\":\"소설\",\"price\":\"100\",\"name\":\"사람은 무엇으로 사는가?\",\"writer\":\"톨스토이\",\"publisher\":\"톨스토이 출판사\"},{\"genre\":\"소설\",\"price\":\"300\",\"name\":\"홍길동전\",\"writer\":\"허균\",\"publisher\":\"허균 출판사\"},{\"genre\":\"소설\",\"price\":\"900\",\"name\":\"레미제라블\",\"writer\":\"빅토르 위고\",\"publisher\":\"빅토르 위고 출판사\"}],\"persons\":[{\"nickname\":\"남궁민수\",\"age\":\"25\",\"name\":\"송강호\",\"gender\":\"남자\"},{\"nickname\":\"예니콜\",\"age\":\"21\",\"name\":\"전지현\",\"gender\":\"여자\"}]}";
		Object parsedObj = parser.parse(strs);
		jsonob.put("res",parsedObj);
		
		OutputStream out = null;
		
		try{
			
			out = response.getOutputStream();
			out.write(jsonob.toJSONString().getBytes());
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(out != null){
				
				out.flush();
				out.close();
				out= null;
			}
		}
		
//		return new JSONDataView();
	}
}
