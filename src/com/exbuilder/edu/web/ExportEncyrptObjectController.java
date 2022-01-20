//package com.exbuilder.edu.web;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.nio.charset.Charset;
//import java.security.NoSuchAlgorithmException;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//import java.util.function.Consumer;
//import java.util.zip.GZIPOutputStream;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.swing.text.View;
//
//import org.apache.commons.codec.binary.Hex;
//import org.json.simple.parser.JSONParser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.cleopatra.json.JSONArray;
//import com.cleopatra.json.JSONObject;
//import com.cleopatra.protocol.data.DataRequest;
//import com.cleopatra.protocol.data.ParameterGroup;
//
//@Controller
//@RequestMapping("/encrypt")
//public class ExportEncyrptObjectController {
//	
//	
//	private SecretKeySpec secretKey ;
////	private String InstanceKey = "AES/ECB/PKCS5Padding";
//	public ExportEncyrptObjectController(){
//		
//	}
//	public ExportEncyrptObjectController(String reqSecretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//		
//		
//		this.secretKey = new SecretKeySpec(reqSecretKey.getBytes("UTF-8"),"AES");
//	}
//	
//	public String AesECBEncode(String plainText) throws Exception {
//		
//		Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
//		c.init(Cipher.ENCRYPT_MODE, secretKey);
//		
//		byte[] encryptBtye = c.doFinal(plainText.getBytes("UTF-8"));
//		
//		return Hex.encodeHexString(encryptBtye);
//	}
//	
//	public String AesECBDecode(String encodeText) throws Exception {
//		Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
//		
//		c.init(Cipher.DECRYPT_MODE, secretKey);
//		
//		byte[] decodeByte = Hex.decodeHex(encodeText.toCharArray());
//		
//		return new String(c.doFinal(decodeByte), "UTF-8");
//	}
//	
//	public String getType(Object obj) throws Exception {
//		
//		String name = obj.getClass().getName();
//		return name;
//	}
//	
//	public static boolean isBaseTypeOrArray(Object obj) {
//	    Class<?> c = obj.getClass();
//	    return  c.equals(String.class) 
//	         || c.equals(String[].class)
//	         || c.isArray() && c.getComponentType().isPrimitive();
//	}
//	@SuppressWarnings("null")
//	public JSONObject getJson(JSONObject hanJsons) throws Exception{
//		String key_128 = "aeskey1234567898";
//		ExportEncyrptObjectController aes = new ExportEncyrptObjectController(key_128);
//		
//		JSONObject newJson = new JSONObject();
//		
//		Set<String> keys = hanJsons.keySet();
//		JSONObject jsonObj = null;
//		String strValue = "";
//		int intValue = 0;
//		for(String strKey : keys) {
//			Object objects = hanJsons.get(strKey);
//			String typeInfo = objects.getClass().getName();
//			String encryptStrKey = aes.AesECBEncode(strKey);
//			if(typeInfo.indexOf("Object") > -1 && typeInfo.indexOf("Null") == -1) {
//				jsonObj = hanJsons.getJSONObject(strKey);
//				newJson.put(encryptStrKey, getJson(jsonObj));
//			}
//			else if(typeInfo.indexOf("Null") > -1) {
//				newJson.put(encryptStrKey, JSONObject.NULL);
//			}
//			else if(typeInfo.indexOf("String") > -1) {
//				
//				strValue = hanJsons.getString(strKey);
//				strValue = aes.AesECBEncode(strValue);
//				newJson.put(encryptStrKey, strValue);
//			} else if(typeInfo.indexOf("Integer")> -1) {
//				intValue = hanJsons.getInt(strKey);
//				newJson.put(encryptStrKey,intValue);
//			} else if(typeInfo.indexOf("JSONArray") > -1) {
//				JSONArray jArray = hanJsons.getJSONArray(strKey);
//				Object[] temp = new Object[jArray.length()];
//				
//				for(int i = 0; i < jArray.length(); i++) {
//					
//					JSONObject jo;
//					try{
//						
//						jo = jArray.getJSONObject(i);
//						temp[i] = getJson(jo);
//					} catch(Exception e){
//						//TODO JSONArray가 아닌 
//						Object temptemp = jArray.get(i);
//						String[] a = temptemp.toString().split(",");
//						String[] tempArr = new String[a.length];
//						for(int j = 0; j < a.length; j++){
//							tempArr[j] = aes.AesECBEncode(a[j]);
//						}
//						temp[i] = tempArr;
//					}					
//					
//	
//				}
//				newJson.put(encryptStrKey, temp);
//				
//			}
//		}
//		
//		return newJson;
//	}
//	
//	@RequestMapping("encrypt.do")
//	public void exportEncryptJson(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
//		
//		
//		String key_128 = "aeskey1234567898";
//		ExportEncyrptObjectController aes1 = new ExportEncyrptObjectController(key_128);
//		
//		JSONObject reqObj = dataRequest.getRequestObject();
//		String  encryptedStr = aes1.AesECBEncode(reqObj.toString());
////		JSONObject newobj = getJson(reqObj);
//		String downloadFileName = "JsonFile" + ".json";
//		downloadFileName = encodingDownloadFileName(request,response,downloadFileName);
//		
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("application/json");
//		response.setHeader("Content-Transfer-Encoding","binary");
//		
//		PrintWriter out = response.getWriter();
//		out.print(encryptedStr);
//		out.flush();
//	}
//	
//	@RequestMapping("encryptZip.do")
//	public void exportEncryptZip(HttpServletRequest request, HttpServletResponse response, DataRequest datareq) throws Exception {
//		
//		String key_128 = "aeskey1234567898";
//		ExportEncyrptObjectController aes1 = new ExportEncyrptObjectController(key_128);
//		
//		JSONObject reqObj = datareq.getRequestObject();
//		String newobj = aes1.AesECBEncode(reqObj.toString());
//		
//		String strZipFileName = "encrypt.zip";
//		strZipFileName = this.encodingDownloadFileName(request, response,strZipFileName);
//		
//		response.setCharacterEncoding("utf-8");
//		response.setHeader("Content-Transfer-Encoding", "binary");
//		File jsonFile = new File("sample.json");
////		FileWriter writer = new FileWriter(jsonFile);
////		writer.write(newobj);
//		
//		ServletOutputStream out = response.getOutputStream();
//		ZipOutputStream zOut = new ZipOutputStream(out, Charset.forName("UTF-8"));
//		
//			String strFileName = jsonFile.getName();
//			ZipEntry entry = new ZipEntry(strFileName);
//			zOut.putNextEntry(entry);
//			
//			int i = 0;
//			FileReader reader = new FileReader(jsonFile.getPath());
//				zOut.write(newobj.getBytes());
////			}
//			zOut.closeEntry();
//		
//		zOut.finish();
//		zOut.flush();
//		zOut.close();
//		jsonFile.delete();
//	}
//	
//	@RequestMapping("decode.do")
//	public void getDeencrypt(HttpServletRequest request, HttpServletResponse response, DataRequest datareq) throws Exception {
//		
//		String key_128 = "aeskey1234567898";
//		ExportEncyrptObjectController aes1 = new ExportEncyrptObjectController(key_128);
//		
//		FileInputStream in = null;
//		BufferedInputStream bis = null;
//		File file = null;
//		StringBuilder sb = new StringBuilder();
//		
//		Map<String, File[]> uploadFiles = datareq.getFiles();
//		Set<Entry<String, File[]>> entries = uploadFiles.entrySet();
//		for(Entry<String, File[]> entry : entries) {
//			String name = entry.getKey();
//			File[] files = entry.getValue();
//			if(files != null && files.length > 0) {
//				file = files[0];
//				break;
//			}
//		}
//		try{
//			in = new FileInputStream(file);
//			bis = new BufferedInputStream(in);
//			byte[] btyeBuff = new byte[1024];
//			int line;
//			while((line = bis.read(btyeBuff)) > 0) {
//				String strBuff = new String(btyeBuff,0,line);
//				sb.append(strBuff);
//			}
//			
//		}catch(Exception e) {
//			
//		} finally {
//			if(in != null) in.close();
//			if(bis != null) bis.close();
//		}
//		
//	}
//	
//	private String encodingDownloadFileName(HttpServletRequest request, HttpServletResponse response, String downloadFileName) throws UnsupportedEncodingException {
//		String userAgent = request.getHeader("User-Agent");
//		
//		if(userAgent.contains("MSIE") || userAgent.contains("Chrome") || userAgent.contains("Firefox") ||(userAgent.contains("Windows") && userAgent.contains("Trident"))){
//			downloadFileName = URLEncoder.encode(downloadFileName, "utf-8");
//			downloadFileName = downloadFileName.replaceAll("\\+","%20");
//			
//			response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadFileName + "\";filename*=UTF-8''" + downloadFileName);
//        } else {
//    		response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadFileName + "\"");
//        }
//		
//		return downloadFileName;
//	}
//}
