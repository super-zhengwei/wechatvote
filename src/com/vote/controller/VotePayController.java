
package com.vote.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.weixin.sdk.api.PaymentApi;
import com.jfinal.weixin.sdk.api.PaymentApi.TradeType;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.vote.interceptor.LoginInterceptor;
import com.vote.model.Vote;
import com.vote.model.VoteData;
import com.vote.model.VoteGift;
import com.vote.model.VotePay;
import com.vote.util.Ret;

@Before(POST.class)
public class VotePayController extends BaseController {
	static Log log = Log.getLog(VotePayController.class);
	/**
	 * @Description: 查询分组 
	 * @author zhengwei  
	 * @date 2017年4月4日 下午1:56:45
	 * 
	 * @return void    
	 */
	@Before({LoginInterceptor.class})
	public void query() {
		Ret ret = new Ret();
		int voteId = getParaToInt("voteId");
		String group = getPara("group");
		String flag = getPara("flag");
		List<Record> list = VotePay.dao.getList(voteId,group,flag);
		ret.setData(list);
		renderJson(ret);
	}
	/**
	 * 
	 * @Description: 赠送礼物 
	 * @author zhengwei  
	 * @date 2017年4月4日 上午12:21:48
	 * 
	 * @return void    
	 */
	public void save(){
		Ret ret = new Ret();
		int voteId=getParaToInt("voteId");
		int itemId=getParaToInt("itemId");
		int giftId=getParaToInt("giftId");
		int giftNum=getParaToInt("giftNum");
		String openid=getPara("openid");
		Vote vote = Vote.dao.findById(voteId);
		if("1".equals(vote.getStr("vote_status"))){
			ret.addError("活动已暂停");
			renderJson(ret);
			return;
		}
		if(!Vote.dao.checkDate(voteId)){
			ret.addError("活动未开始或已结束");
			renderJson(ret);
			return;
		}
		VoteGift gift = VoteGift.dao.findById(giftId);
		VotePay model = new VotePay();
		model.set("openid", openid);
		model.set("vote_id", voteId);
		model.set("item_id", itemId);
		model.set("gift_id", giftId);
		model.set("gift_num", giftNum);
		model.set("vote_num", giftNum*gift.getInt("add_vote_num"));
		model.set("gift_type", gift.get("gift_type"));
		model.set("order_no", System.currentTimeMillis()+"");
		model.set("order_status", "00");
		model.set("total_amt", giftNum*gift.getFloat("gift_price"));
		model.set("create_time", new Date());
		model.save();
		String out_trade_no=model.getStr("order_no");
		String appId=PropKit.get("appId");
		String mchId=PropKit.get("mchId");
		String key=PropKit.get("key");
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appId);
		params.put("mch_id", mchId);
		params.put("body", "赠送礼物");
		params.put("out_trade_no", out_trade_no);
		String price=new BigDecimal(model.getFloat("total_amt")).multiply(new BigDecimal(100)).setScale(0,4).toString();
		params.put("total_fee", price);
		params.put("spbill_create_ip", "127.0.0.1");
		params.put("trade_type", TradeType.JSAPI.name());
		params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
		params.put("notify_url", PropKit.get("siteUrl")+"/votePay/payNotify");
		params.put("openid",openid);
		String sign = PaymentKit.createSign(params, key);
		params.put("sign", sign);
		String xmlResult = PaymentApi.pushOrder(params);
		log.warn(xmlResult);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
			ret.addError(return_msg);
			renderJson(ret);
			return;
		}
		String result_code = result.get("result_code");
		if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
			ret.addError(result.get("err_code_des"));
			renderJson(ret);
			return;
		}
		// 以下字段在return_code 和result_code都为SUCCESS的时候有返回
		String prepay_id = result.get("prepay_id");
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appId", appId);
		packageParams.put("timeStamp", System.currentTimeMillis() / 1000 + "");
		packageParams.put("nonceStr", System.currentTimeMillis() + "");
		packageParams.put("package", "prepay_id=" + prepay_id);
		packageParams.put("signType", "MD5");
		String packageSign = PaymentKit.createSign(packageParams, key);
		packageParams.put("paySign", packageSign);
		ret.setData(packageParams);
		renderJson(ret);
	}
	
	
	/**
	 * @Description: 微信支付--通知 
	 * @author zhengwei  
	 * @date 2016年11月25日 下午10:18:54
	 * 
	 * @return void    
	 */
	public void payNotify() {
		//获取所有的参数
		StringBuffer sbf=new StringBuffer();
		Enumeration<String>  en=getParaNames();
		while (en.hasMoreElements()) {
			Object o= en.nextElement();
			sbf.append(o.toString()+"="+getPara(o.toString()));
		}
		String xmlMsg = HttpKit.readData(getRequest());
		log.error("支付通知参数："+xmlMsg);
		Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
		String total_fee     = params.get("total_fee");
		String transaction_id      = params.get("transaction_id");
		String out_trade_no      = params.get("out_trade_no");
		String time_end      = params.get("time_end");
		String result_code  = params.get("result_code");
		// 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
		// 避免已经成功、关闭、退款的订单被再次更新
		VotePay votePay = VotePay.dao.getByWxOrderNo(transaction_id);
		if (votePay==null) {
			if(PaymentKit.verifyNotify(params, PropKit.get("key"))){
				if (("SUCCESS").equals(result_code)) {
					//更新订单信息
					VotePay model = VotePay.dao.getByOrderNo(out_trade_no);
					model.set("order_status", "10");
					model.set("pay_amt", new BigDecimal(total_fee).divide(new BigDecimal("100")));
					model.set("wx_order_no", transaction_id);
					model.set("pay_time", time_end);
					model.update();
					VoteData voteData = new VoteData();
					voteData.set("openid", model.getStr("openid"));
					voteData.set("vote_id", model.getInt("vote_id"));
					voteData.set("item_id", model.getInt("item_id"));
					voteData.set("pay_id", model.getInt("id"));
					voteData.set("vote_type", model.getStr("gift_type"));
					voteData.set("vote_num", model.getInt("vote_num"));
					voteData.set("create_time", new Date());
					voteData.save();
					//通知微信
					Map<String, String> xml = new HashMap<String, String>();
					xml.put("return_code", "SUCCESS");
					xml.put("return_msg", "OK");
					renderText(PaymentKit.toXml(xml));
				}
			}
		}
	}
}
