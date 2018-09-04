
package com.vote.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class WxReplyMsg extends Model<WxReplyMsg>
{
    public static final WxReplyMsg dao = new WxReplyMsg();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    
    public void deleteByReplyId(int replyId){
    	Db.update("delete from wx_reply_msg where reply_id=?",replyId);
    }
    
    public List<Record> getMsgByReplyId(int replyId){
    	return Db.find("select b.id,b.msg_title,b.msg_description,b.msg_img,b.msg_url,a.sort from wx_reply_msg a,wx_msg b where a.msg_id=b.id and a.reply_id=?",replyId);
    }
}
