
package com.vote.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.VoteCode;
import com.vote.model.VoteItem;
import com.vote.model.VoteItemImg;
import com.vote.util.Ret;
import com.vote.util.Utility;

@Before(POST.class)
public class VoteItemController extends BaseController {
	
	/**
	 * @Description: 查询分组 
	 * @author zhengwei  
	 * @date 2017年4月4日 下午1:56:45
	 * 
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void query() {
		String keyword = getPara("keyword");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		int voteId = getParaToInt("voteId");
		Page<VoteItem> list= VoteItem.dao.query(keyword,"",voteId,pageNumber, pageSize,"","all");
		renderJson(list);
	}
	/**
	 * 
	 * @Description: 新增或者修改 
	 * @author zhengwei  
	 * @date 2017年1月19日 下午8:20:05      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void submit() {
		Ret ret = new Ret();
		VoteItem voteItem = new VoteItem();
		Map<String,Object> param = Utility.changeMap(getParaMap());
		JSONArray list = JSONArray.parseArray(getPara("list"));
		param.remove("list");
		voteItem._setAttrs(param);
		if(Utility.isEmpty(voteItem.getStr("id"))){
			voteItem.set("user_code", VoteCode.dao.getCode(getParaToInt("vote_id")));
			voteItem.set("status", "00");
			voteItem.set("create_time", new Date());
			voteItem.save();
			ret.setData(voteItem.getInt("id"));
		}else{
			voteItem.update();
			VoteItemImg.dao.deleteByItemId(Integer.parseInt(voteItem.getStr("id")));
			ret.setData(voteItem.getStr("id"));
		}
		for(int i=0;i<list.size();i++) {
			VoteItemImg img = new VoteItemImg();
			img.set("vote_id", voteItem.get("vote_id"));
			img.set("item_id", voteItem.get("id"));
			img.set("item_img", list.get(i));
			img.save();
		}
		ret.success("保存成功");
		renderJson(ret);
	}
	/**
	 * 
	 * @Description:删除
	 * @author zhengwei  
	 * @date 2017年1月19日 下午8:20:05      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void delete() {
		Ret ret = new Ret();
		VoteItem.dao.deleteById(getPara("id"));
		ret.success("删除成功");
		renderJson(ret);
	}
	/**
	 * 
	 * @Description:审核
	 * @author zhengwei  
	 * @date 2017年1月19日 下午8:20:05      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void updateStatus() {
		Ret ret = new Ret();
		VoteItem item = VoteItem.dao.findById(getPara("id"));
		if("00".equals(item.get("status"))){
			item.set("status", "10");
			item.update();
			ret.success("审核成功");
		}else{
			ret.addError("不能重复审核");
		}
		renderJson(ret);
	}
	/**
	 * 
	 * @Description: 新增或者修改 
	 * @author zhengwei  
	 * @date 2017年1月19日 下午8:20:05      
	 * @return void    
	 */
	public void save() {
		Ret ret = new Ret();
		VoteItem voteItem = new VoteItem();
		Map<String,Object> param = Utility.changeMap(getParaMap());
		JSONArray list = JSONArray.parseArray(getPara("list"));
		param.remove("list");
		voteItem._setAttrs(param);
		if(Utility.isEmpty(voteItem.getStr("id"))){
			Record record = VoteItem.dao.findByOpenid(getPara("openid"));
			if(record!=null){
				ret.addError("你已经报名过了，不能重复报名");
				renderJson(ret);
				return;
			}
			voteItem.set("status", "00");
			voteItem.set("user_code", VoteCode.dao.getCode(getParaToInt("vote_id")));
			voteItem.set("create_time", new Date());
			voteItem.save();
			ret.setData(voteItem.getInt("id"));
		}else{
			voteItem.update();
			VoteItemImg.dao.deleteByItemId(Integer.parseInt(voteItem.getStr("id")));
			ret.setData(voteItem.getStr("id"));
		}
		for(int i=0;i<list.size();i++) {
			VoteItemImg img = new VoteItemImg();
			img.set("vote_id", voteItem.get("vote_id"));
			img.set("item_id", voteItem.get("id"));
			img.set("item_img", list.get(i));
			img.save();
		}
		ret.success("保存成功");
		renderJson(ret);
	}
	
	public void getList(){
		Ret ret = new Ret();
		int voteId = getParaToInt("id");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		String keyword = getPara("keyword");
		String itemType = getPara("itemType");
		String flag = getPara("flag");
		Page<VoteItem> list= VoteItem.dao.query(keyword,itemType,voteId,pageNumber, pageSize,flag,"10");
		ret.setData(list);
		renderJson(ret);
	}
	
	public void getItemById(){
		Ret ret = new Ret();
		int id = getParaToInt("id");
		Record item = VoteItem.dao.getItem(id);
		item.set("list", VoteItemImg.dao.getList(id));
		ret.setData(item);
		renderJson(ret);
	}
	
	public void getItemInfo(){
		Ret ret = new Ret();
		int itemId = getParaToInt("itemId");
		Record voteItem = VoteItem.dao.getItem(itemId);
		Record item = VoteItem.dao.getItemInfo(itemId);
		List<Record> list = VoteItem.dao.getItemList(voteItem.getInt("vote_id"),"",(voteItem.getInt("group_id")-1)+"");
		for(int i=0;i<list.size();i++){
			Record record1 = new Record();
			Record record2 = new Record();
			if(i!=0){
				 record1 =  list.get(i-1);
			}
			if(i!=list.size()-1){
				 record2 =  list.get(i+1);
			}
			Record record =  list.get(i);
			if(itemId==record.getInt("id")){
				item.set("num4", i+1);
				if(i==0){
					item.set("num5", 0);
				}else{
					item.set("num5", record1.getBigDecimal("total_num").intValue()-record.getBigDecimal("total_num").intValue());
				}
				if(i==list.size()-1){
					item.set("num6", 0);
				}else{
					item.set("num6", record.getBigDecimal("total_num").intValue()-record2.getBigDecimal("total_num").intValue());
				}
			}
		}
		ret.setData(item);
		renderJson(ret);
	}

	public void getItemList(){
		Ret ret = new Ret();
		int voteId = getParaToInt("voteId");
		String itemType = getPara("itemType");
		String flag = getPara("flag");
		List<Record> list = VoteItem.dao.getItemList(voteId,itemType,flag);
		ret.setData(list);
		renderJson(ret);
	}
}
