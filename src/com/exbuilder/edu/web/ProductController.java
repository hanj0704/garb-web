package com.exbuilder.edu.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.ws.Response;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.jasper.tagplugins.jstl.core.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.View;

import com.cleopatra.XBConfig;
import com.cleopatra.json.JSONObject;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.UploadFile;
import com.cleopatra.spring.JSONDataView;
import com.cleopatra.spring.TSVDataView;
import com.cleopatra.spring.UIView;
import com.exbuilder.edu.dao.ProductDAO;
import com.fasterxml.jackson.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;

import jdk.nashorn.internal.parser.JSONParser;

@Controller
@RequestMapping("/cisweb")
public class ProductController {

	public ProductController() {
	}
	
	public class City{
		private String column1;
		private String column2;
		
		public String getCol1(){
			return column1;
		}
		public void setCol1(String cols1){
			this.column1 = cols1;
		}
		public String getCol2(){
			return column2;
		}
		public void setCol2(String cols2){
			this.column2 = cols2;
		}
	}
	@Autowired
	private ProductDAO productDao;

	@RequestMapping("/change.do")
	public View change(HttpServletRequest req, HttpServletResponse res, DataRequest dataRequest) throws IOException {
		
		String queryString = URLDecoder.decode(req.getQueryString(), "UTF-8");
		String[] paramStrings = queryString.split("=");
//		req.getServletContext().getResourcePaths(arg0)
//		File file = new File("");
//		InputStream ip = new FileInputStream(file);
//		String.valueOf(1).getBytes();
		
		InputStream in = new FileInputStream("");
		String a= "aa";
		a.getBytes();
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(res.getOutputStream());
//		gzipOutputStream.write();
		String tempStr ;
		tempStr = paramStrings[1];
//		for(int i = 0; i < paramStrings.length ; i++) {
//			tempStr = paramStrings[i].split("=");
//			paramMap.put(tempStr[0], tempStr[1]);
//		}
		
		Map<String,Object> initParam = new HashMap<>();
		initParam.put("paramString", tempStr);
		return new UIView("/gb/202001/test2.clx",initParam);
		
		
	}
	@RequestMapping("/getMatList.do")
	public View getmatList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws IOException {
		
		System.out.println(dataRequest.getParameterGroup("ds3"));
		return new JSONDataView();
	}
	@CrossOrigin
	@RequestMapping("/getList.do")
	public View init(HttpServletRequest req, HttpServletResponse res, DataRequest dataRequest) throws IOException {

		// Random random = new Random();
		// SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
		// SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		// Date date = new Date();
		
		res.setHeader("Access-Control-Allow-Headers", "*");
		res.setHeader("Access_control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Origin", "*");
		
		
		List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		
		System.out.println(req.getAttribute("com.cleopatra.data_request"));
		int hans = RandomUtils.nextInt(2000);
		for (int i = 0; i < hans; i++) {
			Map<String, Object> rowData = new HashMap<String, Object>();

			rowData.put("rowNum", i);
			rowData.put("totCnt", 1190 + i);
			rowData.put("schId", null);
			listData.add(rowData);
		}
//		res.addHeader("Access-Control-Allow-Origin", "*");
		dataRequest.setResponse("dsPlan", listData);
		res.setStatus(201, "IS202s");
		return new JSONDataView();
	}
	
