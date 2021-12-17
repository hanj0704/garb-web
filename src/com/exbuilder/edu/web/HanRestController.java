package com.exbuilder.edu.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hans")
public class HanRestController {

	@RequestMapping("/test.do")
	public void corsGetMethodApi(HttpServletRequest req, HttpServletResponse res) {
		
//		System.out.println(request.getSession().getServletContext().getRealPath("/"));
//		String urls = request.getSession().getServletContext().getRealPath("/")+"ui/data/massData.json";
//		response.setHeader("Content-Encoding","gzip");
//		response.setHeader("Content-Type","application/json");
//
//		System.out.println( urls );
//
//		InputStream input = null ;
//		OutputStream output  =null ;
//
//		try {
//		int readCount = 0 ;
//		input = new FileInputStream(urls);  
//		output  = new GZIPOutputStream(response.getOutputStream());
//		byte[] buffer = new byte[1024]; 
//			while ((readCount = input.read(buffer)) != -1) {
//				 
//				output.write(buffer,0,readCount);
//			}
//			
//		} catch(Exception e){}
//		finally{ 
//			
//			
//			if( input != null ){
//				input.close();
//				input = null ;
//			}
//			if( output != null ){
//				output.flush();
//				output.close();
//				output = null ;
//			}
//		}
		 try {
             File file = new File("C:/Users/HANS/Documents/test/tester2.docx");
             FileInputStream fis = new FileInputStream(file.getAbsolutePath());

             XWPFDocument document = new XWPFDocument(fis);

             List<XWPFParagraph> paragraphs = document.getParagraphs();
             
//             extractImages(document);
             Iterator<XWPFParagraph> iter = paragraphs.iterator();
             
             while(iter.hasNext()) {
            	 XWPFParagraph para = iter.next();
            	
            	 System.out.println(para.getParagraphText());
            	 System.out.println(para.getPictureText());
            	 System.out.println(para.getDocument());
            	 System.out.println(para.getText());
             }
//             List<IBodyElement> ibo = document.getBodyElements();
//             Iterator<IBodyElement> it = ibo.iterator();
//             
//             while(it.hasNext()) {
//            	 IBodyElement ib = it.next();
//            	 Iterator<IBodyElement> itt = ib.getBody().getBodyElements().iterator();
//            	 while(itt.hasNext()) {
//            		 
//            		 System.out.println(itt.next().getPart());
//            	 }
//             }
//             for (XWPFParagraph para : paragraphs) {
//                 System.out.println(para.getText());
//             }
             fis.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
	}
	
	public static void extractImages(XWPFDocument docx){
		try {
			List<XWPFPictureData> piclist = docx.getAllPictures();
			
			Iterator<XWPFPictureData> iter = piclist.iterator();
			
			int i = 0 ; 
			while(iter.hasNext()) {
				XWPFPictureData pic = iter.next();
				byte[] bytepic = pic.getData();
				BufferedImage imag = ImageIO.read(new ByteArrayInputStream(bytepic));
				ImageIO.write(imag, "jpg", new File("C:/Users/HANS/Documents/test/"+pic.getFileName()));
				i++;
			}
		} catch(Exception e ) {
			e.printStackTrace();
		}
	}
}


