
package com.vote.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.vote.util.Utility;

@SuppressWarnings("serial")
public class SysUser extends Model<SysUser>
{
    public static final SysUser dao = new SysUser();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    
    public SysUser getSysUser(String userName){
    	return findFirst("select * from sys_user where user_name = ?", userName);
    }
    
    public SysUser findValidUserByMobile(String mobile){
    	return findFirst("select * from sys_user where mobile = ? and status=0", mobile);
    }
    
    public Page<SysUser> query(String keyword, int pageNumber, int pageSize){
    	StringBuffer sql = new StringBuffer();
    	StringBuffer select = new StringBuffer();
    	select.append(" select id,user_name,real_name,type,mobile,create_time,status,");
    	select.append(" (select group_concat(role_name) from sys_user_role a left join sys_role b on a.role_id=b.id where a.user_id=t.id) role_name");
    	sql.append(" from sys_user t where 1=1 ");
    	if(!Utility.isEmpty(keyword)){
    		sql.append(" and (user_name like '%"+keyword+"%' or real_name like '%"+keyword+"%' or mobile like '%"+keyword+"%')");
    	}
    	sql.append(" order by id");
        return paginate(pageNumber, pageSize, select.toString(),
                sql.toString());
    }

    public List<SysUser> findByUsername(String username){
        return find("select * from sys_user where user_name = ?", username);
    }
}
