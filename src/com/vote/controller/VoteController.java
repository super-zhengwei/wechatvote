
package com.vote.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.SysUser;
import com.vote.model.Vote;
import com.vote.model.VoteCode;
import com.vote.util.Constants;
import com.vote.util.Ret;
import com.vote.util.Utility;

@Before(POST.class)
public class VoteController extends BaseController {
	
	
	
	/**
	 * 
	 * @Description: 活动查询 
	 * @author zhengwei  
	 * @date 2017年1月19日 上午10:24:53      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void query() {
		String keyword = getPara("keyword");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		Page<Vote> list= Vote.dao.query(keyword,pageNumber, pageSize);
		renderJson(list);
	}
	
	/**
	 * 
	 * @Description: 新增或者修改 
	 * @author zhengwei  
	 * @date 2017年1月19日 下午8:20:05      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void submit() {
		Map<String, Object> map = new HashMap<String,Object>();
		Vote vote = new Vote();
		Map<String,Object> param = Utility.changeMap(getParaMap());
		param.remove("editorValue");
		vote._setAttrs(param);
		if(Utility.isEmpty(vote.getStr("id"))){
			SysUser user = Constants.getLoginUser(getSession());
			vote.set("create_time", new Date());
			vote.set("creator", user.getInt("id"));
			vote.save();
			VoteCode voteCode = new VoteCode();
			voteCode.set("vote_id", vote.getInt("id"));
			voteCode.set("vote_code", 1);
			voteCode.save();
			map.put("success", true);
			map.put("msg", "保存成功");
			map.put("data", vote.getInt("id"));
		}else{
			vote.update();
			map.put("success", true);
			map.put("msg", "保存成功");
			map.put("data", vote.getStr("id"));
		}
		renderJson(map);
	}
	
	
	/**
	 * 
	 * @Description: 删除用户 
	 * @author zhengwei  
	 * @date 2017年1月19日 上午10:19:18      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void delete() {
		Map<String, Object> map = new HashMap<String,Object>();
		Vote.dao.deleteById(getPara("id"));
		map.put("success", true);
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: 修改状态 
	 * @author zhengwei  
	 * @date 2016年8月7日 下午12:10:59      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void updateStatus() {
		Map<String, Object> map = new HashMap<String,Object>();
		String id = getPara("id");
		String vote_status = getPara("vote_status");
		Vote vote = new Vote();
		vote.set("id", id);
		vote.set("vote_status", vote_status);
		vote.update();
		map.put("success", true);
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: TODO 
	 * @author zhengwei  
	 * @date 2016年8月8日 下午4:44:35      
	 * @return void    
	 * @throws Exception 
	 */
	@Before({LoginInterceptor.class})
	public void getVote() throws Exception {
		int id = getParaToInt("id");
		Vote vote = Vote.dao.findById(id);
		if(vote.getBytes("vote_detail")!=null){
			vote.set("vote_detail", new String(vote.getBytes("vote_detail"),"utf-8"));
		}
		renderJson(vote);
	}
	
	/**
	 * 
	 * @Description: TODO 
	 * @author zhengwei  
	 * @date 2017年2月15日 下午9:06:53
	 * 
	 * @return void    
	 * @throws Exception 
	 */
	public void queryVote() throws Exception{
		Ret ret =new Ret();
		int id = getParaToInt("id");
		Vote vote = Vote.dao.findById(id);
		if(vote.getBytes("vote_detail")!=null){
			vote.set("vote_detail", new String(vote.getBytes("vote_detail"),"utf-8"));
		}
		ret.setData(vote);
		renderJson(ret);
	}
	
	/**
	 * 
	 * @Description: 获取活动数据信息 
	 * @author zhengwei  
	 * @date 2017年2月21日 下午8:56:25
	 * 
	 * @return void    
	 */
	public void getVoteInfo(){
		Ret ret =new Ret();
		int id = getParaToInt("id");
		Record record = Vote.dao.getVoteInfo(id);
		ret.setData(record);
		renderJson(ret);
	}
}
