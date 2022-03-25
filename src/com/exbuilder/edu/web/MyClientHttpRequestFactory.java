package com.exbuilder.edu.web;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class MyClientHttpRequestFactory extends SimpleClientHttpRequestFactory{

	@Override
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException{
		super.prepareConnection(connection, httpMethod);
		if("DELETE".equals(httpMethod)){
			connection.setDoOutput(true);
		}
		
	}
}
