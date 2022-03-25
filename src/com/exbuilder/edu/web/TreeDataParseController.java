package com.exbuilder.edu.web;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.json.JSONObject;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;


@Controller
@RequestMapping("/tres")
public class TreeDataParseController {

	public TreeDataParseController(){
		
	}
	@RequestMapping("hey.do")
	public void getJsonData(HttpServletRequest request, HttpServletResponse response, DataRequest req) throws Exception{
		JSONObject requestObject = req.getRequestObject();
//		requestObject.
	}
	@RequestMapping("getData.do")
	public void getDatas(HttpServletRequest request, HttpServletResponse response, DataRequest datareq) throws Exception{
		
//		List<Map<String,Object>> resultList = getRandomList(10000);
//		datareq.setResponse("dsList", resultList);
//		JSONObject reqObj = datareq.getRequestObject();
		ParameterGroup parm = datareq.getParameterGroup("dm1");
		String cnt = parm.getValue("count");
		int count = Integer.parseInt(cnt);
//		String  encryptedStr = aes1.AesECBEncode(reqObj.toString());
//		JSONObject newobj = getJson(reqObj);
		JSONArray resultJa = getRandomList(count);
		String writed = resultJa.toJSONString();
		String downloadFileName = "JsonFile" + ".json";
		downloadFileName = encodingDownloadFileName(request,response,downloadFileName);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		response.setHeader("Content-Transfer-Encoding","binary");
		
		PrintWriter out = response.getWriter();
		out.print(writed);
		out.flush();
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getRandomList(int maxLength){
		
		List<Map<String,Object>> resultList = null;
		JSONArray ja = new JSONArray();
		
		for(int i = 0 ; i < maxLength ; i++) {
			int numRandom = (int) Math.round(Math.random());
			JSONObject tempObj = new JSONObject();
			tempObj.put("label", "label"+i);
			tempObj.put("value", "value"+i);
			tempObj.put("parent", "");
			tempObj.put("column1", "column1"+i);
			tempObj.put("column2", "column2"+i);
			tempObj.put("column3", "column3"+i);
			tempObj.put("column4", "column4"+i);
			tempObj.put("column5", "column5"+i);
			tempObj.put("column6", "column6"+i);
			tempObj.put("column7", "column7"+i);
			tempObj.put("column8", "column8"+i);
			tempObj.put("column9", "column9"+i);
			tempObj.put("column10", "column10"+i);
			tempObj.put("column11", "column11"+i);
			tempObj.put("column12", "column12"+i);
			tempObj.put("column13", "column13"+i);
			tempObj.put("column14", "column14"+i);
			tempObj.put("column15", "column15"+i);
			tempObj.put("column16", "column16"+i);
			tempObj.put("column17", "column17"+i);
			tempObj.put("column18", "column18"+i);
			tempObj.put("column19", "column19"+i);
			
			if(numRandom == 1 && i != 0) {
				
				int parentNum = (int)Math.round(Math.random() * (ja.size()-1));
				System.out.println(parentNum);
				JSONObject object = (JSONObject)ja.get(parentNum);
				String strParent = object.get("value").toString();
				tempObj.put("parent", strParent);
			}
			ja.add(tempObj);
		}
		return ja;
	}
	
	private String encodingDownloadFileName(HttpServletRequest request, HttpServletResponse response, String downloadFileName) throws UnsupportedEncodingException {
		String userAgent = request.getHeader("User-Agent");
		
		if(userAgent.contains("MSIE") || userAgent.contains("Chrome") || userAgent.contains("Firefox") ||(userAgent.contains("Windows") && userAgent.contains("Trident"))){
			downloadFileName = URLEncoder.encode(downloadFileName, "utf-8");
			downloadFileName = downloadFileName.replaceAll("\\+","%20");
			
			response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadFileName + "\";filename*=UTF-8''" + downloadFileName);
        } else {
    		response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadFileName + "\"");
        }
		
		return downloadFileName;
	}
}
