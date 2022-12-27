package com.exbuilder.edu.web;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;


public class TestClass {
	public TestClass(){
		
	}
	public static String getStringMan(String param){
		System.out.println(param);
		return "";
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
//			String str = "\uAC00";
//			System.out.println(str);
//			byte[] utfStr = str.getBytes("UTF-8");
//			String hey = new String(utfStr,"UTF-8");
//			System.out.println(hey);
//			String hexString = Integer.toHexString(44032);
//			System.out.println(hexString);
//			String unescapeJava = StringEscapeUtils.unescapeJava("\\u"+hexString);
//			System.out.println(unescapeJava);
			String regex = "&#44032;&#44033;&#44034;&#44035;";
			String replacer = regex.replaceAll("&#[0-9]+;",getStringMan(regex));
			String[] split = replacer.split("&#[0-9]+;");
			System.out.println(split);
			for(String a : split){
				System.out.println(a);
			}
//			System.out.println(replacer);
//			System.out.println(regex.matches("&#[0-9]+;"));
			Pattern pat = Pattern.compile("&#[0-9]+;");
			Matcher mat = pat.matcher(regex);
//			mat.find();
//			System.out.println(mat.group());
			while(mat.find()) {
				String finder = mat.group();
				String group = finder.replaceAll("[&#;]", "");
				String hexString = "\\u"+Integer.toHexString(Integer.parseInt(group));
				String lol = StringEscapeUtils.unescapeJava(hexString);
				System.out.println(lol);
				regex = regex.replaceFirst(finder, lol);
			}
			System.out.println(regex);
		String unescapeJava = StringEscapeUtils.unescapeJava("\\u2019");
		System.out.println(unescapeJava);
	}
	

}
