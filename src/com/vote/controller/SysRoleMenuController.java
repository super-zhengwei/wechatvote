
package com.vote.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.SysRoleMenu;
import com.vote.model.SysUser;
import com.vote.util.Constants;

@Before({LoginInterceptor.class,POST.class})
public class SysRoleMenuController extends BaseController{
	
	/**
	 * 
	 * @Description: 获取用户的菜单 
	 * @author zhengwei  
	 * @date 2016年8月14日 下午10:30:40      
	 * @return void    
	 */
	public void getMenu(){
		SysUser user = Constants.getLoginUser(getSession());
		List<Record> list =SysRoleMenu.dao.queryParentMenu(user.getInt("id"));
		for(Record r:list){
			r.set("children", SysRoleMenu.dao.queryChildrenMenu(user.getInt("id"), r.getInt("id")));
		}
		renderJson(list);
	}
	/**
	 * 
	 * @Description: 角色菜单查询 
	 * @author zhengwei  
	 * @date 2016年8月14日 上午10:24:53      
	 * @return void    
	 */
	public void queryByRoleId() {
		int roleId = getParaToInt("roleId");
		List<Record> list= SysRoleMenu.dao.queryByRoleId(roleId);
		renderJson(list);
	}
	
	/**
	 * 
	 * @Description: 删除用户 
	 * @author zhengwei  
	 * @date 2016年8月7日 上午10:19:18      
	 * @return void    
	 */
	public void save() {
		Map<String, Object> map = new HashMap<String,Object>();
		try{
			String menuIds = getPara("menuIds");
			int roleId = getParaToInt("roleId");
			String str[]=menuIds.split(",");
			
			//先删除再保存
			SysRoleMenu.dao.deleteByRoleId(roleId);
			List<SysRoleMenu> list =new ArrayList<SysRoleMenu>();
			for(int i=0;i<str.length;i++){
				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.set("role_id", roleId);
				roleMenu.set("menu_id", Integer.parseInt(str[i]));
				list.add(roleMenu);
			}
			Db.batchSave(list, 1000);
			map.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
		}
		renderJson(map);
	}
}
