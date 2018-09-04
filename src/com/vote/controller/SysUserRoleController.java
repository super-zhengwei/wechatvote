
package com.vote.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.SysUserRole;

@Before({LoginInterceptor.class,POST.class})
public class SysUserRoleController extends BaseController{
	
	
	/**
	 * 
	 * @Description: 用户角色查询 
	 * @author zhengwei  
	 * @date 2016年8月7日 上午10:24:53      
	 * @return void    
	 */
	public void query() {
		Map<String,Object> map = new HashMap<String,Object>();
		int userId = getParaToInt("id");
		List<SysUserRole> select = SysUserRole.dao.getSelectRole(userId);
		List<SysUserRole> notselect = SysUserRole.dao.getNotSelectRole(userId);
		map.put("select", select);
		map.put("notselect", notselect);
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: 添加角色
	 * @author zhengwei  
	 * @date 2016年8月10日 下午8:20:05      
	 * @return void    
	 */
	public void addRole() {
		Map<String, Object> map = new HashMap<String,Object>();
		try{
			String ids = getPara("ids");
			int userId = getParaToInt("userId");
			String str[]=ids.split(",");
			for(int i=0;i<str.length;i++){
				SysUserRole userRole = new SysUserRole();
				userRole.set("user_id", userId);
				userRole.set("role_id", Integer.parseInt(str[i]));
				userRole.save();
			}
			map.put("success", true);
			map.put("msg", "添加成功");
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "添加失败");
		}
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: 删除用户 
	 * @author zhengwei  
	 * @date 2016年8月7日 上午10:19:18      
	 * @return void    
	 */
	public void delRole() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String ids = getPara("ids");
			int userId = getParaToInt("userId");
			String str[] = ids.split(",");
			List<Object[]> paramsList = new ArrayList<Object[]>();
			for (int i = 0; i < str.length; i++) {
				List<Object> paras = new ArrayList<Object>();
				paras.add(userId);
				paras.add(Integer.parseInt(str[i]));
				paramsList.add(paras.toArray());
			}
			Object[][] params = new Object[paramsList.size()][];
			int i = 0;
			for (Object[] obj : paramsList) {
				params[i++] = obj;
			}
			SysUserRole.dao.delRole(params);
			map.put("success", true);
			map.put("msg", "移除成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "移除失败");
		}
		renderJson(map);
	}
}
