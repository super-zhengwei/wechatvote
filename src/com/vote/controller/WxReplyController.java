
package com.vote.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.WxReply;
import com.vote.model.WxReplyMsg;
import com.vote.util.Utility;

@Before({LoginInterceptor.class,POST.class})
public class WxReplyController extends BaseController{
	
	
	/**
	 * 
	 * @Description: 关键字查询 
	 * @author zhengwei  
	 * @date 2016年8月17日 上午10:24:53      
	 * @return void    
	 */
	public void query() {
		String keyword = getPara("keyword");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		Page<WxReply> list= WxReply.dao.query(keyword,pageNumber, pageSize);
		renderJson(list);
	}
	
	/**
	 * 
	 * @Description: 查询关注回复消息 
	 * @author zhengwei  
	 * @date 2016年8月29日 下午3:19:20      
	 * @return void    
	 */
	public void getFollow() {
		Record record = WxReply.dao.getFollow();
		if(record==null){
			renderJson("1");
			return;
		}
		renderJson(record);
	}
	
	/**
	 * 
	 * @Description: 关注回复消息
	 * @author zhengwei  
	 * @date 2016年8月28日 下午8:20:05      
	 * @return void    
	 */
	@Before(Tx.class)
	public void followSave() {
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> res = new HashMap<String,Object>();
		String id = getPara("id");//WxReplyMsg id
		//保存或者修改
		if(Utility.isEmpty(id)){
			WxReply wxReply = new WxReply();
			wxReply.set("reply_type", "1");//关注回复类型
			wxReply.save();
			WxReplyMsg wxReplyMsg = new WxReplyMsg();
			wxReplyMsg.set("reply_id", wxReply.getInt("id"));
			wxReplyMsg.set("rm_type", "1");//文本消息
			wxReplyMsg.set("rm_content", getPara("rm_content"));
			wxReplyMsg.save();
			res.put("id", wxReplyMsg.getInt("id"));
		}else{
			WxReplyMsg wxReplyMsg = new WxReplyMsg();
			wxReplyMsg.set("id", Integer.parseInt(id));
			wxReplyMsg.set("rm_content", getPara("rm_content"));
			wxReplyMsg.update();
			res.put("id", wxReplyMsg.getInt("id"));
		}
		map.put("success", true);
		map.put("res", res);
		map.put("msg", "保存成功");
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: 删除关键字 
	 * @author zhengwei  
	 * @date 2016年8月29日 上午10:19:18      
	 * @return void    
	 */
	@Before(Tx.class)
	public void delete() {
		Map<String, Object> map = new HashMap<String,Object>();
		String id = getPara("id");
		WxReply.dao.deleteById(id);
		WxReplyMsg.dao.deleteByReplyId(Integer.parseInt(id));
		map.put("success", true);
		renderJson(map);
	}
	/**
	 * 
	 * @Description: 关注回复消息
	 * @author zhengwei  
	 * @date 2016年8月29日 下午8:20:05      
	 * @return void    
	 */
	@Before(Tx.class)
	public void keySave() {
		Map<String, Object> map = new HashMap<String,Object>();
		String id = getPara("id");
		WxReply wxReply = new WxReply();
		//保存或者修改
		if(Utility.isEmpty(id)){//WxReply id
			wxReply.set("reply_type", "2");//关键字回复类型
			wxReply.set("reply_keyword", getPara("reply_keyword"));//关键字回复类型
			wxReply.save();
		}else{
			wxReply.set("id", Integer.parseInt(id));//关键字回复类型
			wxReply.set("reply_keyword", getPara("reply_keyword"));//关键字回复类型
			wxReply.update();
			WxReplyMsg.dao.deleteByReplyId(Integer.parseInt(id));
		}
		//保存明细
		if("1".equals(getPara("rm_type"))){//文本消息
			WxReplyMsg wxReplyMsg = new WxReplyMsg();
			wxReplyMsg.set("reply_id", wxReply.getInt("id"));
			wxReplyMsg.set("rm_type", getPara("rm_type"));
			wxReplyMsg.set("rm_content", getPara("rm_content"));
			wxReplyMsg.save();
		}else{//图文消息
			String msgIds = ObjectUtils.toString(getPara("msgIds"));
			List<WxReplyMsg> list =new ArrayList<WxReplyMsg>();
			JSONArray arr = JSONArray.parseArray(msgIds);
			for(int i=0;i<arr.size();i++){
				JSONObject obj = (JSONObject) arr.get(i);
				WxReplyMsg menuMsg = new WxReplyMsg();
				menuMsg.set("reply_id",wxReply.getInt("id"));
				menuMsg.set("rm_type", getPara("rm_type"));
				menuMsg.set("msg_id", obj.getIntValue("msgId"));
				menuMsg.set("sort", obj.getIntValue("sort"));
				list.add(menuMsg);
			}
			Db.batchSave(list, 1000);
		}
		map.put("success", true);
		map.put("msg", "保存成功");
		
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: TODO 
	 * @author zhengwei  
	 * @date 2016年8月29日 下午4:44:35      
	 * @return void    
	 */
	public void getReply() {
		Map<String, Object> map = new HashMap<String,Object>();
		int id = getParaToInt("id");
		Record record = WxReply.dao.getReply(id);
		map.put("reply", record);
		if("2".equals(record.getStr("rm_type"))){
			List<Record> list= WxReplyMsg.dao.getMsgByReplyId(id);
			map.put("list", list);
		}
		renderJson(map);
	}
	
}
