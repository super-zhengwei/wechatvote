var itemId=$.getUrlVars("itemId");
var voteId=$.getUrlVars("voteId");
var data;
var giftList;
var gift;
mui.previewImage();
mui.init();
/**
 *初始化、添加监听 
 */
mui.ready(function(){
	getVoteUser();
	$('.mui-tab-item.l').on('tap', function(e) {
		e.stopPropagation();
		location.href="../mobile/index.jsp?voteId="+voteId;
	});
	$('.mui-tab-item.r').on('tap',function(e) {
		e.stopPropagation();
		location.href="../mobile/rank.jsp?voteId="+voteId;
	});
	$(".vote-user").on("tap","li",function(e) {
		e.stopPropagation();
		location.href="../mobile/userVoteDetail.jsp?voteId="+voteId+"&itemId="+itemId;
	});
	$('.mui-tab-item .toupiao-bar').on('tap',function(e) {
		if(data.status=="00"){
			layer.open({content: "该选手还未审核",time: 3});
			return;
		}
		ajaxPost(base+"/voteData/save",{openid:openid,voteId:data.vote_id,itemId:data.id},function(json){
			console.info(json);
			if(json.success){
				getItemInfo();
				layer.open({content: "投票成功",time: 3});
			}else{
				if(json.data){
					mui.alert(json.msg, '提示信息', function() {
					});
				}else{
					layer.open({content: json.msg,time: 3});
				}
			}
		});
	});
	$(".mui-table").on("tap",".mui-btn",function(e) {
		if(!$(this).hasClass("mui-active")){
			$(this).addClass("mui-active");
			$(this).siblings().removeClass("mui-active");
		}
	});
	$("#gift").on("tap","li",function(e) {
		gift = giftList[$(this).index()];
		$("#giftDetail img").attr("src",imageUrl+gift.gift_img);
		$("#giftPrice").html(gift.gift_price);
		$(".giftName").html(gift.gift_name);
		$("#giftNum").html(10*parseInt(gift.add_vote_num));
		$(".mui-table .mui-btn:first").addClass("mui-active");
		$(".mui-table input").removeClass("mui-active");
		$(".mui-table input").val("");
		$("#giftDetail").show();
		$("#giftBackdrop").show();
	});
	$("#giftBackdrop").on("tap",function(e) {
		$("#giftDetail").hide();
		$("#giftBackdrop").hide();
	});
	$("#giftDetail").on("tap",".mui-popup-button",function(e) {
		if(data.status=="00"){
			layer.open({content: "该选手还未审核",time: 3});
			return;
		}
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
	
	$(".user-name").on("tap","span",function(){
		location.href="../mobile/baoming.jsp?voteId="+voteId+"&itemId="+itemId;
	});
});

function onBridgeReady(json){
	WeixinJSBridge.invoke(
		'getBrandWCPayRequest', 
		json,
		function(res){
			if(res.err_msg == "get_brand_wcpay_request:ok" ) {
				$("#giftDetail").hide();
				$("#giftBackdrop").hide();
				getItemInfo();
				layer.open({content: '赠送成功',time: 3});
			}else{
				layer.open({content: '赠送失败',time: 3});
			}
		}
	); 
}
function getItem(){
	ajaxPost(base+"/voteItem/getItemById",{id:itemId},function(json){
		data=json.data;
		$(".index-head").css("background-image","url("+imageUrl+data.list[0].item_img+")");
		$(".user-avatar").css("background-image","url("+imageUrl+data.head_img+")");
		share.title=data.user_name+"正在参加"+data.vote_name+",快来为"+data.user_name+"投票吧！";
		share.imgUrl=imageUrl+data.pic1;
		if(data.openid==openid){
			$(".user-name").html(data.user_name+'<span class="pl10"><i class="mui-icon iconfont icon-bianji2 fs16"></i> 编辑</span>');
		}else{
			$(".user-name").html(data.user_name);
		}
		if(data.status=="00"){
			$(".status").html('待审核');
		}else{
			$(".status").html('已审核');
		}
		if(data.group_id==1){
			$(".type-name").html(getTypeName(data.item_type));
		}
		$(".group-name").html(data.group_name);
		$(".remark").html(data.remark);
		$(document).attr("title",data.user_name+"正在参加"+data.vote_name+",快来为"+data.user_name+"投票吧！");
		$("#userCode").html(data.user_code);
		$.each(data.list,function(id,item){
			$("#thelist1").append('<div class="image-item"><img data-preview-src="" data-preview-group="1"'+
					'src="'+imageUrl+item.item_img+'">'+
	               	   '</div>');
		});
		getItemInfo();
		//getGift();
	});
}
//查询礼物
function getGift(){
	ajaxPost(base+"/voteGift/getGiftList",{voteId:data.vote_id},function(json){
		console.info(json);
		giftList=json.data;
		if(giftList&&giftList.length>0){
			var html = [];
			$.each(giftList,function(id,item){
				html.push('<li class="mui-table-view-cell mui-media" id="2">');
				html.push('<a href="../mobile/msgDetail.jsp?id=2">');
				html.push('<img class="mui-media-object mui-pull-left" src="'+imageUrl+item.gift_img+'">');
				html.push('<div class="mui-media-body items" style="height:60px;">');
				var str="";
				if(item.remark){
					str=item.remark;
				}else{
					str=item.gift_name;
				}
				html.push('<p class="mui-ellipsis-2 color1 fs14">'+str+'</p>');
				html.push('</div>');
				html.push('</a>');
				html.push('</li>');
			});
			$("#gift").html(html.join(""));
		}
	});
}
function getItemInfo(){
	post(base+"/voteItem/getItemInfo",{itemId:itemId},function(json){
		$("#total_num").html(nullToZero(json.data.total_num));
		$("#log_num").html(nullToZero(json.data.log_num));
		$("#git_num").html(nullToZero(json.data.git_num));
		if(json.data.num4==1){
			$("#itemInfo").html(data.user_name+'当前排名：<span class="fs24">'+nullToZero(json.data.num4)+'</span><br>'+
					'与第二名相差<span class="fs24">'+nullToZero(json.data.num6)+'</span>票，继续保持吧！');
		}else{
			$("#itemInfo").html(data.user_name+'当前排名：<span class="fs24">'+nullToZero(json.data.num4)+'</span><br>'+
			'与前一名相差<span class="fs24">'+nullToZero(json.data.num5)+'</span>票 | 与后一名相差<span class="fs24">'+nullToZero(json.data.num6)+'</span>票');
		}
	});
}
function getVoteUser(){
	post(base+"/voteData/getVoteUser",{itemId:itemId},function(json){
		if(json.data){
			$.each(json.data,function(id,item){
				if(item.headimgurl){
					$('.vote-user .mui-media').append('<img class="mui-media-object mui-pull-left avatar-bg" src="'+item.headimgurl+'">');
				}
			});
		}
	});
}
function num(obj){
	if(obj.value.length==1){
		obj.value=obj.value.replace(/[^1-9]/g,'');
	}else{
		obj.value=obj.value.replace(/\D/g,'');
    }
	$("#giftNum").html(parseInt(obj.value)*parseInt(gift.add_vote_num));
}