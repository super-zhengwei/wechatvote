
package com.vote.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Record;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.SysMenu;
import com.vote.util.Utility;

@Before({LoginInterceptor.class,POST.class})
public class SysMenuController extends BaseController{
	
	
	/**
	 * 
	 * @Description: 菜单查询 
	 * @author zhengwei  
	 * @date 2016年8月7日 上午10:24:53      
	 * @return void    
	 */
	public void query() {
		String menuName = getPara("menuName");
		List<Record> list= SysMenu.dao.query(menuName);
		renderJson(list);
	}
	
	/**
	 * 
	 * @Description: 删除菜单 
	 * @author zhengwei  
	 * @date 2016年8月7日 上午10:19:18      
	 * @return void    
	 */
	public void delete() {
		Map<String, Object> map = new HashMap<String,Object>();
		int id = getParaToInt("id");
		try{
			SysMenu.dao.deleteById(id);
			SysMenu.dao.deleteByParentId(id);
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
	 * @date 2016年8月11日 下午8:20:05      
	 * @return void    
	 */
	public void submit() {
		Map<String, Object> map = new HashMap<String,Object>();
		try{
			SysMenu sysMenu = new SysMenu();
			sysMenu._setAttrs(Utility.changeMap(getParaMap()));
			if(Utility.isEmpty(sysMenu.getStr("id"))){
				sysMenu.save();
			}else{
				sysMenu.update();
			}
			map.put("success", true);
			map.put("msg", "保存成功");
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
	public void getMenu() {
		int id = getParaToInt("id");
		SysMenu menu = SysMenu.dao.findById(id);
		renderJson(menu);
	}
	
	public void getParentMenu() {
		List<Record> list = SysMenu.dao.getParentMenu();
		renderJson(list);
	}
}
