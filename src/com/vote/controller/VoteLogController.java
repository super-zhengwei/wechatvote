
package com.vote.controller;

import java.util.Date;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.StrKit;
import com.vote.model.VoteLog;
import com.vote.util.Ret;

@Before(POST.class)
public class VoteLogController extends BaseController {
	
	/**
	 * 
	 * @Description: 保存访问日志 
	 * @author zhengwei  
	 * @date 2017年2月21日 上午11:02:39
	 * 
	 * @return void    
	 */
	public void save() {
		Ret ret = new Ret();
		VoteLog voteLog = new VoteLog();
		voteLog.set("openid", getPara("openid"));
		voteLog.set("vote_id", getParaToInt("voteId"));
		if(!StrKit.isBlank(getPara("itemId"))){
			voteLog.set("item_id", getParaToInt("itemId"));
		}
		voteLog.set("create_time", new Date());
		voteLog.save();
		ret.setData(voteLog);
		renderJson(ret);
	}
}
