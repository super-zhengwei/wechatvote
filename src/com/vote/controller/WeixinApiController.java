package com.vote.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.JsTicket;
import com.jfinal.weixin.sdk.api.JsTicketApi;
import com.jfinal.weixin.sdk.api.JsTicketApi.JsApiType;
import com.jfinal.weixin.sdk.api.SnsAccessToken;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import com.jfinal.weixin.sdk.api.SnsApi;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import com.vote.model.Users;

public class WeixinApiController extends ApiController{
	static Log log = Log.getLog(WeixinApiController.class);
	
	/**
	 * @Description: 网页授权获取openid 
	 * @author zhengwei  
	 * @date 2016年11月25日 下午10:18:25
	 * 
	 * @return void    
	 */
	public void index() {
		String code=getPara("code");
		String redirect=getPara("redirect");
		if (code!=null) {
			String appId=ApiConfigKit.getApiConfig().getAppId();
			String secret=ApiConfigKit.getApiConfig().getAppSecret();
			SnsAccessToken snsAccessToken=SnsAccessTokenApi.getSnsAccessToken(appId,secret,code);
			String token=snsAccessToken.getAccessToken();
			String openid=snsAccessToken.getOpenid();
			ApiResult apiResult=SnsApi.getUserInfo(token, openid);
			if (apiResult.isSucceed()) {
				ApiResult userInfo = UserApi.getUserInfo(openid);
				if (userInfo.isSucceed()) {
					JSONObject jsonObject = JSON.parseObject(apiResult.getJson());
					String userStr = userInfo.toString();
					jsonObject.put("subscribe", JSON.parseObject(userStr).getIntValue("subscribe"));
					Users.dao.save(JSONObject.toJSONString(jsonObject));
				}
			}
			Cookie userCookie=new Cookie("openid"+PropKit.get("version"),openid);
            userCookie.setMaxAge(-1);
            userCookie.setPath("/");
            getResponse().addCookie(userCookie);
			redirect(redirect);
		}else {
			renderText("code is null");
		}
	}
	
	/**
	 * @Description: 跳转到授权页面 
	 * @author zhengwei  
	 * @date 2016年11月25日 下午10:21:08
	 * 
	 * @return void    
	 * @throws UnsupportedEncodingException 
	 */
	public void toOauth() throws UnsupportedEncodingException{
		String redirect=getPara("redirect");
		System.out.println(redirect);
		redirect=URLEncoder.encode(redirect,"utf-8");
		String calbackUrl=PropKit.get("siteUrl")+"/weixin?redirect="+redirect;
		calbackUrl=URLEncoder.encode(calbackUrl,"utf-8");
		String url=SnsAccessTokenApi.getAuthorizeURL(PropKit.get("appId"), calbackUrl, "0",false);
		redirect(url);
	}
	
	
	public void getTicket() {
		String url=getPara("url");
		Map<String,Object> map = new HashMap<String,Object>();
		JsTicket jsApiTicket = JsTicketApi.getTicket(JsApiType.jsapi);
		String jsapi_ticket = jsApiTicket.getTicket();
		String nonce_str = UUID.randomUUID().toString();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		// 这里参数的顺序要按照 key 值 ASCII 码升序排序
		//注意这里参数名必须全部小写，且必须有序
		String  str = "jsapi_ticket=" + jsapi_ticket +
        "&noncestr=" + nonce_str +
        "&timestamp=" + timestamp +
        "&url=" + url;
		String signature = HashKit.sha1(str);
		map.put("appId", ApiConfigKit.getApiConfig().getAppId());
		map.put("nonceStr", nonce_str);
		map.put("timestamp", timestamp);
		map.put("url", url);
		map.put("signature", signature);
		map.put("jsapi_ticket", jsapi_ticket);
		renderJson(map);
	}
	/**
	 * @Description: 获取用户信息 
	 * @author zhengwei  
	 * @date 2016年11月25日 下午10:18:44
	 * 
	 * @return void    
	 */
	public void getUserInfo(){
		String openid = getPara("openid");
		ApiResult result = UserApi.getUserInfo(openid);
		renderJson(result);
	}
}
