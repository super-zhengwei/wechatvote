
package com.vote.model;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;


@SuppressWarnings("serial")
public class VotePay extends Model<VotePay>
{
	static Log log = Log.getLog(VotePay.class);
    public static final VotePay dao = new VotePay();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    public List<Record> getList(int voteId,String group,String flag){
    	StringBuffer sql = new StringBuffer();
    	if("0".equals(flag)){
	    	sql.append(" select a.item_id, b.user_name,sum(a.gift_num) gift_num,sum(a.total_amt) total_amt ");
			sql.append(" from vote_pay a,vote_item b");
			sql.append(" where a.vote_id="+voteId+" and a.item_id=b.id and order_status='10'");
			if(StrKit.notBlank(group)){
				sql.append(" and b.group_id="+group+"");
			}
			sql.append(" group by a.item_id,b.user_name order by total_amt desc");
    	}else{
    		sql.append(" select b.group_id, c.group_name user_name,sum(a.gift_num) gift_num,sum(a.total_amt) total_amt ");
			sql.append(" from vote_pay a,vote_item b,vote_group c");
			sql.append(" where a.vote_id="+voteId+" and a.item_id=b.id and b.group_id=c.id and order_status='10'");
			if(StrKit.notBlank(group)){
				sql.append(" and b.group_id="+group+"");
			}
			sql.append(" group by b.group_id, c.group_name order by total_amt desc");
    	}
		return Db.find(sql.toString());
    }
    public VotePay getByOrderNo(String orderNo){
    	return findFirst("select * from vote_pay where order_no=?",orderNo);
    }
    
    public VotePay getByWxOrderNo(String transactionId){
		return findFirst("select * from vote_pay where wx_order_no=?",transactionId);
	}
    
    public double getSumAmt(String openid){
    	Record record = Db.findFirst("select IFNULL(sum(total_amt),0) sum_amt "
    			+ "from vote_pay where order_status='10' and openid=?",openid);
    	return record.getDouble("sum_amt");
    }
}
