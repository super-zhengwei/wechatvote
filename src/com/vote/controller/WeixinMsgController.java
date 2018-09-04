package com.vote.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.sdk.jfinal.MsgControllerAdapter;
import com.jfinal.weixin.sdk.msg.in.InImageMsg;
import com.jfinal.weixin.sdk.msg.in.InLinkMsg;
import com.jfinal.weixin.sdk.msg.in.InLocationMsg;
import com.jfinal.weixin.sdk.msg.in.InShortVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.InVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InVoiceMsg;
import com.jfinal.weixin.sdk.msg.in.event.InCustomEvent;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InLocationEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMassEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.in.event.InQrCodeEvent;
import com.jfinal.weixin.sdk.msg.in.event.InTemplateMsgEvent;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.News;
import com.jfinal.weixin.sdk.msg.out.OutCustomMsg;
import com.jfinal.weixin.sdk.msg.out.OutNewsMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;
import com.jfinal.weixin.sdk.msg.out.OutVoiceMsg;
import com.vote.model.UserScanLog;
import com.vote.model.Users;
import com.vote.model.WxMenuMsg;
import com.vote.model.WxReply;
import com.vote.model.WxReplyMsg;
import com.vote.model.WxUserMsg;
import com.vote.util.Constants;
import com.vote.util.Utility;

/**
 * 将此 DemoController 在YourJFinalConfig 中注册路由，
 * 并设置好weixin开发者中心的 URL 与 token ，使 URL 指向该
 * DemoController 继承自父类 WeixinController 的 index
 * 方法即可直接运行看效果，在此基础之上修改相关的方法即可进行实际项目开发
 */
public class WeixinMsgController extends MsgControllerAdapter {
	static Log logger = Log.getLog(WeixinMsgController.class);

	protected void processInTextMsg(InTextMsg inTextMsg)
	{
		String msgContent = inTextMsg.getContent().trim();
		//////////////记录用户留言///////////////
		WxUserMsg userMsg = new WxUserMsg();
		userMsg.set("openId", inTextMsg.getFromUserName());
		userMsg.set("content", msgContent);
		userMsg.set("createTime", new Date());
		userMsg.save();
		/////////////////////////////////////
		List<Record> list = WxReply.dao.queryReply(msgContent);
		if(list!=null&&list.size()>0){
			for(Record record:list){
				if("1".equals(record.getStr("rm_type"))){//文本消息
					OutTextMsg outMsg = new OutTextMsg(inTextMsg);
					outMsg.setContent(record.getStr("rm_content"));
					render(outMsg);
				}else{//图文消息
					List<Record> ls = WxReplyMsg.dao.getMsgByReplyId(record.getInt("id"));
					OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
					List<News> news = new ArrayList<News>();
					for(Record model:ls){
						News item = new News();
						item.setDescription(model.getStr("msg_description"));
						item.setPicUrl(Constants.getImageUrl(model.getStr("msg_img")));
						item.setTitle(model.getStr("msg_title"));
						item.setUrl(Constants.getUrl(getRequest(),model.getStr("msg_url")));
						news.add(item);
					}
					outMsg.addNews(news);
					render(outMsg);
				}
			}
		}else{
			renderOutTextMsg("你发的内容为："+msgContent);
			//转发给多客服PC客户端
//			OutCustomMsg outCustomMsg = new OutCustomMsg(inTextMsg);
//			render(outCustomMsg);
		}
		
	}

	@Override
	protected void processInVoiceMsg(InVoiceMsg inVoiceMsg)
	{
		//转发给多客服PC客户端
//		OutCustomMsg outCustomMsg = new OutCustomMsg(inVoiceMsg);
//		render(outCustomMsg);
		OutVoiceMsg outMsg = new OutVoiceMsg(inVoiceMsg);
		// 将刚发过来的语音再发回去
		outMsg.setMediaId(inVoiceMsg.getMediaId());
		render(outMsg);
	}

