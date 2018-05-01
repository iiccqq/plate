package com.kexin.vod.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/hao")
@Component
public class HaoSocketServer {
	static Logger log = LoggerFactory.getLogger(HaoSocketServer.class);

	public static List<HaoSocketServer> list = new ArrayList<HaoSocketServer>();
	public static List<Integer> resultList = new ArrayList<Integer>();
	private Session session;
	static String adminUser = "admin";
	static String guestUser = "guest";
	public static List<Integer> haoList = new ArrayList<Integer>();
	static int a = 1;
	public  static volatile boolean isRunning = false;
	public  static volatile boolean isCheat = false;
	private static Thread t = null;
	public static int cheatNum = 100;
	public static int currentNum = 0;

	static {
		for(int i = 0;i<10000;i++)
		
					
					
		haoList.add(i);
        t = new Thread(new Runnable() {	
			
			@Override
			public void run() {
				while(true) {
					
					int index=(int)(Math.random()*haoList.size());
					Integer hao =haoList.get(index);
					if(isRunning)
						currentNum = hao;
				for(HaoSocketServer s : list) {
				
					try {
						if(isRunning)
						   s.sendNumMessage(hao);
						else {
							s.sendNumMessage(currentNum);
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(90);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		});
		t.start();
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("user") String user) {
		this.session = session;
		list.add(this);
		
		
	}

	@OnClose
	public void onClose() {
	   list.remove(this);
	}
	@OnMessage
	public void onMessage(String message, Session session,@PathParam("user") String user) {
		log.info("来自客户端的消息:" + message);
		
	}

	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	public void sendMessage(Integer message) throws IOException {
	 this.sendMessage(String.valueOf(message));
	}

	public void sendNumMessage(Integer message) throws IOException {
	 this.sendMessage("{\"num\":" +  String.valueOf(message) + "}");
	}
	public void sendResultMessage(List<Integer> message) throws IOException {
		String s = "";
		for(Integer m : message) {
			if(s.length()!=0)
				s+=",";
			s+= m;
		}
		String r = "{\"list\":[" + s+"]}";
	 this.sendMessage(r);
	}


}