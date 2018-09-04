
package com.vote.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.Menu;
import com.jfinal.weixin.sdk.api.Menu.SubButton;
import com.jfinal.weixin.sdk.api.MenuApi;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.WxMenu;
import com.vote.model.WxMenuMsg;
import com.vote.util.Utility;

@Before({LoginInterceptor.class,POST.class})
public class WxMenuController extends BaseController{
	
	
	/**
	 * 
	 * @Description: 菜单查询 
	 * @author zhengwei  
	 * @date 2016年8月17日 上午10:24:53      
	 * @return void    
	 */
	public void query() {
		List<Record> list= WxMenu.dao.query();
		renderJson(list);
	}
	
	/**
	 * 
	 * @Description: 删除菜单 
	 * @author zhengwei  
	 * @date 2016年8月7日 上午10:19:18      
	 * @return void    
	 */
	public void delete() {
		Map<String, Object> map = new HashMap<String,Object>();
		int id = getParaToInt("id");
		try{
			WxMenu.dao.deleteById(id);
			WxMenu.dao.deleteByParentId(id);
			WxMenuMsg.dao.deleteByMenuId(id);
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
	 * @date 2016年8月11日 下午8:20:05      
	 * @return void    
	 */
	@Before(Tx.class)
	public void save() {
		Map<String, Object> map = new HashMap<String,Object>();
		WxMenu wxMenu = new WxMenu();
		Map<String,Object> param = Utility.changeMap(getParaMap());
		String msgIds = "";
		//获取图文消息列表
		if("click".equals(param.get("type"))){
			msgIds = ObjectUtils.toString(param.get("msgIds"));
			param.remove("msgIds");
		}
		wxMenu._setAttrs(param);
		//保存或者修改
		if(Utility.isEmpty(wxMenu.getStr("id"))){
			if(Utility.isEmpty(wxMenu.getStr("parent_id"))){
				List<Record> l = WxMenu.dao.getParentMenu();
				if(l.size()>=3){
					map.put("success", false);
					map.put("msg", "微信一级菜单不能超过3个");
					renderJson(map);
					return;
				}
			}else{
				List<WxMenu> l = WxMenu.dao.getChildMenu(Integer.parseInt(wxMenu.getStr("parent_id")));
				if(l.size()>=5){
					map.put("success", false);
					map.put("msg", "微信二级菜单不能超过5个");
					renderJson(map);
					return;
				}
			}
			wxMenu.save();
			if("click".equals(param.get("type"))){
				wxMenu.set("key", "key"+wxMenu.get("id"));
			}
			wxMenu.update();
		}else{
			wxMenu.update();
		}
		//保存菜单图文消息
		if("click".equals(param.get("type"))){
			List<WxMenuMsg> list =new ArrayList<WxMenuMsg>();
			WxMenuMsg.dao.deleteByMenuId(Integer.parseInt(wxMenu.get("id").toString()));
			JSONArray arr = JSONArray.parseArray(msgIds);
			for(int i=0;i<arr.size();i++){
				JSONObject obj = (JSONObject) arr.get(i);
				WxMenuMsg menuMsg = new WxMenuMsg();
				menuMsg.set("menu_id", Integer.parseInt(wxMenu.get("id").toString()));
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
	 * @date 2016年8月8日 下午4:44:35      
	 * @return void    
	 */
	public void getMenu() {
		Map<String, Object> map = new HashMap<String,Object>();
		int id = getParaToInt("id");
		WxMenu menu = WxMenu.dao.findById(id);
		map.put("menu", menu);
		if("click".equals(menu.getStr("type"))){
			List<Record> list= WxMenuMsg.dao.getMsgByMenuId(id);
			map.put("list", list);
		}
		renderJson(map);
	}
	
	public void getParentMenu() {
		List<Record> list = WxMenu.dao.getParentMenu();
		renderJson(list);
	}
	
	public void createMenu(){
		Map<String, Object> map = new HashMap<String,Object>();
		List<Menu> menus = new ArrayList<Menu>();
		List<Record> btnList = WxMenu.dao.getParentMenu();
		for(Record btnRecord:btnList){
			Menu menu = new Menu();
			List<WxMenu> subBtnList = WxMenu.dao.getChildMenu(btnRecord.getInt("id"));
			List<SubButton> subBtns = new ArrayList<SubButton>();
			menu.setName(btnRecord.get("name").toString());
			if(btnRecord.get("type")!=null){
				menu.setType(btnRecord.get("type").toString());
			}
			if("click".equals(btnRecord.get("type"))){
				menu.setKey(btnRecord.get("key").toString());
			}else if("view".equals(btnRecord.get("type"))){
				menu.setUrl(btnRecord.get("url").toString());
			}
			for(WxMenu wxMenu:subBtnList){
				SubButton subBtn = new SubButton();
				subBtn.setName(wxMenu.get("name").toString());
				subBtn.setType(wxMenu.get("type").toString());
				if("click".equals(wxMenu.get("type"))){
					subBtn.setKey(wxMenu.get("key").toString());
				}else if("view".equals(wxMenu.get("type"))){
					subBtn.setUrl(wxMenu.get("url").toString());
				}
				subBtns.add(subBtn);
			}
			menu.setSubButton(subBtns);
			menus.add(menu);
		}
		StringBuilder json = new StringBuilder("{\"button\":"+ JSON.toJSONString(menus) + "}");
		
		ApiResult apiResult = MenuApi.createMenu(json.toString());
		if (apiResult.isSucceed()){
			map.put("success", true);
			map.put("msg", apiResult.getJson());
		}else{
			map.put("success", false);
			map.put("msg", apiResult.getErrorMsg());
		}
		renderJson(map);
	}
}
