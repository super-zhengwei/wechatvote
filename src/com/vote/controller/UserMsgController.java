package com.vote.controller;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.vote.model.WxUserMsg;

@Before(POST.class)
public class UserMsgController extends BaseController {
	
	/**
	 * @Description: 获取订单列表,在后台管理界面查看
	 * @author jim  
	 * @date 2017年1月16日 
	 * @return void  
	 */
	public void getUserMsgs() {
		String openid = getPara("openid");
		String startDate = getPara("startdate");
		String endDate = getPara("enddate");
		int pageSize = getParaToInt("pageSize");
		int pageNumber = getParaToInt("pageNumber");
		Page<WxUserMsg> page = WxUserMsg.ME.getUserMsgs(openid,
				startDate,endDate,pageNumber,pageSize);
		
		renderJson(page);
	}
}
