
package com.vote.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jfinal.kit.PropKit;
import com.vote.model.SysUser;

public class Constants
{
    // 登陆用户
    public static final String LOGINUSER = "loginUser";

    public static SysUser getLoginUser(HttpSession session)
    {
        return (SysUser) session.getAttribute(LOGINUSER);

    }
    
    /**
     * 
     * @Description: 获取图片地址 
     * @author zhengwei  
     * @date 2016年10月3日 下午4:04:59      
     * @return String    
     */
    public static String getImageUrl(String path){
    	if(path.startsWith("http")){
    		return path;
    	}else{
    		return PropKit.get("imageUrl")+(path.startsWith("/") ? path : "/" + path);
    	}
    }
    
    /**
     * 
     * @Description: 获取项目地址 
     * @author zhengwei  
     * @date 2016年10月3日 下午4:05:35      
     * @return String    
     */
    public static String getUrl(HttpServletRequest request,String path){
    	String rootPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    	if(path.startsWith("http")||path.startsWith("www")){
    		return path;
    	}else{
    		return rootPath+(path.startsWith("/") ? path : "/" + path);
    	}
    }
}
