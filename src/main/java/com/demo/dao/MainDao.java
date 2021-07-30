package com.demo.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MainDao {

	//로그인
	int selectCntMem(HashMap<Object, String> hm);

	//회원가입
	void insertMem(HashMap<Object, String> hm);

	//아이디 중복체크
	int checkId(String id);
	

	//닉네임 중복체크
	int checkNick(String nick);
	
	//닉네임 가져오기
	String getNick(String id);
		
	
	
	
	
    //채팅내용 db insert
    int insertChat(@Param("ic")String ic,@Param("nickName") String nickName);
   
    
    
    //채팅방 select
  //  int selectRoom(String sr);

	//채팅방 insert
    int insertRoom(@Param("roomName")String roomName, @Param("nickName")String nickName);

	


    
}
