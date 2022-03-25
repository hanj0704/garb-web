package com.exbuilder.edu.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.cleopatra.spring.TSVDataView;
import com.cleopatra.spring.UIView;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/simples")
public class SimpleController {

	
	public SimpleController(){
		
	}
	@RequestMapping("hanKey.do")
	public View hanLLists(HttpServletRequest request, HttpServletResponse response, DataRequest dataReq) throws IOException {
		
		
		
		return new UIView("/202102/tester.clx");
	}
	
	@RequestMapping("testMass.do")
	public void massTestList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Random random = new Random();
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < 5000; i++) {
			Map<String, Object> rowData = new HashMap<String, Object>();
			
			int idx = (int)Math.ceil(i / 6);
			rowData.put("column1", "대구분-" + idx);
			rowData.put("column2", "중구분-" + idx);
			rowData.put("column3", "소구분-" + idx);
			rowData.put("column4", "세구분-" + idx);
			rowData.put("column5", "상세정보상세정보상세정보상세정보상세정보상세정보상세정보상세정보");
			
			rowData.put("column6", random.nextInt());
			rowData.put("column7", random.nextInt());
			rowData.put("column8", random.nextInt());
			rowData.put("column9", random.nextInt());
			rowData.put("column10", random.nextInt());
			rowData.put("column11", dayFormat.format(date));
			rowData.put("column12", hourFormat.format(date));
			rowData.put("column13", "상세정보상세정보상세정보");
//			rowData.put("column14", dayFormat.format(date));
//			rowData.put("column15", hourFormat.format(date));
//			rowData.put("column16", "상세정보상세정보상세정보");
//			rowData.put("column17", dayFormat.format(date));
//			rowData.put("column18", "Y");
//			rowData.put("column19", "N");
//			rowData.put("column20", "비고비고비고비고비고비고비고비고비고");
			
			listData.add(rowData);
		}
		
//		try{
//			response.setContentLength(jsonob.toJSONString().getBytes().length);
//			out = response.getOutputStream();
//			out.write(jsonob.toJSONString().getBytes());
//			
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}finally {
//			if(out != null){
//				
//				out.flush();
//				out.close();
//				out= null;
//			}
//		}
	}
	
	@CrossOrigin
	@RequestMapping("/massList.do")
	public View masslist(HttpServletRequest request, HttpServletResponse response, DataRequest datareq)throws IOException {
		
//		response.setHeader("Access-Control-Allow-Headers", "*");
//		response.setHeader("Access_control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
//		response.setHeader("Access-Control-Max-Age", "3600");
//		response.setHeader("Access-Control-Allow-Origin", "*");
		
		Random random = new Random();
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < 5000; i++) {
			Map<String, Object> rowData = new HashMap<String, Object>();
			
			int idx = (int)Math.ceil(i / 6);
			rowData.put("column1", "대구분-" + idx);
			rowData.put("column2", "중구분-" + idx);
			rowData.put("column3", "소구분-" + idx);
			rowData.put("column4", "세구분-" + idx);
			rowData.put("column5", "상세정보상세정보상세정보상세정보상세정보상세정보상세정보상세정보");
			
			rowData.put("column6", random.nextInt());
			rowData.put("column7", random.nextInt());
			rowData.put("column8", random.nextInt());
			rowData.put("column9", random.nextInt());
			rowData.put("column10", random.nextInt());
			rowData.put("column11", dayFormat.format(date));
			rowData.put("column12", hourFormat.format(date));
			rowData.put("column13", "상세정보상세정보상세정보");
//			rowData.put("column14", dayFormat.format(date));
//			rowData.put("column15", hourFormat.format(date));
//			rowData.put("column16", "상세정보상세정보상세정보");
//			rowData.put("column17", dayFormat.format(date));
//			rowData.put("column18", "Y");
//			rowData.put("column19", "N");
//			rowData.put("column20", "비고비고비고비고비고비고비고비고비고");
			
			listData.add(rowData);
		}