	@ResponseBody
	@RequestMapping("/getList2.do")
	public View List(@RequestBody String body) throws IOException {
			
		
//		System.out.println(param.get("column1"));
		System.out.println(body);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/getList34.do")
	public View List(HttpServletRequest request, HttpServletResponse response,DataRequest datarequest) throws IOException {
		
		System.out.println(request.getAttribute("com.cleopatra.data_request"));
		
		return new JSONDataView();
	}
	public List<Map<String,Object>> returnList(int han){
	List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		
		int hans = han;
		for (int i = 0; i < hans; i++) {
			Map<String, Object> rowData = new HashMap<String, Object>();

			rowData.put("rowNum", i);
			rowData.put("totCnt", 1190 + i);
			rowData.put("schId", null);
			listData.add(rowData);
		}
		return listData;
	}
	@ResponseBody
	@RequestMapping("/encode.do")
	public void list2(HttpServletRequest req, HttpServletResponse response) throws Exception{
		
//		Map<String,Object> resMap = new HashMap<>();
//		
//		resMap.put("grd1", returnList(1));
//		resMap.put("tre1",returnList(1));
//		resMap.put("cmb1",returnList(1));
//		JSONObject jsons = new JSONObject(resMap);
//		
//		PrintWriter out = null;
//		out = response.getWriter();
//		out.write(jsons.toString());
//		out.flush();
//		out.close();
		response.setHeader("Content-Encoding","gzip");
		response.setHeader("Content-Type","application/json");

		InputStream input = null ;
		OutputStream output  =null ;

		try {
		int readCount = 0 ;
		input =  new FileInputStream("C:\\Users\\HANS\\Desktop\\dm.json"); 
		output  = new GZIPOutputStream(response.getOutputStream());
		byte[] buffer = new byte[1024]; 
			while ((readCount = input.read(buffer)) != -1) {
				 
				output.write(buffer,0,readCount);
			}
			
		} catch(Exception e){}
		finally{ 
			
			
			if( input != null ){
				input.close();
				input = null ;
			}
			if( output != null ){
				output.flush();
				output.close();
				output = null ;
			}
		}
//		String str = "abcd";
//		InputStream in = new FileInputStream("C:\\Users\\HANS\\Desktop\\dm.json");
//		OutputStream out  = new GZIPOutputStream(response.getOutputStream());
//		byte[] buffer = new byte[1024];
//		
//		while(true) {
//			int count = in.read(buffer);
//
//			if(count == -1) {
//				break;
//			}
////			System.out.println(str.getBytes());
//			System.out.println(String.valueOf(count).getBytes());
//			out.write(buffer);
//		}
//		response.setHeader("Content-Encoding","gzip");
//		in.close();
//		out.flush();
//		out.close();
//		out = null;
	}
//	@PostMapping("/kobong.do")
//	public String test(Connection conn, HttpServletRequest request, HttpServletResponse response, RequestData requestData) throws IOException {
//		EntityManager em = emf.createEntityManager();
//		CMN_RES_MSG  cmn_res_msg = new CMN_RES_MSG("test", "test", "test", "test", "test");
//		em.persist(cmn_res_msg);
////		
//		JPAQueryFactory query = new JPAQueryFactory(em); 
//		QCMN_RES_MSG qHello = QCMN_RES_MSG.cMN_RES_MSG; 
//		List<Tuple> result = query.select(qHello.MSG, qHello.MSG_CD).from(qHello).fetch();
//		
//		for (Tuple row : result) {
//			
//		     System.out.println("MSG " + row.get(qHello.MSG));
//		}
////		return "";
//
//
//	}
	@RequestMapping("/upload.do")
	public View upload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws IOException {
		
		String root = XBConfig.getInstance().getFileUploadConfig().getTempDir();
		System.out.println(root);
		Map<String,UploadFile[]> files = dataRequest.getUploadFiles();
		
		Set<Entry<String,UploadFile[]>> entrySet = files.entrySet();
		
		for(Entry<String,UploadFile[]> entrys : entrySet) {
			
			UploadFile[] fileA = entrys.getValue();
			for(UploadFile file : fileA) {
				File oldFile = file.getFile();
				String fileName = file.getFileName();
				
				File saveFile = new File(root+"/"+fileName);
				FileCopyUtils.copy(oldFile, saveFile);
				
				response.setHeader("Content-Length", String.valueOf(saveFile.length()));
				oldFile.delete();
			}
		}
		return new JSONDataView();
	}
	
	@RequestMapping("/getList4.do")
	public @ResponseBody String save(@RequestBody String param) throws IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> input = mapper.readValue(param,new TypeReference<Map<String,Object>>(){});
		System.out.println(input);
		return new String();
	}
	
