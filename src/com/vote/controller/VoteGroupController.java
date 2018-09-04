
package com.vote.controller;

import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.VoteGroup;
import com.vote.model.VoteItem;
import com.vote.util.Ret;
import com.vote.util.Utility;

@Before(POST.class)
public class VoteGroupController extends BaseController {
	
	/**
	 * @Description: 查询分组 
	 * @author zhengwei  
	 * @date 2017年4月4日 下午1:56:45
	 * 
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void query() {
		String groupName = getPara("groupName");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		int voteId = getParaToInt("voteId");
		Page<VoteGroup> list= VoteGroup.dao.query(groupName,voteId,pageNumber, pageSize);
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
		VoteGroup voteGroup = new VoteGroup();
		Map<String,Object> param = Utility.changeMap(getParaMap());
		voteGroup._setAttrs(param);
		if(Utility.isEmpty(voteGroup.getStr("id"))){
			voteGroup.save();
		}else{
			voteGroup.update();
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
		int id = getParaToInt("id");
		List<Record> list = VoteItem.dao.findByGroup(id);
		if(list.isEmpty()){
			VoteGroup.dao.deleteById(getPara("id"));
			ret.success("删除成功");
		}else{
			ret.addError("删除失败，该分组已经被选择");
		}
		renderJson(ret);
	}
	/**
	 * 
	 * @Description:获取分组
	 * @author zhengwei  
	 * @date 2017年1月19日 下午8:20:05      
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void getGroup() {
		Ret ret = new Ret();
		int id = getParaToInt("id");
		VoteGroup item = VoteGroup.dao.findById(id);
		ret.setData(item);
		renderJson(ret);
	}
	/**
	 * 
	 * @Description: 查询分组 
	 * @author zhengwei  
	 * @date 2017年2月16日 下午2:30:55
	 * 
	 * @return void    
	 */
	public void queryGroup(){
		Ret ret = new Ret();
		int voteId = getParaToInt("voteId");
		List<VoteGroup> list = VoteGroup.dao.queryGroup(voteId);
		ret.setData(list);
		renderJson(ret);
	}
}
