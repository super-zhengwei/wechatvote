
package com.vote.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.vote.model.WxMsgType;
import com.vote.util.Utility;

@Before(POST.class)
public class WxMsgTypeController extends BaseController {
	
	/**
	 * 
	 * @Description: 图文消息查询 
	 * @author zhengwei  
	 * @date 2016年8月23日 下午8:54:37      
	 * @return void    
	 */
	public void query() {
		String keyword = getPara("keyword");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		Page<WxMsgType> list= WxMsgType.dao.query(keyword, pageNumber, pageSize);
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
			WxMsgType.dao.deleteById(id);
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
		WxMsgType wxMsg = new WxMsgType();
		Map<String, Object> param = Utility.changeMap(getParaMap());
		wxMsg._setAttrs(param);
		if(Utility.isEmpty(wxMsg.getStr("id"))){
			wxMsg.save();
		}else{
			wxMsg.update();
		}
		map.put("success", true);
		map.put("msg", "保存成功");
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: TODO 
	 * @author zhengwei  
	 * @date 2016年8月8日 下午4:44:35      
	 * @return void    
	 */
	public void getMsgType() {
		int id = getParaToInt("id");
		WxMsgType msg = WxMsgType.dao.findById(id);
		renderJson(msg);
	}	
}
