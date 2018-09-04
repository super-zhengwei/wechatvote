
package com.vote.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vote.util.Constants;
import com.vote.util.Utility;

@SuppressWarnings("serial")
public class WxReply extends Model<WxReply>
{
    public static final WxReply dao = new WxReply();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    public Page<WxReply> query(String keyword, int pageNumber, int pageSize){
    	StringBuffer sql = new StringBuffer();
    	StringBuffer select = new StringBuffer();
    	select.append(" select a.id,a.reply_keyword,case b.rm_type when '1' then '文本消息' else '图文消息' end rm_type,");
    	select.append(" case b.rm_type when '1' then b.rm_content else '图文消息' end rm_content");
    	sql.append(" from wx_reply a,wx_reply_msg b where a.id = b.reply_id and a.reply_type='2' ");
    	if(!Utility.isEmpty(keyword)){
    		sql.append(" and a.reply_keyword like '%"+keyword+"%'");
    	}
    	sql.append(" group by a.id ");
        return paginate(pageNumber, pageSize, select.toString(),
                sql.toString());
    }
    
    
    public Record getFollow(){
    	System.out.println(Constants.getImageUrl("https://gss0.bdstatic.com/5eR1dDebRNRTm2_p8IuM_a/res/r/image/2016-10-03/172a1e903c830af4dc680e9aa9f27ebf.jpg"));
    	return Db.findFirst("select b.id,b.rm_content from wx_reply a,wx_reply_msg b "
    			+ "where a.id = b.reply_id and a.reply_type='1'");
    }
    
    public Record getReply(int id){
    	return Db.findFirst("select a.reply_keyword,b.rm_type,b.rm_content from wx_reply a,wx_reply_msg b "
    			+ "where a.id = b.reply_id and a.reply_type='2' and a.id=? group by a.id",id);
    }
    
    public List<Record> queryReply(String keyword){
    	return Db.find("select a.id,b.rm_type,b.rm_content from wx_reply a,wx_reply_msg b "
    			+ "where a.id = b.reply_id and a.reply_type='2' and a.reply_keyword like '%"+keyword+"%' group by a.id");
    }
}
