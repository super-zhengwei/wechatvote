
package com.vote.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;


@SuppressWarnings("serial")
public class VoteLog extends Model<VoteLog>
{
	static Log log = Log.getLog(VoteLog.class);
    public static final VoteLog dao = new VoteLog();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    
}
