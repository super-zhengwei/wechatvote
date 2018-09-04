
package com.vote.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vote.util.Utility;


@SuppressWarnings("serial")
public class Vote extends Model<Vote>
{
	static Log log = Log.getLog(Vote.class);
    public static final Vote dao = new Vote();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    
    public Page<Vote> query(String keyword, int pageNumber, int pageSize){
    	StringBuffer sql = new StringBuffer();
    	StringBuffer select = new StringBuffer();
    	select.append(" select * ");
    	sql.append(" from vote  where 1=1 ");
    	if(!Utility.isEmpty(keyword)){
    		sql.append(" and vote_name like '%"+keyword+"%'");
    	}
    	sql.append(" order by create_time desc");
        return paginate(pageNumber, pageSize, select.toString(),
                sql.toString());
    }
    
    public boolean checkDate(int id){
    	Vote vote =  findFirst("select * from vote where id=? and CURDATE() between start_date and end_date",id);
    	if(vote!=null){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public Record getVoteInfo(int id){
    	return Db.findFirst("SELECT (select count(*) from vote_item where status='10' and vote_id=?) num1,"
    			+ "(select sum(vote_num) from vote_data where vote_id=?) num2,"
    			+ "(select count(distinct openid) from vote_log where vote_id=?) num3,"
    			+ "(select count(*) from vote_group where vote_id=?) num4 from dual",id,id,id,id);
    }
}
