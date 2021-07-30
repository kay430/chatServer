package com.demo.two;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import com.demo.service.MainService;

import ch.qos.logback.core.net.SyslogOutputStream;

public class Service extends Thread{
	
	 AucRoom myRoom; //클라이언트가 입장한 대화방	

	 //소켓관련 입출력서비스
	 BufferedReader in;
	 //OutputStream out;	   
	 //PrintWriter writer = null;	 
	 ObjectInputStream  ois;
	 ObjectOutputStream obs;

	 Vector<Service> allV; //모든 사용자(대기실사용자 + 대화방사용자)
	 Vector<Service> waitV; //대기실 사용자	   
	 Vector<AucRoom> roomV; //개설된 대화방 Room-vs(Vector) : 대화방사용자

     Socket s;
	 String nickName;
	 MainService service;
	 
	 
	 //생성자
	 public Service(Socket s, Server server) {
		 allV=server.allV;
	     waitV=server.waitV;
	     roomV=server.roomV;
	     service=server.service;

	     this.s = s;		   

		 try {
//			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
//			out = s.getOutputStream();	
//			writer = new PrintWriter(out, true);
			ois = new ObjectInputStream(s.getInputStream());
			obs = new ObjectOutputStream(s.getOutputStream());
	
			//생성된 인스턴스에서 start() 메소드를 호출 --> 쓰레드 실행
			start();
	
		 } catch (IOException e) {	
		     e.printStackTrace();	
	     }
     }
	 
