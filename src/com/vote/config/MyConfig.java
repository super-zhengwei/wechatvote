
package com.vote.config;

import org.apache.log4j.Logger;

import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.tx.TxByMethodRegex;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.vote.controller.AdminsController;
import com.vote.controller.CommonController;
import com.vote.controller.DictController;
import com.vote.controller.IndexController;
import com.vote.controller.SysMenuController;
import com.vote.controller.SysRoleController;
import com.vote.controller.SysRoleMenuController;
import com.vote.controller.SysUserController;
import com.vote.controller.SysUserRoleController;
import com.vote.controller.UserMsgController;
import com.vote.controller.UsersController;
import com.vote.controller.VoteCodeController;
import com.vote.controller.VoteController;
import com.vote.controller.VoteDataController;
import com.vote.controller.VoteGiftController;
import com.vote.controller.VoteGroupController;
import com.vote.controller.VoteItemController;
import com.vote.controller.VoteLogController;
import com.vote.controller.VotePayController;
import com.vote.controller.WeixinApiController;
import com.vote.controller.WeixinMsgController;
import com.vote.controller.WxMenuController;
import com.vote.controller.WxMsgController;
import com.vote.controller.WxMsgTypeController;
import com.vote.controller.WxReplyController;
import com.vote.controller.WxReplyMsgController;
import com.vote.model._MappingKit;

/**
 * API引导式配置
 */
public class MyConfig extends JFinalConfig
{
    private static Logger logger = Logger.getLogger(MyConfig.class);

    /**
     * 配置常量
     */
    public void configConstant(Constants me)
    {
        PropKit.use("config.properties"); // 加载少量必要配置，随后可用PropKit.get(...)获取值
        me.setDevMode(PropKit.getBoolean("devMode", false));
        me.setViewType(ViewType.JSP);// 2.2中默认的视图为jsp
        me.setEncoding("utf-8");
        me.setError404View("admin/common/404.jsp");
        me.setError500View("admin/common/500.jsp");
    }

    /**
     * 配置路由
     * base ==“/”== webcontent目录下
     * me.add(第一个参数是请求路径,第二个参数是哪个class响应，第三个参数是响应的web子目录)
     */
    public void configRoute(Routes me)
    {
        me.add("/", IndexController.class); // 第三个参数为该Controller的视图存放路径
        me.add("/index", IndexController.class, "/");
        me.add("/common", CommonController.class, "/admin");//公共
        me.add("/admin", AdminsController.class, "/admin");// 按照bootstrap模板开发而成
        me.add("/sysUser", SysUserController.class, "/admin");// 用户
        me.add("/sysRole", SysRoleController.class, "/admin");// 角色
        me.add("/sysRoleMenu", SysRoleMenuController.class, "/admin");// 角色菜单
        me.add("/sysUserRole", SysUserRoleController.class, "/admin");// 用户角色
        me.add("/sysMenu", SysMenuController.class, "/admin");// 菜单
        me.add("/dict", DictController.class, "/admin");// 字典
        me.add("/wxMenu", WxMenuController.class, "/admin");// 微信菜单
        me.add("/wxMsg", WxMsgController.class, "/admin");// 图文消息
        me.add("/wxMsgType", WxMsgTypeController.class, "/admin");// 图文消息分类
        me.add("/wxReply", WxReplyController.class, "/admin");// 自动回复
        me.add("/wxReplyMsg", WxReplyMsgController.class, "/admin");// 自动回复消息
        me.add("/msg", WeixinMsgController.class, "/admin");// 微信接口配置到公众号内
        me.add("/weixin", WeixinApiController.class, "/admin");// 微信相关
        me.add("/users", UsersController.class, "/admin");// 微信用户--会员
        me.add("/userMsg", UserMsgController.class, "/admin");// 微信用户留言
        me.add("/vote", VoteController.class, "/admin");// 活动管理
        me.add("/voteItem", VoteItemController.class, "/admin");// 选项
        me.add("/voteGroup", VoteGroupController.class, "/admin");// 分组
        me.add("/voteCode", VoteCodeController.class, "/admin");// 编号
        me.add("/voteData", VoteDataController.class, "/admin");// 用户投票数据
        me.add("/voteLog", VoteLogController.class, "/admin");// 访问日志
        me.add("/voteGift", VoteGiftController.class, "/admin");// 礼物
        me.add("/votePay", VotePayController.class, "/admin");// 支付
    }

    public static C3p0Plugin createC3p0Plugin() {
		return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}

    /**
     * 配置插件
     */
    public void configPlugin(Plugins me)
    {
		// 配置C3p0数据库连接池插件
 		//C3p0Plugin C3p0Plugin = createC3p0Plugin();
 		//me.add(C3p0Plugin);
 		
 		logger.info("配置插件开始..");
        DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"),
                PropKit.get("user"), PropKit.get("password"));
        WallFilter wf = new WallFilter();
        wf.setDbType("mysql");
        dp.addFilter(wf);
        me.add(dp);
        // 配置Ecache插件
        me.add(new EhCachePlugin());
        
        // 配置ActiveRecord插件
 		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
 		me.add(arp);
 		
 		// 所有配置在 MappingKit 中搞定
 		_MappingKit.mapping(arp);
        logger.info("配置插件结束..");
    }

    /**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me)
    {
        //me.addGlobalActionInterceptor(new PhoneInterceptor());
        me.add(new TxByMethodRegex("(.*submit.*|.*save.*|.*update.*|.*delete.*)"));// 2.2改动
    }

    /**
     * 配置处理器
     */
    public void configHandler(Handlers me)
    {
        logger.info("配置处理器开始..");
        me.add(new ContextPathHandler("base"));
        DruidStatViewHandler dsvh = new DruidStatViewHandler("/druid");
        me.add(dsvh);
        logger.info("配置处理器结束..");
    }

    @Override
    public void afterJFinalStart()
    {
        logger.info("afterJFinalStart开始..");
        ApiConfig ac = new ApiConfig();
		// 配置微信 API 相关参数
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));

		/**
		 *  是否对消息进行加密，对应于微信平台的消息加解密方式：
		 *  1：true进行加密且必须配置 encodingAesKey
		 *  2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		ApiConfigKit.putApiConfig(ac);
        // 网站域名
        JFinal.me().getServletContext().setAttribute("siteUrl",PropKit.get("siteUrl"));
        // 网站名称
        JFinal.me().getServletContext().setAttribute("siteName",PropKit.get("siteName"));
        JFinal.me().getServletContext().setAttribute("imageUrl",PropKit.get("imageUrl"));
        JFinal.me().getServletContext().setAttribute("appId",PropKit.get("appId"));
        JFinal.me().getServletContext().setAttribute("version",PropKit.get("version"));
        logger.info("afterJFinalStart结束..");
    }

//    @Override
//    public void beforeJFinalStop()
//    {
//        beforeJFinalStop();
//        logger.info("beforeJFinalStop结束");
//    }

    public static void main(String[] args)
    {
        JFinal.start("WebRoot", 80, "/mw_trip", 5);

    }
}
