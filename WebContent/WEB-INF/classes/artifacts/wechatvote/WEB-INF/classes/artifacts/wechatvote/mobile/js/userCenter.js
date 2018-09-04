var voteId=$.getUrlVars("voteId");
mui.init();
var user;
var data;
/**
 *初始化、添加监听 
 */
mui.ready(function(){
	getUserInfo();
	$('.mui-bar-tab').on('tap', 'a', function(e) {
		location.href=$(this).attr("href")+"?voteId="+voteId;
	});
	$('.mui-grid-view').on('tap', '.grid-box', function(e) {
		if($(this).attr("value")){
			if($(this).attr("value")=="../mobile/baoming.jsp"){
				location.href=$(this).attr("value")+"?voteId="+voteId;
			}else{
				location.href=$(this).attr("value")+"?voteId="+voteId+"&itemId="+data.id;
			}
		}
	});
	$('.search-bar').on('tap', function(e) {
		e.stopPropagation();
		$(".search").show();
		$(".mui-popup-backdrop").show();
	});
	$(".mui-popup-backdrop").on('tap', function(e) {
		$(".search").hide();
		$(".mui-popup-backdrop").hide();
	});
	$("#search-btn").on('tap',function(e){
		var keyword = $.trim($("#search").val());
		if(keyword==""){
			layer.open({content: '请输入选手编号或姓名',time: 3});
			return;
		}
		$(".search").hide();
		$(".mui-popup-backdrop").hide();
		sessionStorage.setItem("keyword",$.trim($("#search").val()));
		location.href="../mobile/index.jsp?voteId="+voteId;
	});
});

//查询用户信息
function getUserInfo(){
	ajaxPost(base+"/users/findByOpenId",{openid:openid},function(json){
		if(json.success){
			user = json.data;
			if(user){
				if(user.username){
					$(".mui-text-center").html(user.username);
				}else{
					$(".mui-text-center").html(user.nickname);
				}
				if(user.headimgurl){
					$(".user-avatar").css("background-image","url("+user.headimgurl+")");
				}
			}
		}else{
			layer.open({content: json.msg,time: 3});
		}
	});
	post(base+"/users/getUserInfo",{openid:openid},function(json){
		if(json.success){
			console.info(json);
			data = json.data;
			if(data.id){
				$("#baoming").addClass("mui-hidden");
				$("#detail").removeClass("mui-hidden");
			}else{
				$("#baoming").removeClass("mui-hidden");
				$("#detail").addClass("mui-hidden");
			}
			$("#voteNum .grid-label").html(data.voteNum);
			$("#giftNum .grid-label").html(data.giftNum);
			$("#sumAmt .grid-label").html(data.sumAmt);
		}else{
			layer.open({content: json.msg,time: 3});
		}
	});
}