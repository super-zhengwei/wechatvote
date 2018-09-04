package com.vote.model;

import com.jfinal.plugin.activerecord.Model;

public class UserScanLog extends Model<UserScanLog> {
	
	private static final long serialVersionUID = 2525097105000999264L;
	
	public static final UserScanLog dao = new UserScanLog();
    
	public UserScanLog getByOpenId(String openid){
		return findFirst("select * from scan_log where openId=? order by scanTime desc",openid);
	}
}
