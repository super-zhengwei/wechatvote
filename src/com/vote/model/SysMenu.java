
package com.vote.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.vote.util.Utility;

@SuppressWarnings("serial")
public class SysMenu extends Model<SysMenu>
{
    public static final SysMenu dao = new SysMenu();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    public List<Record> query(String menuName){
    	List<Record> list = getParentMenu();
    	for(Record sysMenu:list){
    		sysMenu.set("children", getChildMenu(sysMenu.getInt("id"),menuName));
    	}
    	return list;
    }
    /**
     * 
     * @Description: 获取一级菜单 
     * @author zhengwei  
     * @date 2016年8月12日 下午4:45:50      
     * @return List<SysMenu>    
     */
    public List<Record> getParentMenu(){
    	return Db.find("select * from sys_menu where parent_id = '' or parent_id is null order by sort");
    }
    
    /**
     * 
     * @Description: 获取子菜单 
     * @author zhengwei  
     * @date 2016年8月12日 下午4:45:50      
     * @return List<SysMenu>    
     */
    public List<SysMenu> getChildMenu(int parentId,String menuName){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select * from sys_menu where parent_id = "+parentId+" ");
    	if(!Utility.isEmpty(menuName)){
    		sql.append(" and menu_name like '%"+menuName+"%'");
    	}
    	sql.append(" order by sort ");
    	return find(sql.toString());
    }
    
    public void deleteByParentId(int parentId){
    	Db.update("delete from sys_menu where parent_id = "+parentId+" ");
    }
}
