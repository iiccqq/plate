package com.kexin.vod.cache;

import com.kexin.vod.constant.VodConstants;
import com.kexin.vod.model.WxSessionInfo;

public class UserCache {

	public static WxSessionInfo getUserInfoBy(String sessionId) {
		 String openId = (String)CacheManger.get(VodConstants.KEY_SESSIONID +sessionId);
		 WxSessionInfo wxUserInfo = (WxSessionInfo) CacheManger.get(VodConstants.KEY_OPENID + openId);
		 return wxUserInfo;
	}

}
