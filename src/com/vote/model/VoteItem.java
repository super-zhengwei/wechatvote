
package com.vote.model;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vote.util.Utility;


@SuppressWarnings("serial")
public class VoteItem extends Model<VoteItem>
{
	static Log log = Log.getLog(VoteItem.class);
    public static final VoteItem dao = new VoteItem();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    
    public Page<VoteItem> query(String keyword,String itemType, int voteId,int pageNumber, 
    		int pageSize,String flag,String status){
    	StringBuffer sql = new StringBuffer();
    	StringBuffer select = new StringBuffer();
    	select.append(" select a.*,(select sum(vote_num) from vote_data where item_id=a.id) vote_num,");
    	select.append(" (select count(*) from vote_log where item_id=a.id) log_num,");
    	select.append(" (select group_name from vote_group where id=a.group_id) group_name");
    	sql.append(" from vote_item a where a.vote_id="+voteId+"");
    	if(!"all".equals(status)){
    		sql.append(" and a.status='"+status+"'");
    	}
    	if(!Utility.isEmpty(keyword)){
    		sql.append(" and (a.user_code='"+keyword+"' or a.user_name like '%"+keyword+"%')");
    	}
    	if(!Utility.isEmpty(itemType)&&"0".equals(flag)){
    		sql.append(" and a.item_type="+itemType+"");
    	}
    	if("0".equals(flag)){
    		sql.append(" and a.group_id = 1");
    	}else if("1".equals(flag)){
    		sql.append(" and a.group_id = 2");
    	}
    	if("all".equals(status)){
			sql.append(" order by a.create_time desc");
    	}else {
    		sql.append(" order by a.sort desc, a.user_code");
    	}
        return paginate(pageNumber, pageSize, select.toString(),
                sql.toString());
    }
    
    public Record getItem(int id){
    	return Db.findFirst("select a.*,b.headimgurl, c.vote_name,d.group_name from vote_item a "
    			+ " left join users b on a.openid = b.openid ,vote c,vote_group d "
    			+ " where a.vote_id=c.id and a.group_id = d.id and a.id=?",id);
    }
    
    public Record getItemInfo(int itemId){
    	return Db.findFirst(
    		"SELECT "
    		+ " IFNULL((select count(*) from vote_log where item_id=?) ,0) log_num,"
    		+ " IFNULL((select sum(vote_num) from vote_data where item_id=? ),0) total_num,"
    		+ " IFNULL((select sum(gift_num) from vote_pay where item_id=? and order_status='10'),0) gift_num from dual"
    		,itemId,itemId,itemId);
    }
    
    public List<Record> getItemList(int voteId, String itemType, String flag){
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select *, IFNULL((select count(*) from vote_log where item_id=a.id),0) log_num, ");
    	sql.append(" IFNULL((select sum(vote_num) from vote_data where item_id=a.id ),0) total_num ");
    	sql.append(" from vote_item a where a.status='10' and a.vote_id=? ");
    	if(StrKit.notBlank(itemType)){
    		sql.append(" and a.item_type='"+itemType+"'");
    	}
    	if("0".equals(flag)){
    		sql.append(" and a.group_id = 1");
    	}else if("1".equals(flag)){
    		sql.append(" and a.group_id = 2");
    	}
    	sql.append(" order by total_num desc,create_time ");
    	return Db.find(sql.toString(),voteId);
    }
    
    public List<Record> getGroupList(int voteId,String group){
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select b.group_name user_name,sum(c.vote_num) total_num, ");
    	sql.append(" IFNULL((select count(*) from vote_log where item_id=a.id),0) log_num");
    	sql.append(" from vote_item a,vote_group b ,vote_data c");
    	sql.append(" where a.group_id=b.id and c.item_id=a.id and a.vote_id=?");
    	if(StrKit.notBlank(group)){
    		sql.append(" and a.group_id="+group+"");
    	}
    	sql.append(" group by a.group_id order by total_num desc,a.create_time ");
    	return Db.find(sql.toString(),voteId);
    }
    
    public Record findByOpenid(String openid){
    	return Db.findFirst("select * from vote_item where openid=?",openid);
    }
    
    public List<Record> findByGroup(int groupId){
    	return Db.find("select * from vote_item where group_id=?",groupId);
    }
}
