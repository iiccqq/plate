package com.kexin.vod.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

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

@ServerEndpoint(value = "/chat/{vodId}")
@Component
public class WebSocketServer {
	static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

	private static Map<String, CopyOnWriteArraySet<WebSocketServer>> vodId2WebSocketServerMap = new HashMap<String, CopyOnWriteArraySet<WebSocketServer>>();
	
	private Session session;

	@OnOpen
	public void onOpen(Session session, @PathParam("vodId") String vodId) {
		this.session = session;

		CopyOnWriteArraySet<WebSocketServer> webSocketSet = vodId2WebSocketServerMap.get(vodId);
		if (webSocketSet == null) {
			webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
			vodId2WebSocketServerMap.put(vodId, webSocketSet);
		}
		if (!webSocketSet.contains(this))
			webSocketSet.add(this);
	
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(@PathParam("vodId") String vodId) {
		CopyOnWriteArraySet<WebSocketServer> webSocketSet = vodId2WebSocketServerMap.get(vodId);
		if (webSocketSet == null) {
			webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
			vodId2WebSocketServerMap.put(vodId, webSocketSet);
		}
		if(webSocketSet.contains(this))
			webSocketSet.remove(this); 
	
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session,@PathParam("vodId") String vodId) {
		log.info("来自客户端的消息:" + message);
		CopyOnWriteArraySet<WebSocketServer> webSocketSet = vodId2WebSocketServerMap.get(vodId);
		if (webSocketSet == null) {
			webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
			vodId2WebSocketServerMap.put(vodId, webSocketSet);
		}
		// 群发消息
		for (WebSocketServer item : webSocketSet) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}


}