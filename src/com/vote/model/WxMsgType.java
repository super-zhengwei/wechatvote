
package com.vote.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.vote.util.Utility;


@SuppressWarnings("serial")
public class WxMsgType extends Model<WxMsgType>
{
	static Log log = Log.getLog(WxMsgType.class);
    public static final WxMsgType dao = new WxMsgType();
   
    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    public Page<WxMsgType> query(String keyword,int pageNumber, int pageSize){
    	StringBuffer sql = new StringBuffer();
    	StringBuffer select = new StringBuffer();
    	select.append(" select * ");
    	sql.append(" from wx_msg_type t where 1=1 ");
    	if(!Utility.isEmpty(keyword)){
    		sql.append(" and type_name like '%"+keyword+"%'");
    	}
        return paginate(pageNumber, pageSize, select.toString(),
                sql.toString());
    }
}
