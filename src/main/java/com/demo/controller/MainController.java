package com.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.MainService;
import com.demo.two.Server;

@RestController
public class MainController {
	
	@Autowired MainService service;
	@Autowired Server Server;
	
	private static final Logger log = LoggerFactory.getLogger(MainController.class);
	
	
//	String url = "http://192.168.1.171:5000";
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request) {
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String id = (String) request.getParameter("id");
		String pw = (String) request.getParameter("pw");
		log.info("[login] 안드로이드에서 받아온 id: " + id);
		log.info("[login] 안드로이드에서 받아온 pw: " + pw);
		
		HashMap<Object, String> hm = new HashMap<>();
		hm.put("id", id);
		hm.put("pw", pw);
		
		boolean isExist = service.selectMem(hm);
		
		if(isExist == true) {
			return "login success";
			
		} else {
			return "login fail";
			
		}
	
	}
	
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(HttpServletRequest request) {
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String id = (String) request.getParameter("id");
		String pw = (String) request.getParameter("pw");
		log.info("[join] 안드로이드에서 받아온 id: " + id);
		log.info("[join] 안드로이드에서 받아온 pw: " + pw);
		
		HashMap<Object, String> hm = new HashMap<>();
		hm.put("id", id);
		hm.put("pw", pw);
		
		int checkId = service.checkId(id);
		
		if(checkId > 0) {
			return "join fail";
		
		} else {
			service.insertMem(hm);
			return "join success";
		}
		
	}

}
