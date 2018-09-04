
package com.vote.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.Users;
import com.vote.model.VoteItem;
import com.vote.model.VotePay;
import com.vote.util.Ret;
import com.vote.util.Utility;

@Before(POST.class)
public class UsersController extends BaseController {
	
	/**
	 * 
	 * @Description: 根据openid获取用户信息 
	 * @author zhengwei  
	 * @date 2016年12月10日 下午12:15:17
	 * 
	 * @return void    
	 */
	public void findByOpenId(){
		Ret ret = new Ret();
		Users users = Users.dao.findByOpenId(getPara("openid"));
		ret.setData(users);
		renderJson(ret);
	}
	
	public void update(){
		Ret ret = new Ret();
		Users.dao.update(getPara("openid"),getPara("username"),getPara("mobile"));
		ret.setSuccess(true);
		renderJson(ret);
	}
	
	/**
	 * 
	 * @Description: 用户查询 
	 * @author zhengwei  
	 * @date 2017年1月19日 上午10:24:53      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void query() {
		String keyword = getPara("keyword");
		String userType = getPara("userType");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		Page<Users> list= Users.dao.query(keyword,userType,pageNumber, pageSize);
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
		Users users = new Users();
		users._setAttrs(Utility.changeMap(getParaMap()));
		if(Utility.isEmpty(users.getStr("dis_num"))){
			users.set("dis_num", null);
		}
		if(Utility.isEmpty(users.getStr("id"))){
			users.set("modify_time", new Date());
			users.save();
			map.put("success", true);
			map.put("msg", "保存成功");
		}else{
			users.set("modify_time", new Date());
			users.update();
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: 获取用户 
	 * @author zhengwei  
	 * @date 2017年1月19日 下午4:44:35      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void getUser() {
		int id = getParaToInt("id");
		Users user = Users.dao.findById(id);
		renderJson(user);
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
		Users.dao.deleteById(getPara("id"));
		map.put("success", true);
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: 个人中心数据 
	 * @author zhengwei  
	 * @date 2016年12月10日 下午12:15:17
	 * 
	 * @return void    
	 */
	public void getUserInfo(){
		Ret ret = new Ret();
		String openid = getPara("openid");
		Record record = VoteItem.dao.findByOpenid(openid);
		if(record!=null){
			Record item = VoteItem.dao.getItemInfo(record.getInt("id"));
			record.set("voteNum", item.getBigDecimal("total_num").intValue());
			record.set("giftNum", item.getBigDecimal("gift_num").intValue());
		}else{
			record= new Record();
			record.set("voteNum", 0);
			record.set("giftNum", 0);
		}
		record.set("sumAmt", VotePay.dao.getSumAmt(openid));
		ret.setData(record);
		renderJson(ret);
	}
}
