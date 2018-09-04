
package com.vote.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.vote.model.SysUser;
import com.vote.util.Constants;
import com.vote.util.Utility;

public class LoginInterceptor implements Interceptor
{

    @Override
    public void intercept(Invocation inv)
    {
        Controller controller = inv.getController();
        SysUser u = Constants.getLoginUser(controller.getSession());
        if (!Utility.empty(u))
        {
            inv.invoke();
        }
        else
        {
            String message = "请先登录再进行操作！";
            controller.setAttr("message", message);
            String redirectionUrl = controller.getRequest().getContextPath()
                    + "/";
            controller.setAttr("redirectionUrl", redirectionUrl);
            controller.renderJsp("login.jsp");
        }
    }
}
