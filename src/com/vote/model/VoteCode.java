
package com.vote.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;


@SuppressWarnings("serial")
public class VoteCode extends Model<VoteCode>
{
	static Log log = Log.getLog(VoteCode.class);
    public static final VoteCode dao = new VoteCode();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    public int getCode(int voteId){
    	VoteCode voteCode = findFirst("select * from vote_code where vote_id=?",voteId);
    	int code = voteCode.getInt("vote_code");
    	voteCode.set("vote_code", code+1);
    	voteCode.update();
    	return code;
    }
}
