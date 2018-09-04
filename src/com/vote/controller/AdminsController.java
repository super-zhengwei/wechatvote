
package com.vote.controller;

import com.jfinal.aop.Before;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.SysUser;
import com.vote.util.Constants;

@Before({LoginInterceptor.class})
public class AdminsController extends BaseController
{
    public void index()
    {
    	SysUser user = Constants.getLoginUser(getSession());
        if (empty(user))
        {
        	render("login.jsp");
            return;
        }
        render("index.jsp");
    }
}
