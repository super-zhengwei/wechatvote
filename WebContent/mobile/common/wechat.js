var share;
var vote;
var itemType=[];
mui.init()
$(function(){
	if(window.history.length<2){
		$(".mui-action-back").hide();
	}
	queryVote();
	addLog();
	getTicket();
});

function getTypeName(typeCode){
	var name = "";
	$.each(itemType,function(id,item){
		if(item.dict_key==typeCode){
			name = item.dict_value;
		}
	});
	return name;
}

function queryVote(){
	if(!$.getUrlVars("voteId")){
		return;
	}
	if(sessionStorage.getItem("vote")){
		vote = JSON.parse(sessionStorage.getItem("vote"));
		initVote();
	}else{
		post(base+"/vote/queryVote",{id:$.getUrlVars("voteId")},function(json){
			if(json.data){
				vote = json.data;
				sessionStorage.setItem("vote", JSON.stringify(json.data));
				initVote();
			}
		});
	}
}

function initVote(){
	share = new Object();
	share.title=$(document).attr("title");
	share.link=location.href;
	share.desc=$('meta[name="description"]').eq(0).attr('content');
	share.imgUrl=imageUrl+vote.vote_img;
	var pathname = window.document.location.pathname;
	if(pathname.indexOf("index.jsp")!=-1){
		$(".index-head").css("background-image","url("+imageUrl+vote.vote_img+")");
		$("#sjVal").html(vote.start_date+"至"+vote.end_date);
		$(".mui-content").show();
		$(".mui-bar-tab").show();
	}else if (pathname.indexOf("rank.jsp")!=-1){
		$(".index-head").css("background-image","url("+imageUrl+vote.vote_img+")");
	}else if(pathname.indexOf("itemDetail.jsp")!=-1){
		getItem();
	}
	if($(document).attr("title")){
		$(document).attr("title",vote.vote_name+"-"+$(document).attr("title"));
	}else{
		$(document).attr("title",vote.vote_name);
	}
}
//添加访问日志
function addLog(){
	if(!$.getUrlVars("voteId")){
		return;
	}
	ajaxPost(base+"/voteLog/save",{openid:openid,voteId:$.getUrlVars("voteId"),
		itemId:$.getUrlVars("itemId")},function(json){});
}
//获取微信js参数
function getTicket(){
	post(base+"/weixin/getTicket",{url:window.location.href},function(data){
		wx.config({
			  debug: false,
		      appId:data.appId,
		      timestamp: data.timestamp,
		      nonceStr: data.nonceStr,
		      signature: data.signature,
		      jsApiList: [
		        'onMenuShareTimeline',
		        'onMenuShareAppMessage',
		      ]
		  });
	});
}
//分享
wx.ready(function(){
	wx.onMenuShareTimeline({
	    title: share.title, // 分享标题
	    link: share.link, // 分享链接
	    imgUrl: share.imgUrl, // 分享图标
	    success: function () { 
	    	if(typeof share.callback=='function'){
	    		share.callback();
	    	}
	    },
	    cancel: function () { 
	    	alert('取消分享');
	    }
	});
	
	wx.onMenuShareAppMessage({
	    title: share.title, // 分享标题
	    desc: share.desc, // 分享描述
	    link: share.link, // 分享链接
	    imgUrl: share.imgUrl, // 分享图标
	    type: 'link', // 分享类型,music、video或link，不填默认为link
	    success: function () {
	    	if(typeof share.callback=='function'){
	    		share.callback();
	    	}
	    },
	    cancel: function () {
	    	alert('取消分享');
	    }
	});
});

function nullToSpace(param){
	if(param == null || typeof param == 'undefined'){
		param = '';
	}
	return param;
}

function nullToZero(param) {
	if(param == null || typeof param == 'undefined'){
		param = 0;
	}
	return param;
}

function ajaxPost(url,params,callback,msg){
	var index=null;
	if(!msg){
		msg="加载中...";
	}
	$.ajax({
		type:"POST",
		url:url,
		data:params,
		dataType:"json",
		timeout:20000,
		beforeSend:function(){
			index=layer.open({type: 3,shadeClose:false,content: msg});
		},
		success:function(data){
			layer.close(index);
			if(typeof callback=='function'){
				callback(data);
			}
		},
		error:function(){
			layer.close(index);
			layer.open({content: '请求失败',time: 3});
		},
		complete: function (XHR, TS) { XHR = null; } 
	});
}

function post(url,params,callback){
	$.ajax({
		type:"POST",
		url:url,
		data:params,
		dataType:"json",
		timeout:20000,
		success:function(data){
			if(typeof callback=='function'){
				callback(data);
			}
		},
		error:function(){
			layer.open({content: '请求失败',time: 3});
		},
		complete: function (XHR, TS) { XHR = null; } 
	});
}

Array.prototype.elremove = function(m){  
	 if(isNaN(m)||m>this.length){return false;}  
	 this.splice(m,1);  
}

function toDecimal(x) { 
    var f = parseFloat(x); 
    if (isNaN(f)) { 
       return; 
    } 
    f = Math.round(x*100)/100; 
    return f; 
}

Date.prototype.format = function(fmt){ 
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}

function openUrl(url){
	if(!url){
		return;
	}
	if(url.indexOf("http")!='-1'){
		self.location.href=url;
	}else{
		self.location.href=$.getPath()+url;
	}
}

$.extend({
	getUrlVars :function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if(r==null){
			r = window.parent.location.search.substr(1).match(reg);
		}
		if (r!=null) return unescape(r[2]); return null;
	}
});

$.extend({
	// 判断是否使用微信打开
	openInWx:function(){
		document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
			WeixinJSBridge.call('hideToolbar');
			if(typeof  hideOptionMenu == "undefined"){
				WeixinJSBridge.call('hideOptionMenu');
			}
		});
		var ua = navigator.userAgent.toLowerCase();
		if(ua.match(/MicroMessenger/i) == 'micromessenger') {
			return true;
		} else {
//			$("body").hide();
//			alert("请使用微信内置浏览器访问");
//			self.location = "../commons/404.html";
			return false;
		}
	}
});

$.extend({ 
	tip:function(obj,msg,icon,top){
		if(!top){
			top="50%"
		}
		$(""+obj+"").append('<div class="center" style="top:'+top+'">'+
				'<div class="mui-icon iconfont icon-sousuo fs44 mb10"></div><div>'+msg+'</div></div>');
	}
});

function addDate(date,days){
	var a = new Date(date)
	a = a.valueOf();
	a = a + days * 24 * 60 * 60 * 1000
	a = new Date(a)
	return a;
}


function checkMobile(phone){
	var reg= /(^1[3|4|5|7|8][0-9]{9}$)/;
	return reg.test(phone);
}

function IdentityCodeValid(code) { 
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
    return reg.test(code)
}

Array.prototype.removeAt=function(Index){
	if(isNaN(Index)||Index>this.length){return false;}
	for(var i=0,n=0;i<this.length;i++){
    	if(this[i]!=this[Index]){
    		this[n++]=this[i]
    	}
   }
   this.length-=1
}