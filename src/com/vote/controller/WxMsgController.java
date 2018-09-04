
package com.vote.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.vote.model.SysUser;
import com.vote.model.WxMsg;
import com.vote.util.Constants;
import com.vote.util.Utility;

@Before(POST.class)
public class WxMsgController extends BaseController{
	
	/**
	 * 
	 * @Description: 图文消息查询 
	 * @author zhengwei  
	 * @date 2016年8月23日 下午8:54:37      
	 * @return void    
	 */
	public void query() {
		String keyword = getPara("keyword");
		String msgType = getPara("msgType");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		Page<WxMsg> list= WxMsg.dao.query(keyword,msgType, pageNumber, pageSize);
		renderJson(list);
	}
	
	/**
	 * 
	 * @Description: 删除图文消息 
	 * @author zhengwei  
	 * @date 2016年8月23日 上午10:19:18      
	 * @return void    
	 */
	public void delete() {
		Map<String, Object> map = new HashMap<String,Object>();
		int id = getParaToInt("id");
		try{
			WxMsg.dao.deleteById(id);
			map.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
		}
		renderJson(map);
	}
	
	
	/**
	 * 
	 * @Description: 新增或者修改 
	 * @author zhengwei  
	 * @date 2016年8月21日 下午8:20:05      
	 * @return void    
	 */
	public void submit() {
		Map<String, Object> map = new HashMap<String,Object>();
		try{
			WxMsg wxMsg = new WxMsg();
			Map<String, Object> param = Utility.changeMap(getParaMap());
			param.remove("editorValue");
			wxMsg._setAttrs(param);
			SysUser user = Constants.getLoginUser(getSession());
			if(Utility.isEmpty(wxMsg.getStr("id"))){
				wxMsg.set("create_time", new Date());
				wxMsg.set("creator", user.getInt("id"));
				wxMsg.set("modify_time", new Date());
				wxMsg.set("modifier", user.getInt("id"));
				wxMsg.save();
				if("2".equals(wxMsg.getStr("msg_mode"))){
					wxMsg.set("msg_url", "../mobile/msgDetail.jsp?id="+wxMsg.getInt("id"));
					wxMsg.update();
				}
			}else{
				wxMsg.set("modify_time", new Date());
				wxMsg.set("modifier", user.getInt("id"));
				if("2".equals(wxMsg.getStr("msg_mode"))){
					wxMsg.set("msg_url", "../mobile/msgDetail.jsp?id="+wxMsg.getStr("id"));
				}
				wxMsg.update();
			}
			map.put("success", true);
			map.put("msg", "保存成功");
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "保存失败");
		}
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: TODO 
	 * @author zhengwei  
	 * @date 2016年8月8日 下午4:44:35      
	 * @return void    
	 */
	public void getMsg() {
		int id = getParaToInt("id");
		WxMsg msg = WxMsg.dao.findById(id);
		try {
			if(msg.getBytes("msg_content")!=null){
				msg.set("msg_content", new String(msg.getBytes("msg_content"),"utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		renderJson(msg);
	}
	
}