//		byte[] bytes = listData.toString().getBytes();
//		System.out.println(bytes.length);
//		System.out.println(datareq.getResponse().toString().length());
//		response.setContentLength(bytes.length);
//		response.setHeader("Content-Length", String.valueOf(listData.toString().length()));
		response.setHeader("Content-Encoding","gzip");
		datareq.setResponse("mainDS", listData);
		return new JSONDataView();
	}
	
	@RequestMapping("/realMass.do")
	public void realMassList(HttpServletRequest request, HttpServletResponse response, DataRequest datareq) throws IOException {

		ParameterGroup pg  =  datareq.getParameterGroup("dmSearch");
		String strCnt = pg.getValue("cnt");
		int limits = 10;
		if( strCnt == null || "".equals(strCnt.trim())) {
			strCnt = "10000" ;
		}
		limits = Integer.parseInt(strCnt);
//		response.setHeader("Content-Encoding","gzip");
//		response.setHeader("Content-Type","application/json");

		Random random = new Random();  
 
//		OutputStream output  = response.getOutputStream() ;
		OutputStream output = null;
		StringBuffer fullBuffer = new StringBuffer();
		try {  
			output  = new GZIPOutputStream(response.getOutputStream()); 
			
			StringBuffer rowData = new StringBuffer();
			output.write(rowData.append("{\"dsList\":[").toString().getBytes());
//			response.setContentLength(rowData.toString().getBytes().length);
			
			for(int i = 0; i < limits; i++) {
				rowData = new StringBuffer() ;
				int idx = (int)Math.ceil(i / 6);
				char uc = (char)((int)(Math.random()*26+65));
				rowData.append("{");
				rowData.append("\"column1\":\"row-data-" + uc + "\",");
				rowData.append("\"column2\":\"row-data-" + uc + "\",");
				rowData.append("\"column3\":\"row-data-" + uc + "\",");
				rowData.append("\"column4\":\"row-data-" + uc + "\",");
				rowData.append("\"column5\":\"row-data-" + uc + "\",");
				rowData.append("\"column6\":\"row-data-" + uc + "\",");
				rowData.append("\"column7\":\"row-data-" + uc + "\",");
				rowData.append("\"column8\":\"row-data-" + uc + "\",");
				rowData.append("\"column9\":\"row-data-" + uc + "\",");
				rowData.append("\"column10\":\"row-data-" + uc + "\",");
				rowData.append("\"column11\":\"row-data-" + uc + "\",");
				rowData.append("\"column12\":\"row-data-" + uc + "\",");
				rowData.append("\"column13\":\"row-data-" + uc + "\",");
				rowData.append("\"column14\":\"row-data-" + uc + "\",");
				rowData.append("\"column15\":\"row-data-" + uc + "\",");
				rowData.append("\"column16\":\"row-data-" + uc + "\",");
				rowData.append("\"column17\":\"row-data-" + uc + "\",");
				rowData.append("\"column18\":\"row-data-" + uc + "\",");
				rowData.append("\"column19\":\"row-data-" + uc + "\",");
				rowData.append("\"column20\":\"row-data-" + uc + "\",");
				rowData.append("\"column21\":\"row-data-" + uc + "\",");
				rowData.append("\"column22\":\"row-data-" + uc + "\",");
				rowData.append("\"column23\":\"row-data-" + uc + "\",");
				rowData.append("\"column24\":\"row-data-" + uc + "\",");
				rowData.append("\"column25\":\"row-data-" + uc + "\",");
				rowData.append("\"column26\":\"row-data-" + uc + "\",");
				rowData.append("\"column27\":\"row-data-" + uc + "\",");
				rowData.append("\"column28\":\"row-data-" + uc + "\",");
				rowData.append("\"column29\":\"row-data-" + uc + "\",");
				rowData.append("\"column30\":\"row-data-" + uc + "\",");
				rowData.append("\"column31\":\"row-data-" + uc + "\",");
				rowData.append("\"column32\":\"row-data-" + uc + "\",");
				rowData.append("\"column33\":\"row-data-" + uc + "\",");
				rowData.append("\"column34\":\"row-data-" + uc + "\",");
				rowData.append("\"column35\":\"row-data-" + uc + "\",");
				rowData.append("\"column36\":\"row-data-" + uc + "\",");
				rowData.append("\"column37\":\"row-data-" + uc + "\",");
				rowData.append("\"column38\":\"row-data-" + uc + "\",");
				rowData.append("\"column39\":\"row-data-" + uc + "\",");
				rowData.append("\"column40\":\"row-data-" + uc + "\","); 
				rowData.append("\"column41\":\"row-data-" + uc + "\",");
				rowData.append("\"column42\":\"row-data-" + uc + "\",");
				rowData.append("\"column43\":\"row-data-" + uc + "\",");
				rowData.append("\"column44\":\"row-data-" + uc + "\",");
				rowData.append("\"column45\":\"row-data-" + uc + "\",");
				rowData.append("\"column46\":\"row-data-" + uc + "\",");
				rowData.append("\"column47\":\"row-data-" + uc + "\",");
				rowData.append("\"column48\":\"row-data-" + uc + "\",");
				rowData.append("\"column49\":\"row-data-" + uc + "\",");
				rowData.append("\"column50\":\"row-data-" + uc + "\",");
				rowData.append("\"column51\":\"row-data-" + uc + "\",");
				rowData.append("\"column52\":\"row-data-" + uc + "\",");
				rowData.append("\"column53\":\"row-data-" + uc + "\",");
				rowData.append("\"column54\":\"row-data-" + uc + "\",");
				rowData.append("\"column55\":\"row-data-" + uc + "\",");
				rowData.append("\"column56\":\"row-data-" + uc + "\",");
				rowData.append("\"column57\":\"row-data-" + uc + "\",");
				rowData.append("\"column58\":\"row-data-" + uc + "\",");
				rowData.append("\"column59\":\"row-data-" + uc + "\",");
				rowData.append("\"column60\":\"row-data-" + uc + "\",");
				rowData.append("\"column61\":\"row-data-" + uc + "\",");
				rowData.append("\"column62\":\"row-data-" + uc + "\",");
				rowData.append("\"column63\":\"row-data-" + uc + "\",");
				rowData.append("\"column64\":\"row-data-" + uc + "\",");
				rowData.append("\"column65\":\"row-data-" + uc + "\",");
				rowData.append("\"column66\":\"row-data-" + uc + "\",");
				rowData.append("\"column67\":\"row-data-" + uc + "\",");
				rowData.append("\"column68\":\"row-data-" + uc + "\",");
				rowData.append("\"column69\":\"row-data-" + uc + "\",");
				rowData.append("\"column70\":\"row-data-" + uc + "\",");
				rowData.append("\"column71\":\"row-data-" + uc + "\",");
				rowData.append("\"column72\":\"row-data-" + uc + "\",");
				rowData.append("\"column73\":\"row-data-" + uc + "\",");
				rowData.append("\"column74\":\"row-data-" + uc + "\",");
				rowData.append("\"column75\":\"row-data-" + uc + "\",");
				rowData.append("\"column76\":\"row-data-" + uc + "\",");
				rowData.append("\"column77\":\"row-data-" + uc + "\",");
				rowData.append("\"column78\":\"row-data-" + uc + "\",");
				rowData.append("\"column79\":\"row-data-" + uc + "\",");
				rowData.append("\"column80\":\"row-data-" + uc + "\",");
				rowData.append("\"column81\":\"row-data-" + uc + "\",");
				rowData.append("\"column82\":\"row-data-" + uc + "\",");
				rowData.append("\"column83\":\"row-data-" + uc + "\",");
				rowData.append("\"column84\":\"row-data-" + uc + "\",");
				rowData.append("\"column85\":\"row-data-" + uc + "\",");
				rowData.append("\"column86\":\"row-data-" + uc + "\",");
				rowData.append("\"column87\":\"row-data-" + uc + "\",");
				rowData.append("\"column88\":\"row-data-" + uc + "\",");
				rowData.append("\"column89\":\"row-data-" + uc + "\",");
				rowData.append("\"column90\":\"row-data-" + uc + "\",");
				rowData.append("\"column91\":\"row-data-" + uc + "\",");
				rowData.append("\"column92\":\"row-data-" + uc + "\",");
				rowData.append("\"column93\":\"row-data-" + uc + "\",");
				rowData.append("\"column94\":\"row-data-" + uc + "\",");
				rowData.append("\"column95\":\"row-data-" + uc + "\",");
				rowData.append("\"column96\":\"row-data-" + uc + "\",");
				rowData.append("\"column97\":\"row-data-" + uc + "\",");
				rowData.append("\"column98\":\"row-data-" + uc + "\",");
				rowData.append("\"column99\":\"row-data-" + uc + "\",");
				rowData.append("\"column100\":\"row-data-" + uc + "\",");
				rowData.append("\"column101\":\"row-data-" + uc + "\",");
				rowData.append("\"column102\":\"row-data-" + uc + "\",");
				rowData.append("\"column103\":\"row-data-" + uc + "\",");
				rowData.append("\"column104\":\"row-data-" + uc + "\",");
				rowData.append("\"column105\":\"row-data-" + uc + "\",");
				rowData.append("\"column106\":\"row-data-" + uc + "\",");
				rowData.append("\"column107\":\"row-data-" + uc + "\",");
				rowData.append("\"column108\":\"row-data-" + uc + "\",");
				rowData.append("\"column109\":\"row-data-" + uc + "\",");
				rowData.append("\"column110\":\"row-data-" + uc + "\",");
				rowData.append("\"column111\":\"row-data-" + uc + "\",");
				rowData.append("\"column112\":\"row-data-" + uc + "\",");
				rowData.append("\"column113\":\"row-data-" + uc + "\",");
				rowData.append("\"column114\":\"row-data-" + uc + "\",");
				rowData.append("\"column115\":\"row-data-" + uc + "\",");
				rowData.append("\"column116\":\"row-data-" + uc + "\",");
				rowData.append("\"column117\":\"row-data-" + uc + "\",");
				rowData.append("\"column118\":\"row-data-" + uc + "\",");
				rowData.append("\"column119\":\"row-data-" + uc + "\",");
				rowData.append("\"column120\":\"row-data-" + uc + "\",");
				rowData.append("\"column121\":\"row-data-" + uc + "\",");
				rowData.append("\"column122\":\"row-data-" + uc + "\",");
				rowData.append("\"column123\":\"row-data-" + uc + "\",");
				rowData.append("\"column124\":\"row-data-" + uc + "\",");
				rowData.append("\"column125\":\"row-data-" + uc + "\",");
				rowData.append("\"column126\":\"row-data-" + uc + "\",");
				rowData.append("\"column127\":\"row-data-" + uc + "\",");
				rowData.append("\"column128\":\"row-data-" + uc + "\",");
				rowData.append("\"column129\":\"row-data-" + uc + "\",");
				rowData.append("\"column130\":\"row-data-" + uc + "\",");
				rowData.append("\"column131\":\"row-data-" + uc + "\",");
				rowData.append("\"column132\":\"row-data-" + uc + "\",");
				rowData.append("\"column133\":\"row-data-" + uc + "\",");
				rowData.append("\"column134\":\"row-data-" + uc + "\",");
				rowData.append("\"column135\":\"row-data-" + uc + "\",");
				rowData.append("\"column136\":\"row-data-" + uc + "\",");
				rowData.append("\"column137\":\"row-data-" + uc + "\",");
				rowData.append("\"column138\":\"row-data-" + uc + "\",");
				rowData.append("\"column139\":\"row-data-" + uc + "\",");
				rowData.append("\"column140\":\"row-data-" + uc + "\","); 
				rowData.append("\"column141\":\"row-data-" + uc + "\",");
				rowData.append("\"column142\":\"row-data-" + uc + "\",");
				rowData.append("\"column143\":\"row-data-" + uc + "\",");
				rowData.append("\"column144\":\"row-data-" + uc + "\",");
				rowData.append("\"column145\":\"row-data-" + uc + "\",");
				rowData.append("\"column146\":\"row-data-" + uc + "\",");
				rowData.append("\"column147\":\"row-data-" + uc + "\",");
				rowData.append("\"column148\":\"row-data-" + uc + "\",");
				rowData.append("\"column149\":\"row-data-" + uc + "\",");
				rowData.append("\"column150\":\"row-data-" + uc + "\",");
				rowData.append("\"column151\":\"row-data-" + uc + "\",");
				rowData.append("\"column152\":\"row-data-" + uc + "\",");
				rowData.append("\"column153\":\"row-data-" + uc + "\",");
				rowData.append("\"column154\":\"row-data-" + uc + "\",");
				rowData.append("\"column155\":\"row-data-" + uc + "\",");
				rowData.append("\"column156\":\"row-data-" + uc + "\",");
				rowData.append("\"column157\":\"row-data-" + uc + "\",");
				rowData.append("\"column158\":\"row-data-" + uc + "\",");
				rowData.append("\"column159\":\"row-data-" + uc + "\",");
				rowData.append("\"column160\":\"row-data-" + uc + "\",");
				rowData.append("\"column161\":\"row-data-" + uc + "\",");
				rowData.append("\"column162\":\"row-data-" + uc + "\",");
				rowData.append("\"column163\":\"row-data-" + uc + "\",");
				rowData.append("\"column164\":\"row-data-" + uc + "\",");
				rowData.append("\"column165\":\"row-data-" + uc + "\",");
				rowData.append("\"column166\":\"row-data-" + uc + "\",");
				rowData.append("\"column167\":\"row-data-" + uc + "\",");
				rowData.append("\"column168\":\"row-data-" + uc + "\",");
				rowData.append("\"column169\":\"row-data-" + uc + "\",");
				rowData.append("\"column170\":\"row-data-" + uc + "\",");
				rowData.append("\"column171\":\"row-data-" + uc + "\",");
				rowData.append("\"column172\":\"row-data-" + uc + "\",");
				rowData.append("\"column173\":\"row-data-" + uc + "\",");
				rowData.append("\"column174\":\"row-data-" + uc + "\",");
				rowData.append("\"column175\":\"row-data-" + uc + "\",");
				rowData.append("\"column176\":\"row-data-" + uc + "\",");
				rowData.append("\"column177\":\"row-data-" + uc + "\",");
				rowData.append("\"column178\":\"row-data-" + uc + "\",");
				rowData.append("\"column179\":\"row-data-" + uc + "\",");
				rowData.append("\"column180\":\"row-data-" + uc + "\",");
				rowData.append("\"column181\":\"row-data-" + uc + "\",");
				rowData.append("\"column182\":\"row-data-" + uc + "\",");
				rowData.append("\"column183\":\"row-data-" + uc + "\",");
				rowData.append("\"column184\":\"row-data-" + uc + "\",");
				rowData.append("\"column185\":\"row-data-" + uc + "\",");
				rowData.append("\"column186\":\"row-data-" + uc + "\",");
				rowData.append("\"column187\":\"row-data-" + uc + "\",");
				rowData.append("\"column188\":\"row-data-" + uc + "\",");
				rowData.append("\"column189\":\"row-data-" + uc + "\",");
				rowData.append("\"column190\":\"row-data-" + uc + "\",");
				rowData.append("\"column191\":\"row-data-" + uc + "\",");
				rowData.append("\"column192\":\"row-data-" + uc + "\",");
				rowData.append("\"column193\":\"row-data-" + uc + "\",");
				rowData.append("\"column194\":\"row-data-" + uc + "\",");
				rowData.append("\"column195\":\"row-data-" + uc + "\",");
				rowData.append("\"column196\":\"row-data-" + uc + "\",");
				rowData.append("\"column197\":\"row-data-" + uc + "\",");
				rowData.append("\"column198\":\"row-data-" + uc + "\",");
				rowData.append("\"column199\":\"row-data-" + uc + "\",");
				rowData.append("\"column200\":\"row-data-" + uc + "\"");
//				rowData.append("\"column1\":\"row-data-" + "\",");
//				rowData.append("\"column2\":\"row-data-" + "\",");
//				rowData.append("\"column20\":\"row-data-" + "\""); 
				if( i == (limits -1) )
				    rowData.append("}"); 
				else
				    rowData.append("},"); 
				
				fullBuffer.append(rowData.toString());
				output.write(rowData.toString().getBytes()); 
			}
			 rowData = new StringBuffer();
			output.write(rowData.append("]}").toString().getBytes());
			fullBuffer.append(rowData.toString());
		} catch(Exception e){
			e.printStackTrace();
		}
		finally{  
			 
			System.out.println(fullBuffer.toString().getBytes().length);
			response.setContentLength(fullBuffer.toString().getBytes().length+11);
			if( output != null ){
				output.flush();
				output.close();
				output = null ;
			}
		}
	}
	
	@RequestMapping("/hey.do")
	public View heyList(HttpServletRequest request, HttpServletResponse response, DataRequest datareq) throws IOException {
		System.out.println(request.getHeader("referer"));
//		System.out.println(request.getServerName());
//		System.out.println(request.getRemoteAddr());
//		System.out.println(request.getRequestURL());
//		response.setHeader("Access-Control-Allow-Headers", "*");
//		response.setHeader("Access_control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
//		response.setHeader("Access-Control-Max-Age", "3600");
//		response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:52194");
		return new UIView("202101/trans.clx");
	}
	
	@RequestMapping("/hi.do")
	@ResponseBody
	public String hiList(@RequestParam String id, @RequestParam String callback) throws IOException {
		
		Map<String,String> paramMap = new HashMap<String,String>();
		
		paramMap.put("a", "abc");
		paramMap.put("result", "success");
		
		String result = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try{
			result = mapper.writeValueAsString(paramMap);
			
		} catch(JsonGenerationException e){
			e.printStackTrace();
		} catch(JsonMappingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
//		datareq.setResponse("mainDS", listData);
		System.out.println(result);
		
		return callback+"("+result+")";
		
//		return new JSONDataView();
	}
	
	
	
	
	
}
