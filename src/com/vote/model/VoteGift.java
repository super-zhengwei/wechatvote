
package com.vote.model;

import java.util.List;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.vote.util.Utility;


@SuppressWarnings("serial")
public class VoteGift extends Model<VoteGift>
{
	static Log log = Log.getLog(VoteGift.class);
    public static final VoteGift dao = new VoteGift();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    public Page<VoteGift> query(String giftName, int voteId, int pageNumber, int pageSize){
    	StringBuffer sql = new StringBuffer();
    	StringBuffer select = new StringBuffer();
    	select.append(" select *");
    	sql.append(" from vote_gift t where vote_id="+voteId+" ");
    	if(!Utility.isEmpty(giftName)){
    		sql.append(" and gift_name like '%"+giftName+"%'");
    	}
    	sql.append(" order by id");
        return paginate(pageNumber, pageSize, select.toString(),
                sql.toString());
    }
    
    public List<VoteGift> getGiftList(int voteId){
    	return find("select * from vote_gift where vote_id=? order by gift_price",voteId);
    }
}
