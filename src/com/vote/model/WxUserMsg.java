package com.vote.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.vote.util.Utility;

public class WxUserMsg extends Model<WxUserMsg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final WxUserMsg ME= new WxUserMsg();
	
	public Page<WxUserMsg> getUserMsgs(String openid,String startDate,
			String endDate,int pageNumber, int pageSize)
    {
    	StringBuffer sql = new StringBuffer();
    	StringBuffer select = new StringBuffer();
    	select.append(" select * ");
    	sql.append(" from wx_user_msg where 1=1 ");
    	if(!Utility.isEmpty(openid)){
    		sql.append(" and (openId = '"+openid+"')");
    	}
    	if(!Utility.isEmpty(startDate)){
    		sql.append(" and (createTime >= '"+startDate+"')");
    	}
    	if(!Utility.isEmpty(endDate)){
    		sql.append(" and createTime <='"+endDate+"'");
    	}
    	sql.append(" order by createTime desc");
        return paginate(pageNumber, pageSize, select.toString(),
                sql.toString());
	}

}
