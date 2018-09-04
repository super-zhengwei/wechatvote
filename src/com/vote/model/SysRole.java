
package com.vote.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.vote.util.Utility;

@SuppressWarnings("serial")
public class SysRole extends Model<SysRole>
{
    public static final SysRole dao = new SysRole();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    
    public SysRole getSysRole(String roleName){
    	return findFirst("select * from sys_role where role_name = ?", roleName);
    }
    
    public Page<SysRole> query(String roleName, int pageNumber, int pageSize)
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" from sys_role where 1=1 ");
    	if(!Utility.isEmpty(roleName)){
    		sql.append(" and role_name like '%"+roleName+"%'");
    	}
    	sql.append(" order by id");
        return paginate(pageNumber, pageSize, "select * ",
                sql.toString());
    }

    public List<SysRole> findByUsername(String username)
    {
        return find("select * from sys_user where user_name = ?", username);
    }
}
