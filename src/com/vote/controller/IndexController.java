
package com.vote.controller;

import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.QrcodeApi;
import com.vote.util.Constants;

/**
 * IndexController
 */
public class IndexController extends BaseController {
	
	public void index(){
		redirect("/mobile/index.jsp");
	}

	public void logout() {

		if (getSession().getAttribute(Constants.LOGINUSER) != null) {
			// 暂时存放的是字符串，真实项目中应该是登陆用户的User实体类
			removeSessionAttr(Constants.LOGINUSER);
			renderJson(1);
			return;
		} else {
			renderJson(0);
			return;
		}

	}

	public void logoutside() {
		if (getSession().getAttribute(Constants.LOGINUSER) != null) {
			// 暂时存放的是字符串，真实项目中应该是登陆用户的User实体类
			removeSessionAttr(Constants.LOGINUSER);
			// redirect("/index");
		}
		sys();
	}

	public void sys() {
		render("admin/login.jsp");
	}
	
	public void test(){
		ApiResult result=QrcodeApi.createPermanent("123");
		renderJson(result);
	}
}
