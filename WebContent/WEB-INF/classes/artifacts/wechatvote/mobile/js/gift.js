var itemId=$.getUrlVars("itemId");
mui.init();
var data;
var list;
/**
 *初始化、添加监听 
 */
mui.ready(function(){
	getItem();
	$(".mui-table-view").on("tap",".gift",function(e){
		e.stopPropagation();
		$(this).addClass("gift-border");
		$(this).parents("li").siblings().find(".gift").removeClass("gift-border");
	});
	$(".mui-table").on("tap",".mui-btn",function(e) {
		if(!$(this).hasClass("mui-active")){
			$(this).addClass("mui-active");
			$(this).siblings().removeClass("mui-active");
		}
	});
	$(".mui-btn-primary").on("tap",function(e) {
		if(data.status=="00"){
			layer.open({content: "该选手还未审核",time: 3});
			return;
		}
		var gift = list[$(".gift-border").parents("li").index()];
		var giftNum;
		if($(".mui-table .mui-active").html()!=""){
			giftNum=$(".mui-table .mui-active").html();
		}else if($(".mui-table .mui-active").val()!=""){
			giftNum=$(".mui-table .mui-active").val();
		}else{
			return layer.open({content: "请输入礼物数量",time: 3});
		}
		ajaxPost(base+"/votePay/save",{
			voteId:data.vote_id,
			itemId:itemId,
			giftId:gift.id,
			giftNum:giftNum,
			openid:openid
			},function(json){
				if(json.success){
					var data=json.data;
					if (typeof WeixinJSBridge == "undefined"){
		    			if( document.addEventListener ){
		    				document.addEventListener('WeixinJSBridgeReady', onBridgeReady(data), false);
		    			}else if (document.attachEvent){
		    				document.attachEvent('WeixinJSBridgeReady', onBridgeReady(data)); 
		    				document.attachEvent('onWeixinJSBridgeReady', onBridgeReady(data));
		    			}
		    		}else{
		    			onBridgeReady(data);
		    		}
				}else{
					layer.open({content: json.msg,time: 3});
				}
		});
	});
});

function onBridgeReady(json){
	WeixinJSBridge.invoke(
		'getBrandWCPayRequest', 
		json,
		function(res){
			if(res.err_msg == "get_brand_wcpay_request:ok" ) {
				layer.open({content: '赠送成功',time: 3});
				setTimeout(function(){
					location.href="../mobile/itemDetail.jsp?voteId="+voteId+"&itemId="+itemId;
				},2000)
			}else{
				layer.open({content: '赠送失败',time: 3});
			}
		}
	); 
}
//查询选手
function getItem(){
	ajaxPost(base+"/voteItem/getItemById",{id:itemId},function(json){
		console.info(json);
		data=json.data;
		if(data.headimgurl){
			$(".user-avatar").css("background-image","url("+data.headimgurl+")");
		}else{
			$(".user-avatar").css("background-image","url("+imageUrl+data.pic1+")");
		}
		$(".mui-text-center").html("送给 "+data.user_name);
		$(document).attr("title",data.vote_name);
		getGift();
	});
}
//查询礼物
function getGift(){
	ajaxPost(base+"/voteGift/getGiftList",{voteId:data.vote_id},function(json){
		console.info(json);
		list=json.data;
		if(list&&list.length>0){
			var html = [];
			$.each(list,function(id,item){
				html.push('<li class="mui-table-view-cell mui-media mui-col-xs-4">');
				html.push('<a href="#">');
				if(id==0){
					html.push('<div class="gift gift-border">');
				}else{
					html.push('<div class="gift">');
				}
				html.push('<div class="gift-num">+'+item.add_vote_num+item.gift_name+'</div>');
				html.push('<img src="'+imageUrl+item.gift_img+'">');
				html.push('<div class="gift-label">'+item.gift_name+' ¥'+item.gift_price+'</div>');
				html.push('</div>');
				html.push('</a>');
				html.push('</li>');
			});
			$(".mui-table-view").html(html.join(""));
		}else{
			$(".mui-table-view").css("padding","0");
			$.tip(".mui-content","暂无礼物","","60%");
			$(".mui-btn-primary").hide();
			$(".bgcolor1").hide();
		}
	});
}