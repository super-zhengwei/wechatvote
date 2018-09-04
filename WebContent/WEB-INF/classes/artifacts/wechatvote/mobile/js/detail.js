var id=$.getUrlVars("voteId");
var data;
mui.init();
/**
 *初始化、添加监听 
 */
mui.ready(function(){
	queryVote();
});

function queryVote(){
	ajaxPost(base+"/vote/queryVote",{id:id},function(json){
		data=json.data;
		$(document).attr("title",data.vote_name);
		$(".mui-content-padded").html(data.vote_detail);
	});
}