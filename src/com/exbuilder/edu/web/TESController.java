package com.exbuilder.edu.web;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSplitPaneUI;

import org.apache.pdfbox.*;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontFactory;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.PDFontSetting;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.PDFText2HTML;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import com.cleopatra.json.JSONArray;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.UploadFile;
import com.cleopatra.spring.JSONDataView;
import com.cleopatra.spring.UIView;

@Controller
@RequestMapping("/tes")
public class TESController {

	public TESController(){
		
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
		
		request.setCharacterEncoding("text/html; charset=UTF-8");
		InputStreamReader input = new InputStreamReader(new FileInputStream(file1),"euc-kr");
	
//		for(Entry<String,UploadFile[]> entrys : entrySet) {
//			
//			UploadFile[] fileA = entrys.getValue();
//		
//			for(UploadFile file : fileA) {
//				File oldFile = file.getFile();
//				
//				System.out.println(oldFile);
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
