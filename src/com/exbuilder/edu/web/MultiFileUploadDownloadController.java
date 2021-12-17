package com.exbuilder.edu.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.XBConfig;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

@Controller
@RequestMapping("/multifile")
public class MultiFileUploadDownloadController {

	public MultiFileUploadDownloadController() {
	}

	@Autowired
	private ServletContext servletContext;

	@RequestMapping("/list.do")
	public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) {
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();

		File root = new File(XBConfig.getInstance().getFileUploadConfig().getTempDir());
		File[] files = root.listFiles();
		for (File file : files) {
			Map<String, Object> fileInfo = new HashMap<String, Object>();

			String fileName = file.getName();
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			long fileSize = file.length();

			fileInfo.put("fileName", fileName);
			fileInfo.put("fileSize", fileSizeformat(fileSize, 2));

			fileList.add(fileInfo);
		}

		dataRequest.setResponse("dsFileList", fileList);

		return new JSONDataView();
	}

	@RequestMapping("/upload.do")
	public View upload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) {

		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();

		String tempPath = XBConfig.getInstance().getFileUploadConfig().getTempDir();
		String newPath = tempPath + File.separator + "upload";
		File folder = new File(newPath);
		if (!folder.exists()) {
			folder.mkdir();
		}

		// files
		Map<String, File[]> uploadFiles = dataRequest.getFiles();
		Set<Entry<String, File[]>> entries = uploadFiles.entrySet();
		for (Entry<String, File[]> entry : entries) {
			String name = entry.getKey();
			File[] files = entry.getValue();

			List<String> fileNames = new ArrayList<String>();
			int index = 0;
			for (File file : files) {
				if (file.exists() && file.isFile()) {

					String strOriginalfileName = file.getName().replace(".tmp", "");
					long fileSize = file.length();
					fileNames.add(strOriginalfileName);

					// 파일 이동
					File newFile = new File(folder.getPath() + File.separator + strOriginalfileName);
					file.renameTo(newFile);
					
					// 데이터 저장을 위한 파일 데이터 저장
					Map<String, Object> fileInfo = new HashMap<String, Object>();
					fileInfo.put("fileName", strOriginalfileName);
					fileInfo.put("filePath", newFile.getPath().replace(tempPath, ""));
					fileInfo.put("fileSize", fileSizeformat(fileSize, 2)); // 파일사이즈(ATCH_SZ)
					fileList.add(fileInfo);
				}
			}
		}

		dataRequest.setResponse("dsFileList", fileList);

		return new JSONDataView();
	}

	@RequestMapping("/download.do")
	public void download(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		String filePath = XBConfig.getInstance().getFileUploadConfig().getTempDir() + File.separator + "upload";
		ServletOutputStream out = null;
		
		try {
			Map<String, File[]> uploadFiles = dataRequest.getFiles();
			Set<Entry<String, File[]>> entries = uploadFiles.entrySet();
			
			out = response.getOutputStream();
			
			String strFileSizes = "";
			String strFileNames = "";
			
			for (Entry<String, File[]> entry : entries) {
				String name = entry.getKey();
				File[] files = entry.getValue();
				
				for (int idx = 0; idx < files.length; idx++) {
					String strOriginalfileName = files[idx].getName().replace(".tmp", "");
					String newFilePath = filePath + File.separator + strOriginalfileName;

					File realFile = new File(newFilePath);
					if (realFile.exists()) {
						String downloadFileName = this.encodingDownloadFileName(request, realFile.getName());
						long fileSize = realFile.length();
						if(idx == files.length-1) {
							strFileSizes += fileSize;
							strFileNames += downloadFileName;
						} else {
							strFileSizes += fileSize + ",";
							strFileNames += downloadFileName + ",";
						}
					}
				}
				
				for (File file : files) {
					String strOriginalfileName = file.getName().replace(".tmp", "");
					String newFilePath = filePath + File.separator + strOriginalfileName;
					
					File realFile = new File(newFilePath);
					file.delete();
					if (realFile.exists()) {
						
						response.setContentType("application/octet-stream");
						response.setHeader("Content-Disposition", "filename=\"" + strFileNames + "\";filesize=\""+strFileSizes+"\"");
						
						byte[] buffer = new byte[512];
						InputStream in = null;
						
						try {
							in = new FileInputStream(realFile);
							
							for (int size = 0; (size = in.read(buffer)) != -1;) {
								out.write(buffer, 0, size);
							}
						} finally {
							// 스트림 닫기
							in.close();
						}
					}
				}
			}
			
		} catch (Exception eout) {
			eout.printStackTrace();
		} finally { 
			out.flush();
			out.close();
		}
	}

	private String encodingDownloadFileName(HttpServletRequest request, String downloadFileName)
			throws UnsupportedEncodingException {
		String userAgent = request.getHeader("User-Agent");

		if (userAgent.contains("MSIE") || userAgent.contains("Chrome") || userAgent.contains("Firefox")
				|| (userAgent.contains("Windows") && userAgent.contains("Trident"))) {
			downloadFileName = URLEncoder.encode(downloadFileName, "utf-8");
			downloadFileName = downloadFileName.replaceAll("\\+", "%20");
		}

		return downloadFileName;
	}

	/**
	 * Method to format bytes in human readable format
	 * 
	 * @param bytes
	 *            - the value in bytes
	 * @param digits
	 *            - number of decimals to be displayed
	 * @return human readable format string
	 */
	public static String fileSizeformat(double bytes, int digits) {
		String[] dictionary = { "byte", "kb", "mb", "gb", "tb", "pb", "eb", "zb", "yb" };
		int index = 0;
		for (index = 0; index < dictionary.length; index++) {
			if (bytes < 1024) {
				break;
			}
			bytes = bytes / 1024;
		}
		return String.format("%." + digits + "f", bytes) + " " + dictionary[index];
	}
}
