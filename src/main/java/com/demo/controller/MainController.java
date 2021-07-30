package com.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.MainService;
import com.demo.two.Server;
//import com.fasterxml.jackson.databind.util.JSONPObject;

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
		
		JSONObject rjson = new JSONObject();
		
		if(isExist == true) {
			String usernick = service.getNick(id);
//			data.put("result", "login success");
//			data.put("nick", usernick);
			rjson.put("result", "success");
			rjson.put("nick", usernick);
				
		} else {
			rjson.put("result", "login fail");
			//data.put("result", "login fail");			
		}
		
		return rjson.toString();
		
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
		String nick = (String) request.getParameter("nick");
		log.info("[join] 안드로이드에서 받아온 id: " + id);
		log.info("[join] 안드로이드에서 받아온 pw: " + pw);
		log.info("[join] 안드로이드에서 받아온 nick: " + nick);
		
		HashMap<Object, String> hm = new HashMap<>();
		hm.put("id", id);
		hm.put("pw", pw);
		hm.put("nick", nick);
		
		int checkId = service.checkId(id);
		int checkNick = service.checkNick(nick);
		
		if(checkId > 0 || checkNick > 0) {
			return "join fail";
		
		} else {
			service.insertMem(hm);
			return "join success";
		}
		
	}

}
