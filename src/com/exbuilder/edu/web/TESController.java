package com.exbuilder.edu.web;

import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSplitPaneUI;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.pdfbox.*;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontFactory;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.PDFontSetting;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.PDFText2HTML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.UploadFile;
import com.cleopatra.spring.JSONDataView;
import com.cleopatra.spring.UIView;
import com.exbuilder.edu.dto.TestVO;

@Controller
@RequestMapping("/tes")
public class TESController {

	public TESController(){
		
	}
	public String getS (String param){
		
		return "";
	}
	@RequestMapping(value="/mci.do")
	public void getMci(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String,Object> parameter
//			DataRequest datareq
			) throws IOException, ClassNotFoundException {
//		String parameter = datareq.getParameter("packageUrl");
//		String parameter = null;
//		System.out.println(parameter);
		String packageUrl = parameter.get("packageUrl").toString();
//		StringBuilder builder = new StringBuilder();
//		BufferedReader reader = null;
//		try {
//			ServletInputStream inputStreamq = request.getInputStream();
//			if(inputStreamq != null) {
//				reader = new BufferedReader(new InputStreamReader(inputStreamq));
//				char[] charBuffer = new char[128];
//				int byteRead = -1;
//				while((byteRead = reader.read(charBuffer))> 0) {
//					builder.append(charBuffer,0,byteRead);
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally {
//			if(reader != null) {
//				reader.close();
//				System.out.println(builder.length());
//				parameter = builder.toString();
//				System.out.println(parameter);
//			}
//		}
//		Enumeration<String> parameterNames = request.getParameterNames();
//		System.out.println(parameterNames);
//		String nextElement = parameterNames.nextElement();
//		if(!"".equals(nextElement) && nextElement != null) {
//			System.out.println(nextElement);
//			while(!"".equals(nextElement) && nextElement != null){
//				nextElement = parameterNames.nextElement();
//			}
//		}
//		Package pack = Package.getPackage("com.exbuilder.edu.web");
		Class<?> forName = Class.forName(packageUrl);
		String[] value = forName.getAnnotation(RequestMapping.class).value();
		String join = String.join("", value);
		List<Map<String,String>> lists = new ArrayList<Map<String,String>>();
		Method[] methods = forName.getMethods();
		for (Method method : methods) {
			String name = method.getName();
//			if(name.indexOf("List") > -1) {
				
				Map<String,String> paramMap = new HashMap<String, String>();
				RequestMapping annotation = method.getAnnotation(RequestMapping.class);
				if(annotation != null) {
					
					String[] mappingInfo = annotation.value();
					if(mappingInfo.length > 0) {
						
						String mappingUrl = String.join("", mappingInfo);
						paramMap.put("interfacename", name);
						paramMap.put("classUrl", join);
						paramMap.put("mappingUrl", mappingUrl);
						lists.add(paramMap);
					}
				}
//			}
		}
		OutputStream output = null;
		StringBuffer buffer = new StringBuffer();
		System.out.println(lists.toString());
//		String jsonString = JSONArray.toJSONString(lists);
		JSONObject obj = new JSONObject();
		obj.put("list", lists);
		String jsonString2 = obj.toJSONString();
		System.out.println(jsonString2);
//		System.out.println(jsonString);
		buffer.append(jsonString2);
		try {
			output = response.getOutputStream();
			output.write(buffer.toString().getBytes());
		} finally {
			output.flush();
			output.close();
			output = null;
		}
//		datareq.setResponse("list", lists);
//		return new JSONDataView();
//		Class<? extends Package> class1 = pack.getClass();
//		System.out.println(class1);
	}
	@RequestMapping("/pdfhtml.do")
	public void getHtml(HttpServletRequest request, HttpServletResponse response, DataRequest req) throws IOException {
		Map<String,UploadFile[]> uploadFiles = req.getUploadFiles();
		UploadFile[] uploadFiles2 = uploadFiles.get("file1");
		File file1 = uploadFiles2[0].getFile();
		PDDocument document = null;
		try {
			document = Loader.loadPDF(file1);
			PDFText2HTML html = new PDFText2HTML();
//			String string = html.getTextMatrix().toString();
//			System.out.println(string);
			PDPageTree pages = Loader.loadPDF(file1).getPages();
			PDPage pdPage = pages.get(1);
			String text2 = html.getText(document);
			PDResources resources = html.getCurrentPage().getResources();
			Iterable<COSName> xObjectNames = resources.getXObjectNames();
			for (COSName cosName : xObjectNames) {
				PDXObject xObject = resources.getXObject(cosName);
				if(xObject instanceof PDImageXObject) {
					BufferedImage bImage = ((PDImageXObject) xObject).getImage();
					System.out.println(bImage);
				}
			}
//			PDResources resources2 = document.getPage(5).getResources();
//			Iterable<COSName> xObjectNames = resources2.getXObjectNames();
//			for (COSName cosName : xObjectNames) {
//				System.out.println(cosName);
//				PDXObject xObject = resources2.getXObject(cosName);
//				COSStream cosObject = xObject.getCOSObject();
//				cosObject.
//			}
//			resources.get
//			Pattern pat = Pattern.compile("&#[0-9]+;");
//			Matcher mat = pat.matcher(text2);
//			while(mat.find()) {
//				String finder = mat.group();
//				String group = finder.replaceAll("[&#;]", "");
//				String hexString = "\\u"+Integer.toHexString(Integer.parseInt(group));
//				String lol = StringEscapeUtils.unescapeJava(hexString);
//				text2 = text2.replaceFirst(finder,lol);
//			}
//			downloadPDF(request, response, text2);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
		}
	}
	public void downloadPDF(HttpServletRequest request,HttpServletResponse response, String str) {
		File saveFile = new File("test.html");
		FileWriterWithEncoding writer = null;
		try{
			writer = new FileWriterWithEncoding(saveFile, "UTF-8");
			writer.write(str);
			writer.flush();
			
			response.setContentType("application/octet-stream");
			String fileName = new String(saveFile.getName().getBytes("utf-8"),"iso-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\";");
			
			FileInputStream fis = new FileInputStream(saveFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ServletOutputStream so = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(so);
			
			byte[] data = new byte[2048];
			int input = 0;
			while((input=bis.read(data))!= -1) {
				bos.write(data,0,input);
				bos.flush();
			}
			if(bos != null) bos.close();
			if(so != null) so.close();
			if(bis != null) bis.close();
			if(fis != null) fis.close();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
		}
	}
	@RequestMapping("/pdf.do")
	public void getPDF(HttpServletRequest request, HttpServletResponse response, DataRequest datareq) throws IOException {
//		pp.parse(arg0)
		Map<String, UploadFile[]> uploadFiles = datareq.getUploadFiles();
		
		Set<Entry<String,UploadFile[]>> entrySet = uploadFiles.entrySet();
		
		UploadFile[] uploadFiles2 = uploadFiles.get("file1");
		UploadFile[] uploadFiles3 = uploadFiles.get("file2");
		File file1 = uploadFiles2[0].getFile();
		File file2 = uploadFiles3[0].getFile();
		System.out.println(file1);
		System.out.println(file2);
		
//		request.setCharacterEncoding("text/html;charset=UTF-8");
		InputStreamReader input = new InputStreamReader(new FileInputStream(file1),"euc-kr");
	
//		for(Entry<String,UploadFile[]> entrys : entrySet) {
//			
//			UploadFile[] fileA = entrys.getValue();
//		
//			for(UploadFile file : fileA) {
//				File oldFile = file.getFile();
//				
//				System.out.println(oldFile);
				File writer = new File("file.html");
//				writer.
				PDDocument document =null;
				try{
					BufferedReader buf = new BufferedReader(input);
					
					OutputStream out = response.getOutputStream();
					document = Loader.loadPDF(file1);
//					PDPageTree pages = document.getPages();
					PDFTextStripper strip = new PDFTextStripper();
					PDFText2HTML htm = new PDFText2HTML();
					PDType0Font font = PDType0Font.load(document, file2);
					String text2 = htm.getText(document);
					String text = strip.getText(document);
				
					System.out.println(text2);
//					text2.code
//					for(int idx=0; idx < pages.getCount();idx++) {
//						PDPage pdPage = pages.get(idx);
//						InputStream contents = pdPage.getContents();
//						System.out.println(contents.read());
//					}
				}catch (Exception e) {
					// TODO: handle exception
				} finally {
					if(document != null) {
						document.close();
					}
				}
//			}
//		}
	}
	
	@RequestMapping("/list.do")
	public void getList(HttpServletRequest request, HttpServletResponse response, DataRequest datareq)throws IOException, ParseException {
		
		System.out.println(request.getRemoteAddr());
		ParameterGroup paramGrp = datareq.getParameterGroup("");
		JSONParser parser= new JSONParser();
		
		JSONObject jsonob = new JSONObject();
//		String strs = "{\"LIST\": {\"CL\": [{\"SLOT_NO\":\"1\",\"WAFER_NO\":\"21\",\"WAFER_LOADED\":\"Y\"},{\"SLOT_NO\":\"2\",\"WAFER_NO\":\"22\",\"WAFER_LOADED\":\"Y\"},{\"SLOT_NO\":\"3\",\"WAFER_NO\":\"23\",\"WAFER_LOADED\":\"Y\"}],\"LP\" :[{\"LP_ID\":\"1\",\"LP_EXIST\":\"Y\",\"MAX_WAFER_CNT\":25,\"FOUP_LOADED\":\"Y\",\"LP_DOOR_STATUS\":\"N\"},{\"LP_ID\":\"2\",\"LP_EXIST\":\"Y\",\"MAX_WAFER_CNT\":25,\"FOUP_LOADED\":\"Y\",\"LP_DOOR_STATUS\":\"N\"},{\"LP_ID\":\"3\",\"LP_EXIST\":\"Y\",\"MAX_WAFER_CNT\":25,\"FOUP_LOADED\":\"Y\",\"LP_DOOR_STATUS\":\"N\"},{\"LP_ID\":\"4\",\"LP_EXIST\":\"Y\",\"MAX_WAFER_CNT\":25,\"FOUP_LOADED\":\"Y\",\"LP_DOOR_STATUS\":\"N\"}]},\"MAP\": {\"TM\": {\"TM_ROTATE\":90,\"A_ARM_EXIST\":\"TRUE\",\"B_ARM_EXIST\":\"FALSE\"},\"AL\" :{\"WAFER_LOADED\": \"Y\",\"WAFER_NO\" : \"13\"}}}";
//		String strs = "{\"books\":[{\"genre\":\"소설\",\"price\":\"100\",\"name\":\"사람은 무엇으로 사는가?\",\"writer\":\"톨스토이\",\"publisher\":\"톨스토이 출판사\"},{\"genre\":\"소설\",\"price\":\"300\",\"name\":\"홍길동전\",\"writer\":\"허균\",\"publisher\":\"허균 출판사\"},{\"genre\":\"소설\",\"price\":\"900\",\"name\":\"레미제라블\",\"writer\":\"빅토르 위고\",\"publisher\":\"빅토르 위고 출판사\"}],\"persons\":[{\"nickname\":\"남궁민수\",\"age\":\"25\",\"name\":\"송강호\",\"gender\":\"남자\"},{\"nickname\":\"예니콜\",\"age\":\"21\",\"name\":\"전지현\",\"gender\":\"여자\"}]}";
//		Object parsedObj = parser.parse(strs);
//		jsonob.put("res",parsedObj);
		
		OutputStream out = null;
		
		try{
			
			out = response.getOutputStream();
			out.write(jsonob.toJSONString().getBytes());
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(out != null){
				response.setContentLength(jsonob.toJSONString().getBytes().length);
				out.flush();
				out.close();
				out= null;
			}
		}
		
//		return new JSONDataView();
	}
	@RequestMapping("/page.do")
	public View pageChange(HttpServletRequest req, HttpServletResponse res, DataRequest datareq) throws IOException {
		
		UIView uiv = new UIView("/202202/tester.clx");
		
		return uiv;
	}
	
	@ResponseBody
	@RequestMapping("/fiTest.do")
	public void fitest(@RequestParam(value="file1", required = false) MultipartFile[] mf) throws IOException {
		System.out.println("zz");
		System.out.println(mf);
		System.out.println(mf.length);
		System.out.println(mf[1].getOriginalFilename());
//		System.out.println(mf.getName());
//		System.out.println(mf.getOriginalFilename());
	}
	
	@RequestMapping("/fileDabal.do")
	public void fileTest(HttpServletRequest req, HttpServletResponse res, DataRequest datareq) throws IOException {
		
		
		Map<String, UploadFile[]> uploadFiles = datareq.getUploadFiles();
		UploadFile[] uploadFiles2 = uploadFiles.get("tester");
	}
}
