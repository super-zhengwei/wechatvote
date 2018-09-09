
package com.vote.model;

import java.util.List;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;


@SuppressWarnings("serial")
public class VoteData extends Model<VoteData>
{
	static Log log = Log.getLog(VoteData.class);
    public static final VoteData dao = new VoteData();

    public Record getByOpenid(String openid, int groupId){
    	return Db.findFirst("select b.item_type from vote_data a, vote_item b where a.openid=? and a.vote_type='10'"
    			+ " and a.item_id =b.id and b.group_id=? and DATE_FORMAT(a.create_time, '%Y-%m-%d') = CURDATE() ",openid, groupId);
    }
    
    public int getVoteNum(int itemId){
    	Record record = Db.findFirst("select sum(vote_num) vote_num from vote_data where item_id=?",itemId);
    	return record.getBigDecimal("vote_num").intValue();
    }
    
    public Page<VoteData> getList(int itemId,int pageNumber,int pageSize){
    	StringBuffer sql = new StringBuffer();
    	StringBuffer select = new StringBuffer();
    	select.append(" select a.*,b.nickname,b.headimgurl,c.gift_num,");
    	select.append(" (select gift_name from vote_gift where id = c.gift_id) gift_name");
    	sql.append(" from vote_data a left join vote_pay c on a.pay_id = c.id,users b");
    	sql.append(" where a.openid = b.openid and a.item_id="+itemId+" order by a.create_time desc");
    	return paginate(pageNumber, pageSize, select.toString(),
                sql.toString());
    }
    
    public List<Record> getVoteUser(int itemId){
    	return Db.find("select b.nickname,b.headimgurl from vote_data a,users b"
    			+ " where a.openid = b.openid and a.item_id=? "
    			+ "group by b.nickname,b.headimgurl order by a.create_time desc",itemId);
    }
    
}
