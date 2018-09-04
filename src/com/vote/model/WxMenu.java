
package com.vote.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class WxMenu extends Model<WxMenu>
{
    public static final WxMenu dao = new WxMenu();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    public List<Record> query(){
    	List<Record> list = getParentMenu();
    	for(Record sysMenu:list){
    		sysMenu.set("children", getChildMenu(sysMenu.getInt("id")));
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
    	return Db.find("select * from wx_menu where parent_id = '' or parent_id is null order by sort");
    }
    
    /**
     * 
     * @Description: 获取子菜单 
     * @author zhengwei  
     * @date 2016年8月12日 下午4:45:50      
     * @return List<SysMenu>    
     */
    public List<WxMenu> getChildMenu(int parentId){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select * from wx_menu where parent_id = "+parentId+" ");
    	sql.append(" order by sort ");
    	return find(sql.toString());
    }
    
    /**
     * 
     * @Description: 删除子菜单 
     * @author zhengwei  
     * @date 2016年8月27日 下午3:41:18      
     * @return void    
     */
    public void deleteByParentId(int parentId){
    	Db.update("delete from wx_menu where parent_id = "+parentId+" ");
    }
    
    public String getNameByURL(String url){
    	if(StringUtils.isNotBlank(url)){
    		WxMenu menu = findFirst("select * from wx_menu where url = '" + url+"'");
    		String name = null;
    		if(menu!=null){
    			name = menu.getStr("name");
    			if(StringUtils.isBlank(name))
    				name = "-";
    		}
    		return name;
    	}
    	return "-";
    }
}
