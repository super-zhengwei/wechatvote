
package com.vote.model;

import java.util.List;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.vote.util.Utility;


@SuppressWarnings("serial")
public class VoteGroup extends Model<VoteGroup>
{
	static Log log = Log.getLog(VoteGroup.class);
    public static final VoteGroup dao = new VoteGroup();
    
    public Page<VoteGroup> query(String groupName, int voteId,int pageNumber, int pageSize){
    	StringBuffer sql = new StringBuffer();
    	StringBuffer select = new StringBuffer();
    	select.append(" select *");
    	sql.append(" from vote_group t where vote_id="+voteId+" ");
    	if(!Utility.isEmpty(groupName)){
    		sql.append(" and group_name like '%"+groupName+"%'");
    	}
    	sql.append(" order by id");
        return paginate(pageNumber, pageSize, select.toString(),
                sql.toString());
    }
    
    public List<VoteGroup> queryGroup(int voteId){
    	return find("select * from vote_group where vote_id=?",voteId);
    }
}
