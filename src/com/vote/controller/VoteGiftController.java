
package com.vote.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.VoteGift;
import com.vote.util.Ret;
import com.vote.util.Utility;

@Before(POST.class)
public class VoteGiftController extends BaseController {
	
	/**
	 * @Description: 查询礼物 
	 * @author zhengwei  
	 * @date 2017年4月4日 下午1:56:45
	 * 
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void query() {
		String giftName = getPara("giftName");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		int voteId = getParaToInt("voteId");
		Page<VoteGift> list= VoteGift.dao.query(giftName,voteId,pageNumber, pageSize);
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
		VoteGift voteGift = new VoteGift();
		Map<String,Object> param = Utility.changeMap(getParaMap());
		voteGift._setAttrs(param);
		if(Utility.isEmpty(voteGift.getStr("id"))){
			voteGift.set("create_time", new Date());
			voteGift.save();
		}else{
			voteGift.update();
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
		VoteGift.dao.deleteById(getPara("id"));
		ret.success("删除成功");
		renderJson(ret);
	}
	/**
	 * 
	 * @Description:获取礼物
	 * @author zhengwei  
	 * @date 2017年1月19日 下午8:20:05      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void getGift() {
		Ret ret = new Ret();
		int id = getParaToInt("id");
		VoteGift item = VoteGift.dao.findById(id);
		ret.setData(item);
		renderJson(ret);
	}
	/**
	 * 
	 * @Description: 获取礼物列表 
	 * @author zhengwei  
	 * @date 2017年2月23日 下午5:00:02
	 * 
	 * @return void    
	 */
	public void getGiftList(){
		Ret ret = new Ret();
		int voteId = getParaToInt("voteId");
		List<VoteGift> list = VoteGift.dao.getGiftList(voteId);
		ret.setData(list);
		renderJson(ret);
	}
}