	 //run() 메소드 override
	 @Override
	 public void run() {		 
		 try {			 
			 while(true){				 
				 String msg = (String)ois.readObject();//클라이언트의 모든 메시지를 받기 (객체에서 Object를 읽는다)
				 
//				 System.out.println("msg: " + msg);
				 
				 messageWait("90|"+ getRoomInfoName());				 
//				 obs.writeObject(msg);
//				 obs.flush();
				 
			     if(msg == null) return; //비정상적인 종료		     
			     System.out.println("Client 접속  :"+s.getInetAddress().getHostAddress());
			     
			     if(msg.trim().length() > 0){			    	 
			     System.out.println("from Client: "+ msg +":"+s.getInetAddress().getHostAddress());
			     
	             String msgs[]=msg.split("\\|");
	             String protocol = msgs[0];
	             String getnick = msgs[1].trim();
	             System.out.println("로그인한 사람 nick: " + getnick);
	             
 			     switch(protocol){

			      case "100": //대기실 접속
			    	  this.nickName = getnick;
			    	  
			    	  allV.add(this);//전체사용자에 등록
			    	  
			    	  waitV.add(this);//대기실사용자에 등록
			    	  
			    	// System.out.println(waitV.indexOf(this));
			    	// System.out.println("100 대기실 입장:" + s.getInetAddress().getHostAddress());
			    	  
			    	  //nick = service.selectNickName(nickName);
			    	  
			    	  System.out.println(waitV.indexOf(this));
			    	  
					  
					  
						
			    	  
			    	  		System.out.println("100 대기실 입장:" + s.getInetAddress().getHostAddress());
			    	  		
							System.out.println("전체 : " + allV.size() + " 대기 : " + waitV.size());
			    	  		
					        for(int k = 0; k < allV.size(); k++) {
					        	System.out.println("대화방+대기실 인원: " + k + "번째// " + allV.get(k).nickName);
					    	}
					    	for(int k = 0; k < waitV.size(); k++) {
					    		System.out.println("대기실 인원: " + k + "번째// "+ allV.get(k).nickName);
					    	}
					        
				            break;
				            
				            
			      case "150": //대화명 입력
			    	  
//			    	  for(int i = 0; i < allV.size(); i++) {
//			    		  String tem = allV.get(i).nickName;
//			    		  System.out.println("유저 리스트????: " + tem);
//			    	  }
			    	  
			    	  int ncheck = 0;
			    	  
			    	  String temName ="";
			    	  
 if(allV.size() > 0) {
			    		  
				    	  for(int i = 0; i < allV.size(); i++) {
				    		  temName = allV.get(i).nickName;
				    		  System.out.println("유저 리스트: " + temName);
				    		  System.out.println("입력된 유저 이름: " + msgs[1].trim());
				    		  
				    		  if(temName == null) {
				    			  temName = "";
				    		  }
				    		  
				    		  if(temName.equals(msgs[1].trim())) {
				    			  ncheck = 1;
				    			  
				    			  break;
				    			  
				    		  } 
			    	    	
				    	  }
				    	  
				    	  if(ncheck == 1) {
				    		  System.out.println("닉네임 중복!!!!!");
			    			  
			    			  messageTo("130| 중복된 닉네임");
			    			  
				    	  } else {
				    		  System.out.println("닉네임 중복 아님!!!!");
				    		  messageTo("140| 닉네임 등록");
			    			  nickName = msgs[1].trim(); //사용자가 입력한 값
				    	  }
				    	  
				    	  
				    	  
				    	  System.out.println("150 전체 : " + allV.size() + " 대기 : " + waitV.size());
				    	  				    	  
				    	  for(int k = 0; k < allV.size(); k++) {
					        	System.out.println("이름 설정 후 대화방+대기실 인원: " + k + "번째// " + allV.get(k).nickName);
				    	  }
				    	  
				    	  for(int k = 0; k < waitV.size(); k++) {
				    		  System.out.println("이름 설정 후 대기실 인원: " + k + "번째// "+ waitV.get(k).nickName);
				    	  }
			    	  }
			    	  
			    	  
			    	  
 			    	        //최초 대화명 입력했을때 대기실의 정보를 출력
			    	        //messageWait("160|"+ getRoomInfo());
			    	        //messageWait("180|"+ getWaitInwon());
			          break;
			      case "160": //방만들기 (대화방 입장)
			    	  
			    	  	
				      int check = 0;
				      String rname = msgs[1].toString().replaceAll("\\s", ""); //문자열 사이 공백 제거
				    	  
			    	  // 전체 방 리스트 for문으로 돌면서 방제 비교해
			    	  //해당 사용자에게 110번으로 메시지 보내기
			    	  for(int i = 0; i < roomV.size(); i++){
			    		  AucRoom rc = roomV.get(i);
//			    		  System.out.println("리스트: " + roomV.get(i).title);
//			    		  System.out.println("입력된 방 이름: " + msgs[1]);
//			    		  System.out.println("입력된 방 이름: " + rname);
			    		  
			    	
			    		  
			    		  if(rc.title.equals(rname)) {	
			    			  
			    			  for(int j = 0; j < waitV.size(); j++) {
			    				  Service user = waitV.get(j);
			    				  
			    				  if(nickName.equals(user.nickName)) {
			    					  
			    					  messageTo("110| 중복된 방 이름");
			    					  check = 1;
			    					  break;
			    					  
			    				  } 
			    					  
			    			  }
			    		  } 
			    	  }
			    	  
			    	  if(check == 1) {
			    		  check = 0;
			    		  break;
			    	  } 
			    	  
					  System.out.println("방 중복 아님!!!!");
					  messageTo("120| 방 등록");
			    	  
	    			  myRoom = new AucRoom();
	    			  myRoom.title = msgs[1].toString().replaceAll("\\s", "");//방제목
	    			  
	    			  myRoom.count = 1;
	    			  myRoom.boss = nickName;
	    			  roomV.add(myRoom);
	    			  
	    			  System.out.println(nickName+"닉네임"+msgs[1]+"방이름");//
	    			  
	    			  int tem = 0;
	    			  
	                     //String ic = new String (); 
	                      
	                     tem = service.insertRoom(msgs[1],nickName);
	                     System.out.println("d");//
		    			     
	    			  
	    			  
	                     //String ic = new String (); 
	                      
	                   //  tem = service.insertRoom();
	                      
	    			  
	    			  //생성된 방정보 전달
	    			  messageWait("90|"+ getRoomInfoName());			    	        
		    			  
//		    	            //대기실----> 대화방 이동!!
//			    	        waitV.remove(this);
//			    	        myRoom.userV.add(this);			    	        
//			    	        messageRoom("200|"+nickName);//방인원에게 입장 알림		    	          
		    			  
		    			  //대기실 사용자들에게 방정보를 출력
		    			  //예) 대화방명:JavaLove
		    			  //-----> roomInfo(JList) :  JavaLove--1			    	        
		    			  //messageWait("160|"+ getRoomInfo());
		    			  //messageWait("180|"+ getWaitInwon());
		    			  
	    			  break;
			    		  
			      case "170": //(대기실에서) 대화방 인원정보
			    	        messageTo("170|"+getRoomInwon(msgs[1]));
			    	        break;
			      case "175": //(대화방에서) 대화방 인원정보
			    	  		messageRoom("175|"+getRoomInwon());	
			    	  		break;
			      case "200": //방들어가기 (대화방 입장) ----> msgs[] = {"200","자바방"}		
			    	         for(int i=0; i<roomV.size(); i++){//방이름 찾기!!
			    	        	 AucRoom r = roomV.get(i);
			    	        	 if(r.title.equals(msgs[1].trim())){//일치하는 방 찾음!!
			    	        		 myRoom = r;
			    	        		 myRoom.count++;//인원수 1증가
			    	        		 break;
			    	        	 }
			    	         }
			    	         
			    	         
			    	         //대기실----> 대화방 이동!!
			    	         waitV.remove(this);
			    	         myRoom.userV.add(this);
			    	         messageRoom("200|"+nickName);//방인원에게 입장 알림 
			    	         
			    	         System.out.println("200 전체 : " + allV.size() + " 대기 : " + waitV.size());
			    	          

			    	         //들어갈 방의 title전달
			    	         messageTo("202|"+ myRoom.title);
			    	         messageWait("160|"+ getRoomInfo());
			    	         messageWait("180|"+ getWaitInwon());
			    	         
			    	         for(int k = 0; k < myRoom.userV.size(); k++) {
					    		  System.out.println("myRoom.userV: " + myRoom.userV.get(k).nickName);
					    	  }
			    	         break;
			    	         
			    	         
			      case "300": //메시지
		    	         messageRoom("300|["+nickName+"] : "+msgs[1]);
		    	         //클라이언트에게 메시지 보내기
		    	         
	                     int temp = 0;
	                     //String ic = new String (); 
	                      
	                     temp = service.insertChat(msgs[1],nickName);
	                      
	                      
	                     System.out.println("출력"+msgs[1]+nickName);
	                  
		    	         
		    	         break;
			      case "400": //대화방 퇴장
			    	  		 myRoom.count--;//인원수 감소
			    	         messageRoom("400|"+nickName);//방인원들에게 퇴장 알림!!
	
			    	         //대화방----> 대기실 이동!!
			    	         myRoom.userV.remove(this);	
			    	         waitV.add(this);
			    	         
			    	         System.out.println("400 전체 : " + allV.size() + " 대기 : " + waitV.size());
			    	         
			    	         //대화방 퇴장후 방인원 다시출력
			    	         messageRoom("175|"+getRoomInwon());
			    	         
			    	         //대기실에 방정보 다시출력
			    	         messageWait("90|"+ getRoomInfoName());
			    	         //messageWait("160|"+ getRoomInfo());
			    	         
			    	        
			    	         break;
			    }
			  }
			}
	
		 }catch (IOException e) {
			 System.out.println("★");
			 e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getRoomInfo(){
		  String str="";
		  for(int i=0; i<roomV.size(); i++){
			  AucRoom r= roomV.get(i);
			  str += r.title+"--"+r.count;			  
			  if(i<roomV.size()-1)str += ",";
		  }		  
		  return str;
	}
	
	public String getRoomInfoName(){
		  String str="";
		  for(int i=0; i<roomV.size(); i++){
			  AucRoom r= roomV.get(i);
			  str += r.title+"|";			  
		  }		  
		  return str;
	}
	
	public String getRoomInwon(){//같은방의 인원정보
		   String str="";	   
		   for(int i=0; i<myRoom.userV.size(); i++){
			    Service ser= myRoom.userV.get(i);
				str += ser.nickName;
			    if(i<myRoom.userV.size()-1)str += ",";
		   }
		  return str;
	}
	
	public String getRoomInwon(String title){//방제목 클릭시 방의 인원정보		   
		  String str="";		   
		  for(int i=0; i<roomV.size(); i++){
			   AucRoom room = roomV.get(i);
			   if(room.title.equals(title)){
				   for(int j=0; j<room.userV.size(); j++){
					   Service ser= room.userV.get(j);
					   str += ser.nickName;
					   if(j<room.userV.size()-1)str += ",";	
				   }				   
				   break;
			   }
		   }
		   return str;
	}
	
	public String getWaitInwon(){
		   String str="";
		   for(int i=0; i<waitV.size(); i++){
			   System.out.println("getWaitInwon : " + i);
			   
			   Service ser= waitV.get(i);
			   str += ser.nickName;
			   if(i<waitV.size()-1)str += ",";
		   }
		   return str;
	}
	
	
	public void messageAll(String msg){//전체사용자
		   //접속된 모든 클라이언트(대기실+대화방)에게 메시지 전달   
		   for(int i=0; i<allV.size(); i++){//벡터 인덱스
			  Service service = allV.get(i); //각각의 클라이언트 얻어오기
			  try {
				  service.messageTo(msg);
				} catch (IOException e) {
					//에러발생 ---> 클라이언트 접속 끊음!!
					allV.remove(i--); //접속 끊긴 클라이언트를 벡터에서 삭제!!
				    System.out.println("클라이언트 접속 끊음!!");
				}
		   }
	}//messageAll
	
	public void messageWait(String msg){//대기실 사용자	   
		   for(int i=0; i<waitV.size(); i++){//벡터 인덱스
			   Service service = waitV.get(i); //각각의 클라이언트 얻어오기
			   try {
				   service.messageTo(msg);
			   } catch (IOException e) {
				   //에러발생 ---> 클라이언트 접속 끊음!!
				   waitV.remove(i--); //접속 끊긴 클라이언트를 벡터에서 삭제!!
				   System.out.println("클라이언트 접속 끊음!!");
			   }
		   }
	}//messageWait
	
	public void messageRoom(String msg){//대화방사용자
		   for(int i=0; i< myRoom.userV.size(); i++){//벡터 인덱스
			   Service service = myRoom.userV.get(i); //각각의 클라이언트 얻어오기
			   try {
				   service.messageTo(msg);
			   } catch (IOException e) {
				   //에러발생 ---> 클라이언트 접속 끊음!!
				   myRoom.userV.remove(i--); //접속 끊긴 클라이언트를 벡터에서 삭제!!
				   System.out.println("클라이언트 접속 끊음!!");
			   }
		   }
	}//messageAll
	
	public void messageTo(String msg) throws IOException{
		   obs.writeObject(msg+"\n");
		   obs.flush();
		   //out.write(  (msg+"\n").getBytes()   );
	
	}


}