	@Override
	protected void processInVideoMsg(InVideoMsg inVideoMsg)
	{
		/*
		 * 腾讯 api 有 bug，无法回复视频消息，暂时回复文本消息代码测试 OutVideoMsg outMsg = new
		 * OutVideoMsg(inVideoMsg); outMsg.setTitle("OutVideoMsg 发送");
		 * outMsg.setDescription("刚刚发来的视频再发回去"); // 将刚发过来的视频再发回去，经测试证明是腾讯官方的 api
		 * 有 bug，待 api bug 却除后再试 outMsg.setMediaId(inVideoMsg.getMediaId());
		 * render(outMsg);
		 */
		OutTextMsg outMsg = new OutTextMsg(inVideoMsg);
		outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inVideoMsg.getMediaId());
		render(outMsg);
	}

	@Override
	protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg)
	{
		OutTextMsg outMsg = new OutTextMsg(inShortVideoMsg);
		outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inShortVideoMsg.getMediaId());
		render(outMsg);
	}

	@Override
	protected void processInLocationMsg(InLocationMsg inLocationMsg)
	{
		OutTextMsg outMsg = new OutTextMsg(inLocationMsg);
		outMsg.setContent("已收到地理位置消息:" + "\nlocation_X = " + inLocationMsg.getLocation_X() + "\nlocation_Y = "
				+ inLocationMsg.getLocation_Y() + "\nscale = " + inLocationMsg.getScale() + "\nlabel = "
				+ inLocationMsg.getLabel());
		render(outMsg);
	}

	@Override
	protected void processInLinkMsg(InLinkMsg inLinkMsg)
	{
		//转发给多客服PC客户端
		OutCustomMsg outCustomMsg = new OutCustomMsg(inLinkMsg);
		render(outCustomMsg);
	}

	@Override
	protected void processInCustomEvent(InCustomEvent inCustomEvent)
	{
		logger.debug("测试方法：processInCustomEvent()");
		renderNull();
	}

	protected void processInImageMsg(InImageMsg inImageMsg)
	{
		//转发给多客服PC客户端
		OutCustomMsg outCustomMsg = new OutCustomMsg(inImageMsg);
		render(outCustomMsg);
	}

	/**
	 * 实现父类抽方法，处理关注/取消关注消息
	 */
	protected void processInFollowEvent(InFollowEvent inFollowEvent)
	{
		if (InFollowEvent.EVENT_INFOLLOW_SUBSCRIBE.equals(inFollowEvent.getEvent()))
		{
			ApiResult result = UserApi.getUserInfo(inFollowEvent.getFromUserName());
			Users.dao.save(result.toString());
			Cookie userCookie=new Cookie("openid",inFollowEvent.getFromUserName());
            userCookie.setMaxAge(-1);
            userCookie.setPath("/");
            getResponse().addCookie(userCookie);
			OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
			String content  = "欢迎关注我们官方微信。";
			Record record = WxReply.dao.getFollow();
			if(record!=null&&!Utility.isEmpty(record.getStr("rm_content"))){
				content=record.getStr("rm_content");
			}
			outMsg.setContent(content);
			render(outMsg);
		}
		// 如果为取消关注事件，将无法接收到传回的信息
		if (InFollowEvent.EVENT_INFOLLOW_UNSUBSCRIBE.equals(inFollowEvent.getEvent()))
		{
			Users.dao.unsubscribe(inFollowEvent.getFromUserName());
			logger.debug("取消关注：" + inFollowEvent.getFromUserName());
		}
	}

	@Override
	protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent)
	{
		System.out.println("扫码.......");
		if (InQrCodeEvent.EVENT_INQRCODE_SUBSCRIBE.equals(inQrCodeEvent.getEvent()))
		{
			logger.debug("扫码未关注：" + inQrCodeEvent.getFromUserName());
			//扫描记录保存 start
			UserScanLog scanLog = new UserScanLog();
			scanLog.set("openId", inQrCodeEvent.getFromUserName());
			String eventKey = inQrCodeEvent.getEventKey();
			String disNumStr = eventKey==null?null:eventKey.substring(8);
			int disNum = disNumStr==null?0:Integer.parseInt(disNumStr);
//			scanLog.set("openId", inQrCodeEvent.getEventKey());
			scanLog.set("scanTime", new Date());
			if(disNum>0)
				scanLog.set("paraNum", disNum);
			scanLog.save();
			//扫描记录保存 end 
			OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
			outMsg.setContent("欢迎关注我们官方微信。");
			render(outMsg);
		}
		if (InQrCodeEvent.EVENT_INQRCODE_SCAN.equals(inQrCodeEvent.getEvent()))
		{
			logger.debug("扫码已关注：" + inQrCodeEvent.getFromUserName());
			//扫描记录保存 start
			UserScanLog scanLog = new UserScanLog();
			scanLog.set("openId", inQrCodeEvent.getFromUserName());
			String eventKey = inQrCodeEvent.getEventKey();
			String disNumStr = eventKey==null?null:eventKey;
			int disNum = disNumStr==null?0:Integer.parseInt(disNumStr);
//			scanLog.set("openId", inQrCodeEvent.getEventKey());
			scanLog.set("scanTime", new Date());
			if(disNum>0)
				scanLog.set("paraNum", disNum);
			scanLog.save();
			//扫描记录保存 end 
			
			renderOutTextMsg("您曾经关注过小美了，请问还有需要服务的吗？");
		}
		
	}

	@Override
	protected void processInLocationEvent(InLocationEvent inLocationEvent)
	{
		logger.debug("发送地理位置事件：" + inLocationEvent.getFromUserName());
		OutTextMsg outMsg = new OutTextMsg(inLocationEvent);
		outMsg.setContent("地理位置是：" + inLocationEvent.getLatitude());
		render(outMsg);
	}

	@Override
	protected void processInMassEvent(InMassEvent inMassEvent)
	{
		logger.debug("测试方法：processInMassEvent()");
		renderNull();
	}

	/**
	 * 实现父类抽方法，处理自定义菜单事件
	 */
	protected void processInMenuEvent(InMenuEvent inMenuEvent)
	{
		OutNewsMsg outMsg = new OutNewsMsg(inMenuEvent);
		String key = inMenuEvent.getEventKey();
		List<Record> list = WxMenuMsg.dao.getMsgByKey(key);
		List<News> news = new ArrayList<News>();
		for(Record record:list){
			News item = new News();
			item.setDescription(record.getStr("msg_description"));
			item.setPicUrl(Constants.getImageUrl(record.getStr("msg_img")));
			item.setTitle(record.getStr("msg_title"));
			item.setUrl(Constants.getUrl(getRequest(),record.getStr("msg_url")));
			news.add(item);
		}
		outMsg.addNews(news);
		render(outMsg);
	}

	@Override
	protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults)
	{
		logger.debug("语音识别事件：" + inSpeechRecognitionResults.getFromUserName());
		OutTextMsg outMsg = new OutTextMsg(inSpeechRecognitionResults);
		outMsg.setContent("语音识别内容是：" + inSpeechRecognitionResults.getRecognition());
		render(outMsg);
	}

	@Override
	protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent)
	{
		logger.debug("测试方法：processInTemplateMsgEvent()");
		renderNull();
	}


}






