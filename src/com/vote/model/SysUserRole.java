
package com.vote.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class SysUserRole extends Model<SysUserRole>
{
    public static final SysUserRole dao = new SysUserRole();
    
    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    
    /**
     * 
     * @Description: 已选择角色 
     * @author zhengwei  
     * @date 2016年8月10日 下午2:52:31      
     * @return List<SysUserRole>    
     */
    public List<SysUserRole> getSelectRole(int userId){
    	return find("select * from sys_role where id in(select role_id from sys_user_role where user_id=?)", userId);
    }
    
    /**
     * 
     * @Description: 未选择角色 
     * @author zhengwei  
     * @date 2016年8月10日 下午2:52:46      
     * @return List<SysUserRole>    
     */
    public List<SysUserRole> getNotSelectRole(int userId){
    	return find("select * from sys_role where id not in(select role_id from sys_user_role where user_id=?)", userId);
    }
    
    /**
     * 
     * @Description: 批量删除 
     * @author zhengwei  
     * @date 2016年8月10日 下午10:33:53      
     * @return void    
     */
    public void delRole(Object[][] paras){
	   Db.batch("delete from sys_user_role where user_id = ? and role_id=?", paras,1000);
    }

}
