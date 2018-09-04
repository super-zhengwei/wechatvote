
package com.vote.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.SysRole;
import com.vote.util.Utility;

@Before({LoginInterceptor.class,POST.class})
public class SysRoleController extends BaseController{
	
	
	/**
	 * 
	 * @Description: 角色查询 
	 * @author zhengwei  
	 * @date 2016年8月7日 上午10:24:53      
	 * @return void    
	 */
	public void query() {
		String roleName = getPara("roleName");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		Page<SysRole> list= SysRole.dao.query(roleName,pageNumber, pageSize);
		renderJson(list);
	}
	
	/**
	 * 
	 * @Description: 删除用户 
	 * @author zhengwei  
	 * @date 2016年8月7日 上午10:19:18      
	 * @return void    
	 */
	public void delete() {
		Map<String, Object> map = new HashMap<String,Object>();
		String id = getPara("id");
		try{
			SysRole.dao.deleteById(id);
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
	 * @date 2016年8月8日 下午8:20:05      
	 * @return void    
	 */
	public void submit() {
		Map<String, Object> map = new HashMap<String,Object>();
		try{
			SysRole sysRole = new SysRole();
			sysRole._setAttrs(Utility.changeMap(getParaMap()));
			SysRole role =SysRole.dao.getSysRole(sysRole.getStr("role_name"));
			if(Utility.isEmpty(sysRole.getStr("id"))){
				if(role!=null){
					map.put("success", false);
					map.put("msg", "角色名称已经存在，请重新填写");
				}else{
					sysRole.save();
					map.put("success", true);
					map.put("msg", "保存成功");
				}
			}else{
				if(role!=null&&role.getInt("id")!=Integer.parseInt(sysRole.getStr("id"))){
					map.put("success", false);
					map.put("msg", "角色名称已经存在，请重新填写");
				}else{
					sysRole.update();
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
	public void getRole() {
		int id = getParaToInt("id");
		SysRole role = SysRole.dao.findById(id);
		renderJson(role);
	}
}
