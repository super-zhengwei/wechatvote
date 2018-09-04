var typeId=$.getUrlVars("typeId");
var pageNum = 1;
var pageSize=10;
var totalPage=0;
mui.init({
	pullRefresh: {
		container: '#pullrefresh',
		up: {
			contentrefresh: '正在加载...',
			callback: pullupRefresh
		}
	}
});

mui.ready(function() {
	pullupRefresh();
	$('.mui-table-view').on('tap', '.mui-table-view-cell', function(event) {
		top.location.href=$(this).find("a").attr("href");
	});
});

function nofind(){ 
	var img=event.srcElement; 
	img.src=resourceUrl+"/mobile/image/blank_2.png"; 
	img.onerror=null;
}

/**
 * 上拉加载具体业务实现
 */
function pullupRefresh() {
	query();
}

function query() {
	ajaxPost(base+"/wxMsg/query",{
		msgType:typeId,
		pageNumber:pageNum,
		pageSize:pageSize
	},function(json){
		totalPage=json.totalPage;
		if(json.list&&json.list.length>0){
			if(pageNum>=totalPage){
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
			}else{
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
			}
			pageNum++;
			jQuery.each(json.list,function(id,item){
				var html="";
				html +='<li class="mui-table-view-cell mui-media" id="'+item.id+'">';
				html +='<a href="'+item.msg_url+'">';
				if(item.msg_img){
					html +='<img class="mui-media-object mui-pull-left" src="'+imageUrl+item.msg_img+'" onerror="nofind();">';
				}
				html +='<div class="mui-media-body">';
				html +='<p class="mui-ellipsis-2 color1 fs14">'+nullToSpace(item.msg_title)+'</p>';
				html +='<p class="lh18 fs12">'+nullToSpace(item.create_time)+'</p>';
				html +='</div>';
				html +='</a>';
				html +='</li>';
				jQuery(".mui-table-view").append(html);
			});
		}else{
			mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
			if(pageNum==1){
				$.tip(".mui-content","暂无数据");
			}
		}
	});
}

function getListContent(){
	ajaxPost(ctx+'/wxPubMsgListController/getListContent.do',{listId:listId},function(json){
		if(json.status=="error"){
			return layer.open({content: '请求异常',time: 3});
		}
		if(json.data){
			$(".mui-title").html(json.data.listTitle);
			$("#post-date").html("发布日期："+json.data.publishTimeString+"");
			$("#js_content").html(json.data.listCont);
		}
	});
}

function getAll(){
	ajaxPost(ctx+'/wxPubMsgListController/getAll.do',{},function(json){
		if(json.status=="error"){
			return layer.open({content: '请求异常',time: 3});
		}
		$.each(json,function(id,item){
			$("#type"+item.LIST_TYPE+ " .mui-badge").removeClass("mui-hidden");
			$("#type"+item.LIST_TYPE+ " .mui-badge").html(parseInt($("#type"+item.LIST_TYPE+ " .mui-badge").html())+1);
		});
	});
}