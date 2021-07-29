package com.demo.service;

import java.util.HashMap;
import java.util.Map;


public interface MainService {

	//로그인
	boolean selectMem(HashMap<Object, String> hm);

	//회원가입
	void insertMem(HashMap<Object, String> hm);

	//아이디 중복체크
	int checkId(String id);
	
	//채팅내용 저장
    int insertChat(String ic, String nickName);
   
   //채팅방 insert
	int insertRoom(String roomName,String nickName);

	

	


}