	@RequestMapping("/getList3.do")
	public void list2(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws IOException {

		Map<String,Object> maps = new HashMap<>();
		
		maps.put("column1", "Aa");
		maps.put("column2", "bb");
		
		System.out.println(maps);
		dataRequest.setResponse("", "alert('HelloWorld';");
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
//		return maps;
	}
	
	@RequestMapping("/build.do")
	public void antTask(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Process process = null;
        Runtime runtime = Runtime.getRuntime();
        StringBuffer successOutput = new StringBuffer(); // 성공 스트링 버퍼
        StringBuffer errorOutput = new StringBuffer(); // 오류 스트링 버퍼
        BufferedReader successBufferReader = null; // 성공 버퍼
        BufferedReader errorBufferReader = null; // 오류 버퍼
        String msg = null; // 메시지
        File dir = new File("C:/myStation/workspace_Han/edu-ui1/ant");
        List<String> cmdList = new ArrayList<String>();
//        ProcessBuilder pb = new ProcessBuilder("C:/myStation/workspace_Han/edu-ui1/ant/bui.sh","/c","");
        
        // 운영체제 구분 (window, window 가 아니면 무조건 linux 로 판단)
        if (System.getProperty("os.name").indexOf("Windows") > -1) {
            cmdList.add("cmd.exe");
            cmdList.add("/c");
        } else {
            cmdList.add("/bin/sh");
            cmdList.add("-c");
        }
        // 명령어 셋팅
        cmdList.add("ant -DOUT_LOCA=101 -f build.xml");
        String[] array = cmdList.toArray(new String[cmdList.size()]);
 
        try {
 
            // 명령어 실행
            process = runtime.exec(array, null, dir);
 
            // shell 실행이 정상 동작했을 경우
            successBufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
 
            while ((msg = successBufferReader.readLine()) != null) {
            	if(msg.indexOf("ERROR") > 0) {
            		errorOutput.append(msg+System.getProperty("line.separator"));
            		process.destroy();
            	}
                successOutput.append(msg + System.getProperty("line.separator"));
            }
 
            // shell 실행시 에러가 발생했을 경우
            errorBufferReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
            while ((msg = errorBufferReader.readLine()) != null) {
            	System.out.println(msg);
                errorOutput.append(msg + System.getProperty("line.separator"));
            }
 
            // 프로세스의 수행이 끝날때까지 대기
            process.waitFor();
 
            // shell 실행이 정상 종료되었을 경우
//            if (process.exitValue() == 0) {
//                System.out.println("성공");
//                System.out.println(successOutput.toString());
//            } else {
//                // shell 실행이 비정상 종료되었을 경우
//                System.out.println("비정상 종료");
//                System.out.println(errorOutput.toString());
//            }
            if(errorOutput.length() > 0) {
            	System.out.println(errorOutput.toString());
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                process.destroy();
                if (successBufferReader != null) successBufferReader.close();
                if (errorBufferReader != null) errorBufferReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
		
//		Project p = 
//		File buildFile = new File("C:\\myStation\\workspace_Han\\edu-ui1\\ant\\build.xml");
//		Project p = new Project();
//		
//		System.out.println(buildFile.getAbsoluteFile());
//		p.setUserProperty("ant.file", buildFile.getAbsolutePath());
//		System.out.println(p);
//		p.fireBuildStarted();
//				p.init();
//		   p.executeTarget(p.getDefaultTarget());
		
//		Project p = new Project();

	}
	
	@RequestMapping("download.do")
	private void download(HttpServletRequest request, HttpServletResponse response, DataRequest datarequest) throws Exception{
		
		String message = "console.log('ㅁㅁㅁㅁ');";
		File file = new File("abc.js");
		FileWriter writer = null;
		try{
			
			writer = new FileWriter(file,true);
			writer.write(message);
			writer.flush();
		}catch(IOException e) {
			
			e.printStackTrace();
		}
		
		  String userAgent = request.getHeader("User-Agent");
		  boolean ie = userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("rv:11") > -1;
		  String fileName = null;
		  
		  if (ie) {
		   fileName = URLEncoder.encode(file.getName(), "utf-8");
		  } else {
		   fileName = new String(file.getName().getBytes("utf-8"),"iso-8859-1");
		  }
		  
		  response.setContentType("application/octet-stream");
		  response.setHeader("Content-Disposition","attachment;filename=\"" +fileName+"\";");
		  
		  FileInputStream fis=new FileInputStream(file);
		  BufferedInputStream bis=new BufferedInputStream(fis);
		  ServletOutputStream so=response.getOutputStream();
		  BufferedOutputStream bos=new BufferedOutputStream(so);
		  
		  byte[] data=new byte[2048];
		  int input=0;
		  while((input=bis.read(data))!=-1){
		   bos.write(data,0,input);
		   bos.flush();
		  }
		  
		  if(bos!=null) bos.close();
		  if(bis!=null) bis.close();
		  if(so!=null) so.close();
		  if(fis!=null) fis.close();
	}
	public static String calPlayTime(String vsType, String vsPlayTime, String vsDuration) throws Exception {
//		* SOM--> DURATION --> EOM 계산 방식
//		  - UI 함수
//		  - 파라미터 3개
//		    -->  vsType : DF -> Frame 까지 계산(8자리),  SS -> Sec 까지 계산 (6자리)
//		    -->  vsPlayTime                        -->SOM
//		    -->  vsDuration                        --> DURATION
//		   누적하여 계산된 값을 RETURN   --> EOM이 됩니다.

		int iHH1, iHH2, iMM1, iMM2, iSS1, iSS2, iFF1, iFF2;
		Long iCalFrame1, iCalFrame2, iRevFrame, iCalFrame;
		int iHH = 0, iMM = 0, iSS = 0, iFF = 0;
		String rtnPlayTime = "";

		if ( vsPlayTime == null || "".equals(vsPlayTime) ) {	//<<<<<<<<<<<<<<<<<<<<<<<<<<  000000 (8자리 초까지만 계산하여 RETURN)
			vsPlayTime = "00000000";
		}
		if ( vsDuration == null || "".equals(vsDuration) ) {	//<<<<<<<<<<<<<<<<<<<<<<<<<<  000000 (8자리 초까지만 계산하여 RETURN)
			vsDuration = "00000000";
		}

		if ( vsType != null && "DF".equals(vsType) ) {
			 iHH1 = Integer.parseInt(vsPlayTime.substring(0, 2)); // Integer(Mid(vsPlayTime, 1, 2))
			 iMM1 = Integer.parseInt(vsPlayTime.substring(2, 4)) + (iHH1 * 60);	// Integer(Mid(vsPlayTime, 3, 2)) + (iHH1 * 60)
			 iSS1 = Integer.parseInt(vsPlayTime.substring(4, 6));	//  Integer(Mid(vsPlayTime, 5, 2))
			 iFF1 = Integer.parseInt(vsPlayTime.substring(6, 8));	//  Integer(Mid(vsPlayTime, 7, 2))

			 iHH2 = Integer.parseInt(vsDuration.substring(0, 2)); // iHH2 = Integer(Mid(vsDuration, 1, 2))
			 iMM2 = Integer.parseInt(vsDuration.substring(2, 4)) + (iHH2 * 60);	// iMM2 = Integer(Mid(vsDuration, 3, 2)) + (iHH2 * 60)
			 iSS2 = Integer.parseInt(vsDuration.substring(4, 6));	//  iSS2 = Integer(Mid(vsDuration, 5, 2))
			 iFF2 = Integer.parseInt(vsDuration.substring(6, 8));	//  iFF2 = Integer(Mid(vsDuration, 7, 2))

			 // Frame 계산할 때에는 1분을 1800Frame으로 계산하자.
			 iCalFrame1 = (long)(iMM1 * 1800 + (Math.toIntExact(iMM1 / 10) * 2) + (iSS1 * 30) + iFF1 + 1);
			 iCalFrame2 = (long) (iMM2 * 1800 + (Math.toIntExact(iMM2 / 10) * 2) + (iSS2 * 30) + iFF2 + 1);
			 System.out.println(iCalFrame1);
			 System.out.println(iCalFrame2);
			 iCalFrame = iCalFrame1 + iCalFrame2-1 ;
			 iMM = Math.toIntExact (iCalFrame / 1800);
			 
			 iRevFrame = (long) (Math.toIntExact(iMM / 10) * 2);
			 if ( ((iCalFrame % 1800) - iRevFrame - 1) < 0 ) {//(Mod(iCalFrame, 1800) - iRevFrame - 1) < 0 THEN
//				  iMM --
//				  iRevFrame = (long) (Math.toIntExact(iMM / 10) * 2);
//				  iCalFrame = (iCalFrame % 1800) + 1800 - iRevFrame -1;
				  System.out.println("앙기모리");
			 } else {
				 System.out.println(iCalFrame%1800);
				  iCalFrame = (iCalFrame % 1800) - iRevFrame -1;
				  System.out.println("여긴가요");
				  System.out.println(iRevFrame-1);
			 }
			 
			 iHH = Math.toIntExact(iMM / 60);
			 iMM = iMM % 60;
			 iSS = Math.toIntExact(iCalFrame / 30);
			 iFF = Math.toIntExact (iCalFrame % 30);
			 rtnPlayTime = setLPad(Integer.toString(iHH), 2, "0") + setLPad(Integer.toString(iMM), 2, "0") + setLPad(Integer.toString(iSS), 2, "0") + setLPad(Integer.toString(iFF), 2, "0");
		}
		
		return rtnPlayTime;
	}
	
	public static String setLPad( String strContext, int iLen, String strChar ) {
		String strResult = "";
		StringBuilder sbAddChar = new StringBuilder();
		for( int i = strContext.length(); i < iLen; i++ ) {
			// iLen길이 만큼 strChar문자로 채운다.
			sbAddChar.append( strChar );
		}
		strResult = sbAddChar + strContext; // LPAD이므로, 채울문자열 + 원래문자열로 Concate한다.
		return strResult;
	}
}

