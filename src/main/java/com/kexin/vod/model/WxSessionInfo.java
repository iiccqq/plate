package com.kexin.vod.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WxSessionInfo {

	@JsonProperty("session_key")
	String sessionKey;
	@JsonProperty("openid")
	String openid;
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
}
