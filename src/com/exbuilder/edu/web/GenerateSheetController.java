package com.exbuilder.edu.web;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.View;
import javax.xml.bind.DatatypeConverter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

@Controller
@RequestMapping("/download")
public class GenerateSheetController {
	
	
	@RequestMapping("/excelChart.do")
	public void getChartImage(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGrp = dataRequest.getParameterGroup("dmParam");
		
		String imgStr = paramGrp.getValue("imgStr");
		
		generateSpreadSheet(request, response,imgStr);
	}

	public void generateSpreadSheet(HttpServletRequest req, HttpServletResponse res, String strBlob) throws IOException {
		XSSFWorkbook my_workbook = new XSSFWorkbook();
        XSSFSheet my_sheet = my_workbook.createSheet("MyLogo");         

        String pngImageURL = strBlob;
		
		String encodingPrefix = "base64,";
		int contentStaratIndex = pngImageURL.indexOf(encodingPrefix) + encodingPrefix.length();
		byte[] imageData1 = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL.substring(contentStaratIndex));
       
        int my_picture_id = my_workbook.addPicture(imageData1, XSSFWorkbook.PICTURE_TYPE_PNG);
        XSSFDrawing drawing = my_sheet.createDrawingPatriarch();
        XSSFClientAnchor my_anchor = new XSSFClientAnchor();
        my_anchor.setCol1(2);
        my_anchor.setRow1(1);           
        XSSFPicture  my_picture = drawing.createPicture(my_anchor, my_picture_id);
        my_picture.resize();            
        String fileName = "EXPORT_EXPORT";
    
        String userAgent = req.getHeader("User-Agent");
		
		if(userAgent.contains("MSIE") || userAgent.contains("Chrome") || userAgent.contains("Firefox") ||(userAgent.contains("Windows") && userAgent.contains("Trident"))){
			fileName = URLEncoder.encode(fileName, "utf-8");
			fileName = fileName.replaceAll("\\+","%20");
			
			res.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\";filename*=UTF-8''" + fileName);
        } else {
    		res.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        }
        
        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xlsx");
        
        OutputStream os = null;
        os = res.getOutputStream();
        
        my_workbook.write(os);
        my_workbook.close();
        os.flush();
        os.close();
}
	
}