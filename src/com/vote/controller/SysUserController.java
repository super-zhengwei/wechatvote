
package com.vote.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.SysLoginInfo;
import com.vote.model.SysUser;
import com.vote.util.Constants;
import com.vote.util.Utility;

@Before(POST.class)
public class SysUserController extends BaseController{
	
	/**
	 * 
	 * @Description: 登录 
	 * @author zhengwei  
	 * @date 2016年8月6日 上午10:24:35      
	 * @return void    
	 */
	public void login() throws IOException {
		String username = getPara("username");
		String password = getPara("password");
		Map<String,Object> map = new HashMap<String,Object>();
		SysUser sysUser = new SysUser();
		sysUser = SysUser.dao.getSysUser(username);
		if (empty(sysUser)) {
			map.put("success", false);
			map.put("msg", "不存在该用户");
			renderJson(map);
			return;
		}
		if (!Utility.MD5(sysUser.getStr("uuid") + password)
				.equals(sysUser.getStr("password"))) {
			map.put("success", false);
			map.put("msg", "用户密码错误");
			renderJson(map);
			return;
		}
		if (sysUser.getInt("status") == 1) {
			map.put("success", false);
			map.put("msg", "您的账号已被冻结，请联系管理员解封");
			renderJson(map);
			return;
		}
		getSession().setAttribute(Constants.LOGINUSER, sysUser);
		SysLoginInfo userlogininfo = new SysLoginInfo();
		userlogininfo.set("user_id", sysUser.getInt("id"));
		userlogininfo.set("login_time", new Date());
		String ip = getIpAddress();
		userlogininfo.set("login_ip", ip);
		userlogininfo.save();
		map.put("success", true);
		map.put("msg", "登录成功");
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: 用户查询 
	 * @author zhengwei  
	 * @date 2016年8月6日 上午10:24:53      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void query() {
		String keyword = getPara("keyword");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		Page<SysUser> list= SysUser.dao.query(keyword,pageNumber, pageSize);
		renderJson(list);
	}
	
	/**
	 * 
	 * @Description: 删除用户 
	 * @author zhengwei  
	 * @date 2016年8月7日 上午10:19:18      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void delete() {
		Map<String, Object> map = new HashMap<String,Object>();
		String id = getPara("id");
		try{
			SysUser.dao.deleteById(id);
			map.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
		}
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: 修改用户状态 
	 * @author zhengwei  
	 * @date 2016年8月7日 下午12:10:59      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void updateStatus() {
		Map<String, Object> map = new HashMap<String,Object>();
		String id = getPara("id");
		String status = getPara("status");
		try{
			SysUser sysUser = new SysUser();
			sysUser.set("id", id);
			sysUser.set("status", status);
			sysUser.update();
			map.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
		}
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: 新增或者修改 
	 * @author zhengwei  
	 * @date 2016年8月7日 下午8:20:05      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void submit() {
		Map<String, Object> map = new HashMap<String,Object>();
		try{
			SysUser sysUser = new SysUser();
			sysUser._setAttrs(Utility.changeMap(getParaMap()));
			SysUser user = Constants.getLoginUser(getSession());
			SysUser u=SysUser.dao.getSysUser(sysUser.getStr("user_name"));
			if(Utility.isEmpty(sysUser.getStr("id"))){
				if(u!=null){
					map.put("success", false);
					map.put("msg", "账号已经存在，请重新填写");
				}else{
					sysUser.set("uuid", UUID());
					sysUser.set("status", "0");
					sysUser.set("password", Utility.MD5(sysUser.getStr("uuid") + "123456"));
					sysUser.set("create_time", new Date());
					sysUser.set("creator", user.getInt("id"));
					sysUser.set("modify_time", new Date());
					sysUser.set("modifier", user.getInt("id"));
					sysUser.save();
					map.put("success", true);
					map.put("msg", "保存成功");
				}
			}else{
				if(u!=null&&u.getInt("id")!=Integer.parseInt(sysUser.getStr("id"))){
					map.put("success", false);
					map.put("msg", "账号已经存在，请重新填写");
				}else{
					sysUser.set("modify_time", new Date());
					sysUser.set("modifier", user.getInt("id"));
					sysUser.update();
					map.put("success", true);
					map.put("msg", "保存成功");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "保存失败");
		}
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: TODO 
	 * @author zhengwei  
	 * @date 2016年8月8日 下午4:44:35      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void getUser() {
		int id = getParaToInt("id");
		SysUser user = SysUser.dao.findById(id);
		renderJson(user);
	}
	
	/**
	 * 
	 * @Description: 修改密码 
	 * @author zhengwei  
	 * @date 2016年8月15日 下午3:44:15      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void updatePwd(){
		Map<String, Object> map = new HashMap<String,Object>();
		try{
			String oldPassword = getPara("oldPassword");
			String newPassword = getPara("newPassword");
			SysUser user = Constants.getLoginUser(getSession());
			if (!Utility.MD5(user.getStr("uuid") + oldPassword)
					.equals(user.getStr("password"))) {
				map.put("success", false);
				map.put("msg", "旧密码错误");
			}else{
				user.set("password", Utility.MD5(user.getStr("uuid") + newPassword));
				user.update();
				getSession().setAttribute(Constants.LOGINUSER, user);
				map.put("success", true);
				map.put("msg", "修改成功");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "修改失败");
		}
		renderJson(map);
	}
}
