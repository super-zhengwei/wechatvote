
package com.vote.model;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.vote.util.Utility;
import com.vote.util.WeiXinUtils;


@SuppressWarnings("serial")
public class Users extends Model<Users>
{
	static Log log = Log.getLog(Users.class);
    public static final Users dao = new Users();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    
    /**
     * 
     * @Description: 保存或者更新用户信息 
     * @author zhengwei  
     * @date 2016年12月10日 上午11:22:04
     * @param userInfo
     * @return void    
     */
    public void save(String userStr){
    	JSONObject obj = JSON.parseObject(userStr);
    	System.out.println(userStr);
		//判断openId 是否存在 如果存在就update 如果不存在就保存
		Users user = findByOpenId(obj.getString("openid"));
		if (user!=null) {
			user.set("nickname", WeiXinUtils.filterWeixinEmoji(obj.getString("nickname")));
			user.set("sex", obj.getString("sex"));
			user.set("city", obj.getString("city"));
			user.set("headimgurl", obj.getString("headimgurl"));
			user.set("subscribe", obj.getString("subscribe"));
			user.set("modify_time", new Date());
			user.update();
		}else {//保存关注用户
			if (StrKit.notBlank(obj.getString("openid"))) {
				Users me = new Users();
				me.set("openid", obj.getString("openid"));
				me.set("nickname", WeiXinUtils.filterWeixinEmoji(obj.getString("nickname")));
				me.set("sex", obj.getString("sex"));
				me.set("city", obj.getString("city"));
				me.set("headimgurl", obj.getString("headimgurl"));
				me.set("subscribe", obj.getString("subscribe"));
				me.set("modify_time", new Date());
				me.save();
			}
		}
    }
    
    public void unsubscribe(String openid){
    	Users user = findByOpenId(openid);
    	if(null!=user){
    		user.set("subscribe", "0");
        	user.update();
    	}
    }
    public Users findByOpenId(String openid){
		return this.findFirst("select * from users where openid=?", openid);
	}
    
    public Users findByDisNum(int disNum){
    	return this.findFirst("select * from users where dis_num=?", disNum);
    }
    
    public void update(String openid,String username,String mobile){
    	Users users= findByOpenId(openid);
    	if(users!=null){
    		Db.update("update users set username=?,mobile=? where openid=?",username,mobile,openid);
    	}else{
    		users = new Users();
			users.set("openid", openid);
			users.set("username", username);
			users.set("mobile",mobile);
			users.set("modify_time", new Date());
			users.save();
    	}
    }
    
    public Page<Users> query(String keyword,String userType, int pageNumber, int pageSize){
    	StringBuffer sql = new StringBuffer();
    	StringBuffer select = new StringBuffer();
    	select.append(" select * ");
    	sql.append(" from users  where 1=1 ");
    	if(!Utility.isEmpty(keyword)){
    		sql.append(" and (username like '%"+keyword+"%' or nickname like '%"+keyword+"%' or mobile like '%"+keyword+"%'"
    				+ " or openid like '%"+keyword+"%')");
    	}
    	if(!Utility.isEmpty(userType)){
    		sql.append(" and type = '"+userType+"'");
    	}
    	sql.append(" order by modify_time desc");
        return paginate(pageNumber, pageSize, select.toString(),
                sql.toString());
    }
}
