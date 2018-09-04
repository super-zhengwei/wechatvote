
package com.vote.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.DictDetail;
import com.vote.util.Utility;

public class DictController extends BaseController
{

    public void query(){
    	String keyword = getPara("keyword");
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		Page<DictDetail> list= DictDetail.me.paginate(keyword,pageNumber, pageSize);
		Map<String,String> regionMap = DictDetail.me.getDictMap("REGION");
		JFinal.me().getServletContext().setAttribute("REGION", regionMap);
		Map<String,String> skuTypeMap = DictDetail.me.getDictMap("SKU_TYPE");
		JFinal.me().getServletContext().setAttribute("SKU_TYPE", skuTypeMap);
		renderJson(list);
    }

    /**
	 * 
	 * @Description: 新增或者修改 
	 * @author zhengwei  
	 * @date 2016年9月11日 下午8:20:05      
	 * @return void    
	 */
	@Before({LoginInterceptor.class,Tx.class})
	public void submit() {
		Map<String, Object> map = new HashMap<String,Object>();
		DictDetail dictDetail = new DictDetail();
		dictDetail._setAttrs(Utility.changeMap(getParaMap()));
		if(Utility.isEmpty(dictDetail.getStr("id"))){
			dictDetail.save();
			map.put("success", true);
			map.put("msg", "保存成功");
		}else{
			dictDetail.update();
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: 删除 
	 * @author zhengwei  
	 * @date 2016年9月13日 下午4:34:43      
	 * @return void    
	 */
	@Before({LoginInterceptor.class,Tx.class})
	public void delete() {
		Map<String, Object> map = new HashMap<String,Object>();
		int id = getParaToInt("id");
		DictDetail.me.deleteById(id);
		map.put("success", true);
		renderJson(map);
	}
	
	/**
	 * 
	 * @Description: TODO 
	 * @author zhengwei  
	 * @date 2016年9月13日 下午4:44:35      
	 * @return void    
	 */
	@Before(LoginInterceptor.class)
	public void getDict() {
		int id = getParaToInt("id");
		DictDetail dictDetail = DictDetail.me.findById(id);
		renderJson(dictDetail);
	}
    
    /**
     * 
     * @Description: TODO 
     * @author zhengwei  
     * @date 2016年8月8日 下午3:42:06      
     * @return void    
     */
    @Before(POST.class)
    public void queryByType(){
    	String dictType = getPara("dictType");
    	List<DictDetail> list = DictDetail.me.query(dictType);
    	renderJson(list);
    }
    
    public void getItemType() {
		List<DictDetail> list= DictDetail.me.query("item_type");
		String javascriptText=JsonKit.toJson(list);
		javascriptText = "var itemType="+javascriptText;
   		renderJavascript(javascriptText);
    }
}
