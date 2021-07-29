package com.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.MainDao;

@Service("MainService")
public class MainServiceImpl implements MainService {
	
	@Autowired MainDao dao;
	
	private static final Logger log = LoggerFactory.getLogger(MainServiceImpl.class);

	@Override
	public boolean selectMem(HashMap<Object, String> hm) {
			
		int cnt = dao.selectCntMem(hm);
		
		if(cnt > 0) {
			return true;
		} else {
			return false;
		}
	}

	
	@Override
	public void insertMem(HashMap<Object, String> hm) {
		
		dao.insertMem(hm);
	}


	@Override
	public int checkId(String id) {
		return dao.checkId(id);
	}
	
	
	
	//채팅내용 insert
    @Override
    public int insertChat(String ic ,String nickName) {
       return dao.insertChat(ic, nickName);
    }
    //채팅내용 select
//    @Override
//    public int selectRoom(String sr) {
//       return dao.selectRoom(sr);
//    }
    @Override
    public int insertRoom(String roomName,String nickName) {
    	return dao.insertRoom(roomName, nickName);
    }
  

}
