package com.vote.util;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.weixin.sdk.api.TemplateMsgApi;

public class TemplateMsg {
	static Log log = Log.getLog(TemplateMsg.class);
	/**
	 * 
	 * @Description: TODO 
	 * @author zhengwei  
	 * @date 2016年12月5日 下午5:06:00
	 * @param Record
	 * @return
	 */
	public static void sendMsgToAdmin(Record record,String openid){
		String payStatus = "00".equals(record.getStr("pay_status"))?"（未支付）":"（已支付）";
		String param="{"+
	        "\"touser\":\""+openid+"\","+
	        "\"template_id\":\"HlxLDimk5FU0SJV3t4pyBRmsVWOdT-ZgAlBaYAprUXY\","+
	        "\"topcolor\":\"#FF0000\","+
	        "\"url\":\""+record.getStr("url")+"\","+
	        "\"data\":{"+
                "\"first\": {\"value\":\"您有一条新的订单"+payStatus+"\",\"color\":\"#173177\"},"+
                "\"tradeDateTime\": {\"value\":\""+record.getDate("order_time")+"\",\"color\":\"#173177\"},"+
                "\"orderType\": {\"value\":\""+record.getStr("sku_name")+"\",\"color\":\"#173177\"},"+
                "\"customerInfo\": {\"value\":\""+record.getStr("linker")+"("+record.getStr("link_mb")+")\",\"color\":\"#173177\"},"+
                "\"orderItemName\": {\"value\":\"订单号\"},"+
                "\"orderItemData\": {\"value\":\""+record.getStr("line_order_no")+"\",\"color\":\"#173177\"},"+
                "\"remark\":{\"value\":\"请及时处理\",\"color\":\"#173177\"}"+
                "}"+
			"}";
		TemplateMsgApi.send(param);
    }
	
	/**
	 * 
	 * @Description: TODO 
	 * @author zhengwei  
	 * @date 2016年12月5日 下午5:06:00
	 * @param Record
	 * @return
	 */
	public static void sendMsgToUser(Record record){
		String param="{"+
				"\"touser\":\""+record.getStr("link_openid")+"\","+
				"\"template_id\":\"HlxLDimk5FU0SJV3t4pyBRmsVWOdT-ZgAlBaYAprUXY\","+
				"\"topcolor\":\"#FF0000\","+
				"\"url\":\""+record.getStr("url")+"\","+
				"\"data\":{"+
					"\"first\": {\"value\":\"预订成功，稍后将有客服与您取得联系\",\"color\":\"#173177\"},"+
					"\"tradeDateTime\": {\"value\":\""+record.getDate("order_time")+"\",\"color\":\"#173177\"},"+
	                "\"orderType\": {\"value\":\""+record.getStr("sku_name")+"\",\"color\":\"#173177\"},"+
	                "\"customerInfo\": {\"value\":\""+record.getStr("linker")+"("+record.getStr("link_mb")+")\",\"color\":\"#173177\"},"+
	                "\"orderItemName\": {\"value\":\"订单号\"},"+
	                "\"orderItemData\": {\"value\":\""+record.getStr("line_order_no")+"\",\"color\":\"#173177\"},"+
					"\"remark\":{\"value\":\"如果有疑问，请联系我们：4001781187\",\"color\":\"#173177\"}"+
				"}"+
				"}";
		TemplateMsgApi.send(param);
	}
}
