
package com.vote.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.vote.util.Utility;

@SuppressWarnings("serial")
public class WxMsg extends Model<WxMsg>
{
    public static final WxMsg dao = new WxMsg();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    public Page<WxMsg> query(String keyword,String msgType,int pageNumber, int pageSize){
    	StringBuffer sql = new StringBuffer();
    	StringBuffer select = new StringBuffer();
    	select.append(" select * ,(select type_name from wx_msg_type where id=t.msg_type) type_name");
    	sql.append(" from wx_msg t where 1=1 ");
    	if(!Utility.isEmpty(keyword)){
    		sql.append(" and msg_title like '%"+keyword+"%'");
    	}
    	if(!Utility.isEmpty(msgType)){
    		sql.append(" and msg_type = '"+msgType+"'");
    	}
        return paginate(pageNumber, pageSize, select.toString(),
                sql.toString());
    }
}
