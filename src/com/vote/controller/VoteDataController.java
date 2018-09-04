
package com.vote.controller;

import java.util.Date;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vote.model.Vote;
import com.vote.model.VoteData;
import com.vote.util.Ret;

@Before(POST.class)
public class VoteDataController extends BaseController {
	
	/**
	 * 
	 * @Description: 投票 
	 * @author zhengwei  
	 * @date 2017年2月21日 上午11:02:39
	 * 
	 * @return void    
	 */
	public void save() {
		Ret ret = new Ret();
		String openid = getPara("openid");
		int voteId = getParaToInt("voteId");
		int itemId = getParaToInt("itemId");
//		Users user = Users.dao.findByOpenId(openid);
//		if(!"1".equals(user.getStr("subscribe"))){
//			ret.addError("请先关注我们微信公众号");
//			renderJson(ret);
//			return;
//		}
		VoteData model = VoteData.dao.getByOpenid(openid);
		Vote vote = Vote.dao.findById(voteId);
		if("1".equals(vote.getStr("vote_status"))){
			ret.addError("活动已暂停");
			renderJson(ret);
			return;
		}
		if(!Vote.dao.checkDate(voteId)){
			ret.addError("活动未开始或已结束");
			renderJson(ret);
			return;
		}
		if(model!=null){
			ret.setData("-1");
			ret.addError("您当天的次数已经用完，明天再来支持你的小伙伴~");
			renderJson(ret);
			return;
		}
		VoteData voteData = new VoteData();
		voteData.set("openid", openid);
		voteData.set("vote_id", voteId);
		voteData.set("item_id", itemId);
		voteData.set("vote_type", "10");
		voteData.set("vote_num", 1);
		voteData.set("create_time", new Date());
		voteData.save();
		ret.setData(VoteData.dao.getVoteNum(itemId));
		renderJson(ret);
	}
	
	/**
	 * 
	 * @Description 获取投票列表
	 * @author zhengwei
	 * @date 2017年6月18日 上午2:12:54
	 * @return void
	 */
	public void getVoteDetail(){
		Ret ret = new Ret();
		int itemId = getParaToInt("itemId");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		Page<VoteData> list = VoteData.dao.getList(itemId,pageNumber,pageSize);
		ret.setData(list);
		renderJson(ret);
	}
	
	/**
	 * 
	 * @Description 获取投票人
	 * @author zhengwei
	 * @date 2017年6月18日 上午2:12:54
	 * @return void
	 */
	public void getVoteUser(){
		Ret ret = new Ret();
		int itemId = getParaToInt("itemId");
		List<Record> list = VoteData.dao.getVoteUser(itemId);
		ret.setData(list);
		renderJson(ret);
	}
}
