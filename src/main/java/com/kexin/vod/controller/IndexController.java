package com.kexin.vod.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kexin.vod.dao.CarouselDao;
import com.kexin.vod.dao.ContentDao;
import com.kexin.vod.model.Content;
import com.kexin.vod.websocket.HaoSocketServer;

@RestController
public class IndexController {

	@Resource
	ContentDao contentDao;
	@Resource
	CarouselDao carouselDao;

	@RequestMapping("/index.do")
	Map<String,List<Content>>  getIndex() {
		Map<String,List<Content>> result = new HashMap<String,List<Content>> ();
		List<Content> carouselContentList = carouselDao.getContent();
		List<Content> indexContentList = contentDao.getContentsByCatalogId(1);
		result.put("carousel", carouselContentList);
		result.put("indexContent", indexContentList);
		return result;
	}
	@RequestMapping("/cmd/{cmdType}")
	Map<String,String> control(@PathVariable String cmdType,Integer num) {
	/**	try {
			for(HaoSocketServer s:HaoSocketServer.list) {
				s.sendMessage("123123");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		Map<String,String> map = new HashMap<String,String>();
		if(cmdType.equals("start"))
			HaoSocketServer.isRunning = true;
		else  if(cmdType.equals("stop")) {
		    HaoSocketServer.isRunning = false;
		    HaoSocketServer.currentNum = HaoSocketServer.isCheat?HaoSocketServer.cheatNum:HaoSocketServer.currentNum;
		    List resultList = HaoSocketServer.resultList;
		    if(resultList.contains(HaoSocketServer.currentNum)) {
		    	for(HaoSocketServer s : HaoSocketServer.list) {
		    		try {
						s.sendResultMessage(HaoSocketServer.resultList);
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	}
		    	map.put("status", "1");
		    	return map;
		    }
		    	resultList.add(HaoSocketServer.currentNum);
		    	for(HaoSocketServer s : HaoSocketServer.list) {
		    		try {
						s.sendResultMessage(HaoSocketServer.resultList);
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	}
		  
		}
		else if(cmdType.equals("startCheat")) {
			HaoSocketServer.cheatNum = num;
			HaoSocketServer.isCheat = true;
		}
		else if(cmdType.equals("stopCheat"))
			HaoSocketServer.isCheat = false;
		
	
		map.put("status", "0");
		return map;
	}

}
