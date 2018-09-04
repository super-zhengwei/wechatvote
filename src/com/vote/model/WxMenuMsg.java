
package com.vote.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class WxMenuMsg extends Model<WxMenuMsg>
{
    public static final WxMenuMsg dao = new WxMenuMsg();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    
    /**
     * 
     * @Description: TODO 
     * @author zhengwei  
     * @date 2016年8月28日 下午12:03:12
     * @param menuId 菜单Id     
     * @return void    
     */
    public void deleteByMenuId(int menuId){
    	Db.update("delete from wx_menu_msg where menu_id = "+menuId+" ");
    }
    
    public void saveAll(List<WxMenuMsg> list){
    	Db.batchSave(list, 1000);
    }
    
    /**
     * 
     * @Description: 查询菜单图文消息 
     * @author zhengwei  
     * @date 2016年8月27日 下午4:21:02      
     * @return List<Record>    
     */
    public List<Record> getMsgByMenuId(int menuId){
    	return Db.find("select b.id,b.msg_title,b.msg_description,b.msg_img,b.msg_url,a.sort from wx_menu_msg a,wx_msg b "
    			+ "where a.msg_id=b.id and a.menu_id=?",menuId);
    }
    
    /**
     * 
     * @Description: 根据key值查询图文消息 
     * @author zhengwei  
     * @date 2016年10月3日 上午11:09:04      
     * @return List<Record>    
     */
    public List<Record> getMsgByKey(String key){
    	return Db.find("select b.msg_title,b.msg_description,b.msg_img,b.msg_url"
    			+ " from wx_menu a,wx_msg b,wx_menu_msg c where a.id=c.menu_id and "
    			+ " b.id=c.msg_id and a.key=? order by c.sort",key);
    }
}
