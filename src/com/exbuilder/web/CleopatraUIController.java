package com.exbuilder.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.spring.UIView;
import com.cleopatra.ui.PageGenerator;



@Controller
public class CleopatraUIController {


	public CleopatraUIController() {
	}

	@PostConstruct
	private void initPageGenerator() {
		PageGenerator instance = PageGenerator.getInstance();
		instance.setURLSuffix(".clx");
	}

	@RequestMapping("/**/*.clx")
	public View index(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		HttpSession session = null;
////		HttpSession session = null;
//		
//		session = request.getSession(false);
//		if(session != null) {
//			
//			session.invalidate();
//		}
//		session = request.getSession(true);
//		System.out.println(session.getId());
		return new UIView();
	}

}
