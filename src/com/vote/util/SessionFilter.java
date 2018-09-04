package com.vote.util;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.PropKit;
import com.vote.model.SysUser;
public class SessionFilter implements Filter {

	private String forwardUrl;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filter) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String servletPath = request.getServletPath();
        if(StringUtils.isNotEmpty(request.getQueryString())) {
        	servletPath+="?"+ request.getQueryString();
        }
        String contextPath = request.getContextPath();
        if(servletPath.contains("/admin")){
        	SysUser user = Constants.getLoginUser(request.getSession());
            if (Utility.empty(user)&&!servletPath.contains("login.jsp")) {
                response.sendRedirect(contextPath + StringUtils.defaultIfEmpty(forwardUrl, "/"));
            } else {
                filter.doFilter(req, res);
            }
        }else if(servletPath.contains("/mobile")){
        	String openid="";
        	Cookie[] cookies = request.getCookies();
        	if(cookies!=null){
        	    for(Cookie cookie : cookies){
        	        if(cookie.getName().equals("openid"+PropKit.get("version"))){
        	        	openid = cookie.getValue();
        	        }
        	    }
        	}
        	if("".equals(openid)){
        		//response.sendRedirect(contextPath + "/weixin/toOauth?redirect=" + URLEncoder.encode(PropKit.get("siteUrl")+servletPath, "utf-8"));
        		Cookie userCookie=new Cookie("openid"+PropKit.get("version"),"oRxOB1J1DpsntTpsK7Tnya3DU2urc");
                userCookie.setMaxAge(-1);
                userCookie.setPath("/");
                response.addCookie(userCookie);
                filter.doFilter(req, res);
        	}else {
        		filter.doFilter(req, res);
        	}
        }else{
        	 filter.doFilter(req, res);
        }
        
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		forwardUrl = cfg.getInitParameter("forwardUrl");
	}

	
}
