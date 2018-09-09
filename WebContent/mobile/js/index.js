var voteId=$.getUrlVars("voteId");
var list;
var pageNum = 1;
var pageSize=10;
var totalPage=0;
var flag="0";
mui.init();
/**
 *初始化、添加监听 
 */
mui.ready(function(){
	$("#itemType").append('<option value="">选择分类</option>');
	$.each(itemType,function(id,item){
		$("#itemType").append('<option value="'+item.dict_key+'">'+item.dict_value+'</option>');
	});
	getVoteInfo();
	getList();
	//getGroup();
	$('.mui-bar-tab').on('tap', 'a', function(e) {
		location.href=$(this).attr("href")+"?voteId="+voteId;
	});
	$('#baoming').on('tap', function(e) {
		location.href="../mobile/baoming.jsp?voteId="+voteId;
	});
	$('#detail').on('tap', function(e) {
		location.href="../mobile/detail.jsp?voteId="+voteId;
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
	$(".control-item").on('tap',function(e) {
		if(!$(this).hasClass("mui-active")){
			$(this).addClass("mui-active");
			$(this).siblings().removeClass("mui-active");
			flag=$(this).index();
			if(flag==1){
				$("#itemType").hide();
			}else{
				$("#itemType").show();
			}
			$(".falls .fl").empty();
			$(".falls .fr").empty();
			pageNum = 1;
			getList();
		}
	});
	$("#loadMore").on('tap','span',function(e){
		getList();
	});
	$("#search-btn").on('tap',function(e){
		var keyword = $.trim($("#search").val());
		if(keyword==""){
			layer.open({content: '请输入选手编号或姓名',time: 3});
			return;
		}
		$(".search").hide();
		$(".mui-popup-backdrop").hide();
		$(".falls .fl").empty();
		$(".falls .fr").empty();
		pageNum = 1;
		getList();
	});
	$(".falls").on('tap','.mui-btn-primary',function(e){
		var itemId=$(this).parents("li").attr("id");
		ajaxPost(base+"/voteData/save",{openid:openid,voteId:voteId,itemId:itemId},function(json){
			console.info(json);
			if(json.success){
				getVoteInfo();
				$("#"+itemId).find(".main-color").html(json.data+"票");
				layer.open({content: "投票成功",time: 3});
			}else{
				if(json.data){
					var btnArray = ['查看选手','赠送礼物'];
					mui.confirm(json.msg, '提示信息', btnArray, function(e) {
						if (e.index == 1) {
							location.href="../mobile/gift.jsp?itemId="+itemId;
						} else {
							location.href="../mobile/itemDetail.jsp?voteId="+voteId+"&itemId="+itemId;
						}
					});
				}else{
					layer.open({content: json.msg,time: 3});
				}
			}
		});
	});
	
	$("#itemType").on('change',function(e){
		pageNum = 1;
		$(".falls .fl").empty();
		$(".falls .fr").empty();
		getList();
	});
});

function getVoteInfo(){
	post(base+"/vote/getVoteInfo",{id:voteId},function(json){
		$("#num1").html(nullToZero(json.data.num1));
		$("#num2").html(nullToZero(json.data.num2));
		$("#num3").html(nullToZero(json.data.num3));
		//$("#num4").html(nullToZero(json.data.num4));
	});
}

function getGroup(){
	post(base+"/voteGroup/queryGroup",{voteId:voteId},function(json){
		$("#itemType").empty();
		$("#itemType").append('<option value="">选择分组</option>');
		if(json.success){
			$.each(json.data,function(id,item){
				$("#itemType").append('<option value="'+item.id+'">'+item.group_name+'</option>');
			});
		}else{
			layer.open({content: json.msg,time: 3});
		}
	});
}

function getList() {
    if(pageNum==1){
        $(".falls .fl").empty();
        $(".falls .fr").empty();
        $(".dropload-down").remove();
    }
    $('.content').dropload({
        scrollArea : window,
        loadDownFn : function(me){
            if(sessionStorage.getItem("keyword")){
                $("#search").val(sessionStorage.getItem("keyword"));
                sessionStorage.removeItem("keyword");
            }
            var keyword = $.trim($("#search").val());
            var itemType = $("#itemType").val();
            $("#search").val("");
            ajaxPost(base+"/voteItem/getList",{
                id:voteId,
                pageNumber:pageNum,
                pageSize:pageSize,
                keyword:keyword,
                itemType:itemType,
                flag:flag
            },function(json){
                if(json.data.list&&json.data.list.length>0){
                    if(pageNum==json.data.totalPage){
                        me.lock();
                        me.noData();
                    }else{
                        pageNum++;
                    }
                    $.each(json.data.list,function(id,item){
                        var html=[];
                        html.push('<li id="'+item.id+'">');
                        html.push('<div class="li_box">');
                        html.push('<a href="../mobile/itemDetail.jsp?voteId='+voteId+'&itemId='+item.id+'" target="_top">');
                        html.push('<img src="'+imageUrl+item.head_img+'">');
                        html.push('</a>');
		//				html.push('<div class="info">');
		//				html.push('<span>'+item.user_code+'号</span>');
		//				html.push('<span class="mui-pull-right">'+item.user_name+'</span>');
		//				html.push('</div>');
                        html.push('<div class="toupiao items">');
                        html.push('<div class="item main-color fs14">'+item.user_code+'号 '+item.user_name+'</div>');
                        html.push('<div class="main-color fs14">'+nullToZero(item.vote_num)+'票</div>');
                        html.push('</div>');
                        html.push('</div>');
                        html.push('</li>');
                        if(id%2==0){
                            $(".falls .fl").append(html.join(""));
                        }else{
                            $(".falls .fr").append(html.join(""));
                        }
                    });
                    if(keyword!=""){
                        pageNum = 1;
                    }
                    me.resetload();
                }else{
                    me.lock();
                    me.noData();
                    me.resetload();
                }
            });
        }
    });

// 	ajaxPost(base+"/voteItem/getList",{
// 			id:voteId,
// 			pageNumber:pageNum,
// 			pageSize:pageSize,
// 			keyword:keyword,
// 			itemType:itemType,
// 			flag:flag
// 		},function(json){
// 		console.info(json);
// 		totalPage=json.data.totalPage;
// 		list=json.data.list;
// 		if(json.data.list&&json.data.list.length>0){
// 			if(keyword!=""){
// 				$("#loadMore").html('<span>查看其它选手</span>');
// 			}else{
// 				if(pageNum>=totalPage){
// 					$("#loadMore").html("没有更多数据了");
// 				}else{
// 					$("#loadMore").html('<span>点击查看更多</span>');
// 				}
// 			}
// 			if(pageNum==1){
// 				$(".falls .fl").empty();
// 				$(".falls .fr").empty();
// 			}
// 			pageNum++;
// 			jQuery.each(json.data.list,function(id,item){
// 				var html=[];
// 				html.push('<li id="'+item.id+'">');
// 				html.push('<div class="li_box">');
// 				html.push('<a href="../mobile/itemDetail.jsp?voteId='+voteId+'&itemId='+item.id+'" target="_top">');
// 				html.push('<img src="'+imageUrl+item.head_img+'">');
// 				html.push('</a>');
// //				html.push('<div class="info">');
// //				html.push('<span>'+item.user_code+'号</span>');
// //				html.push('<span class="mui-pull-right">'+item.user_name+'</span>');
// //				html.push('</div>');
// 				html.push('<div class="toupiao items">');
// 				html.push('<div class="item main-color fs14">'+item.user_code+'号 '+item.user_name+'</div>');
// 				html.push('<div class="main-color fs14">'+nullToZero(item.vote_num)+'票</div>');
// 				html.push('</div>');
// 				html.push('</div>');
// 				html.push('</li>');
// 				if(id%2==0){
// 					$(".falls .fl").append(html.join(""));
// 				}else{
// 					$(".falls .fr").append(html.join(""));
// 				}
// 			});
// 			if(keyword!=""){
// 				pageNum = 1;
// 			}
// 		}else{
// 			$("#loadMore").html("没有更多数据了");
// 		}
// 	});
}