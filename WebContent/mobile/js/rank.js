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
	$(".control-item").on('tap',function(e) {
		if(!$(this).hasClass("mui-active")){
			$(this).addClass("mui-active");
			$(this).siblings().removeClass("mui-active");
			flag=$(this).index();
			// if(flag==1){
			// 	$("#itemType").hide();
			// }else{
			// 	$("#itemType").show();
			// }
			getList();
		}
	});
	$("#itemType").on('change',function(e) {
		getList();
	});
	$("#list").on('tap','li',function(e) {
		var item = list[$(this).index()];
		location.href="../mobile/itemDetail.jsp?voteId="+voteId+"&itemId="+item.id;
	});
});


function getList() {
	var itemType = $("#itemType").val();
	ajaxPost(base+"/voteItem/getItemList",{voteId:voteId,itemType:itemType,flag:flag},function(json){
		console.info(json);
		list=json.data;
		if(list&&list.length>0){
			var html=[];
			jQuery.each(list,function(id,item){
				html.push('<li class="mui-table-view-cell">');
				html.push('<div class="mui-table">');
				if(id==0){
					html.push('<div class="mui-text-center mui-table-cell mui-col-xs-2"><div class="one"></div></div>');
				}else if(id==1){
					html.push('<div class="mui-text-center mui-table-cell mui-col-xs-2"><div class="two"></div></div>');
				}else if(id==2){
					html.push('<div class="mui-text-center mui-table-cell mui-col-xs-2"><div class="three"></div></div>');
				}else{
					html.push('<div class="mui-text-center mui-table-cell mui-col-xs-2"><span class="badge">'+(id+1)+'</span></div>');
				}
				if(flag=="0"){
					html.push('<div class="mui-text-center mui-table-cell mui-col-xs-4">'+item.user_code+"号—"+item.user_name+'</div>');
				}else{
					html.push('<div class="mui-text-center mui-table-cell mui-col-xs-4">'+item.user_name+'</div>');
				}
				html.push('<div class="mui-text-center mui-table-cell mui-col-xs-3">'+item.log_num+'</div>');
				html.push('<div class="mui-text-center mui-table-cell mui-col-xs-3">'+item.total_num+'</div>');
				html.push('</div>');
				html.push('</li>');
			});
			$("#list").html(html.join(""));
		}else{
			$("#list").empty();
		}
	});
}

function getGroup(){
	post(base+"/voteGroup/queryGroup",{voteId:voteId},function(json){
		$("#group").empty();
		$("#group").append('<option value="">选择分组</option>');
		if(json.success){
			$.each(json.data,function(id,item){
				$("#group").append('<option value="'+item.id+'">'+item.group_name+'</option>');
			});
		}else{
			layer.open({content: json.msg,time: 3});
		}
	});
}

function getVoteInfo(){
	post(base+"/vote/getVoteInfo",{id:voteId},function(json){
		$("#num1").html(nullToZero(json.data.num1));
		$("#num2").html(nullToZero(json.data.num2));
		$("#num3").html(nullToZero(json.data.num3));
	});
}