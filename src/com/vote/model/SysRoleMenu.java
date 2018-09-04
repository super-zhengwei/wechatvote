
package com.vote.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class SysRoleMenu extends Model<SysRoleMenu>
{
    public static final SysRoleMenu dao = new SysRoleMenu();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    
    public List<Record> queryByRoleId(int roleId){
    	String sql="select t.id,t.menu_name as name, 'false' open"
    			//+ "case t.parent_id WHEN  '' then 'true' else 'false' end open,"
    			+ "case when (select a.id from sys_role_menu a where a.role_id="+roleId+" and a.menu_id=t.id) is null then 'false' else 'true' end checked,"
    			+ "case t.parent_id WHEN  '' then '0' else parent_id end pId from sys_menu t order by t.sort";
    	return Db.find(sql);
    }
    
    public void deleteByRoleId(int roleId){
    	Db.update("delete from sys_role_menu where role_id="+roleId);
    }
    
    public List<Record> queryParentMenu(int userId){
    	String sql="SELECT * from sys_menu m where m.id in"
    			+ "(select r.menu_id from sys_role_menu r where r.role_id in "
    			+ "(select u.role_id from sys_user_role u where u.user_id="+userId+")) "
    			+ "and (parent_id = '' or parent_id is null) order by m.sort";
    	return Db.find(sql);
    }
    public List<Record> queryChildrenMenu(int userId,int parentId){
    	String sql="SELECT * from sys_menu m where m.id in"
    			+ "(select r.menu_id from sys_role_menu r where r.role_id in "
    			+ "(select u.role_id from sys_user_role u where u.user_id="+userId+")) "
    			+ "and parent_id = "+parentId+"  order by m.sort";
    	return Db.find(sql);
    }
}
