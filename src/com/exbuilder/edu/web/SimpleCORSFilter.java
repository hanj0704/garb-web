package com.exbuilder.edu.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter{
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	 @Override
	    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
	            throws IOException, ServletException {
		
	        HttpServletRequest request = (HttpServletRequest) servletRequest;
//	        Cookie[] cookiebongji = request.getCookies();
//	        if(cookiebongji != null && cookiebongji.length > 0) {
//	        	for(Cookie cook : cookiebongji) {
//	        		
//	        		System.out.println(cook);
//	        		System.out.println(cook.getValue());
//	        		System.out.println(cook.getPath());
//	        	}
//	        }
	        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
	        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
	        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
//	        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Credentials", "true");
	        HttpServletResponse resp = (HttpServletResponse) servletResponse;
	        if (request.getMethod().equals("OPTIONS")) {
	            resp.setStatus(HttpServletResponse.SC_OK);
	            return;
	        }
	        chain.doFilter(request, servletResponse);
	    }

	
	@Override
	public void destroy() {
	}

}
