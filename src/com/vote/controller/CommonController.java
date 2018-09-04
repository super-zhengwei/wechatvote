
package com.vote.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.upload.UploadFile;
import com.vote.util.Utility;

@Before(POST.class)
public class CommonController extends BaseController{

	/**
	 * 
	 * @Description: 上传图片 
	 * @author zhengwei  
	 * @date 2016年8月21日 下午4:44:35      
	 * @return void    
	 */
	public void upload() {
		Map<String,Object> map = new HashMap<String,Object>();
		UploadFile file = getFile();
		String folder = getPara("folder")==null?"":getPara("folder");
		String path = Utility.upload(file, folder);
		map.put("success", true);
		map.put("path", path);
		renderJson(map);
	}
}
