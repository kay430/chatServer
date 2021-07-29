package com.demo.two;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.service.MainService;

@Component
public class Server implements Runnable{
	
	Vector<Service> allV;//모든 사용자(대기실사용자 + 대화방사용자)
	Vector<Service> waitV;//대기실 사용자	   
	Vector<AucRoom> roomV;//개설된 대화방 Room-vs(Vector) : 대화방사용자
	@Autowired MainService service;
		
	public Server() {
		allV = new Vector<>();
		waitV = new Vector<>();
		roomV = new Vector<>();
	   //Thread t = new Thread(Service);  t.start();

		new Thread(this).start();

	}//생성자
	@Override
	public void run() {
		
		try {
			ServerSocket ss = new ServerSocket(5000);
    	    //현재 실행중인 ip + 명시된 port ----> 소켓서비스   

		    System.out.println("Start Server.......");

			while(true){
				Socket s = ss.accept();//클라이언트 접속 대기
				//s: 접속한 클라이언트의 소켓정보
				Service ser = new Service(s, this);
				
				System.out.println("server 전체 : " + allV.size() + " 대기 : " + waitV.size());
				

			   }			   

		   } catch (IOException e) {
			   e.printStackTrace();
		   }	   

	}
	
	
	

}
