
package com.vote.controller;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.vote.interceptor.LoginInterceptor;

@Before({LoginInterceptor.class,POST.class})
public class WxReplyMsgController extends BaseController{
	
}
