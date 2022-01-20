package com.exbuilder.edu.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cleopatra.export.CSVExporter;
import com.cleopatra.export.Exporter;
import com.cleopatra.export.ExporterFactory;
import com.cleopatra.export.ExporterFactory.EXPORTTYPE;
import com.cleopatra.export.source.DataSource;
import com.cleopatra.export.source.JSONDataSourceBuilder;
import com.cleopatra.export.target.HttpResponseOutputTarget;
import com.cleopatra.export.target.OutputTarget;
import com.cleopatra.json.JSONArray;
import com.cleopatra.json.JSONObject;
import com.cleopatra.json.XML;
import com.cleopatra.protocol.data.DataRequest;


@Controller
@RequestMapping("/export")
public class CleopatraFileExportController {

	
	public CleopatraFileExportController() {
	}
	
//	@PostConstruct
//	private void init() {
//		PDFExporter.setDefaultTruTypeFont(new java.io.File("C:/work/dev/workspaces/exbuilder5/pdfbox-pilot/doc/NANUMGOTHIC.TTF"));
//		PDFExporter.setDefaultTitleFontSize(8);
//		PDFExporter.setDefaultTableFontSize(6);
//	}
	
	/**
	 * PDF Export
	 * @param request
	 * @param response
	 * @param fileName
	 * @throws IOException
	 */
	@RequestMapping("/{fileName}.pdf")
	public void exportPDF(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName) throws IOException {
		String downloadFileName = fileName + ".pdf";
		downloadFileName = this.encodingDownloadFileName(request, response, downloadFileName);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/pdf");
		
		this.export(request, response, downloadFileName, EXPORTTYPE.PDF);
	}

	@RequestMapping("/{fileName}.csv")
	public void exportCSV(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException {
		String downloadFileName = fileName + ".csv";
		downloadFileName = this.encodingDownloadFileName(request, response, downloadFileName);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/csv;charset=utf-8");
		
		this.export(request, response, fileName, EXPORTTYPE.CSV);
	}
	
	@RequestMapping("/{fileName}.xls")
	public void exportXLS(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException {
		String downloadFileName = fileName + ".xls";
		downloadFileName = this.encodingDownloadFileName(request, response, downloadFileName);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		this.export(request, response, fileName, EXPORTTYPE.XLS);
	}
	
	@RequestMapping("/{fileName}.xlsx")
	public void exportXLSX(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException {
		String downloadFileName = fileName + ".xlsx";
		downloadFileName = this.encodingDownloadFileName(request, response, downloadFileName);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
//		this.export(request, response, fileName, EXPORTTYPE.XLSX);
		this.export(request, response, fileName, EXPORTTYPE.SXLSX);
	}
	
	@RequestMapping("/{fileName}.xml")
	public void exportXML(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest, @PathVariable("fileName") String fileName) throws IOException {
		request.setCharacterEncoding("utf-8");
		JSONObject reqObj = dataRequest.getRequestObject();
		
		String downloadFileName = fileName + ".xml";
		downloadFileName = this.encodingDownloadFileName(request, response, downloadFileName);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/xml");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
//		ServletOutputStream out = response.getOutputStream();
		PrintWriter out = response.getWriter();
//		out.print(JSONML.toString(reqObj));
		out.print(this.toXMLString(reqObj, "root", "list", "val"));
		out.flush();
	}
	
	@RequestMapping("/{fileName}.json")
	public void exportJSON(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest, @PathVariable("fileName") String fileName) throws IOException {
		request.setCharacterEncoding("utf-8");
		JSONObject reqObj = dataRequest.getRequestObject();
		
		String downloadFileName = fileName + ".json";
		downloadFileName = this.encodingDownloadFileName(request, response, downloadFileName);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
//		ServletOutputStream out = response.getOutputStream();
		PrintWriter out = response.getWriter();
		out.print(reqObj.toString(2));
		out.flush();
	}
	
	@RequestMapping("/{fileName}.txt")
	public void exportTXT(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException {
		request.setCharacterEncoding("utf-8");
		
		String downloadFileName = fileName + ".txt";
		downloadFileName = this.encodingDownloadFileName(request, response, downloadFileName);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		String newFileName = URLDecoder.decode(fileName, "utf-8");
		DataSource dataSource = JSONDataSourceBuilder.build(request, newFileName);
		OutputTarget outputTarget = new HttpResponseOutputTarget(response);
		
		ExporterFactory exporterFactory = ExporterFactory.getInstance();
		CSVExporter exporter = (CSVExporter)exporterFactory.getExporter(EXPORTTYPE.CSV);
		exporter.setAutoWrap(false);
		exporter.setApplyLayout(true);
		exporter.setDelimiter("\t");
		exporter.export(dataSource, outputTarget);
		
		response.flushBuffer();
	}
	
	private void export(HttpServletRequest request, HttpServletResponse response, String fileName, EXPORTTYPE type) throws IOException {
		request.setCharacterEncoding("utf-8");
		String newFileName = URLDecoder.decode(fileName, "utf-8");
		DataSource dataSource = JSONDataSourceBuilder.build(request, newFileName);
		OutputTarget outputTarget = new HttpResponseOutputTarget(response);
		
		ExporterFactory exporterFactory = ExporterFactory.getInstance();
		Exporter exporter = exporterFactory.getExporter(type);
		exporter.export(dataSource, outputTarget);
		
		response.flushBuffer();
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
	
	// JSON to XML
	private String toXMLString(JSONObject jsonObj, String tagName, String repeatTagName, String valueTagName) {
		StringBuilder xmlString = new StringBuilder();
		
		xmlString.append('<').append(tagName).append('>').append('\n');
		
		Set<String> keySet = jsonObj.keySet();
		for(String key : keySet) {
			Object value = jsonObj.get(key);
			if(value == null) {
				xmlString.append('<').append(key).append("/>").append('\n');
			} else if(value instanceof JSONObject) {
				xmlString.append(this.toXMLString((JSONObject)value, key, repeatTagName, valueTagName));
			} else if(value instanceof JSONArray) {
				xmlString.append(this.toXMLString((JSONArray)value, key, repeatTagName, valueTagName));
			} else {
				xmlString.append('<').append(key).append('>');
				xmlString.append(XML.escape(value.toString()));
				xmlString.append("</").append(key).append('>').append('\n');
			}
		}
		
		xmlString.append("</").append(tagName).append('>').append('\n');
		
		return xmlString.toString();
	}
	
	private String toXMLString(JSONArray jsonArr, String tagName, String repeatTagName, String valueTagName) {
		StringBuilder xmlString = new StringBuilder();
		
		xmlString.append('<').append(tagName).append('>').append('\n');
		
		int length = jsonArr.length();
		for(int i = 0; i < length; i++) {
			Object value = jsonArr.get(i);
			if(value == null) {
				xmlString.append('<').append(repeatTagName).append("/>").append('\n');
			} else if(value instanceof JSONObject) {
				xmlString.append(this.toXMLString((JSONObject)value, repeatTagName, repeatTagName, valueTagName));
			} else if(value instanceof JSONArray) {
				xmlString.append(this.toXMLString((JSONArray)value, repeatTagName, repeatTagName, valueTagName));
			} else {
				xmlString.append('<').append(valueTagName).append('>');
				xmlString.append(XML.escape(value.toString()));
				xmlString.append("</").append(valueTagName).append('>').append('\n');
			}
		}
		
		xmlString.append("</").append(tagName).append('>').append('\n');
		
		return xmlString.toString();
	}
}
