package com.kexin.vod.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kexin.vod.cache.CacheManger;
import com.kexin.vod.cache.UserCache;
import com.kexin.vod.config.VodConfig;
import com.kexin.vod.constant.VodConstants;
import com.kexin.vod.model.WxSessionInfo;
import com.kexin.vod.model.WxUserInfo;

import net.sf.json.JSONObject;

@RestController
public class UserController {

	@Resource
	RestTemplate restTemplate;
	@Resource
	VodConfig vodConfig;

	@RequestMapping("/user")
	Map<String, Object> user(String code) {

		Map<String, Object> result = new HashMap<String, Object>();

		String sessionId = UUID.randomUUID().toString();

		CloseableHttpClient httpClient = HttpClients.createDefault();

		String url = String.format(
				VodConstants.API_WX_SNS_JSCODE2SESSION + "appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
				vodConfig.getAppid(), vodConfig.getSecret(), code);
		HttpGet get = new HttpGet(url);
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			String res = EntityUtils.toString(entity, "utf-8");// {"session_key":"O+nv95xvtobE6n5\/MFCVOg==","openid":"osbQE0fDM_BG4uG-sMAAv1r1FLdM"}
			response.close();
			JSONObject j = JSONObject.fromObject(res);
			WxSessionInfo userInfo = (WxSessionInfo) JSONObject.toBean(j, WxSessionInfo.class);
			String curSessionId = (String) CacheManger.get(VodConstants.KEY_OPENID + userInfo.getOpenid());
			if (curSessionId != null) {
				CacheManger.remove(VodConstants.KEY_SESSIONID + curSessionId);
			}
			CacheManger.set(VodConstants.KEY_SESSIONID + sessionId, userInfo.getOpenid());
			CacheManger.set(VodConstants.KEY_OPENID + userInfo.getOpenid(), sessionId);
			result.put("sessionId", sessionId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		result.put("aa", "aaa");
		return result;
	}

	@RequestMapping("/saveUserInfo")
	Map<String, Object> saveUserInfo(@RequestBody WxUserInfo userInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		//若不存在则保存用户信息
		
		// 查询播放历史
		List list = null;
		result.put("playHistory", list);
		return result;
	}

	@RequestMapping("/myPlayHistory")
	Map<String, Object> me(String sessionId) {
		Map<String, Object> result = new HashMap<String, Object>();

		// 查询播放历史
		List list = null;
		result.put("playHistory", list);
		return result;
	}

	// 返回播放串，写播放历史记录
	@RequestMapping("/play")
	Map<String, Object> play(String vodId, String sessionId) {
		Map<String, Object> result = new HashMap<String, Object>();
		String openId = (String) CacheManger.get(VodConstants.KEY_SESSIONID + sessionId);
		WxSessionInfo wxUserInfo = UserCache.getUserInfoBy(sessionId);
		String playUrl = "";
		result.put("playUrl", playUrl);
		return result;
	}

}
