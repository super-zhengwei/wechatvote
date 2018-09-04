var voteId=$.getUrlVars("voteId");
var itemId=$.getUrlVars("itemId");
var pageNum = 1;
var pageSize=10;
mui.init({
	pullRefresh: {
		container: '#pullrefresh',
		up: {
			contentrefresh: '正在加载...',
			callback: getVoteDetail
		}
	}
});
/**
 *初始化、添加监听 
 */
mui.ready(function(){
	getVoteDetail();
});

//查询用户信息
function getVoteDetail(){
	ajaxPost(base+"/voteData/getVoteDetail",{itemId:itemId,pageNumber:pageNum,pageSize:pageSize},function(json){
		console.info(json);
		if(json.data.list&&json.data.list.length>0){
			if(pageNum>=json.data.totalPage){
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
			}else{
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
			}
			pageNum++;
			var html=[];
			$.each(json.data.list,function(id,item){
				if(!item.headimgurl){
					item.headimgurl='../styles/images/avatar.png';
				}
				html.push('<li class="mui-table-view-cell mui-media">');
				html.push('<img class="mui-media-object mui-pull-left avatar-bg" src="'+item.headimgurl+'">');
				html.push('<div class="mui-media-body">');
				var name=nullToSpace(item.nickname);
				if(item.vote_type=="10"){
					name+="点赞加人气";
				}else{
					name+="赠送"+item.gift_num+"个"+item.gift_name+"";
				}
				html.push('<p class="items color1"><span class="item">'+name+'</span>');
				html.push('<span class="color-yellow fs18">+'+item.vote_num+'</span></p>');
				html.push('<p class="mui-ellipsis fs12">'+item.create_time+'</p>');
				html.push('</div>');
				html.push('</li>');
			});
			$('.mui-table-view').append(html.join(""));
		}else{
			$.tip('.mui-content','暂无数据')
		}
	});
}